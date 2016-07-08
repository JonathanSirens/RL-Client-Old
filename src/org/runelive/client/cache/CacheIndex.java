package org.runelive.client.cache;

import java.io.IOException;
import java.io.RandomAccessFile;

public final class CacheIndex {

	public static final int FILE_HEADER_SIZE = 8;
	public static final boolean READ_24BIT_FILE_HEADER = FILE_HEADER_SIZE == 9;
	private static final int BLOCK_SIZE = 512 + FILE_HEADER_SIZE;

	public CacheIndex(RandomAccessFile data, RandomAccessFile index, int id) {
		this.id = id;
		dataFile = data;
		indexFile = index;
	}

	public synchronized byte[] get(int id) {
		try {
			seekTo(indexFile, id * 6);
			for (int offset = 0, numBytesRead; offset < 6; offset += numBytesRead) {
				numBytesRead = indexFile.read(buffer, offset, 6 - offset);
				if (numBytesRead == -1) {
					return null;
				}
			}
			int fileLength = ((buffer[0] & 0xff) << 16) + ((buffer[1] & 0xff) << 8) + (buffer[2] & 0xff);
			int fileIndex = ((buffer[3] & 0xff) << 16) + ((buffer[4] & 0xff) << 8) + (buffer[5] & 0xff);
			if (fileIndex <= 0 || fileIndex > dataFile.length() / BLOCK_SIZE) {
				return null;
			}
			byte fileData[] = new byte[fileLength];
			int bytesRead = 0;
			for (int index1 = 0; bytesRead < fileLength; index1++) {
				if (fileIndex == 0) {
					return null;
				}
				seekTo(dataFile, fileIndex * BLOCK_SIZE);
				int dataLeft = fileLength - bytesRead;
				if (dataLeft > 512) {
					dataLeft = 512;
				}
				int offset;
				for (int numBytesRead = 0; numBytesRead < dataLeft + FILE_HEADER_SIZE; numBytesRead += offset) {
					offset = dataFile.read(buffer, numBytesRead, dataLeft + FILE_HEADER_SIZE - numBytesRead);
					if (offset == -1) {
						return null;
					}
				}
				int storedFileId;
				int storedLength;
				int dataFileIndex;
				int storedIndexId;
				if (!READ_24BIT_FILE_HEADER) {
					storedFileId = ((buffer[0] & 0xff) << 8) + (buffer[1] & 0xff);// short
					storedLength = ((buffer[2] & 0xff) << 8) + (buffer[3] & 0xff);// short
					dataFileIndex = ((buffer[4] & 0xff) << 16) + ((buffer[5] & 0xff) << 8) + (buffer[6] & 0xff);// medium
																												// int
					storedIndexId = buffer[7] & 0xff;// byte
				} else {
					storedFileId = ((buffer[0] & 0xff) << 16) + ((buffer[1] & 0xff) << 8) + (buffer[2] & 0xff);// medium
																												// int
					storedLength = ((buffer[3] & 0xff) << 8) + (buffer[4] & 0xff);// short
					dataFileIndex = ((buffer[5] & 0xff) << 16) + ((buffer[6] & 0xff) << 8) + (buffer[7] & 0xff);// medium
																												// int
					storedIndexId = buffer[8] & 0xff;// byte
				}
				if (storedFileId != id || storedLength != index1 || storedIndexId != this.id) {
					return null;
				}
				if (dataFileIndex < 0 || dataFileIndex > dataFile.length() / BLOCK_SIZE) {
					return null;
				}
				for (int index2 = 0; index2 < dataLeft; index2++) {
					fileData[bytesRead++] = buffer[index2 + FILE_HEADER_SIZE];
				}

				fileIndex = dataFileIndex;
			}

			return fileData;
		} catch (IOException _ex) {
			return null;
		}
	}

	public synchronized boolean put(int length, byte data[], int id) {
		boolean successful = put(true, id, length, data);
		if (!successful) {
			successful = put(false, id, length, data);
		}
		return successful;
	}

	private synchronized boolean put(boolean flag, int id, int length, byte data[]) {
		try {
			int l;
			if (flag) {
				seekTo(indexFile, id * 6);
				int k1;
				for (int i1 = 0; i1 < 6; i1 += k1) {
					k1 = indexFile.read(buffer, i1, 6 - i1);
					if (k1 == -1) {
						return false;
					}
				}
				l = ((buffer[3] & 0xff) << 16) + ((buffer[4] & 0xff) << 8) + (buffer[5] & 0xff);
				if (l <= 0 || l > dataFile.length() / BLOCK_SIZE) {
					return false;
				}
			} else {
				l = (int) ((dataFile.length() + (BLOCK_SIZE - 1)) / BLOCK_SIZE);
				if (l == 0) {
					l = 1;
				}
			}
			buffer[0] = (byte) (length >> 16);
			buffer[1] = (byte) (length >> 8);
			buffer[2] = (byte) length;
			buffer[3] = (byte) (l >> 16);
			buffer[4] = (byte) (l >> 8);
			buffer[5] = (byte) l;
			seekTo(indexFile, id * 6);
			indexFile.write(buffer, 0, 6);
			int j1 = 0;
			for (int index1 = 0; j1 < length; index1++) {
				int dataFileIndex = 0;
				if (flag) {
					seekTo(dataFile, l * BLOCK_SIZE);
					int j2;
					int l2;
					for (j2 = 0; j2 < FILE_HEADER_SIZE; j2 += l2) {
						l2 = dataFile.read(buffer, j2, FILE_HEADER_SIZE - j2);
						if (l2 == -1) {
							break;
						}
					}
					if (j2 == FILE_HEADER_SIZE) {
						int fileId;
						int j3;
						int version;
						if (!READ_24BIT_FILE_HEADER) {
							fileId = ((buffer[0] & 0xff) << 8) + (buffer[1] & 0xff);
							j3 = ((buffer[2] & 0xff) << 8) + (buffer[3] & 0xff);
							dataFileIndex = ((buffer[4] & 0xff) << 16) + ((buffer[5] & 0xff) << 8) + (buffer[6] & 0xff);
							version = buffer[7] & 0xff;
						} else {
							fileId = ((buffer[0] & 0xff) << 16) + ((buffer[1] & 0xff) << 8) + (buffer[2] & 0xff);
							j3 = ((buffer[3] & 0xff) << 8) + (buffer[4] & 0xff);
							dataFileIndex = ((buffer[5] & 0xff) << 16) + ((buffer[6] & 0xff) << 8) + (buffer[7] & 0xff);
							version = buffer[8] & 0xff;
						}
						;
						if (fileId != id || j3 != index1 || version != this.id) {
							return false;
						}
						if (dataFileIndex < 0 || dataFileIndex > dataFile.length() / BLOCK_SIZE) {
							return false;
						}
					}
				}
				if (dataFileIndex == 0) {
					flag = false;
					dataFileIndex = (int) ((dataFile.length() + (BLOCK_SIZE - 1)) / BLOCK_SIZE);
					if (dataFileIndex == 0) {
						dataFileIndex++;
					}
					if (dataFileIndex == l) {
						dataFileIndex++;
					}
				}
				if (length - j1 <= 512) {
					dataFileIndex = 0;
				}
				if (!READ_24BIT_FILE_HEADER) {
					buffer[0] = (byte) (id >> 8);
					buffer[1] = (byte) id;
					buffer[2] = (byte) (index1 >> 8);
					buffer[3] = (byte) index1;
					buffer[4] = (byte) (dataFileIndex >> 16);
					buffer[5] = (byte) (dataFileIndex >> 8);
					buffer[6] = (byte) dataFileIndex;
					buffer[7] = (byte) this.id;
				} else {
					buffer[0] = (byte) (id >> 16);
					buffer[1] = (byte) (id >> 8);
					buffer[2] = (byte) id;
					buffer[3] = (byte) (index1 >> 8);
					buffer[4] = (byte) index1;
					buffer[5] = (byte) (dataFileIndex >> 16);
					buffer[6] = (byte) (dataFileIndex >> 8);
					buffer[7] = (byte) dataFileIndex;
					buffer[8] = (byte) this.id;
				}
				seekTo(dataFile, l * BLOCK_SIZE);
				dataFile.write(buffer, 0, FILE_HEADER_SIZE);
				int k2 = length - j1;
				if (k2 > 512) {
					k2 = 512;
				}
				dataFile.write(data, j1, k2);
				j1 += k2;
				l = dataFileIndex;
			}

			return true;
		} catch (IOException _ex) {
			return false;
		}
	}

	private synchronized void seekTo(RandomAccessFile randomaccessfile, int j) throws IOException {
		try {
			randomaccessfile.seek(j);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public long getFileCount() {
		try {
			if (indexFile != null) {
				return indexFile.length() / 6;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	private static final byte[] buffer = new byte[BLOCK_SIZE];
	private final RandomAccessFile dataFile;
	private final RandomAccessFile indexFile;
	private final int id;

}
