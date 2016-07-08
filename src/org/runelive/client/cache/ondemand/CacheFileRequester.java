package org.runelive.client.cache.ondemand;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;
import java.util.zip.CRC32;
import java.util.zip.GZIPInputStream;

import org.runelive.Configuration;
import org.runelive.client.Client;
import org.runelive.client.Signlink;
import org.runelive.client.cache.Archive;
import org.runelive.client.cache.CacheIndex;
import org.runelive.client.cache.node.Deque;
import org.runelive.client.cache.node.NodeSub;
import org.runelive.client.cache.node.NodeSubList;
import org.runelive.client.io.ByteBuffer;

public final class CacheFileRequester implements Runnable {

	public void dump() {
		int exceptions = 0;
		for (int element : mapIndices2) {
			try {
				byte abyte[] = clientInstance.cacheIndices[4].get(element);
				File map = new File(Signlink.getCacheDirectory() + "/mapdata/" + element + ".gz");
				FileOutputStream fos = new FileOutputStream(map);
				fos.write(abyte);
				fos.close();
			} catch (Exception e) {
				// e.printStackTrace();
				exceptions++;
			}
		}
		for (int element : mapIndices3) {
			try {
				byte abyte[] = clientInstance.cacheIndices[4].get(element);
				File map = new File(Signlink.getCacheDirectory() + "/mapdata/" + element + ".gz");
				FileOutputStream fos = new FileOutputStream(map);
				fos.write(abyte);
				fos.close();
			} catch (Exception e) {
				e.printStackTrace();
				exceptions++;
			}
		}

		System.out.println("dumped maps with " + exceptions + " exceptions.");
	}

	/**
	 * Grabs the version of a file from the cache.
	 *
	 * @param type
	 *            The type of file (0 = model, 1 = anim, 2 = midi, 3 = map).
	 * @param id
	 *            The id of the file.
	 * @return
	 */
	public int getVersion(int type, int id) {
		int version = 1;
		byte[] data = clientInstance.cacheIndices[type + 1].get(id);
		if (data != null) {
			int length = data.length - 2;
			version = ((data[length] & 0xff) << 8) + (data[length + 1] & 0xff);
		}
		return version;
	}

	private int totalFiles;
	private final Deque requested;
	private int maxPriority;
	public String statusString;
	private int writeLoopCycle;
	private long openSocketTime;
	public int[] mapIndices3;
	private final CRC32 crc32;
	private final byte[] inputBuffer;
	private final byte[][] fileStatus;
	private Client clientInstance;
	private final Deque extraFilesList;
	private int completedSize;
	private int currentFileSize;
	private int[] anIntArray1348;
	public int[] mapIndices2;
	private int filesLoaded;
	private boolean running;
	private OutputStream out;
	private int[] mapIndices4;
	private boolean expectingData;
	private final Deque completed;
	private final byte[] inflationBuffer;
	private final NodeSubList remainingMandatory;
	private InputStream in;
	private Socket socket;
	private final int[][] checksums;
	private int incompletedCount;
	private int completedCount;
	private final Deque toRequest;
	private CacheFileRequest current;
	private final Deque mandatory;
	private int[] mapIndices1;
	private int connectionIdleTicks;
	private byte[] modelIndices;
	public int errorCount;

	public PriorityRequestHandler getPriorityHandler() {
		return priorityRequestHandler;
	}

	private PriorityRequestHandler priorityRequestHandler;

	public CacheFileRequester() {
		requested = new Deque();
		statusString = "";
		crc32 = new CRC32();
		inputBuffer = new byte[500];
		fileStatus = new byte[6][];
		extraFilesList = new Deque();
		running = true;
		expectingData = false;
		completed = new Deque();
		inflationBuffer = new byte[9999999];
		remainingMandatory = new NodeSubList();
		checksums = new int[6][];
		toRequest = new Deque();
		mandatory = new Deque();
		this.priorityRequestHandler = new PriorityRequestHandler(this);
	}

	private void checkReceived() {
		CacheFileRequest request;
		synchronized (mandatory) {
			request = (CacheFileRequest) mandatory.popFront();
		}
		while (request != null) {
			expectingData = true;
			byte data[] = null;
			if (clientInstance.cacheIndices[0] != null) {
				data = clientInstance.cacheIndices[request.getIndex() + 1].get(request.getId());
			}
			if (Configuration.FILE_SERVER_ENABLED) {
				if (request.getIndex() >= 0 && request.getIndex() <= Configuration.CACHE_INDEX_COUNT
						&& checksums[request.getIndex()] != null) {
					if (request.getId() >= checksums[request.getIndex()].length) {
						System.err.println("Error: Requested file id " + request.getId()
								+ " exceeds checksum capacity of " + checksums[request.getIndex()].length);
					}
					if (!crcMatches(checksums[request.getIndex()][request.getId()], data)) {
						data = null;
					}
				} else {
					System.err.println("Error: " + request.getIndex() + "," + request.getId());
				}
			}
			synchronized (mandatory) {
				if (data == null) {
					toRequest.pushFront(request);
				} else {
					request.setData(data);
					synchronized (completed) {
						completed.pushFront(request);
					}
				}
				request = (CacheFileRequest) mandatory.popFront();
			}
		}
	}

	/**
	 * Request data to update server.
	 *
	 * @param onDemandRequest
	 *            : Request to be sent to update server.
	 */
	private void closeRequest(CacheFileRequest onDemandRequest) {
		try {
			if (socket == null) {
				long currentTime = System.currentTimeMillis();

				if (currentTime - openSocketTime < 4000L) {
					return;
				}

				openSocketTime = currentTime;
				socket = clientInstance.createFileServerSocket(Configuration.ONDEMAND_PORT);
				in = socket.getInputStream();
				out = socket.getOutputStream();
				out.write(15);

				for (int j = 0; j < 8; j++) {
					in.read();
				}

				connectionIdleTicks = 0;
			}

			if (CacheIndex.READ_24BIT_FILE_HEADER) {
				inputBuffer[0] = (byte) onDemandRequest.getIndex();
				inputBuffer[1] = (byte) (onDemandRequest.getId() >> 16);
				inputBuffer[2] = (byte) (onDemandRequest.getId() >> 8);
				inputBuffer[3] = (byte) onDemandRequest.getId();

				if (onDemandRequest.incomplete) {
					inputBuffer[4] = 2;
				} else if (!clientInstance.loggedIn) {
					inputBuffer[4] = 1;
				} else {
					inputBuffer[4] = 0;
				}

				out.write(inputBuffer, 0, 5);
			} else {
				inputBuffer[0] = (byte) onDemandRequest.getIndex();
				inputBuffer[1] = (byte) (onDemandRequest.getId() >> 8);
				inputBuffer[2] = (byte) onDemandRequest.getId();

				if (onDemandRequest.incomplete) {
					inputBuffer[3] = 2;
				} else if (!clientInstance.loggedIn) {
					inputBuffer[3] = 1;
				} else {
					inputBuffer[3] = 0;
				}
				// System.out.println("Pushed request: " +
				// onDemandRequest.getIndex() + "," + onDemandRequest.getId());
				out.write(inputBuffer, 0, 4);
			}
			writeLoopCycle = 0;
			errorCount = -10000;
			return;
		} catch (IOException ioexception) {
			errorCount++;
		}

		try {
			socket.close();
		} catch (Exception _ex) {
		}

		socket = null;
		in = null;
		out = null;
		currentFileSize = 0;
		errorCount++;
	}

	private boolean crcMatches(int expectedCrc, byte data[]) {
		if (data == null) {
			return false;
		}
		crc32.reset();
		crc32.update(data, 0, data.length);
		int crc = (int) crc32.getValue();
		return crc == expectedCrc;
	}

	/**
	 * Stop On Demand Fetcher service
	 */
	public void dispose() {
		running = false;
	}

	private void getExtras() {
		while (incompletedCount == 0 && completedCount < 10) {
			if (maxPriority == 0) {
				break;
			}

			CacheFileRequest onDemandData;

			synchronized (extraFilesList) {
				onDemandData = (CacheFileRequest) extraFilesList.popFront();
			}

			while (onDemandData != null) {
				if (fileStatus[onDemandData.getIndex()][onDemandData.getId()] != 0) {
					fileStatus[onDemandData.getIndex()][onDemandData.getId()] = 0;
					requested.pushFront(onDemandData);
					closeRequest(onDemandData);
					expectingData = true;

					if (filesLoaded < totalFiles) {
						filesLoaded++;
					}

					statusString = "Loading extra files - " + filesLoaded * 100 / totalFiles + "%";
					completedCount++;

					if (completedCount == 10) {
						return;
					}
				}

				synchronized (extraFilesList) {
					onDemandData = (CacheFileRequest) extraFilesList.popFront();
				}
			}

			for (int j = 0; j < 4; j++) {
				byte abyte0[] = fileStatus[j];
				int k = abyte0.length;

				for (int l = 0; l < k; l++) {
					if (abyte0[l] == maxPriority) {
						abyte0[l] = 0;
						CacheFileRequest extras = new CacheFileRequest();
						extras.setCacheIndex(j);
						extras.setId(l);
						extras.incomplete = false;
						requested.pushFront(extras);
						closeRequest(extras);
						expectingData = true;

						if (filesLoaded < totalFiles) {
							filesLoaded++;
						}

						statusString = "Downloading Extra files - " + filesLoaded * 100 / totalFiles + "% Compelete";
						completedCount++;

						if (completedCount == 10) {
							return;
						}
					}
				}
			}

			maxPriority--;
		}
	}

	/**
	 * Get total of files in a cache index.
	 *
	 * @return: Amount of files that contains the cache at index
	 *          {@code cacheIndex}
	 */
	public int getFileCount(int index) {
		return checksums[index].length;
	}

	/*
	 * public int getMapCount(int arg0, int arg1, int arg2) { int id = (arg2 <<
	 * 8) + arg1;
	 * 
	 * for (int i = 0; i < mapIndices1.length; i++) { if (mapIndices1[i] == id)
	 * { if (arg0 == 0) { return mapIndices2[i] > 3535 ? -1 : mapIndices2[i]; }
	 * else { return mapIndices3[i] > 3535 ? -1 : mapIndices3[i]; } } } return
	 * -1; }
	 */

	public final int getMapCount(int landscapeOrObject, int regionY, int regionX) {
		int mapCount2;
		int mapCount3;
		int regionId = (regionX << 8) + regionY;
		for (int j1 = 0; j1 < mapIndices1.length; j1++)
			if (mapIndices1[j1] == regionId) {
				if (landscapeOrObject == 0) {
					// Soulwars
					if (mapIndices2[j1] >= 3700 && mapIndices2[j1] <= 3840)
						return mapIndices2[j1];
					for (int cheapHax : cheapHaxValues)
						if (mapIndices2[j1] == cheapHax)
							return mapIndices2[j1];
					mapCount2 = mapIndices2[j1] > 3535 ? -1 : mapIndices2[j1];
					return mapCount2;
				} else {
					if (mapIndices3[j1] >= 3700 && mapIndices3[j1] <= 3840)
						return mapIndices3[j1];
					for (int cheapHax : cheapHaxValues)
						if (mapIndices3[j1] == cheapHax)
							return mapIndices3[j1];
					mapCount3 = mapIndices3[j1] > 3535 ? -1 : mapIndices3[j1];
					return mapCount3;
				}
			}
		return -1;
	}

	public int getRegionIndex(int regionId) {
		for (int index = 0; index < mapIndices1.length; index++) {
			if (mapIndices1[index] == regionId) {
				return index;
			}
		}
		return -1;
	}

	int[] cheapHaxValues = new int[] { 3627, 3628, 3655, 3656, 3625, 3626, 3629, 3630, 4071, 4072, 5253, 1816, 1817,
			3653, 3654, 4067, 4068, 3639, 3640, 1976, 1977, 3571, 3572, 5129, 5130, 2066, 2067, 3545, 3546, 3559, 3560,
			3569, 3570, 3551, 3552, 3579, 3580, 3575, 3576, 1766, 1767, 3547, 3548, 3682, 3683, 3696, 3697, 3692, 3693,
			4013, 4079, 4080, 4082, 3996, 4083, 4084, 4075, 4076, 3664, 3993, 3994, 3995, 4077, 4078, 4073, 4074, 4011,
			4012, 3998, 3999, 4081, };

	public void setExtraPriority(byte byte0, int i, int j) {
		try {
			if (clientInstance.cacheIndices[0] == null) {
				return;
			}
			byte[] abyte0 = clientInstance.cacheIndices[i + 1].get(j);
			if (crcMatches(checksums[i][j], abyte0)) {
				return;
			}
			fileStatus[i][j] = byte0;
			if (byte0 > maxPriority) {
				maxPriority = byte0;
			}
			totalFiles++;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loadRegions(boolean members) {
		try {
			int total = mapIndices1.length;
			for (int k = 0; k < total; k++) {
				if (members || mapIndices4[k] != 0) {
					setExtraPriority((byte) 2, 3, mapIndices3[k]);
					setExtraPriority((byte) 2, 3, mapIndices2[k]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public CacheFileRequest next() {
		CacheFileRequest request;
		synchronized (completed) {
			request = (CacheFileRequest) completed.popFront();
		}
		if (request == null) {
			return null;
		}
		synchronized (remainingMandatory) {
			request.unlinkCacheable();
		}
		if (request.getData() == null) {
			return request;
		}
		int length = 0;
		try {
			GZIPInputStream in = new GZIPInputStream(new ByteArrayInputStream(request.getData()));
			do {
				if (length == inflationBuffer.length) {
					throw new RuntimeException("Buffer overflow: [index=" + request.getIndex() + ", id="
							+ request.getId() + " length=" + request.getData().length + "]");
				}
				int numRead = in.read(inflationBuffer, length, inflationBuffer.length - length);
				if (numRead == -1) {
					break;
				}
				length += numRead;
			} while (true);
		} catch (IOException _ex) {
			System.out.println("Failed to unzip - Data type: " + request.getIndex() + ". File id: " + request.getId());
			// throw new RuntimeException("error unzipping");
			return null;
		}
		request.setData(new byte[length]);
		System.arraycopy(inflationBuffer, 0, request.getData(), 0, length);

		return request;
	}

	/**
	 * Get total of data left to be downloaded.
	 *
	 * @return: Total of data to be downloaded.
	 */
	public int getRemaining() {
		synchronized (remainingMandatory) {
			return remainingMandatory.getSize();
		}
	}

	public boolean remainingContains(int index, int id) {
		synchronized (remainingMandatory) {
			List<NodeSub> list = remainingMandatory.getList();
			for (NodeSub sub : list) {
				CacheFileRequest request = (CacheFileRequest) sub;
				if (request.getIndex() == index && request.getId() == id) {
					return true;
				}
			}
		}
		return false;
	}

	private void handleFailed() {
		incompletedCount = 0;
		completedCount = 0;
		for (CacheFileRequest onDemandData = (CacheFileRequest) requested
				.getTail(); onDemandData != null; onDemandData = (CacheFileRequest) requested.next()) {
			if (onDemandData.incomplete) {
				incompletedCount++;
			} else {
				completedCount++;
			}
		}

		while (incompletedCount < 10) {
			CacheFileRequest request = (CacheFileRequest) toRequest.popFront();
			if (request == null) {
				break;
			}
			if (fileStatus[request.getIndex()][request.getId()] != 0) {
				filesLoaded++;
			}
			fileStatus[request.getIndex()][request.getId()] = 0;
			requested.pushFront(request);
			incompletedCount++;
			closeRequest(request);
			expectingData = true;
		}
	}

	public void method560(int file, int cache) {
		if (clientInstance.cacheIndices[0] == null) {
			return;
		}
		if (fileStatus[cache][file] == 0) {
			return;
		}
		if (maxPriority == 0) {
			return;
		}
		CacheFileRequest onDemandData = new CacheFileRequest();
		onDemandData.setCacheIndex(cache);
		onDemandData.setId(file);
		onDemandData.incomplete = false;
		synchronized (extraFilesList) {
			extraFilesList.pushFront(onDemandData);
		}
	}

	public boolean isFloorMap(int mapFileId) {
		for (int mapFile = 0; mapFile < mapIndices1.length; mapFile++) {
			if (mapIndices3[mapFile] == mapFileId) {
				return true;
			}
		}
		return false;
	}

	public void method566() {
		synchronized (extraFilesList) {
			extraFilesList.removeAll();
		}
	}

	/**
	 * Read received data from Update Server First read 6 bytes. Put those 6
	 * bytes in a byte array {@code inputBuffer}; Decode array into file type,
	 * file ID, size of the file and chunk of the file.
	 */
	private void handleResponse() {
		int offset = CacheIndex.READ_24BIT_FILE_HEADER ? 7 : 6;
		try {
			int available = in.available();
			if (currentFileSize == 0) {
				if (available >= offset) {
					expectingData = true;
					for (int k = 0; k < offset; k += in.read(inputBuffer, k, offset - k)) {
						;
					}
					int cacheIndex;
					int fileId;
					int fileSize;
					// int chunk;
					if (CacheIndex.READ_24BIT_FILE_HEADER) {
						cacheIndex = inputBuffer[0] & 0xff;
						fileId = ((inputBuffer[1] & 0xff) << 16) + ((inputBuffer[2] & 0xff) << 8)
								+ (inputBuffer[3] & 0xff);
						fileSize = ((inputBuffer[4] & 0xff) << 16) + ((inputBuffer[5] & 0xff) << 8)
								+ (inputBuffer[6] & 0xff);
						// chunk = inputBuffer[7] & 0xff;
					} else {
						cacheIndex = inputBuffer[0] & 0xff;
						fileId = ((inputBuffer[1] & 0xff) << 8) + (inputBuffer[2] & 0xff);
						fileSize = ((inputBuffer[3] & 0xff) << 16) + ((inputBuffer[4] & 0xff) << 8)
								+ (inputBuffer[5] & 0xff);
						// chunk = inputBuffer[6] & 0xff;
					}
					// System.out.println("cacheIndex: " + cacheIndex + ",
					// fileId: " + fileId + ", fileSize: " + fileSize);
					current = null;
					for (CacheFileRequest cacheFileRequest = (CacheFileRequest) requested
							.getTail(); cacheFileRequest != null; cacheFileRequest = (CacheFileRequest) requested
									.next()) {
						if (cacheFileRequest.getIndex() == cacheIndex && cacheFileRequest.getId() == fileId) {
							current = cacheFileRequest;
						}
						if (current != null) {
							cacheFileRequest.requestAge = 0;
						}
					}

					if (current != null) {
						connectionIdleTicks = 0;
						if (fileSize == 0) {
							Signlink.reportError("Rej: " + cacheIndex + "," + fileId);
							current.setData(null);
							if (current.incomplete) {
								synchronized (completed) {
									completed.pushFront(current);
								}
							} else {
								current.unlink();
							}
							current = null;
						} else {
							/*
							 * if (current.getData() == null && chunk == 0) {
							 * current.setData(new byte[fileSize]); } if
							 * (current.getData() == null && chunk != 0) { throw
							 * new IOException("missing start of file"); }
							 */
							current.setData(new byte[fileSize]);
						}
					}
					completedSize = 0;
					currentFileSize = fileSize;
					/*
					 * if (currentFileSize > fileSize) { currentFileSize =
					 * fileSize; }
					 */
				}
			} else {
				// if (currentFileSize > 0 && available >= currentFileSize) {
				expectingData = true;
				byte buf[] = inputBuffer;
				int i1 = 0;
				if (current != null) {
					buf = current.getData();
					i1 = completedSize;
				}
				for (int k1 = 0; k1 < currentFileSize; k1 += in.read(buf, k1 + i1, currentFileSize - k1)) {
					;
				}
				if (currentFileSize + completedSize >= buf.length && current != null) {
					if (clientInstance.cacheIndices[0] != null) {
						clientInstance.cacheIndices[current.getIndex() + 1].put(buf.length, buf, current.getId());
					}
					if (!current.incomplete && current.getIndex() == 3) {
						current.incomplete = true;
						current.setCacheIndex(93);
						System.out.println("Requested full map for: " + current.getId());
					}
					if (current.incomplete) {
						synchronized (completed) {
							// System.out.println("Pushing " +
							// current.getIndex() + "," + current.getId() + " to
							// front");
							completed.pushFront(current);
						}
					} else {
						current.unlink();
					}
				}
				currentFileSize = 0;
			}
		} catch (IOException ioexception) {
			try {
				socket.close();
			} catch (Exception _ex) {
			}
			socket = null;
			in = null;
			out = null;
			currentFileSize = 0;
		}
	}

	/**
	 * Start a file data request, if it wasn't requested already.
	 *
	 * @param cacheIndex
	 *            : Data type of the file.
	 * @param fileId
	 *            : ID of the file.
	 */
	public void pushRequest(int cacheIndex, int fileId) {
		if (cacheIndex < 0 || fileId < 0) {
			return;
		}
		synchronized (remainingMandatory) {
			for (CacheFileRequest onDemandData = (CacheFileRequest) remainingMandatory
					.reverseGetFirst(); onDemandData != null; onDemandData = (CacheFileRequest) remainingMandatory
							.reverseGetNext()) {
				if (onDemandData.getIndex() == cacheIndex && onDemandData.getId() == fileId) {
					return;
				}
			}

			CacheFileRequest request = new CacheFileRequest();
			request.setCacheIndex(cacheIndex);
			request.setId(fileId);
			request.incomplete = true;

			synchronized (mandatory) {
				mandatory.pushFront(request);
			}

			remainingMandatory.insertHead(request);
		}
	}

	@Override
	public void run() {
		try {
			while (running) {
				int i = 20;
				if (maxPriority == 0 && clientInstance.cacheIndices[0] != null) {
					i = 50;
				}
				try {
					Thread.sleep(i);
				} catch (Exception _ex) {
				}
				expectingData = true;
				for (int j = 0; j < 100; j++) {
					if (!expectingData) {
						break;
					}
					expectingData = false;
					checkReceived();
					handleFailed();
					if (incompletedCount == 0 && j >= 5) {
						break;
					}
					getExtras();
					if (in != null) {
						handleResponse();
					}
				}

				boolean flag = false;
				for (CacheFileRequest request = (CacheFileRequest) requested
						.getTail(); request != null; request = (CacheFileRequest) requested.next()) {
					if (request.incomplete) {
						flag = true;
						request.requestAge++;
						if (request.requestAge > 50) {
							request.requestAge = 0;
							closeRequest(request);
						}
					}
				}

				if (!flag) {
					for (CacheFileRequest request = (CacheFileRequest) requested
							.getTail(); request != null; request = (CacheFileRequest) requested.next()) {
						flag = true;
						request.requestAge++;
						if (request.requestAge > 50) {
							request.requestAge = 0;
							closeRequest(request);
						}
					}

				}
				if (flag) {
					connectionIdleTicks++;
					if (connectionIdleTicks > 750) {
						try {
							socket.close();
						} catch (Exception _ex) {
						}
						socket = null;
						in = null;
						out = null;
						currentFileSize = 0;
					}
				} else {
					connectionIdleTicks = 0;
					statusString = "";
				}
				if (clientInstance.loggedIn && socket != null && out != null
						&& (maxPriority > 0 || clientInstance.cacheIndices[0] == null)) {
					writeLoopCycle++;
					if (writeLoopCycle > 500) {
						writeLoopCycle = 0;
						inputBuffer[0] = 0;
						inputBuffer[1] = 0;
						inputBuffer[2] = 0;
						inputBuffer[3] = 10;
						try {
							out.write(inputBuffer, 0, 4);
						} catch (IOException _ex) {
							connectionIdleTicks = 5000;
						}
					}
				}
			}
		} catch (Exception exception) {
			Signlink.reportError("od_ex " + exception.getMessage());
			exception.printStackTrace();
		}
	}

	public int getModelIndex(int i) {
		return modelIndices[i] & 0xff;
	}

	public void start(Archive archive, Client client1) {
		byte[] data = null;
		int j1 = 0;
		String as1[] = { "model_crc", "anim_crc", "midi_crc", "map_crc", "textures_crc", "model_extended_crc" };
		for (int index = 0; index < 6; index++) {
			data = archive.get(as1[index]);
			ByteBuffer buffer = new ByteBuffer(data);
			int fileAmount = data.length / 4;
			checksums[index] = new int[fileAmount];
			fileStatus[index] = new byte[fileAmount];
			for (int fileId = 0; fileId < fileAmount; fileId++) {
				checksums[index][fileId] = buffer.getIntLittleEndian();
			}

		}

		data = archive.get("model_index");
		j1 = checksums[0].length;
		modelIndices = new byte[j1];
		for (int k1 = 0; k1 < j1; k1++) {
			if (k1 < data.length) {
				modelIndices[k1] = data[k1];
			} else {
				modelIndices[k1] = 0;
			}
		}
		data = archive.get("map_index");
		ByteBuffer stream2 = new ByteBuffer(data);
		int mapCount = stream2.getUnsignedShort();
		mapIndices1 = new int[mapCount];
		mapIndices2 = new int[mapCount];
		mapIndices3 = new int[mapCount];
		mapIndices4 = new int[mapCount];
		int[] dntUse = new int[] { 5181, 5182, 5183, 5184, 5180, 5179, 5175, 5176, 4014, 3997, 5314, 5315, 5172 };
		for (int i2 = 0; i2 < mapCount; i2++) {
			mapIndices1[i2] = stream2.getUnsignedShort();
			mapIndices2[i2] = stream2.getUnsignedShort();
			mapIndices3[i2] = stream2.getUnsignedShort();
			for (int i : dntUse) {
				if (mapIndices2[i2] == i)
					mapIndices2[i2] = -1;
				if (mapIndices3[i2] == i)
					mapIndices3[i2] = -1;
			}
		}
		/** ZULRAH **/
		mapIndices1[107] = 8751;
		mapIndices2[107] = 1946;
		mapIndices3[107] = 1947;
		mapIndices1[108] = 8752;
		mapIndices2[108] = 938;
		mapIndices3[108] = 939;
		mapIndices1[129] = 9007;
		mapIndices2[129] = 1938;
		mapIndices3[129] = 1939;
		mapIndices1[130] = 9008;
		mapIndices2[130] = 946;
		mapIndices3[130] = 947;
		mapIndices1[149] = 9263;
		mapIndices2[149] = 1210;
		mapIndices3[149] = 1211;
		mapIndices1[150] = 9264;
		mapIndices2[150] = 956;
		mapIndices3[150] = 957;
		/*
		 * j1 = abyte2.length / 6; int loopVal = j1; mapIndices1 = new int[j1];
		 * mapIndices2 = new int[j1]; mapIndices3 = new int[j1]; mapIndices4 =
		 * new int[j1]; for (int i2 = 0; i2 < loopVal; i2++) { mapIndices1[i2] =
		 * stream2.getUnsignedShort(); mapIndices2[i2] =
		 * stream2.getUnsignedShort(); mapIndices3[i2] =
		 * stream2.getUnsignedShort(); }
		 */

		// Cerberus
		mapIndices1[151] = 4883;
		mapIndices2[151] = 1984;
		mapIndices3[151] = 1985;

		data = archive.get("midi_index");
		stream2 = new ByteBuffer(data);
		j1 = data.length;
		anIntArray1348 = new int[j1];
		for (int k2 = 0; k2 < j1; k2++) {
			anIntArray1348[k2] = stream2.getUnsignedByte();
		}

		data = archive.get("file_priorities.dat");
		ByteBuffer buffer = new ByteBuffer(data);
		int listCount = buffer.getByte();
		for (int list = 0; list < listCount; list++) {
			int size = buffer.getMediumInt();
			for (int index = 0; index < size; index++) {
				int id = buffer.getMediumInt();
				byte priority = buffer.getByte();
				if (list == 0) {
					this.priorityRequestHandler.addModel(id);
				} else if (list == 1) {
					this.priorityRequestHandler.addAnim(id);
				} else if (list == 3) {
					this.priorityRequestHandler.addMap(id);
				}
			}
		}

		clientInstance = client1;
		running = true;
		clientInstance.startRunnable(this, 2);
	}

	public final int getAnimCount() {
		return 33568;
	}

}