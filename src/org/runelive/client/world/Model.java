package org.runelive.client.world;

import org.runelive.Configuration;
import org.runelive.client.Class33;
import org.runelive.client.Client;
import org.runelive.client.FrameReader;
import org.runelive.client.ModelHeader;
import org.runelive.client.SkinList;
import org.runelive.client.cache.ondemand.CacheFileRequest;
import org.runelive.client.cache.ondemand.CacheFileRequester;
import org.runelive.client.graphics.Canvas2D;
import org.runelive.client.io.ByteBuffer;
import org.runelive.client.renderable.Animable;

public class Model extends Animable {

	public static boolean aBoolean1684;
	private static boolean aBooleanArray1663[] = new boolean[8000];
	private static boolean outOfReach[] = new boolean[8000];
	private static ModelHeader modelHeaderCache[];
	public static Model aModel_1621 = new Model(true);
	private static int anInt1681;
	private static int anInt1682;
	private static int anInt1683;
	public static int anInt1685;
	public static int anInt1686;
	public static int anInt1687;
	private static int anIntArray1622[] = new int[2000];
	private static int anIntArray1623[] = new int[2000];
	private static int anIntArray1624[] = new int[2000];
	private static int anIntArray1625[] = new int[2000];
	private static int projected_vertex_x[] = new int[8000];
	private static int projected_vertex_y[] = new int[8000];
	private static int anIntArray1667[] = new int[8000];
	private static int camera_vertex_y[] = new int[8000];
	private static int camera_vertex_x[] = new int[8000];
	private static int camera_vertex_z[] = new int[8000];
	private static int depthListIndices[] = new int[1500];
	private static int anIntArray1673[] = new int[12];
	private static int anIntArray1675[] = new int[2000];
	private static int anIntArray1676[] = new int[2000];
	private static int anIntArray1677[] = new int[12];
	private static int anIntArray1678[] = new int[10];
	private static int anIntArray1679[] = new int[10];
	private static int anIntArray1680[] = new int[10];
	public static int mapObjIds[] = new int[1000];
	public static int anIntArray1688[] = new int[1000];
	private static int faceLists[][] = new int[1500][512];
	private static int anIntArrayArray1674[][] = new int[12][2000];
	private static CacheFileRequester onDemandRequester;
	public static int SINE[];
	public static int COSINE[];
	private static int[] hsl2rgb;
	private static int[] lightDecay;
	private static boolean[] isNewModel;

	static {
		SINE = Canvas3D.SINE;
		COSINE = Canvas3D.COSINE;
		hsl2rgb = Canvas3D.anIntArray1482;
		lightDecay = Canvas3D.anIntArray1469;
	}

	public static void initialize(int count, CacheFileRequester onDemandFetcherParent) {
		modelHeaderCache = new ModelHeader[80000];
		isNewModel = new boolean[100000];
		onDemandRequester = onDemandFetcherParent;
	}

	public static void decodeModelHeader(byte[] tmp, int fileId) {
		try {
			if (tmp == null) {
				ModelHeader modelHeader = modelHeaderCache[fileId] = new ModelHeader();
				modelHeader.anInt369 = 0;
				modelHeader.anInt370 = 0;
				modelHeader.anInt371 = 0;
				return;
			}

			ByteBuffer buffer = new ByteBuffer(tmp);
			buffer.position = tmp.length - 18;
			ModelHeader modelHeader_1 = modelHeaderCache[fileId] = new ModelHeader();
			modelHeader_1.aByteArray368 = tmp;
			modelHeader_1.anInt369 = buffer.getUnsignedShort();
			modelHeader_1.anInt370 = buffer.getUnsignedShort();
			modelHeader_1.anInt371 = buffer.getUnsignedByte();
			int k = buffer.getUnsignedByte();
			int l = buffer.getUnsignedByte();
			int i1 = buffer.getUnsignedByte();
			int j1 = buffer.getUnsignedByte();
			int k1 = buffer.getUnsignedByte();
			int l1 = buffer.getUnsignedShort();
			int i2 = buffer.getUnsignedShort();
			int j2 = buffer.getUnsignedShort();
			int k2 = buffer.getUnsignedShort();
			int l2 = 0;
			modelHeader_1.anInt372 = l2;
			l2 += modelHeader_1.anInt369;
			modelHeader_1.anInt378 = l2;
			l2 += modelHeader_1.anInt370;
			modelHeader_1.anInt381 = l2;

			if (l == 255) {
				l2 += modelHeader_1.anInt370;
			} else {
				modelHeader_1.anInt381 = -l - 1;
			}

			modelHeader_1.anInt383 = l2;

			if (j1 == 1) {
				l2 += modelHeader_1.anInt370;
			} else {
				modelHeader_1.anInt383 = -1;
			}

			modelHeader_1.anInt380 = l2;

			if (k == 1) {
				l2 += modelHeader_1.anInt370;
			} else {
				modelHeader_1.anInt380 = -1;
			}

			modelHeader_1.anInt376 = l2;

			if (k1 == 1) {
				l2 += modelHeader_1.anInt369;
			} else {
				modelHeader_1.anInt376 = -1;
			}

			modelHeader_1.anInt382 = l2;

			if (i1 == 1) {
				l2 += modelHeader_1.anInt370;
			} else {
				modelHeader_1.anInt382 = -1;
			}

			modelHeader_1.anInt377 = l2;
			l2 += k2;
			modelHeader_1.anInt379 = l2;
			l2 += modelHeader_1.anInt370 * 2;
			modelHeader_1.anInt384 = l2;
			l2 += modelHeader_1.anInt371 * 6;
			modelHeader_1.anInt373 = l2;
			l2 += l1;
			modelHeader_1.anInt374 = l2;
			l2 += i2;
			modelHeader_1.anInt375 = l2;
			l2 += j2;
		} catch (Exception _ex) {
		}
	}

	public static Model fetchModel(int fileId) {
		if (modelHeaderCache == null) {
			return null;
		}
		if (fileId == 0) {
			return null;
		}
		ModelHeader modelHeader = modelHeaderCache[fileId];
		if (modelHeader == null) {
			int cacheIndex = CacheFileRequest.MODEL;// default model index
			if (fileId >= 65535) {
				fileId -= 65535;
				cacheIndex = CacheFileRequest.MODEL_EXT;// model_extended index
			}
			onDemandRequester.pushRequest(cacheIndex, fileId);
			return null;
		} else {
			return new Model(fileId);
		}
	}

	public static boolean isModelLoaded(int fileId) {
		if (modelHeaderCache == null) {
			return false;
		}
		ModelHeader modelHeader = modelHeaderCache[fileId];
		if (modelHeader == null) {
			int cacheIndex = CacheFileRequest.MODEL;// default model index
			if (fileId >= 65535) {
				fileId -= 65535;
				cacheIndex = CacheFileRequest.MODEL_EXT;// model_extended index
			}
			onDemandRequester.pushRequest(cacheIndex, fileId);
			return false;
		} else {
			return true;
		}
	}

	private static final int method481(int i, int j, int k) {
		if (i == 65535) {
			return 0;
		}

		if ((k & 2) == 2) {
			if (j < 0) {
				j = 0;
			} else if (j > 127) {
				j = 127;
			}

			j = 127 - j;
			return j;
		}

		j = j * (i & 0x7f) >> 7;

		if (j < 2) {
			j = 2;
		} else if (j > 126) {
			j = 126;
		}

		return (i & 0xff80) + j;
	}

	public static void nullify() {
		modelHeaderCache = null;
		aBooleanArray1663 = null;
		outOfReach = null;
		projected_vertex_y = null;
		anIntArray1667 = null;
		camera_vertex_y = null;
		camera_vertex_x = null;
		camera_vertex_z = null;
		depthListIndices = null;
		faceLists = null;
		anIntArray1673 = null;
		anIntArrayArray1674 = null;
		anIntArray1675 = null;
		anIntArray1676 = null;
		anIntArray1677 = null;
		SINE = null;
		COSINE = null;
		hsl2rgb = null;
		lightDecay = null;
	}

	private boolean aBoolean1618;
	public boolean aBoolean1659;
	public Class33 aClass33Array1660[];
	public int vertexCount;
	public int triangle_count;
	private int anInt1641;
	private int textured_triangle_count;
	public int anInt1646;
	public int anInt1647;
	public int anInt1648;
	public int anInt1649;
	public int anInt1650;
	public int anInt1651;
	private int diagonal3D;
	private int anInt1653;
	public int anInt1654;
	public int vertexX[];
	public int vertexY[];
	public int vertexZ[];
	public int triangle_viewspace_x[];
	public int triangle_viewspace_y[];
	public int triangle_viewspace_z[];
	private int face_shade_a[];
	private int face_shade_b[];
	private int face_shade_c[];
	public int face_fill_attributes[];
	private int priorities[];
	private int alpha[];
	public int colors[];
	private int texture_map_x[];
	private byte[] texture_fill_attributes;
	private int texture_map_y[];
	private int texture_map_z[];
	private int vertex_skin_types[];
	private int triangle_skin_types[];
	public int vertexSkin[][];
	public int triangleSkin[][];

	private Model(boolean flag) {
		aBoolean1618 = true;
		aBoolean1659 = false;

		if (!flag) {
			aBoolean1618 = !aBoolean1618;
		}
	}

	public Model(boolean flag, boolean flag1, boolean flag2, Model model) {
		aBoolean1618 = true;
		aBoolean1659 = false;
		vertexCount = model.vertexCount;
		triangle_count = model.triangle_count;
		textured_triangle_count = model.textured_triangle_count;

		if (flag2) {
			vertexX = model.vertexX;
			vertexY = model.vertexY;
			vertexZ = model.vertexZ;
		} else {
			vertexX = new int[vertexCount];
			vertexY = new int[vertexCount];
			vertexZ = new int[vertexCount];

			for (int j = 0; j < vertexCount; j++) {
				vertexX[j] = model.vertexX[j];
				vertexY[j] = model.vertexY[j];
				vertexZ[j] = model.vertexZ[j];
			}
		}

		if (flag) {
			colors = model.colors;
		} else {
			colors = new int[triangle_count];

			for (int k = 0; k < triangle_count; k++) {
				colors[k] = model.colors[k];
			}
		}

		if (flag1) {
			alpha = model.alpha;
		} else {
			alpha = new int[triangle_count];

			if (model.alpha == null) {
				for (int l = 0; l < triangle_count; l++) {
					alpha[l] = 0;
				}
			} else {
				for (int i1 = 0; i1 < triangle_count; i1++) {
					alpha[i1] = model.alpha[i1];
				}
			}
		}

		vertex_skin_types = model.vertex_skin_types;
		triangle_skin_types = model.triangle_skin_types;
		face_fill_attributes = model.face_fill_attributes;
		triangle_viewspace_x = model.triangle_viewspace_x;
		triangle_viewspace_y = model.triangle_viewspace_y;
		triangle_viewspace_z = model.triangle_viewspace_z;
		priorities = model.priorities;
		anInt1641 = model.anInt1641;
		texture_map_x = model.texture_map_x;
		texture_map_y = model.texture_map_y;
		texture_map_z = model.texture_map_z;
	}

	public Model(boolean flag, boolean flag1, Model model) {
		aBoolean1618 = true;
		aBoolean1659 = false;
		vertexCount = model.vertexCount;
		triangle_count = model.triangle_count;
		textured_triangle_count = model.textured_triangle_count;

		if (flag) {
			vertexY = new int[vertexCount];

			for (int j = 0; j < vertexCount; j++) {
				vertexY[j] = model.vertexY[j];
			}
		} else {
			vertexY = model.vertexY;
		}

		if (flag1) {
			face_shade_a = new int[triangle_count];
			face_shade_b = new int[triangle_count];
			face_shade_c = new int[triangle_count];

			for (int k = 0; k < triangle_count; k++) {
				face_shade_a[k] = model.face_shade_a[k];
				face_shade_b[k] = model.face_shade_b[k];
				face_shade_c[k] = model.face_shade_c[k];
			}

			face_fill_attributes = new int[triangle_count];

			if (model.face_fill_attributes == null) {
				for (int l = 0; l < triangle_count; l++) {
					face_fill_attributes[l] = 0;
				}
			} else {
				for (int i1 = 0; i1 < triangle_count; i1++) {
					face_fill_attributes[i1] = model.face_fill_attributes[i1];
				}
			}

			super.aClass33Array1425 = new Class33[vertexCount];

			for (int j1 = 0; j1 < vertexCount; j1++) {
				Class33 class33 = super.aClass33Array1425[j1] = new Class33();
				Class33 class33_1 = model.aClass33Array1425[j1];
				class33.anInt602 = class33_1.anInt602;
				class33.anInt603 = class33_1.anInt603;
				class33.anInt604 = class33_1.anInt604;
				class33.anInt605 = class33_1.anInt605;
			}

			aClass33Array1660 = model.aClass33Array1660;
		} else {
			face_shade_a = model.face_shade_a;
			face_shade_b = model.face_shade_b;
			face_shade_c = model.face_shade_c;
			face_fill_attributes = model.face_fill_attributes;
		}

		vertexX = model.vertexX;
		vertexZ = model.vertexZ;
		colors = model.colors;
		alpha = model.alpha;
		priorities = model.priorities;
		anInt1641 = model.anInt1641;
		triangle_viewspace_x = model.triangle_viewspace_x;
		triangle_viewspace_y = model.triangle_viewspace_y;
		triangle_viewspace_z = model.triangle_viewspace_z;
		texture_map_x = model.texture_map_x;
		texture_map_y = model.texture_map_y;
		texture_map_z = model.texture_map_z;
		super.modelHeight = model.modelHeight;
		anInt1650 = model.anInt1650;
		anInt1653 = model.anInt1653;
		diagonal3D = model.diagonal3D;
		anInt1646 = model.anInt1646;
		anInt1648 = model.anInt1648;
		anInt1649 = model.anInt1649;
		anInt1647 = model.anInt1647;
	}

	private Model(int modelId) {
		byte[] is = modelHeaderCache[modelId].aByteArray368;

		if (is[is.length - 1] == -1 && is[is.length - 2] == -1) {
			read622Model(is, modelId);
		} else {
			try {
				readOldModel(modelId);
			} catch (Exception e) {
				System.err.println("Error decoding old model: " + modelId);
				e.printStackTrace();
			}
		}
		if ((modelId >= 53347 && modelId <= 53370) || (modelId >= 76001 && modelId <= 76047)) {
			// recolour(0, 255);
			if (priorities != null) { // rofl
				for (int j = 0; j < priorities.length; j++)
					priorities[j] = 10;
			}
		}
	}

	public Model(int i, Model amodel[]) {
		aBoolean1618 = true;
		aBoolean1659 = false;
		boolean flag = false;
		boolean flag1 = false;
		boolean flag2 = false;
		boolean flag3 = false;
		vertexCount = 0;
		triangle_count = 0;
		textured_triangle_count = 0;
		anInt1641 = -1;

		for (int k = 0; k < i; k++) {
			Model model = amodel[k];

			if (model != null) {
				vertexCount += model.vertexCount;
				triangle_count += model.triangle_count;
				textured_triangle_count += model.textured_triangle_count;
				flag |= model.face_fill_attributes != null;

				if (model.priorities != null) {
					flag1 = true;
				} else {
					if (anInt1641 == -1) {
						anInt1641 = model.anInt1641;
					}

					if (anInt1641 != model.anInt1641) {
						flag1 = true;
					}
				}

				flag2 |= model.alpha != null;
				flag3 |= model.triangle_skin_types != null;
			}
		}

		vertexX = new int[vertexCount];
		vertexY = new int[vertexCount];
		vertexZ = new int[vertexCount];
		vertex_skin_types = new int[vertexCount];
		triangle_viewspace_x = new int[triangle_count];
		triangle_viewspace_y = new int[triangle_count];
		triangle_viewspace_z = new int[triangle_count];
		texture_map_x = new int[textured_triangle_count];
		texture_map_y = new int[textured_triangle_count];
		texture_map_z = new int[textured_triangle_count];

		if (flag) {
			face_fill_attributes = new int[triangle_count];
		}

		if (flag1) {
			priorities = new int[triangle_count];
		}

		if (flag2) {
			alpha = new int[triangle_count];
		}

		if (flag3) {
			triangle_skin_types = new int[triangle_count];
		}

		colors = new int[triangle_count];
		vertexCount = 0;
		triangle_count = 0;
		textured_triangle_count = 0;
		int l = 0;

		for (int i1 = 0; i1 < i; i1++) {
			Model model_1 = amodel[i1];

			if (model_1 != null) {
				for (int j1 = 0; j1 < model_1.triangle_count; j1++) {
					if (flag) {
						if (model_1.face_fill_attributes == null) {
							face_fill_attributes[triangle_count] = 0;
						} else {
							int k1 = model_1.face_fill_attributes[j1];

							if ((k1 & 2) == 2) {
								k1 += l << 2;
							}

							face_fill_attributes[triangle_count] = k1;
						}
					}

					if (flag1) {
						if (model_1.priorities == null) {
							priorities[triangle_count] = model_1.anInt1641;
						} else {
							priorities[triangle_count] = model_1.priorities[j1];
						}
					}

					if (flag2) {
						if (model_1.alpha == null) {
							alpha[triangle_count] = 0;
						} else {
							alpha[triangle_count] = model_1.alpha[j1];
						}
					}

					if (flag3 && model_1.triangle_skin_types != null) {
						triangle_skin_types[triangle_count] = model_1.triangle_skin_types[j1];
					}

					colors[triangle_count] = model_1.colors[j1];
					triangle_viewspace_x[triangle_count] = method465(model_1, model_1.triangle_viewspace_x[j1]);
					triangle_viewspace_y[triangle_count] = method465(model_1, model_1.triangle_viewspace_y[j1]);
					triangle_viewspace_z[triangle_count] = method465(model_1, model_1.triangle_viewspace_z[j1]);
					triangle_count++;
				}

				for (int l1 = 0; l1 < model_1.textured_triangle_count; l1++) {
					texture_map_x[textured_triangle_count] = method465(model_1, model_1.texture_map_x[l1]);
					texture_map_y[textured_triangle_count] = method465(model_1, model_1.texture_map_y[l1]);
					texture_map_z[textured_triangle_count] = method465(model_1, model_1.texture_map_z[l1]);
					textured_triangle_count++;
				}

				l += model_1.textured_triangle_count;
			}
		}
	}

	public Model(Model amodel[]) {
		int i = 2;
		aBoolean1618 = true;
		aBoolean1659 = false;
		boolean flag1 = false;
		boolean flag2 = false;
		boolean flag3 = false;
		boolean flag4 = false;
		vertexCount = 0;
		triangle_count = 0;
		textured_triangle_count = 0;
		anInt1641 = -1;

		for (int k = 0; k < i; k++) {
			Model model = amodel[k];
			if (model != null) {
				vertexCount += model.vertexCount;
				triangle_count += model.triangle_count;
				textured_triangle_count += model.textured_triangle_count;
				flag1 |= model.face_fill_attributes != null;
				if (model.priorities != null) {
					flag2 = true;
				} else {
					if (anInt1641 == -1) {
						anInt1641 = model.anInt1641;
					}
					if (anInt1641 != model.anInt1641) {
						flag2 = true;
					}
				}
				flag3 |= model.alpha != null;
				flag4 |= model.colors != null;
			}
		}

		vertexX = new int[vertexCount];
		vertexY = new int[vertexCount];
		vertexZ = new int[vertexCount];
		triangle_viewspace_x = new int[triangle_count];
		triangle_viewspace_y = new int[triangle_count];
		triangle_viewspace_z = new int[triangle_count];
		face_shade_a = new int[triangle_count];
		face_shade_b = new int[triangle_count];
		face_shade_c = new int[triangle_count];
		texture_map_x = new int[textured_triangle_count];
		texture_map_y = new int[textured_triangle_count];
		texture_map_z = new int[textured_triangle_count];
		if (flag1) {
			face_fill_attributes = new int[triangle_count];
		}
		if (flag2) {
			priorities = new int[triangle_count];
		}
		if (flag3) {
			alpha = new int[triangle_count];
		}
		if (flag4) {
			colors = new int[triangle_count];
		}
		vertexCount = 0;
		triangle_count = 0;
		textured_triangle_count = 0;
		int i1 = 0;
		for (int j1 = 0; j1 < i; j1++) {
			Model model_1 = amodel[j1];
			if (model_1 != null) {
				int k1 = vertexCount;
				for (int l1 = 0; l1 < model_1.vertexCount; l1++) {
					vertexX[vertexCount] = model_1.vertexX[l1];
					vertexY[vertexCount] = model_1.vertexY[l1];
					vertexZ[vertexCount] = model_1.vertexZ[l1];
					vertexCount++;
				}

				for (int i2 = 0; i2 < model_1.triangle_count; i2++) {
					triangle_viewspace_x[triangle_count] = model_1.triangle_viewspace_x[i2] + k1;
					triangle_viewspace_y[triangle_count] = model_1.triangle_viewspace_y[i2] + k1;
					triangle_viewspace_z[triangle_count] = model_1.triangle_viewspace_z[i2] + k1;
					face_shade_a[triangle_count] = model_1.face_shade_a[i2];
					face_shade_b[triangle_count] = model_1.face_shade_b[i2];
					face_shade_c[triangle_count] = model_1.face_shade_c[i2];
					if (flag1) {
						if (model_1.face_fill_attributes == null) {
							face_fill_attributes[triangle_count] = 0;
						} else {
							int j2 = model_1.face_fill_attributes[i2];
							if ((j2 & 2) == 2) {
								j2 += i1 << 2;
							}
							face_fill_attributes[triangle_count] = j2;
						}
					}
					if (flag2) {
						if (model_1.priorities == null) {
							priorities[triangle_count] = model_1.anInt1641;
						} else {
							priorities[triangle_count] = model_1.priorities[i2];
						}
					}
					if (flag3) {
						if (model_1.alpha == null) {
							alpha[triangle_count] = 0;
						} else {
							alpha[triangle_count] = model_1.alpha[i2];
						}
					}
					if (flag4 && model_1.colors != null) {
						colors[triangle_count] = model_1.colors[i2];
					}

					triangle_count++;
				}

				for (int k2 = 0; k2 < model_1.textured_triangle_count; k2++) {
					texture_map_x[textured_triangle_count] = model_1.texture_map_x[k2] + k1;
					texture_map_y[textured_triangle_count] = model_1.texture_map_y[k2] + k1;
					texture_map_z[textured_triangle_count] = model_1.texture_map_z[k2] + k1;
					textured_triangle_count++;
				}

				i1 += model_1.textured_triangle_count;
			}
		}

		method466();
	}

	public void method464(Model model, boolean flag) {
		vertexCount = model.vertexCount;
		triangle_count = model.triangle_count;
		textured_triangle_count = model.textured_triangle_count;
		if (anIntArray1622.length < vertexCount) {
			anIntArray1622 = new int[vertexCount + 10000];
			anIntArray1623 = new int[vertexCount + 10000];
			anIntArray1624 = new int[vertexCount + 10000];
		}
		vertexX = anIntArray1622;
		vertexY = anIntArray1623;
		vertexZ = anIntArray1624;
		for (int k = 0; k < vertexCount; k++) {
			vertexX[k] = model.vertexX[k];
			vertexY[k] = model.vertexY[k];
			vertexZ[k] = model.vertexZ[k];
		}

		if (flag) {
			alpha = model.alpha;
		} else {
			if (anIntArray1625.length < triangle_count) {
				anIntArray1625 = new int[triangle_count + 100];
			}
			alpha = anIntArray1625;
			if (model.alpha == null) {
				for (int l = 0; l < triangle_count; l++) {
					alpha[l] = 0;
				}

			} else {
				for (int i1 = 0; i1 < triangle_count; i1++) {
					alpha[i1] = model.alpha[i1];
				}

			}
		}
		face_fill_attributes = model.face_fill_attributes;
		colors = model.colors;
		priorities = model.priorities;
		anInt1641 = model.anInt1641;
		triangleSkin = model.triangleSkin;
		vertexSkin = model.vertexSkin;
		triangle_viewspace_x = model.triangle_viewspace_x;
		triangle_viewspace_y = model.triangle_viewspace_y;
		triangle_viewspace_z = model.triangle_viewspace_z;
		face_shade_a = model.face_shade_a;
		face_shade_b = model.face_shade_b;
		face_shade_c = model.face_shade_c;
		texture_map_x = model.texture_map_x;
		texture_map_y = model.texture_map_y;
		texture_map_z = model.texture_map_z;
	}

	private final int method465(Model model, int i) {
		int j = -1;
		int k = model.vertexX[i];
		int l = model.vertexY[i];
		int i1 = model.vertexZ[i];
		for (int j1 = 0; j1 < vertexCount; j1++) {
			if (k != vertexX[j1] || l != vertexY[j1] || i1 != vertexZ[j1]) {
				continue;
			}
			j = j1;
			break;
		}

		if (j == -1) {
			vertexX[vertexCount] = k;
			vertexY[vertexCount] = l;
			vertexZ[vertexCount] = i1;
			if (model.vertex_skin_types != null) {
				vertex_skin_types[vertexCount] = model.vertex_skin_types[i];
			}
			j = vertexCount++;
		}
		return j;
	}

	public void method466() {
		super.modelHeight = 0;
		anInt1650 = 0;
		anInt1651 = 0;
		for (int i = 0; i < vertexCount; i++) {
			int j = vertexX[i];
			int k = vertexY[i];
			int l = vertexZ[i];
			if (-k > super.modelHeight) {
				super.modelHeight = -k;
			}
			if (k > anInt1651) {
				anInt1651 = k;
			}
			int i1 = j * j + l * l;
			if (i1 > anInt1650) {
				anInt1650 = i1;
			}
		}
		anInt1650 = (int) (Math.sqrt(anInt1650) + 0.98999999999999999D);
		anInt1653 = (int) (Math.sqrt(anInt1650 * anInt1650 + super.modelHeight * super.modelHeight)
				+ 0.98999999999999999D);
		diagonal3D = anInt1653
				+ (int) (Math.sqrt(anInt1650 * anInt1650 + anInt1651 * anInt1651) + 0.98999999999999999D);
	}

	public void method467() {
		super.modelHeight = 0;
		anInt1651 = 0;
		for (int i = 0; i < vertexCount; i++) {
			int j = vertexY[i];
			if (-j > super.modelHeight) {
				super.modelHeight = -j;
			}
			if (j > anInt1651) {
				anInt1651 = j;
			}
		}

		anInt1653 = (int) (Math.sqrt(anInt1650 * anInt1650 + super.modelHeight * super.modelHeight)
				+ 0.98999999999999999D);
		diagonal3D = anInt1653
				+ (int) (Math.sqrt(anInt1650 * anInt1650 + anInt1651 * anInt1651) + 0.98999999999999999D);
	}

	private void method468(int i) {
		super.modelHeight = 0;
		anInt1650 = 0;
		anInt1651 = 0;
		anInt1646 = 0xf423f;
		anInt1647 = 0xfff0bdc1;
		anInt1648 = 0xfffe7961;
		anInt1649 = 0x1869f;
		for (int j = 0; j < vertexCount; j++) {
			int k = vertexX[j];
			int l = vertexY[j];
			int i1 = vertexZ[j];
			if (k < anInt1646) {
				anInt1646 = k;
			}
			if (k > anInt1647) {
				anInt1647 = k;
			}
			if (i1 < anInt1649) {
				anInt1649 = i1;
			}
			if (i1 > anInt1648) {
				anInt1648 = i1;
			}
			if (-l > super.modelHeight) {
				super.modelHeight = -l;
			}
			if (l > anInt1651) {
				anInt1651 = l;
			}
			int j1 = k * k + i1 * i1;
			if (j1 > anInt1650) {
				anInt1650 = j1;
			}
		}

		anInt1650 = (int) Math.sqrt(anInt1650);
		anInt1653 = (int) Math.sqrt(anInt1650 * anInt1650 + super.modelHeight * super.modelHeight);
		if (i != 21073) {
			return;
		} else {
			diagonal3D = anInt1653 + (int) Math.sqrt(anInt1650 * anInt1650 + anInt1651 * anInt1651);
			return;
		}
	}

	public void createBones() {
		if (vertex_skin_types != null) {
			int ai[] = new int[256];
			int j = 0;
			for (int l = 0; l < vertexCount; l++) {
				int j1 = vertex_skin_types[l];
				ai[j1]++;
				if (j1 > j) {
					j = j1;
				}
			}

			vertexSkin = new int[j + 1][];
			for (int k1 = 0; k1 <= j; k1++) {
				vertexSkin[k1] = new int[ai[k1]];
				ai[k1] = 0;
			}

			for (int j2 = 0; j2 < vertexCount; j2++) {
				int l2 = vertex_skin_types[j2];
				vertexSkin[l2][ai[l2]++] = j2;
			}

			vertex_skin_types = null;
		}
		if (triangle_skin_types != null) {
			int ai1[] = new int[256];
			int k = 0;
			for (int i1 = 0; i1 < triangle_count; i1++) {
				int l1 = triangle_skin_types[i1];
				ai1[l1]++;
				if (l1 > k) {
					k = l1;
				}
			}

			triangleSkin = new int[k + 1][];
			for (int i2 = 0; i2 <= k; i2++) {
				triangleSkin[i2] = new int[ai1[i2]];
				ai1[i2] = 0;
			}

			for (int k2 = 0; k2 < triangle_count; k2++) {
				int i3 = triangle_skin_types[k2];
				triangleSkin[i3][ai1[i3]++] = k2;
			}

			triangle_skin_types = null;
		}
	}

	public void applyTransform(int i) {
		if (vertexSkin == null) {
			return;
		}
		if (i == -1) {
			return;
		}
		FrameReader class36 = FrameReader.forId(i);
		if (class36 == null) {
			return;
		}
		SkinList class18 = class36.skinList;
		anInt1681 = 0;
		anInt1682 = 0;
		anInt1683 = 0;
		for (int k = 0; k < class36.stepCount; k++) {
			int l = class36.opcodeLinkTable[k];
			method472(class18.opcodes[l], class18.skinList[l], class36.xOffset[k], class36.yOffset[k],
					class36.zOffset[k]);
		}

	}

	public void interpolateFrames(int firstFrame, int nextFrame, int end, int cycle) {
		if (!Configuration.TWEENING_ENABLED) {
			applyTransform(nextFrame);
			return;
		}
		try {
			if (vertexSkin != null && firstFrame != -1) {
				FrameReader currentAnimation = FrameReader.forId(firstFrame);
				if (currentAnimation == null) {
					applyTransform(nextFrame);
					return;
				}
				SkinList list1 = currentAnimation.skinList;
				anInt1681 = 0;
				anInt1682 = 0;
				anInt1683 = 0;
				FrameReader nextAnimation = null;
				SkinList list2 = null;
				if (nextFrame != -1) {
					nextAnimation = FrameReader.forId(nextFrame);
					if (nextAnimation.skinList != list1)
						nextAnimation = null;
					list2 = nextAnimation.skinList;
				}
				if (nextAnimation == null || list2 == null) {
					for (int i_263_ = 0; i_263_ < currentAnimation.stepCount; i_263_++) {
						int i_264_ = currentAnimation.opcodeLinkTable[i_263_];
						method472(list1.opcodes[i_264_], list1.skinList[i_264_], currentAnimation.xOffset[i_263_],
								currentAnimation.yOffset[i_263_], currentAnimation.zOffset[i_263_]);

					}
				} else {
					for (int i1 = 0; i1 < currentAnimation.stepCount; i1++) {
						int n1 = currentAnimation.opcodeLinkTable[i1];
						int opcode = list1.opcodes[n1];
						int[] skin = list1.skinList[n1];
						int x = currentAnimation.xOffset[i1];
						int y = currentAnimation.yOffset[i1];
						int z = currentAnimation.zOffset[i1];
						boolean found = false;
						for (int i2 = 0; i2 < nextAnimation.stepCount; i2++) {
							int n2 = nextAnimation.opcodeLinkTable[i2];
							if (list2.skinList[n2].equals(skin)) {
								if (opcode != 2) {
									x += (nextAnimation.xOffset[i2] - x) * cycle / end;
									y += (nextAnimation.yOffset[i2] - y) * cycle / end;
									z += (nextAnimation.zOffset[i2] - z) * cycle / end;
								} else {
									x &= 0xff;
									y &= 0xff;
									z &= 0xff;
									int dx = nextAnimation.xOffset[i2] - x & 0xff;
									int dy = nextAnimation.yOffset[i2] - y & 0xff;
									int dz = nextAnimation.zOffset[i2] - z & 0xff;
									if (dx >= 128) {
										dx -= 256;
									}
									if (dy >= 128) {
										dy -= 256;
									}
									if (dz >= 128) {
										dz -= 256;
									}
									x = x + dx * cycle / end & 0xff;
									y = y + dy * cycle / end & 0xff;
									z = z + dz * cycle / end & 0xff;
								}
								found = true;
								break;
							}
						}
						if (!found) {
							if (opcode != 3 && opcode != 2) {
								x = x * (end - cycle) / end;
								y = y * (end - cycle) / end;
								z = z * (end - cycle) / end;
							} else if (opcode == 3) {
								x = (x * (end - cycle) + (cycle << 7)) / end;
								y = (y * (end - cycle) + (cycle << 7)) / end;
								z = (z * (end - cycle) + (cycle << 7)) / end;
							} else {
								x &= 0xff;
								y &= 0xff;
								z &= 0xff;
								int dx = -x & 0xff;
								int dy = -y & 0xff;
								int dz = -z & 0xff;
								if (dx >= 128) {
									dx -= 256;
								}
								if (dy >= 128) {
									dy -= 256;
								}
								if (dz >= 128) {
									dz -= 256;
								}
								x = x + dx * cycle / end & 0xff;
								y = y + dy * cycle / end & 0xff;
								z = z + dz * cycle / end & 0xff;
							}
						}
						method472(opcode, skin, x, y, z);
					}
				}
			}

		} catch (Exception e) {
			// e.printStackTrace();
			applyTransform(firstFrame);
		}
	}

	public void method471(int ai[], int j, int k) {
		if (k == -1) {
			return;
		}
		if (ai == null || j == -1) {
			applyTransform(k);
			return;
		}
		FrameReader class36 = FrameReader.forId(k);
		if (class36 == null) {
			return;
		}
		FrameReader class36_1 = FrameReader.forId(j);
		if (class36_1 == null) {
			applyTransform(k);
			return;
		}
		SkinList class18 = class36.skinList;
		anInt1681 = 0;
		anInt1682 = 0;
		anInt1683 = 0;
		int l = 0;
		int i1 = ai[l++];
		for (int j1 = 0; j1 < class36.stepCount; j1++) {
			int k1;
			for (k1 = class36.opcodeLinkTable[j1]; k1 > i1; i1 = ai[l++]) {
				;
			}
			if (k1 != i1 || class18.opcodes[k1] == 0) {
				method472(class18.opcodes[k1], class18.skinList[k1], class36.xOffset[j1], class36.yOffset[j1],
						class36.zOffset[j1]);
			}
		}

		anInt1681 = 0;
		anInt1682 = 0;
		anInt1683 = 0;
		l = 0;
		i1 = ai[l++];
		for (int l1 = 0; l1 < class36_1.stepCount; l1++) {
			int i2;
			for (i2 = class36_1.opcodeLinkTable[l1]; i2 > i1; i1 = ai[l++]) {
				;
			}
			if (i2 == i1 || class18.opcodes[i2] == 0) {
				method472(class18.opcodes[i2], class18.skinList[i2], class36_1.xOffset[l1], class36_1.yOffset[l1],
						class36_1.zOffset[l1]);
			}
		}

	}

	private void method472(int i, int ai[], int j, int k, int l) {

		int i1 = ai.length;
		if (i == 0) {
			int j1 = 0;
			anInt1681 = 0;
			anInt1682 = 0;
			anInt1683 = 0;
			for (int k2 = 0; k2 < i1; k2++) {
				int l3 = ai[k2];
				if (l3 < vertexSkin.length) {
					int ai5[] = vertexSkin[l3];
					for (int j6 : ai5) {
						anInt1681 += vertexX[j6];
						anInt1682 += vertexY[j6];
						anInt1683 += vertexZ[j6];
						j1++;
					}

				}
			}

			if (j1 > 0) {
				anInt1681 = anInt1681 / j1 + j;
				anInt1682 = anInt1682 / j1 + k;
				anInt1683 = anInt1683 / j1 + l;
				return;
			} else {
				anInt1681 = j;
				anInt1682 = k;
				anInt1683 = l;
				return;
			}
		}
		if (i == 1) {
			for (int k1 = 0; k1 < i1; k1++) {
				int l2 = ai[k1];
				if (l2 < vertexSkin.length) {
					int ai1[] = vertexSkin[l2];
					for (int element : ai1) {
						int j5 = element;
						vertexX[j5] += j;
						vertexY[j5] += k;
						vertexZ[j5] += l;
					}

				}
			}

			return;
		}
		if (i == 2) {
			for (int l1 = 0; l1 < i1; l1++) {
				int i3 = ai[l1];
				if (i3 < vertexSkin.length) {
					int ai2[] = vertexSkin[i3];
					for (int element : ai2) {
						int k5 = element;
						vertexX[k5] -= anInt1681;
						vertexY[k5] -= anInt1682;
						vertexZ[k5] -= anInt1683;
						int k6 = (j & 0xff) * 8;
						int l6 = (k & 0xff) * 8;
						int i7 = (l & 0xff) * 8;
						if (i7 != 0) {
							int j7 = SINE[i7];
							int i8 = COSINE[i7];
							int l8 = vertexY[k5] * j7 + vertexX[k5] * i8 >> 16;
							vertexY[k5] = vertexY[k5] * i8 - vertexX[k5] * j7 >> 16;
							vertexX[k5] = l8;
						}
						if (k6 != 0) {
							int k7 = SINE[k6];
							int j8 = COSINE[k6];
							int i9 = vertexY[k5] * j8 - vertexZ[k5] * k7 >> 16;
							vertexZ[k5] = vertexY[k5] * k7 + vertexZ[k5] * j8 >> 16;
							vertexY[k5] = i9;
						}
						if (l6 != 0) {
							int l7 = SINE[l6];
							int k8 = COSINE[l6];
							int j9 = vertexZ[k5] * l7 + vertexX[k5] * k8 >> 16;
							vertexZ[k5] = vertexZ[k5] * k8 - vertexX[k5] * l7 >> 16;
							vertexX[k5] = j9;
						}
						vertexX[k5] += anInt1681;
						vertexY[k5] += anInt1682;
						vertexZ[k5] += anInt1683;
					}

				}
			}
			return;
		}
		if (i == 3) {
			for (int i2 = 0; i2 < i1; i2++) {
				int j3 = ai[i2];
				if (j3 < vertexSkin.length) {
					int ai3[] = vertexSkin[j3];
					for (int element : ai3) {
						int l5 = element;
						vertexX[l5] -= anInt1681;
						vertexY[l5] -= anInt1682;
						vertexZ[l5] -= anInt1683;
						vertexX[l5] = vertexX[l5] * j / 128;
						vertexY[l5] = vertexY[l5] * k / 128;
						vertexZ[l5] = vertexZ[l5] * l / 128;
						vertexX[l5] += anInt1681;
						vertexY[l5] += anInt1682;
						vertexZ[l5] += anInt1683;
					}
				}
			}
			return;
		}
		if (i == 5 && triangleSkin != null && alpha != null) {
			for (int j2 = 0; j2 < i1; j2++) {
				int k3 = ai[j2];
				if (k3 < triangleSkin.length) {
					int ai4[] = triangleSkin[k3];
					for (int element : ai4) {
						int i6 = element;
						alpha[i6] += j * 8;
						if (alpha[i6] < 0) {
							alpha[i6] = 0;
						}
						if (alpha[i6] > 255) {
							alpha[i6] = 255;
						}
					}
				}
			}
		}
	}

	public void method473() {
		for (int j = 0; j < vertexCount; j++) {
			int k = vertexX[j];
			vertexX[j] = vertexZ[j];
			vertexZ[j] = -k;
		}
	}

	public void rotateX(int i) {
		int k = SINE[i];
		int l = COSINE[i];

		for (int i1 = 0; i1 < vertexCount; i1++) {
			int j1 = vertexY[i1] * l - vertexZ[i1] * k >> 16;
			vertexZ[i1] = vertexY[i1] * k + vertexZ[i1] * l >> 16;
			vertexY[i1] = j1;
		}
	}

	public void translate(int i, int j, int l) {
		for (int i1 = 0; i1 < vertexCount; i1++) {
			vertexX[i1] += i;
			vertexY[i1] += j;
			vertexZ[i1] += l;
		}
	}

	public void method476(int i, int j) {
		for (int k = 0; k < triangle_count; k++) {
			if (colors[k] == i) {
				colors[k] = j;
			}
		}
	}

	public void method477() {
		for (int j = 0; j < vertexCount; j++) {
			vertexZ[j] = -vertexZ[j];
		}
		for (int k = 0; k < triangle_count; k++) {
			int l = triangle_viewspace_x[k];
			triangle_viewspace_x[k] = triangle_viewspace_z[k];
			triangle_viewspace_z[k] = l;
		}
	}

	public void scaleT(int i, int j, int l) {
		for (int i1 = 0; i1 < vertexCount; i1++) {
			vertexX[i1] = vertexX[i1] * i / 128;
			vertexY[i1] = vertexY[i1] * l / 128;
			vertexZ[i1] = vertexZ[i1] * j / 128;
		}

	}

	private final void removeColor(int color) {
		if (colors != null) {
			for (int triangle = 0; triangle < triangle_count; triangle++) {
				if (triangle < colors.length) {
					if (colors[triangle] == color) {
						triangle_viewspace_x[triangle] = 0;
						triangle_viewspace_y[triangle] = 0;
						triangle_viewspace_z[triangle] = 0;
					}
				}
			}
		}
	}

	public final void light(int i, int j, int k, int l, int i1, boolean flag) {
		int j1 = (int) Math.sqrt(k * k + l * l + i1 * i1);
		int k1 = j * j1 >> 8;
		if (face_shade_a == null) {
			face_shade_a = new int[triangle_count];
			face_shade_b = new int[triangle_count];
			face_shade_c = new int[triangle_count];
		}
		if (super.aClass33Array1425 == null) {
			super.aClass33Array1425 = new Class33[vertexCount];
			for (int l1 = 0; l1 < vertexCount; l1++) {
				super.aClass33Array1425[l1] = new Class33();
			}

		}
		removeColor(37798);
		for (int i2 = 0; i2 < triangle_count; i2++) {
			if (colors != null && alpha != null) {
				if (colors[i2] == 65535
						/*
						 * || (colors[i2] == 0 // Black Triangles 633 // Models
						 * - Fixes Gwd walls // & Black models )
						 */ || colors[i2] == 16705) {
					alpha[i2] = 255;
				}
			}
			int j2 = triangle_viewspace_x[i2];
			int l2 = triangle_viewspace_y[i2];
			int i3 = triangle_viewspace_z[i2];
			int j3 = vertexX[l2] - vertexX[j2];
			int k3 = vertexY[l2] - vertexY[j2];
			int l3 = vertexZ[l2] - vertexZ[j2];
			int i4 = vertexX[i3] - vertexX[j2];
			int j4 = vertexY[i3] - vertexY[j2];
			int k4 = vertexZ[i3] - vertexZ[j2];
			int l4 = k3 * k4 - j4 * l3;
			int i5 = l3 * i4 - k4 * j3;
			int j5;
			for (j5 = j3 * j4 - i4 * k3; l4 > 8192 || i5 > 8192 || j5 > 8192 || l4 < -8192 || i5 < -8192
					|| j5 < -8192; j5 >>= 1) {
				l4 >>= 1;
				i5 >>= 1;
			}

			int k5 = (int) Math.sqrt(l4 * l4 + i5 * i5 + j5 * j5);
			if (k5 <= 0) {
				k5 = 1;
			}
			l4 = l4 * 256 / k5;
			i5 = i5 * 256 / k5;
			j5 = j5 * 256 / k5;

			if (face_fill_attributes == null || (face_fill_attributes[i2] & 1) == 0) {

				Class33 class33_2 = super.aClass33Array1425[j2];
				class33_2.anInt602 += l4;
				class33_2.anInt603 += i5;
				class33_2.anInt604 += j5;
				class33_2.anInt605++;
				class33_2 = super.aClass33Array1425[l2];
				class33_2.anInt602 += l4;
				class33_2.anInt603 += i5;
				class33_2.anInt604 += j5;
				class33_2.anInt605++;
				class33_2 = super.aClass33Array1425[i3];
				class33_2.anInt602 += l4;
				class33_2.anInt603 += i5;
				class33_2.anInt604 += j5;
				class33_2.anInt605++;

			} else {

				int l5 = i + (k * l4 + l * i5 + i1 * j5) / (k1 + k1 / 2);
				face_shade_a[i2] = method481(colors[i2], l5, face_fill_attributes[i2]);

			}
		}

		if (flag) {
			method480(i, k1, k, l, i1);
		} else {
			aClass33Array1660 = new Class33[vertexCount];
			for (int k2 = 0; k2 < vertexCount; k2++) {
				Class33 class33 = super.aClass33Array1425[k2];
				Class33 class33_1 = aClass33Array1660[k2] = new Class33();
				class33_1.anInt602 = class33.anInt602;
				class33_1.anInt603 = class33.anInt603;
				class33_1.anInt604 = class33.anInt604;
				class33_1.anInt605 = class33.anInt605;
			}

		}
		if (flag) {
			method466();
			return;
		} else {
			method468(21073);
			return;
		}
	}

	public final void method480(int i, int j, int k, int l, int i1) {
		for (int j1 = 0; j1 < triangle_count; j1++) {
			int k1 = triangle_viewspace_x[j1];
			int i2 = triangle_viewspace_y[j1];
			int j2 = triangle_viewspace_z[j1];
			if (face_fill_attributes == null) {
				int i3 = colors[j1];
				Class33 class33 = super.aClass33Array1425[k1];
				int k2 = i + (k * class33.anInt602 + l * class33.anInt603 + i1 * class33.anInt604)
						/ (j * class33.anInt605);
				face_shade_a[j1] = method481(i3, k2, 0);
				class33 = super.aClass33Array1425[i2];
				k2 = i + (k * class33.anInt602 + l * class33.anInt603 + i1 * class33.anInt604) / (j * class33.anInt605);
				face_shade_b[j1] = method481(i3, k2, 0);
				class33 = super.aClass33Array1425[j2];
				k2 = i + (k * class33.anInt602 + l * class33.anInt603 + i1 * class33.anInt604) / (j * class33.anInt605);
				face_shade_c[j1] = method481(i3, k2, 0);
			} else if ((face_fill_attributes[j1] & 1) == 0) {
				int j3 = colors[j1];
				int k3 = face_fill_attributes[j1];
				Class33 class33_1 = super.aClass33Array1425[k1];
				int l2 = i + (k * class33_1.anInt602 + l * class33_1.anInt603 + i1 * class33_1.anInt604)
						/ (j * class33_1.anInt605);
				face_shade_a[j1] = method481(j3, l2, k3);
				class33_1 = super.aClass33Array1425[i2];
				l2 = i + (k * class33_1.anInt602 + l * class33_1.anInt603 + i1 * class33_1.anInt604)
						/ (j * class33_1.anInt605);
				face_shade_b[j1] = method481(j3, l2, k3);
				class33_1 = super.aClass33Array1425[j2];
				l2 = i + (k * class33_1.anInt602 + l * class33_1.anInt603 + i1 * class33_1.anInt604)
						/ (j * class33_1.anInt605);
				face_shade_c[j1] = method481(j3, l2, k3);
			}
		}

		super.aClass33Array1425 = null;
		aClass33Array1660 = null;
		vertex_skin_types = null;
		triangle_skin_types = null;
		if (face_fill_attributes != null) {
			for (int l1 = 0; l1 < triangle_count; l1++) {
				if ((face_fill_attributes[l1] & 2) == 2) {
					return;
				}
			}

		}
		colors = null;
	}

	public final void renderSingle(int j, int k, int l, int i1, int j1, int k1) {
		int i = 0;
		int l1 = Canvas3D.centerX;
		int i2 = Canvas3D.centerY;
		int j2 = SINE[i];
		int k2 = COSINE[i];
		int l2 = SINE[j];
		int i3 = COSINE[j];
		int j3 = SINE[k];
		int k3 = COSINE[k];
		int l3 = SINE[l];
		int i4 = COSINE[l];
		int j4 = j1 * l3 + k1 * i4 >> 16;
		for (int k4 = 0; k4 < vertexCount; k4++) {
			int l4 = vertexX[k4];
			int i5 = vertexY[k4];
			int j5 = vertexZ[k4];
			if (k != 0) {
				int k5 = i5 * j3 + l4 * k3 >> 16;
				i5 = i5 * k3 - l4 * j3 >> 16;
				l4 = k5;
			}
			if (i != 0) {
				int l5 = i5 * k2 - j5 * j2 >> 16;
				j5 = i5 * j2 + j5 * k2 >> 16;
				i5 = l5;
			}
			if (j != 0) {
				int i6 = j5 * l2 + l4 * i3 >> 16;
				j5 = j5 * i3 - l4 * l2 >> 16;
				l4 = i6;
			}
			l4 += i1;
			i5 += j1;
			j5 += k1;
			int j6 = i5 * i4 - j5 * l3 >> 16;
			j5 = i5 * l3 + j5 * i4 >> 16;
			i5 = j6;
			anIntArray1667[k4] = j5 - j4;
			if (j5 == 0) {
				return;
			}
			projected_vertex_x[k4] = l1 + (l4 << 9) / j5;
			projected_vertex_y[k4] = i2 + (i5 << 9) / j5;
			if (textured_triangle_count > 0) {
				camera_vertex_y[k4] = l4;
				camera_vertex_x[k4] = i5;
				camera_vertex_z[k4] = j5;
			}
		}

		try {
			translateToScreen(false, false, 0, 0);
			return;
		} catch (Exception _ex) {
			_ex.printStackTrace();
			return;
		}
	}

	@Override
	public void method443(int i, int j, int k, int l, int i1, int j1, int k1, int l1, int uid, int newuid) {
		int j2 = l1 * i1 - j1 * l >> 16;
		int k2 = k1 * j + j2 * k >> 16;
		int l2 = anInt1650 * k >> 16;
		int i3 = k2 + l2;
		if (i3 <= 50 || k2 >= 3500) {
			return;
		}
		int j3 = l1 * l + j1 * i1 >> 16;
		int k3 = j3 - anInt1650 << Client.log_view_dist;
		if (k3 / i3 >= Canvas2D.centerY) {
			return;
		}
		int l3 = j3 + anInt1650 << Client.log_view_dist;
		if (l3 / i3 <= -Canvas2D.centerY) {
			return;
		}
		int i4 = k1 * k - j2 * j >> 16;
		int j4 = anInt1650 * j >> 16;
		int k4 = i4 + j4 << Client.log_view_dist;
		if (k4 / i3 <= -Canvas2D.middleY) {
			return;
		}
		int l4 = j4 + (super.modelHeight * k >> 16);
		int i5 = i4 - l4 << Client.log_view_dist;
		if (i5 / i3 >= Canvas2D.middleY) {
			return;
		}
		int j5 = l2 + (super.modelHeight * j >> 16);
		boolean flag = false;
		if (k2 - j5 <= 50) {
			flag = true;
		}
		boolean flag1 = false;
		if (uid > 0 && aBoolean1684) {
			int k5 = k2 - l2;
			if (k5 <= 50) {
				k5 = 50;
			}
			if (j3 > 0) {
				k3 /= i3;
				l3 /= k5;
			} else {
				l3 /= i3;
				k3 /= k5;
			}
			if (i4 > 0) {
				i5 /= i3;
				k4 /= k5;
			} else {
				k4 /= i3;
				i5 /= k5;
			}
			int i6 = anInt1685 - Canvas3D.centerX;
			int k6 = anInt1686 - Canvas3D.centerY;
			if (i6 > k3 && i6 < l3 && k6 > i5 && k6 < k4) {
				if (aBoolean1659) {
					mapObjIds[anInt1687] = newuid;
					anIntArray1688[anInt1687++] = uid;
				} else {
					flag1 = true;
				}
			}
		}
		int l5 = Canvas3D.centerX;
		int j6 = Canvas3D.centerY;
		int l6 = 0;
		int i7 = 0;
		if (i != 0) {
			l6 = SINE[i];
			i7 = COSINE[i];
		}
		for (int j7 = 0; j7 < vertexCount; j7++) {
			int k7 = vertexX[j7];
			int l7 = vertexY[j7];
			int i8 = vertexZ[j7];
			if (i != 0) {
				int j8 = i8 * l6 + k7 * i7 >> 16;
				i8 = i8 * i7 - k7 * l6 >> 16;
				k7 = j8;
			}
			k7 += j1;
			l7 += k1;
			i8 += l1;
			int k8 = i8 * l + k7 * i1 >> 16;
			i8 = i8 * i1 - k7 * l >> 16;
			k7 = k8;
			k8 = l7 * k - i8 * j >> 16;
			i8 = l7 * j + i8 * k >> 16;
			l7 = k8;
			anIntArray1667[j7] = i8 - k2;
			if (i8 >= 50) {
				projected_vertex_x[j7] = l5 + (k7 << Client.log_view_dist) / i8;
				projected_vertex_y[j7] = j6 + (l7 << Client.log_view_dist) / i8;
			} else {
				projected_vertex_x[j7] = -5000;
				flag = true;
			}
			if (flag || textured_triangle_count > 0) {
				camera_vertex_y[j7] = k7;
				camera_vertex_x[j7] = l7;
				camera_vertex_z[j7] = i8;
			} else if (fog) {
				camera_vertex_z[j7] = i8;
			}
		}
		try {
			translateToScreen(flag, flag1, uid, newuid);
			return;
		} catch (Exception _ex) {
			return;
		}
	}

	public static boolean fog;

	private final void translateToScreen(boolean flag, boolean flag1, int i, int id) {
		for (int j = 0; j < diagonal3D; j++) {
			depthListIndices[j] = 0;
		}

		for (int k = 0; k < triangle_count; k++) {
			if (face_fill_attributes == null || face_fill_attributes[k] != -1) {
				int l = triangle_viewspace_x[k];
				int k1 = triangle_viewspace_y[k];
				int j2 = triangle_viewspace_z[k];
				int i3 = projected_vertex_x[l];
				int l3 = projected_vertex_x[k1];
				int k4 = projected_vertex_x[j2];
				if (flag && (i3 == -5000 || l3 == -5000 || k4 == -5000)) {
					outOfReach[k] = true;
					int j5 = (anIntArray1667[l] + anIntArray1667[k1] + anIntArray1667[j2]) / 3 + anInt1653;
					faceLists[j5][depthListIndices[j5]++] = k;
				} else {
					if (flag1 && method486(anInt1685, anInt1686, projected_vertex_y[l], projected_vertex_y[k1],
							projected_vertex_y[j2], i3, l3, k4)) {
						mapObjIds[anInt1687] = id;
						anIntArray1688[anInt1687++] = i;
						flag1 = false;
					}
					if ((i3 - l3) * (projected_vertex_y[j2] - projected_vertex_y[k1])
							- (projected_vertex_y[l] - projected_vertex_y[k1]) * (k4 - l3) > 0) {
						outOfReach[k] = false;
						if (i3 < 0 || l3 < 0 || k4 < 0 || i3 > Canvas2D.centerX || l3 > Canvas2D.centerX
								|| k4 > Canvas2D.centerX) {
							aBooleanArray1663[k] = true;
						} else {
							aBooleanArray1663[k] = false;
						}
						int k5 = (anIntArray1667[l] + anIntArray1667[k1] + anIntArray1667[j2]) / 3 + anInt1653;
						faceLists[k5][depthListIndices[k5]++] = k;
					}
				}
			}
		}

		if (priorities == null) {
			for (int i1 = diagonal3D - 1; i1 >= 0; i1--) {
				int l1 = depthListIndices[i1];
				if (l1 > 0) {
					int ai[] = faceLists[i1];
					for (int j3 = 0; j3 < l1; j3++) {
						rasterise(ai[j3]);
					}

				}
			}

			return;
		}
		for (int j1 = 0; j1 < 12; j1++) {
			anIntArray1673[j1] = 0;
			anIntArray1677[j1] = 0;
		}

		for (int i2 = diagonal3D - 1; i2 >= 0; i2--) {
			int k2 = depthListIndices[i2];
			if (k2 > 0) {
				int ai1[] = faceLists[i2];
				for (int i4 = 0; i4 < k2; i4++) {
					int l4 = ai1[i4];
					int l5 = priorities[l4];
					int j6 = anIntArray1673[l5]++;
					anIntArrayArray1674[l5][j6] = l4;
					if (l5 < 10) {
						anIntArray1677[l5] += i2;
					} else if (l5 == 10) {
						anIntArray1675[j6] = i2;
					} else {
						anIntArray1676[j6] = i2;
					}
				}

			}
		}

		int l2 = 0;
		if (anIntArray1673[1] > 0 || anIntArray1673[2] > 0) {
			l2 = (anIntArray1677[1] + anIntArray1677[2]) / (anIntArray1673[1] + anIntArray1673[2]);
		}
		int k3 = 0;
		if (anIntArray1673[3] > 0 || anIntArray1673[4] > 0) {
			k3 = (anIntArray1677[3] + anIntArray1677[4]) / (anIntArray1673[3] + anIntArray1673[4]);
		}
		int j4 = 0;
		if (anIntArray1673[6] > 0 || anIntArray1673[8] > 0) {
			j4 = (anIntArray1677[6] + anIntArray1677[8]) / (anIntArray1673[6] + anIntArray1673[8]);
		}
		int i6 = 0;
		int k6 = anIntArray1673[10];
		int ai2[] = anIntArrayArray1674[10];
		int ai3[] = anIntArray1675;
		if (i6 == k6) {
			i6 = 0;
			k6 = anIntArray1673[11];
			ai2 = anIntArrayArray1674[11];
			ai3 = anIntArray1676;
		}
		int i5;
		if (i6 < k6) {
			i5 = ai3[i6];
		} else {
			i5 = -1000;
		}
		for (int l6 = 0; l6 < 10; l6++) {
			while (l6 == 0 && i5 > l2) {
				rasterise(ai2[i6++]);
				if (i6 == k6 && ai2 != anIntArrayArray1674[11]) {
					i6 = 0;
					k6 = anIntArray1673[11];
					ai2 = anIntArrayArray1674[11];
					ai3 = anIntArray1676;
				}
				if (i6 < k6) {
					i5 = ai3[i6];
				} else {
					i5 = -1000;
				}
			}
			while (l6 == 3 && i5 > k3) {
				rasterise(ai2[i6++]);
				if (i6 == k6 && ai2 != anIntArrayArray1674[11]) {
					i6 = 0;
					k6 = anIntArray1673[11];
					ai2 = anIntArrayArray1674[11];
					ai3 = anIntArray1676;
				}
				if (i6 < k6) {
					i5 = ai3[i6];
				} else {
					i5 = -1000;
				}
			}
			while (l6 == 5 && i5 > j4) {
				rasterise(ai2[i6++]);
				if (i6 == k6 && ai2 != anIntArrayArray1674[11]) {
					i6 = 0;
					k6 = anIntArray1673[11];
					ai2 = anIntArrayArray1674[11];
					ai3 = anIntArray1676;
				}
				if (i6 < k6) {
					i5 = ai3[i6];
				} else {
					i5 = -1000;
				}
			}
			int i7 = anIntArray1673[l6];
			int ai4[] = anIntArrayArray1674[l6];
			for (int j7 = 0; j7 < i7; j7++) {
				rasterise(ai4[j7]);
			}

		}

		while (i5 != -1000) {
			rasterise(ai2[i6++]);
			if (i6 == k6 && ai2 != anIntArrayArray1674[11]) {
				i6 = 0;
				ai2 = anIntArrayArray1674[11];
				k6 = anIntArray1673[11];
				ai3 = anIntArray1676;
			}
			if (i6 < k6) {
				i5 = ai3[i6];
			} else {
				i5 = -1000;
			}
		}
	}

	private final void rasterise(int i) {
		if (outOfReach[i]) {
			reduce(i);
			return;
		}
		int j = triangle_viewspace_x[i];
		int k = triangle_viewspace_y[i];
		int l = triangle_viewspace_z[i];
		Canvas3D.restrict_edges = aBooleanArray1663[i];
		if (alpha == null) {
			Canvas3D.alpha = 0;
		} else {
			Canvas3D.alpha = alpha[i];
		}
		int i1;
		if (face_fill_attributes == null) {
			i1 = 0;
		} else {
			i1 = face_fill_attributes[i] & 3;
		}
		if (i1 == 0) {
			Canvas3D.drawShadedTriangle(projected_vertex_y[j], projected_vertex_y[k], projected_vertex_y[l],
					projected_vertex_x[j], projected_vertex_x[k], projected_vertex_x[l], face_shade_a[i],
					face_shade_b[i], face_shade_c[i]);
			if (fog) {
				Canvas3D.drawFogTriangle(projected_vertex_y[j], projected_vertex_y[k], projected_vertex_y[l],
						projected_vertex_x[j], projected_vertex_x[k], projected_vertex_x[l], camera_vertex_z[j],
						camera_vertex_z[k], camera_vertex_z[l]);
			}
		} else if (i1 == 1) {
			Canvas3D.drawFlatTriangle(projected_vertex_y[j], projected_vertex_y[k], projected_vertex_y[l],
					projected_vertex_x[j], projected_vertex_x[k], projected_vertex_x[l], hsl2rgb[face_shade_a[i]]);
			if (fog) {
				Canvas3D.drawFogTriangle(projected_vertex_y[j], projected_vertex_y[k], projected_vertex_y[l],
						projected_vertex_x[j], projected_vertex_x[k], projected_vertex_x[l], camera_vertex_z[j],
						camera_vertex_z[k], camera_vertex_z[l]);
			}
		} else if (i1 == 2) {
			int j1 = face_fill_attributes[i] >> 2;
			int l1 = texture_map_x[j1];
			int j2 = texture_map_y[j1];
			int l2 = texture_map_z[j1];
			Canvas3D.drawTexturedTriangle(projected_vertex_y[j], projected_vertex_y[k], projected_vertex_y[l],
					projected_vertex_x[j], projected_vertex_x[k], projected_vertex_x[l], face_shade_a[i],
					face_shade_b[i], face_shade_c[i], camera_vertex_y[l1], camera_vertex_y[j2], camera_vertex_y[l2],
					camera_vertex_x[l1], camera_vertex_x[j2], camera_vertex_x[l2], camera_vertex_z[l1],
					camera_vertex_z[j2], camera_vertex_z[l2], colors[i]);
			if (fog) {
				Canvas3D.drawTexturedFogTriangle(projected_vertex_y[j], projected_vertex_y[k], projected_vertex_y[l],
						projected_vertex_x[j], projected_vertex_x[k], projected_vertex_x[l], camera_vertex_z[j],
						camera_vertex_z[k], camera_vertex_z[l], camera_vertex_y[l1], camera_vertex_y[j2],
						camera_vertex_y[l2], camera_vertex_x[l1], camera_vertex_x[j2], camera_vertex_x[l2],
						camera_vertex_z[l1], camera_vertex_z[j2], camera_vertex_z[l2], colors[i]);
			}
		} else if (i1 == 3) {
			int k1 = face_fill_attributes[i] >> 2;
			int i2 = texture_map_x[k1];
			int k2 = texture_map_y[k1];
			int i3 = texture_map_z[k1];
			Canvas3D.drawTexturedTriangle(projected_vertex_y[j], projected_vertex_y[k], projected_vertex_y[l],
					projected_vertex_x[j], projected_vertex_x[k], projected_vertex_x[l], face_shade_a[i],
					face_shade_a[i], face_shade_a[i], camera_vertex_y[i2], camera_vertex_y[k2], camera_vertex_y[i3],
					camera_vertex_x[i2], camera_vertex_x[k2], camera_vertex_x[i3], camera_vertex_z[i2],
					camera_vertex_z[k2], camera_vertex_z[i3], colors[i]);
			if (fog) {
				Canvas3D.drawTexturedFogTriangle(projected_vertex_y[j], projected_vertex_y[k], projected_vertex_y[l],
						projected_vertex_x[j], projected_vertex_x[k], projected_vertex_x[l], camera_vertex_z[j],
						camera_vertex_z[k], camera_vertex_z[l], camera_vertex_y[i2], camera_vertex_y[k2],
						camera_vertex_y[i3], camera_vertex_x[i2], camera_vertex_x[k2], camera_vertex_x[i3],
						camera_vertex_z[i2], camera_vertex_z[k2], camera_vertex_z[i3], colors[i]);
			}
		}
	}

	private final void reduce(int i) {
		if (colors != null) {
			if (colors[i] == 65535) {
				return;
			}
		}
		int j = Canvas3D.centerX;
		int k = Canvas3D.centerY;
		int l = 0;
		int i1 = triangle_viewspace_x[i];
		int j1 = triangle_viewspace_y[i];
		int k1 = triangle_viewspace_z[i];
		int l1 = camera_vertex_z[i1];
		int i2 = camera_vertex_z[j1];
		int j2 = camera_vertex_z[k1];

		if (l1 >= 50) {
			anIntArray1678[l] = projected_vertex_x[i1];
			anIntArray1679[l] = projected_vertex_y[i1];
			anIntArray1680[l++] = face_shade_a[i];
		} else {
			int k2 = camera_vertex_y[i1];
			int k3 = camera_vertex_x[i1];
			int k4 = face_shade_a[i];
			if (j2 >= 50) {
				int k5 = (50 - l1) * lightDecay[j2 - l1];
				anIntArray1678[l] = j + (k2 + ((camera_vertex_y[k1] - k2) * k5 >> 16) << Client.log_view_dist) / 50;
				anIntArray1679[l] = k + (k3 + ((camera_vertex_x[k1] - k3) * k5 >> 16) << Client.log_view_dist) / 50;
				anIntArray1680[l++] = k4 + ((face_shade_c[i] - k4) * k5 >> 16);
			}
			if (i2 >= 50) {
				int l5 = (50 - l1) * lightDecay[i2 - l1];
				anIntArray1678[l] = j + (k2 + ((camera_vertex_y[j1] - k2) * l5 >> 16) << Client.log_view_dist) / 50;
				anIntArray1679[l] = k + (k3 + ((camera_vertex_x[j1] - k3) * l5 >> 16) << Client.log_view_dist) / 50;
				anIntArray1680[l++] = k4 + ((face_shade_b[i] - k4) * l5 >> 16);
			}
		}
		if (i2 >= 50) {
			anIntArray1678[l] = projected_vertex_x[j1];
			anIntArray1679[l] = projected_vertex_y[j1];
			anIntArray1680[l++] = face_shade_b[i];
		} else {
			int l2 = camera_vertex_y[j1];
			int l3 = camera_vertex_x[j1];
			int l4 = face_shade_b[i];
			if (l1 >= 50) {
				int i6 = (50 - i2) * lightDecay[l1 - i2];
				anIntArray1678[l] = j + (l2 + ((camera_vertex_y[i1] - l2) * i6 >> 16) << Client.log_view_dist) / 50;
				anIntArray1679[l] = k + (l3 + ((camera_vertex_x[i1] - l3) * i6 >> 16) << Client.log_view_dist) / 50;
				anIntArray1680[l++] = l4 + ((face_shade_a[i] - l4) * i6 >> 16);
			}
			if (j2 >= 50) {
				int j6 = (50 - i2) * lightDecay[j2 - i2];
				anIntArray1678[l] = j + (l2 + ((camera_vertex_y[k1] - l2) * j6 >> 16) << Client.log_view_dist) / 50;
				anIntArray1679[l] = k + (l3 + ((camera_vertex_x[k1] - l3) * j6 >> 16) << Client.log_view_dist) / 50;
				anIntArray1680[l++] = l4 + ((face_shade_c[i] - l4) * j6 >> 16);
			}
		}
		if (j2 >= 50) {
			anIntArray1678[l] = projected_vertex_x[k1];
			anIntArray1679[l] = projected_vertex_y[k1];
			anIntArray1680[l++] = face_shade_c[i];
		} else {
			int i3 = camera_vertex_y[k1];
			int i4 = camera_vertex_x[k1];
			int i5 = face_shade_c[i];
			if (i2 >= 50) {
				int k6 = (50 - j2) * lightDecay[i2 - j2];
				anIntArray1678[l] = j + (i3 + ((camera_vertex_y[j1] - i3) * k6 >> 16) << Client.log_view_dist) / 50;
				anIntArray1679[l] = k + (i4 + ((camera_vertex_x[j1] - i4) * k6 >> 16) << Client.log_view_dist) / 50;
				anIntArray1680[l++] = i5 + ((face_shade_b[i] - i5) * k6 >> 16);
			}
			if (l1 >= 50) {
				int l6 = (50 - j2) * lightDecay[l1 - j2];
				anIntArray1678[l] = j + (i3 + ((camera_vertex_y[i1] - i3) * l6 >> 16) << Client.log_view_dist) / 50;
				anIntArray1679[l] = k + (i4 + ((camera_vertex_x[i1] - i4) * l6 >> 16) << Client.log_view_dist) / 50;
				anIntArray1680[l++] = i5 + ((face_shade_a[i] - i5) * l6 >> 16);
			}
		}
		int j3 = anIntArray1678[0];
		int j4 = anIntArray1678[1];
		int j5 = anIntArray1678[2];
		int i7 = anIntArray1679[0];
		int j7 = anIntArray1679[1];
		int k7 = anIntArray1679[2];
		if ((j3 - j4) * (k7 - j7) - (i7 - j7) * (j5 - j4) > 0) {
			Canvas3D.restrict_edges = false;
			if (l == 3) {
				if (j3 < 0 || j4 < 0 || j5 < 0 || j3 > Canvas2D.centerX || j4 > Canvas2D.centerX
						|| j5 > Canvas2D.centerX) {
					Canvas3D.restrict_edges = true;
				}
				int meshType;
				if (face_fill_attributes == null) {
					meshType = 0;
				} else {
					meshType = face_fill_attributes[i] & 3;
				}
				if (meshType == 0) {
					Canvas3D.drawShadedTriangle(i7, j7, k7, j3, j4, j5, anIntArray1680[0], anIntArray1680[1],
							anIntArray1680[2]);
				} else if (meshType == 1) {
					Canvas3D.drawFlatTriangle(i7, j7, k7, j3, j4, j5, hsl2rgb[face_shade_a[i]]);
				} else if (meshType == 2) {
					int j8 = face_fill_attributes[i] >> 2;
					int k9 = texture_map_x[j8];
					int k10 = texture_map_y[j8];
					int k11 = texture_map_z[j8];
					Canvas3D.drawTexturedTriangle(i7, j7, k7, j3, j4, j5, anIntArray1680[0], anIntArray1680[1],
							anIntArray1680[2], camera_vertex_y[k9], camera_vertex_y[k10], camera_vertex_y[k11],
							camera_vertex_x[k9], camera_vertex_x[k10], camera_vertex_x[k11], camera_vertex_z[k9],
							camera_vertex_z[k10], camera_vertex_z[k11], colors[i]);
				} else if (meshType == 3) {
					int k8 = face_fill_attributes[i] >> 2;
					int l9 = texture_map_x[k8];
					int l10 = texture_map_y[k8];
					int l11 = texture_map_z[k8];
					Canvas3D.drawTexturedTriangle(i7, j7, k7, j3, j4, j5, face_shade_a[i], face_shade_a[i],
							face_shade_a[i], camera_vertex_y[l9], camera_vertex_y[l10], camera_vertex_y[l11],
							camera_vertex_x[l9], camera_vertex_x[l10], camera_vertex_x[l11], camera_vertex_z[l9],
							camera_vertex_z[l10], camera_vertex_z[l11], colors[i]);
				}
			}
			if (l == 4) {
				if (j3 < 0 || j4 < 0 || j5 < 0 || j3 > Canvas2D.centerX || j4 > Canvas2D.centerX
						|| j5 > Canvas2D.centerX || anIntArray1678[3] < 0 || anIntArray1678[3] > Canvas2D.centerX) {
					Canvas3D.restrict_edges = true;
				}
				int i8;
				if (face_fill_attributes == null) {
					i8 = 0;
				} else {
					i8 = face_fill_attributes[i] & 3;
				}
				if (i8 == 0) {
					Canvas3D.drawShadedTriangle(i7, j7, k7, j3, j4, j5, anIntArray1680[0], anIntArray1680[1],
							anIntArray1680[2]);
					Canvas3D.drawShadedTriangle(i7, k7, anIntArray1679[3], j3, j5, anIntArray1678[3], anIntArray1680[0],
							anIntArray1680[2], anIntArray1680[3]);
					return;
				}
				if (i8 == 1) {
					int l8 = hsl2rgb[face_shade_a[i]];
					Canvas3D.drawFlatTriangle(i7, j7, k7, j3, j4, j5, l8);
					Canvas3D.drawFlatTriangle(i7, k7, anIntArray1679[3], j3, j5, anIntArray1678[3], l8);
					return;
				}
				if (i8 == 2) {
					int i9 = face_fill_attributes[i] >> 2;
					int i10 = texture_map_x[i9];
					int i11 = texture_map_y[i9];
					int i12 = texture_map_z[i9];
					Canvas3D.drawTexturedTriangle(i7, j7, k7, j3, j4, j5, anIntArray1680[0], anIntArray1680[1],
							anIntArray1680[2], camera_vertex_y[i10], camera_vertex_y[i11], camera_vertex_y[i12],
							camera_vertex_x[i10], camera_vertex_x[i11], camera_vertex_x[i12], camera_vertex_z[i10],
							camera_vertex_z[i11], camera_vertex_z[i12], colors[i]);
					Canvas3D.drawTexturedTriangle(i7, k7, anIntArray1679[3], j3, j5, anIntArray1678[3],
							anIntArray1680[0], anIntArray1680[2], anIntArray1680[3], camera_vertex_y[i10],
							camera_vertex_y[i11], camera_vertex_y[i12], camera_vertex_x[i10], camera_vertex_x[i11],
							camera_vertex_x[i12], camera_vertex_z[i10], camera_vertex_z[i11], camera_vertex_z[i12],
							colors[i]);
					return;
				}
				if (i8 == 3) {
					int j9 = face_fill_attributes[i] >> 2;
					int j10 = texture_map_x[j9];
					int j11 = texture_map_y[j9];
					int j12 = texture_map_z[j9];
					Canvas3D.drawTexturedTriangle(i7, j7, k7, j3, j4, j5, face_shade_a[i], face_shade_a[i],
							face_shade_a[i], camera_vertex_y[j10], camera_vertex_y[j11], camera_vertex_y[j12],
							camera_vertex_x[j10], camera_vertex_x[j11], camera_vertex_x[j12], camera_vertex_z[j10],
							camera_vertex_z[j11], camera_vertex_z[j12], colors[i]);
					Canvas3D.drawTexturedTriangle(i7, k7, anIntArray1679[3], j3, j5, anIntArray1678[3], face_shade_a[i],
							face_shade_a[i], face_shade_a[i], camera_vertex_y[j10], camera_vertex_y[j11],
							camera_vertex_y[j12], camera_vertex_x[j10], camera_vertex_x[j11], camera_vertex_x[j12],
							camera_vertex_z[j10], camera_vertex_z[j11], camera_vertex_z[j12], colors[i]);
				}
			}
		}
	}

	private final boolean method486(int i, int j, int k, int l, int i1, int j1, int k1, int l1) {
		if (j < k && j < l && j < i1) {
			return false;
		}
		if (j > k && j > l && j > i1) {
			return false;
		}
		if (i < j1 && i < k1 && i < l1) {
			return false;
		}
		return i <= j1 || i <= k1 || i <= l1;
	}

	/*
	 * void read814Model(byte[] instream) { int maxDepth = 0; int numTriangles =
	 * 0; byte priority = (byte)0; int numTextureTriangles = 0; ByteBuffer
	 * class588_sub11 = new ByteBuffer(instream); ByteBuffer class588_sub11_156_
	 * = new ByteBuffer(instream); ByteBuffer class588_sub11_157_ = new
	 * ByteBuffer(instream); ByteBuffer class588_sub11_158_ = new
	 * ByteBuffer(instream); ByteBuffer class588_sub11_159_ = new
	 * ByteBuffer(instream); ByteBuffer class588_sub11_160_ = new
	 * ByteBuffer(instream); ByteBuffer class588_sub11_161_ = new
	 * ByteBuffer(instream); int type =
	 * class588_sub11.getUnsignedByte();//ReadUnsignedByte(); if (type != 1)
	 * System.out.println(type);//Console.WriteLine(type); else {
	 * class588_sub11.getUnsignedByte(); int version =
	 * class588_sub11.getUnsignedByte(); class588_sub11.bitPosition =
	 * instream.length - 26; int numVertices =
	 * class588_sub11.getUnsignedShort();//ReadUnsignedShort(); numTriangles =
	 * class588_sub11.getUnsignedShort(); numTextureTriangles =
	 * class588_sub11.getUnsignedShort(); int flags =
	 * class588_sub11.getUnsignedByte(); boolean bool_1 = (flags & 0x1) == 1;
	 * boolean bool_163_ = (flags & 0x2) == 2; boolean bool_164_ = (flags & 0x4)
	 * == 4; boolean bool_165_ = (flags & 0x10) == 16; boolean bool_166_ =
	 * (flags & 0x20) == 32; boolean bool_167_ = (flags & 0x40) == 64; boolean
	 * hasUVCoordinates = (flags & 0x80) == 128; int i_169_ =
	 * class588_sub11.getUnsignedByte(); int i_170_ =
	 * class588_sub11.getUnsignedByte(); int i_171_ =
	 * class588_sub11.getUnsignedByte(); int i_172_ =
	 * class588_sub11.getUnsignedByte(); int i_173_ =
	 * class588_sub11.getUnsignedByte(); int i_174_ =
	 * class588_sub11.getUnsignedShort(); int i_175_ =
	 * class588_sub11.getUnsignedShort(); int i_176_ =
	 * class588_sub11.getUnsignedShort(); int i_177_ =
	 * class588_sub11.getUnsignedShort(); int i_178_ =
	 * class588_sub11.getUnsignedShort(); int i_179_ =
	 * class588_sub11.getUnsignedShort(); int i_180_ =
	 * class588_sub11.getUnsignedShort(); if (!bool_165_) { if (i_173_ == 1)
	 * i_179_ = numVertices; else i_179_ = 0; } if (!bool_166_) { if (i_171_ ==
	 * 1) i_180_ = numTriangles; else i_180_ = 0; } int i_181_ = 0; int i_182_ =
	 * 0; int i_183_ = 0; if (((Model)this).numTextureTriangles > 0) {
	 * textureRenderTypes = new sbyte[numTextureTriangles];
	 * class588_sub11.mainStream.Position = 3; for (int i_184_ = 0; i_184_ <
	 * numTextureTriangles; i_184_++) { sbyte i_185_ =
	 * (textureRenderTypes[i_184_] = (sbyte)class588_sub11.ReadByte()); if
	 * (i_185_ == 0) i_181_++; if (i_185_ >= 1 && i_185_ <= 3) i_182_++; if
	 * (i_185_ == 2) i_183_++; } } int i_186_ = 3 +
	 * ((Model)this).numTextureTriangles; int i_187_ = i_186_; i_186_ +=
	 * numVertices; int i_188_ = i_186_; if (bool_1) i_186_ += numTriangles; int
	 * i_189_ = i_186_; i_186_ += numTriangles; int i_190_ = i_186_; if (i_169_
	 * == 255) i_186_ += numTriangles; int i_191_ = i_186_; i_186_ += i_180_;
	 * int i_192_ = i_186_; i_186_ += i_179_; int i_193_ = i_186_; if (i_170_ ==
	 * 1) i_186_ += numTriangles; int i_194_ = i_186_; i_186_ += i_177_; int
	 * i_195_ = i_186_; if (i_172_ == 1) i_186_ += numTriangles * 2; int i_196_
	 * = i_186_; i_186_ += i_178_; int i_197_ = i_186_; i_186_ += numTriangles *
	 * 2; int i_198_ = i_186_; i_186_ += i_174_; int i_199_ = i_186_; i_186_ +=
	 * i_175_; int i_200_ = i_186_; i_186_ += i_176_; int i_201_ = i_186_;
	 * i_186_ += i_181_ * 6; int i_202_ = i_186_; i_186_ += i_182_ * 6; int
	 * i_203_ = 6; if (version == 14) i_203_ = 7; else if (version >= 15) i_203_
	 * = 9; int i_204_ = i_186_; i_186_ += i_182_ * i_203_; int i_205_ = i_186_;
	 * i_186_ += i_182_; int i_206_ = i_186_; i_186_ += i_182_; int i_207_ =
	 * i_186_; i_186_ += i_182_ + i_183_ * 2; int i_208_ = i_186_; int i_209_ =
	 * instream.Length; int i_210_ = instream.Length; int i_211_ =
	 * instream.Length; int i_212_ = instream.Length; if (hasUVCoordinates) {
	 * ExtendedBufferReader class588_sub11_213_ = new ExtendedBufferReader(new
	 * MemoryStream((byte[])(Array)instream));
	 * class588_sub11_213_.mainStream.Position = (instream.Length - 26);
	 * class588_sub11_213_.mainStream.Position -=
	 * instream[class588_sub11_213_.mainStream.Position - 1];
	 * ((Model)this).anInt1234 = class588_sub11_213_.getUnsignedShort(); int
	 * i_214_ = class588_sub11_213_.getUnsignedShort(); int i_215_ =
	 * class588_sub11_213_.getUnsignedShort(); i_209_ = i_208_ + i_214_; i_210_
	 * = i_209_ + i_215_; i_211_ = i_210_ + numVertices; i_212_ = i_211_ +
	 * ((Model)this).anInt1234 * 2; } verticesX = new int[numVertices];
	 * verticesY = new int[numVertices]; verticesZ = new int[numVertices];
	 * triangleViewspaceX = new short[numTriangles]; triangleViewspaceY = new
	 * short[numTriangles]; triangleViewspaceZ = new short[numTriangles]; if
	 * (i_173_ == 1) vertexSkins = new int[numVertices]; if (bool_1)
	 * faceRenderType = new byte[numTriangles]; if (i_169_ == 255)
	 * trianglePriorities = new sbyte[numTriangles]; else ((Model)this).priority
	 * = (sbyte)i_169_; if (i_170_ == 1) faceAlpha = new sbyte[numTriangles]; if
	 * (i_171_ == 1) triangleSkinValues = new int[numTriangles]; if (i_172_ ==
	 * 1) faceTexture = new short[numTriangles]; if (i_172_ == 1 &&
	 * (((Model)this).numTextureTriangles > 0 || ((Model)this).anInt1234 > 0))
	 * textureCoords = new short[numTriangles]; colorValues = new
	 * short[numTriangles]; if (((Model)this).numTextureTriangles > 0) {
	 * textureTrianglePIndex = new ushort[((Model)this).numTextureTriangles];
	 * textureTriangleMIndex = new ushort[((Model)this).numTextureTriangles];
	 * textureTriangleNIndex = new ushort[((Model)this).numTextureTriangles]; if
	 * (i_182_ > 0) { particleDirectionX = new int[i_182_]; particleDirectionY =
	 * new int[i_182_]; particleDirectionZ = new int[i_182_]; particleLifespanX
	 * = new sbyte[i_182_]; particleLifespanY = new sbyte[i_182_];
	 * particleLifespanZ = new int[i_182_]; } if (i_183_ > 0) {
	 * texturePrimaryColor = new int[i_183_]; textureSecondaryColor = new
	 * int[i_183_]; } } class588_sub11.mainStream.Position = i_187_;
	 * class588_sub11_156_.mainStream.Position = i_198_;
	 * class588_sub11_157_.mainStream.Position = i_199_;
	 * class588_sub11_158_.mainStream.Position = i_200_;
	 * class588_sub11_159_.mainStream.Position = i_192_; int i_216_ = 0; int
	 * i_217_ = 0; int i_218_ = 0; for (int i_219_ = 0; i_219_ < numVertices;
	 * i_219_++) { int i_220_ = class588_sub11.getUnsignedByte(); int i_221_ =
	 * 0; if ((i_220_ & 0x1) != 0) i_221_ =
	 * class588_sub11_156_.ReadUnsignedSmart(); int i_222_ = 0; if ((i_220_ &
	 * 0x2) != 0) i_222_ = class588_sub11_157_.ReadUnsignedSmart(); int i_223_ =
	 * 0; if ((i_220_ & 0x4) != 0) i_223_ =
	 * class588_sub11_158_.ReadUnsignedSmart(); verticesX[i_219_] = i_216_ +
	 * i_221_; verticesY[i_219_] = i_217_ + i_222_; verticesZ[i_219_] = i_218_ +
	 * i_223_; i_216_ = verticesX[i_219_]; i_217_ = verticesY[i_219_]; i_218_ =
	 * verticesZ[i_219_]; if (i_173_ == 1) { if (bool_165_) vertexSkins[i_219_]
	 * = class588_sub11_159_ .ReadSpecialSmart(); else { vertexSkins[i_219_] =
	 * class588_sub11_159_ .getUnsignedByte(); if (vertexSkins[i_219_] == 255)
	 * vertexSkins[i_219_] = -1; } } } if (((Model)this).anInt1234 > 0) {
	 * class588_sub11.mainStream.Position = i_210_;
	 * class588_sub11_156_.mainStream.Position = i_211_;
	 * class588_sub11_157_.mainStream.Position = i_212_; anIntArray1231 = new
	 * int[numVertices]; int i_224_ = 0; int i_225_ = 0; for (; i_224_ <
	 * numVertices; i_224_++) { anIntArray1231[i_224_] = i_225_; i_225_ +=
	 * class588_sub11.getUnsignedByte(); } aByteArray1241 = new
	 * byte[numTriangles]; aByteArray1266 = new byte[numTriangles];
	 * aByteArray1243 = new byte[numTriangles]; texCoordU = new
	 * float[((Model)this).anInt1234]; texCoordV = new
	 * float[((Model)this).anInt1234]; for (i_224_ = 0; i_224_ <
	 * ((Model)this).anInt1234; i_224_++) { texCoordU[i_224_] =
	 * ((float)class588_sub11_156_ .ReadShort() / 4096.0F); texCoordV[i_224_] =
	 * ((float)class588_sub11_157_ .ReadShort() / 4096.0F); } }
	 * class588_sub11.mainStream.Position = i_197_;
	 * class588_sub11_156_.mainStream.Position = i_188_;
	 * class588_sub11_157_.mainStream.Position = i_190_;
	 * class588_sub11_158_.mainStream.Position = i_193_;
	 * class588_sub11_159_.mainStream.Position = i_191_;
	 * class588_sub11_160_.mainStream.Position = i_195_;
	 * class588_sub11_161_.mainStream.Position = i_196_; for (int i_226_ = 0;
	 * i_226_ < numTriangles; i_226_++) { colorValues[i_226_] =
	 * (short)class588_sub11 .getUnsignedShort(); if (bool_1)
	 * faceRenderType[i_226_] = (byte)class588_sub11_156_ .ReadByte(); if
	 * (i_169_ == 255) trianglePriorities[i_226_] = (sbyte)class588_sub11_157_
	 * .ReadByte(); if (i_170_ == 1) faceAlpha[i_226_] =
	 * (sbyte)class588_sub11_158_ .ReadByte(); if (i_171_ == 1) { if (bool_166_)
	 * triangleSkinValues[i_226_] = class588_sub11_159_ .ReadSpecialSmart();
	 * else { triangleSkinValues[i_226_] = class588_sub11_159_
	 * .getUnsignedByte(); if (triangleSkinValues[i_226_] == 255)
	 * triangleSkinValues[i_226_] = -1; } } if (i_172_ == 1) faceTexture[i_226_]
	 * = (short)(class588_sub11_160_ .getUnsignedShort() - 1); if (textureCoords
	 * != null) { if (faceTexture[i_226_] != -1) { if (version >= 16)
	 * textureCoords[i_226_] = (short)(class588_sub11_161_ .ReadSmart() - 1);
	 * else textureCoords[i_226_] = (short)(class588_sub11_161_
	 * .getUnsignedByte() - 1); } else textureCoords[i_226_] = (short)-1; } }
	 * maxDepth = -1; class588_sub11.mainStream.Position = i_194_;
	 * class588_sub11_156_.mainStream.Position = i_189_;
	 * class588_sub11_157_.mainStream.Position = i_209_;
	 * calculateMaxDepth(class588_sub11, class588_sub11_156_,
	 * class588_sub11_157_); class588_sub11.mainStream.Position = i_201_;
	 * class588_sub11_156_.mainStream.Position = i_202_;
	 * class588_sub11_157_.mainStream.Position = i_204_;
	 * class588_sub11_158_.mainStream.Position = i_205_;
	 * class588_sub11_159_.mainStream.Position = i_206_;
	 * class588_sub11_160_.mainStream.Position = i_207_;
	 * decodeTexturedTriangles(class588_sub11, class588_sub11_156_,
	 * class588_sub11_157_, class588_sub11_158_, class588_sub11_159_,
	 * class588_sub11_160_); class588_sub11.mainStream.Position = i_208_; if
	 * (bool_163_) { int i_227_ = class588_sub11.getUnsignedByte(); if (i_227_ >
	 * 0) { surfaces = new Surface[i_227_]; for (int i_228_ = 0; i_228_ <
	 * i_227_; i_228_++) { int i_229_ = class588_sub11 .getUnsignedShort(); int
	 * i_230_ = class588_sub11 .getUnsignedShort(); sbyte i_231_; if (i_169_ ==
	 * 255) i_231_ = trianglePriorities[i_230_]; else i_231_ = (sbyte)i_169_;
	 * surfaces[i_228_] = new Surface(i_229_, i_230_,
	 * triangleViewspaceX[i_230_], triangleViewspaceY[i_230_],
	 * triangleViewspaceZ[i_230_], (byte)i_231_); } } int i_232_ =
	 * class588_sub11.getUnsignedByte(); if (i_232_ > 0) { surfaceSkins = new
	 * SurfaceSkin[i_232_]; for (int i_233_ = 0; i_233_ < i_232_; i_233_++) {
	 * int i_234_ = class588_sub11 .getUnsignedShort(); int i_235_ =
	 * class588_sub11 .getUnsignedShort(); surfaceSkins[i_233_] = new
	 * SurfaceSkin(i_234_, i_235_); } } } if (bool_164_) { int i_236_ =
	 * class588_sub11.getUnsignedByte(); if (i_236_ > 0) { isolatedVertexNormals
	 * = new VertexNormal[i_236_]; for (int i_237_ = 0; i_237_ < i_236_;
	 * i_237_++) { int i_238_ = class588_sub11 .getUnsignedShort(); int i_239_ =
	 * class588_sub11 .getUnsignedShort(); int i_240_; if (bool_167_) i_240_ =
	 * class588_sub11.ReadSpecialSmart(); else { i_240_ =
	 * class588_sub11.getUnsignedByte(); if (i_240_ == 255) i_240_ = -1; } byte
	 * i_241_ = (byte)class588_sub11.ReadByte(); isolatedVertexNormals[i_237_] =
	 * new VertexNormal(i_238_, i_239_, i_240_, i_241_); } } } } }
	 */

	private void read525Model(byte abyte0[], int modelID) {
		ByteBuffer nc1 = new ByteBuffer(abyte0);
		ByteBuffer nc2 = new ByteBuffer(abyte0);
		ByteBuffer nc3 = new ByteBuffer(abyte0);
		ByteBuffer nc4 = new ByteBuffer(abyte0);
		ByteBuffer nc5 = new ByteBuffer(abyte0);
		ByteBuffer nc6 = new ByteBuffer(abyte0);
		ByteBuffer nc7 = new ByteBuffer(abyte0);
		nc1.position = abyte0.length - 23;
		int numVertices = nc1.getUnsignedShort();
		int numTriangles = nc1.getUnsignedShort();
		int numTextureTriangles = nc1.getUnsignedByte();
		ModelHeader ModelDef_1 = modelHeaderCache[modelID] = new ModelHeader();
		ModelDef_1.aByteArray368 = abyte0;
		ModelDef_1.anInt369 = numVertices;
		ModelDef_1.anInt370 = numTriangles;
		ModelDef_1.anInt371 = numTextureTriangles;
		int l1 = nc1.getUnsignedByte();
		boolean bool = (0x1 & l1 ^ 0xffffffff) == -2;
		int i2 = nc1.getUnsignedByte();
		int j2 = nc1.getUnsignedByte();
		int k2 = nc1.getUnsignedByte();
		int l2 = nc1.getUnsignedByte();
		int i3 = nc1.getUnsignedByte();
		int j3 = nc1.getUnsignedShort();
		int k3 = nc1.getUnsignedShort();
		int l3 = nc1.getUnsignedShort();
		int i4 = nc1.getUnsignedShort();
		int j4 = nc1.getUnsignedShort();
		int k4 = 0;
		int l4 = 0;
		int i5 = 0;
		byte[] x = null;
		byte[] O = null;
		byte[] J = null;
		byte[] F = null;
		byte[] cb = null;
		byte[] gb = null;
		byte[] lb = null;
		int[] kb = null;
		int[] y = null;
		int[] N = null;
		short[] D = null;
		int[] triangleColours2 = new int[numTriangles];
		if (numTextureTriangles > 0) {
			O = new byte[numTextureTriangles];
			nc1.position = 0;
			for (int j5 = 0; j5 < numTextureTriangles; j5++) {
				byte byte0 = O[j5] = nc1.getSignedByte();
				if (byte0 == 0) {
					k4++;
				}
				if (byte0 >= 1 && byte0 <= 3) {
					l4++;
				}
				if (byte0 == 2) {
					i5++;
				}
			}
		}
		int k5 = numTextureTriangles;
		int l5 = k5;
		k5 += numVertices;
		int i6 = k5;
		if (l1 == 1) {
			k5 += numTriangles;
		}
		int j6 = k5;
		k5 += numTriangles;
		int k6 = k5;
		if (i2 == 255) {
			k5 += numTriangles;
		}
		int l6 = k5;
		if (k2 == 1) {
			k5 += numTriangles;
		}
		int i7 = k5;
		if (i3 == 1) {
			k5 += numVertices;
		}
		int j7 = k5;
		if (j2 == 1) {
			k5 += numTriangles;
		}
		int k7 = k5;
		k5 += i4;
		int l7 = k5;
		if (l2 == 1) {
			k5 += numTriangles * 2;
		}
		int i8 = k5;
		k5 += j4;
		int j8 = k5;
		k5 += numTriangles * 2;
		int k8 = k5;
		k5 += j3;
		int l8 = k5;
		k5 += k3;
		int i9 = k5;
		k5 += l3;
		int j9 = k5;
		k5 += k4 * 6;
		int k9 = k5;
		k5 += l4 * 6;
		int l9 = k5;
		k5 += l4 * 6;
		int i10 = k5;
		k5 += l4;
		int j10 = k5;
		k5 += l4;
		int k10 = k5;
		k5 += l4 + i5 * 2;
		int[] vertexX = new int[numVertices];
		int[] vertexY = new int[numVertices];
		int[] vertexZ = new int[numVertices];
		int[] facePoint1 = new int[numTriangles];
		int[] facePoint2 = new int[numTriangles];
		int[] facePoint3 = new int[numTriangles];
		vertex_skin_types = new int[numVertices];
		face_fill_attributes = new int[numTriangles];
		priorities = new int[numTriangles];
		alpha = new int[numTriangles];
		triangle_skin_types = new int[numTriangles];
		if (i3 == 1) {
			vertex_skin_types = new int[numVertices];
		}
		if (bool) {
			face_fill_attributes = new int[numTriangles];
		}
		if (i2 == 255) {
			priorities = new int[numTriangles];
		} else {
		}
		if (j2 == 1) {
			alpha = new int[numTriangles];
		}
		if (k2 == 1) {
			triangle_skin_types = new int[numTriangles];
		}
		if (l2 == 1) {
			D = new short[numTriangles];
		}
		if (l2 == 1 && numTextureTriangles > 0) {
			x = new byte[numTriangles];
		}
		triangleColours2 = new int[numTriangles];
		int[] texTrianglesPoint1 = null;
		int[] texTrianglesPoint2 = null;
		int[] texTrianglesPoint3 = null;
		if (numTextureTriangles > 0) {
			texTrianglesPoint1 = new int[numTextureTriangles];
			texTrianglesPoint2 = new int[numTextureTriangles];
			texTrianglesPoint3 = new int[numTextureTriangles];
			if (l4 > 0) {
				kb = new int[l4];
				N = new int[l4];
				y = new int[l4];
				gb = new byte[l4];
				lb = new byte[l4];
				F = new byte[l4];
			}
			if (i5 > 0) {
				cb = new byte[i5];
				J = new byte[i5];
			}
		}
		nc1.position = l5;
		nc2.position = k8;
		nc3.position = l8;
		nc4.position = i9;
		nc5.position = i7;
		int l10 = 0;
		int i11 = 0;
		int j11 = 0;
		for (int k11 = 0; k11 < numVertices; k11++) {
			int l11 = nc1.getUnsignedByte();
			int j12 = 0;
			if ((l11 & 1) != 0) {
				j12 = nc2.getUnsignedSmart();
			}
			int l12 = 0;
			if ((l11 & 2) != 0) {
				l12 = nc3.getUnsignedSmart();
			}
			int j13 = 0;
			if ((l11 & 4) != 0) {
				j13 = nc4.getUnsignedSmart();
			}
			vertexX[k11] = l10 + j12;
			vertexY[k11] = i11 + l12;
			vertexZ[k11] = j11 + j13;
			l10 = vertexX[k11];
			i11 = vertexY[k11];
			j11 = vertexZ[k11];
			if (vertex_skin_types != null) {
				vertex_skin_types[k11] = nc5.getUnsignedByte();
			}
		}
		nc1.position = j8;
		nc2.position = i6;
		nc3.position = k6;
		nc4.position = j7;
		nc5.position = l6;
		nc6.position = l7;
		nc7.position = i8;
		for (int i12 = 0; i12 < numTriangles; i12++) {
			triangleColours2[i12] = nc1.getUnsignedShort();
			if (l1 == 1) {
				face_fill_attributes[i12] = nc2.getSignedByte();
				if (face_fill_attributes[i12] == 2) {
					triangleColours2[i12] = 65535;
				}
				face_fill_attributes[i12] = 0;
			}
			if (i2 == 255) {
				priorities[i12] = nc3.getSignedByte();
			}
			if (j2 == 1) {
				alpha[i12] = nc4.getSignedByte();
				if (alpha[i12] < 0) {
					alpha[i12] = 256 + alpha[i12];
				}
			}
			if (k2 == 1) {
				triangle_skin_types[i12] = nc5.getUnsignedByte();
			}
			if (l2 == 1) {
				D[i12] = (short) (nc6.getUnsignedShort() - 1);
			}
			if (x != null) {
				if (D[i12] != -1) {
					x[i12] = (byte) (nc7.getUnsignedByte() - 1);
				} else {
					x[i12] = -1;
				}
			}
		}
		// /fix's triangle issue, but fucked up - no need, loading all 474-
		// models
		/*
		 * try { for(int i12 = 0; i12 < numTriangles; i12++) {
		 * triangleColours2[i12] = nc1.readUnsignedWord(); if(l1 == 1){
		 * face_fill_attributes[i12] = nc2.readSignedByte(); } if(i2 == 255){
		 * anIntArray1638[i12] = nc3.readSignedByte(); } if(j2 == 1){ alpha[i12]
		 * = nc4.readSignedByte(); if(alpha[i12] < 0) alpha[i12] =
		 * (256+alpha[i12]); } if(k2 == 1) triangle_skin_types[i12] =
		 * nc5.readUnsignedByte(); if(l2 == 1) D[i12] =
		 * (short)(nc6.readUnsignedWord() - 1); if(x != null) if(D[i12] != -1)
		 * x[i12] = (byte)(nc7.readUnsignedByte() -1); else x[i12] = -1; } }
		 * catch (Exception ex) { }
		 */
		nc1.position = k7;
		nc2.position = j6;
		int k12 = 0;
		int i13 = 0;
		int k13 = 0;
		int l13 = 0;
		for (int i14 = 0; i14 < numTriangles; i14++) {
			int j14 = nc2.getUnsignedByte();
			if (j14 == 1) {
				k12 = nc1.getUnsignedSmart() + l13;
				l13 = k12;
				i13 = nc1.getUnsignedSmart() + l13;
				l13 = i13;
				k13 = nc1.getUnsignedSmart() + l13;
				l13 = k13;
				facePoint1[i14] = k12;
				facePoint2[i14] = i13;
				facePoint3[i14] = k13;
			}
			if (j14 == 2) {
				i13 = k13;
				k13 = nc1.getUnsignedSmart() + l13;
				l13 = k13;
				facePoint1[i14] = k12;
				facePoint2[i14] = i13;
				facePoint3[i14] = k13;
			}
			if (j14 == 3) {
				k12 = k13;
				k13 = nc1.getUnsignedSmart() + l13;
				l13 = k13;
				facePoint1[i14] = k12;
				facePoint2[i14] = i13;
				facePoint3[i14] = k13;
			}
			if (j14 == 4) {
				int l14 = k12;
				k12 = i13;
				i13 = l14;
				k13 = nc1.getUnsignedSmart() + l13;
				l13 = k13;
				facePoint1[i14] = k12;
				facePoint2[i14] = i13;
				facePoint3[i14] = k13;
			}
		}
		nc1.position = j9;
		nc2.position = k9;
		nc3.position = l9;
		nc4.position = i10;
		nc5.position = j10;
		nc6.position = k10;
		for (int k14 = 0; k14 < numTextureTriangles; k14++) {
			int i15 = O[k14] & 0xff;
			if (i15 == 0) {
				texTrianglesPoint1[k14] = nc1.getUnsignedShort();
				texTrianglesPoint2[k14] = nc1.getUnsignedShort();
				texTrianglesPoint3[k14] = nc1.getUnsignedShort();
			}
			if (i15 == 1) {
				texTrianglesPoint1[k14] = nc2.getUnsignedShort();
				texTrianglesPoint2[k14] = nc2.getUnsignedShort();
				texTrianglesPoint3[k14] = nc2.getUnsignedShort();
				kb[k14] = nc3.getUnsignedShort();
				N[k14] = nc3.getUnsignedShort();
				y[k14] = nc3.getUnsignedShort();
				gb[k14] = nc4.getSignedByte();
				lb[k14] = nc5.getSignedByte();
				F[k14] = nc6.getSignedByte();
			}
			if (i15 == 2) {
				texTrianglesPoint1[k14] = nc2.getUnsignedShort();
				texTrianglesPoint2[k14] = nc2.getUnsignedShort();
				texTrianglesPoint3[k14] = nc2.getUnsignedShort();
				kb[k14] = nc3.getUnsignedShort();
				N[k14] = nc3.getUnsignedShort();
				y[k14] = nc3.getUnsignedShort();
				gb[k14] = nc4.getSignedByte();
				lb[k14] = nc5.getSignedByte();
				F[k14] = nc6.getSignedByte();
				cb[k14] = nc6.getSignedByte();
				J[k14] = nc6.getSignedByte();
			}
			if (i15 == 3) {
				texTrianglesPoint1[k14] = nc2.getUnsignedShort();
				texTrianglesPoint2[k14] = nc2.getUnsignedShort();
				texTrianglesPoint3[k14] = nc2.getUnsignedShort();
				kb[k14] = nc3.getUnsignedShort();
				N[k14] = nc3.getUnsignedShort();
				y[k14] = nc3.getUnsignedShort();
				gb[k14] = nc4.getSignedByte();
				lb[k14] = nc5.getSignedByte();
				F[k14] = nc6.getSignedByte();
			}
		}
		if (i2 != 255) {
			for (int i12 = 0; i12 < numTriangles; i12++) {
				priorities[i12] = i2;
			}
		}
		colors = triangleColours2;
		vertexCount = numVertices;
		triangle_count = numTriangles;
		this.vertexX = vertexX;
		this.vertexY = vertexY;
		this.vertexZ = vertexZ;
		triangle_viewspace_x = facePoint1;
		triangle_viewspace_y = facePoint2;
		triangle_viewspace_z = facePoint3;
	}

	private void read622Model(byte abyte0[], int modelID) {
		ByteBuffer data = new ByteBuffer(abyte0);
		ByteBuffer data_2 = new ByteBuffer(abyte0);
		ByteBuffer data_3 = new ByteBuffer(abyte0);
		ByteBuffer data_4 = new ByteBuffer(abyte0);
		ByteBuffer data_5 = new ByteBuffer(abyte0);
		ByteBuffer data_6 = new ByteBuffer(abyte0);
		ByteBuffer data_7 = new ByteBuffer(abyte0);
		data.position = abyte0.length - 23;
		int vertexCount = data.getUnsignedShort();
		int triangle_count = data.getUnsignedShort();
		int textured_triangle_count = data.getUnsignedByte();
		ModelHeader ModelDef_1 = modelHeaderCache[modelID] = new ModelHeader();
		ModelDef_1.aByteArray368 = abyte0;
		ModelDef_1.anInt369 = vertexCount;
		ModelDef_1.anInt370 = triangle_count;
		ModelDef_1.anInt371 = textured_triangle_count;
		int has_fill_opcode_2 = data.getUnsignedByte();
		boolean has_fill_opcode = (0x1 & has_fill_opcode_2 ^ 0xffffffff) == -2;
		boolean bool_26_ = (0x8 & has_fill_opcode_2) == 8;
		if (!bool_26_) {
			read525Model(abyte0, modelID);
			return;
		}
		int version = 0;
		if (bool_26_) {
			data.position -= 7;
			version = data.getUnsignedByte();
			data.position += 6;
		}
		if (version == 15) {
			isNewModel[modelID] = true;
		}
		int priority = data.getUnsignedByte();
		int alpha_opcode = data.getUnsignedByte();
		int tSkin_opcode = data.getUnsignedByte();
		int texture_opcode = data.getUnsignedByte();
		int vSkin_opcode = data.getUnsignedByte();
		int j3 = data.getUnsignedShort();
		int k3 = data.getUnsignedShort();
		int l3 = data.getUnsignedShort();
		int i4 = data.getUnsignedShort();
		int j4 = data.getUnsignedShort();
		int k4 = 0;
		int l4 = 0;
		int i5 = 0;
		byte[] pointers = null;
		byte[] texture_fill_attributes = null;
		byte[] J = null;
		byte[] F = null;
		byte[] cb = null;
		byte[] gb = null;
		byte[] lb = null;
		int[] kb = null;
		int[] y = null;
		int[] N = null;
		short[] textures = null;
		int[] colors = new int[triangle_count];
		if (textured_triangle_count > 0) {
			texture_fill_attributes = new byte[textured_triangle_count];
			data.position = 0;
			for (int j5 = 0; j5 < textured_triangle_count; j5++) {
				byte byte0 = texture_fill_attributes[j5] = data.getSignedByte();
				if (byte0 == 0) {
					k4++;
				}
				if (byte0 >= 1 && byte0 <= 3) {
					l4++;
				}
				if (byte0 == 2) {
					i5++;
				}
			}
		}
		int k5 = textured_triangle_count;
		int vertexModOffset = k5;
		k5 += vertexCount;
		int drawTypeBasePos = k5;
		if (has_fill_opcode) {
			k5 += triangle_count;
		}
		if (has_fill_opcode_2 == 1) {
			k5 += triangle_count;
		}
		int triMeshLinkOffset = k5;
		k5 += triangle_count;
		int facePriorityBasePos = k5;
		if (priority == 255) {
			k5 += triangle_count;
		}
		int tSkinBasePos = k5;
		if (tSkin_opcode == 1) {
			k5 += triangle_count;
		}
		int vSkinBasePos = k5;
		if (vSkin_opcode == 1) {
			k5 += vertexCount;
		}
		int alphaBasePos = k5;
		if (alpha_opcode == 1) {
			k5 += triangle_count;
		}
		int triVPointOffset = k5;
		k5 += i4;
		int texturedTriangleTextureIDBasePos = k5;
		if (texture_opcode == 1) {
			k5 += triangle_count * 2;
		}
		int texturedTriangleIDBasePos = k5;
		k5 += j4;
		int triColorOffset = k5;
		k5 += triangle_count * 2;
		int vertexXOffset = k5;
		k5 += j3;
		int vertexYOffset = k5;
		k5 += k3;
		int vertexZOffset = k5;
		k5 += l3;
		int mainBufferOffset = k5;
		k5 += k4 * 6;
		int firstBufferOffset = k5;
		k5 += l4 * 6;
		int i_59_ = 6;
		if (version != 14) {
			if (version >= 15) {
				i_59_ = 9;
			}
		} else {
			i_59_ = 7;
		}
		int secondBufferOffset = k5;
		k5 += i_59_ * l4;
		int thirdBufferOffset = k5;
		k5 += l4;
		int fourthBufferOffset = k5;
		k5 += l4;
		int fifthBufferOffset = k5;
		k5 += l4 + i5 * 2;
		int[] vertexX = new int[vertexCount];
		int[] vertexY = new int[vertexCount];
		int[] vertexZ = new int[vertexCount];
		int[] triangle_viewspace_x = new int[triangle_count];
		int[] triangle_viewspace_y = new int[triangle_count];
		int[] triangle_viewspace_z = new int[triangle_count];
		vertex_skin_types = new int[vertexCount];
		face_fill_attributes = new int[triangle_count];
		priorities = new int[triangle_count];
		this.alpha = new int[triangle_count];
		triangle_skin_types = new int[triangle_count];
		if (vSkin_opcode == 1) {
			vertex_skin_types = new int[vertexCount];
		}
		if (has_fill_opcode) {
			face_fill_attributes = new int[triangle_count];
		}
		if (priority == 255) {
			priorities = new int[triangle_count];
		} else {
		}
		if (alpha_opcode == 1) {
			this.alpha = new int[triangle_count];
		}
		if (tSkin_opcode == 1) {
			triangle_skin_types = new int[triangle_count];
		}
		if (texture_opcode == 1) {
			textures = new short[triangle_count];
		}
		if (texture_opcode == 1 && textured_triangle_count > 0) {
			pointers = new byte[triangle_count];
		}
		colors = new int[triangle_count];
		int[] texture_map_x = null;
		int[] texture_map_y = null;
		int[] texture_map_z = null;
		if (textured_triangle_count > 0) {
			texture_map_x = new int[textured_triangle_count];
			texture_map_y = new int[textured_triangle_count];
			texture_map_z = new int[textured_triangle_count];
			if (l4 > 0) {
				kb = new int[l4];
				N = new int[l4];
				y = new int[l4];
				gb = new byte[l4];
				lb = new byte[l4];
				F = new byte[l4];
			}
			if (i5 > 0) {
				cb = new byte[i5];
				J = new byte[i5];
			}
		}
		data.position = vertexModOffset;
		data_2.position = vertexXOffset;
		data_3.position = vertexYOffset;
		data_4.position = vertexZOffset;
		data_5.position = vSkinBasePos;
		int l10 = 0;
		int i11 = 0;
		int j11 = 0;
		for (int k11 = 0; k11 < vertexCount; k11++) {
			int l11 = data.getUnsignedByte();
			int j12 = 0;
			if ((l11 & 1) != 0) {
				j12 = data_2.getUnsignedSmart();
			}
			int l12 = 0;
			if ((l11 & 2) != 0) {
				l12 = data_3.getUnsignedSmart();
			}
			int j13 = 0;
			if ((l11 & 4) != 0) {
				j13 = data_4.getUnsignedSmart();
			}
			vertexX[k11] = l10 + j12;
			vertexY[k11] = i11 + l12;
			vertexZ[k11] = j11 + j13;
			l10 = vertexX[k11];
			i11 = vertexY[k11];
			j11 = vertexZ[k11];
			if (vertex_skin_types != null) {
				vertex_skin_types[k11] = data_5.getUnsignedByte();
			}
		}
		data.position = triColorOffset;
		data_2.position = drawTypeBasePos;
		data_3.position = facePriorityBasePos;
		data_4.position = alphaBasePos;
		data_5.position = tSkinBasePos;
		data_6.position = texturedTriangleTextureIDBasePos;
		data_7.position = texturedTriangleIDBasePos;
		for (int triangle = 0; triangle < triangle_count; triangle++) {
			colors[triangle] = data.getUnsignedShort();
			if (has_fill_opcode_2 == 1) {
				face_fill_attributes[triangle] = data_2.getSignedByte();
				if (face_fill_attributes[triangle] == 2) {
					// colors[triangle] = 65535;
				}
				// face_fill_attributes[triangle] = 0;
			}
			if (priority == 255) {
				priorities[triangle] = data_3.getSignedByte();
			}
			if (alpha_opcode == 1) {
				this.alpha[triangle] = data_4.getSignedByte();
				if (this.alpha[triangle] < 0) {
					this.alpha[triangle] = 256 + this.alpha[triangle];
				}
			}
			if (tSkin_opcode == 1) {
				triangle_skin_types[triangle] = data_5.getUnsignedByte();
			}
			if (texture_opcode == 1) {
				textures[triangle] = (short) (data_6.getUnsignedShort() - 1);
			}
			if (pointers != null) {
				if (textures[triangle] != -1) {
					pointers[triangle] = (byte) (data_7.getUnsignedByte() - 1);
				} else {
					pointers[triangle] = -1;
				}
			}
		}
		data.position = triVPointOffset;
		data_2.position = triMeshLinkOffset;
		int k12 = 0;
		int i13 = 0;
		int k13 = 0;
		int l13 = 0;
		for (int i14 = 0; i14 < triangle_count; i14++) {
			int j14 = data_2.getUnsignedByte();
			if (j14 == 1) {
				k12 = data.getUnsignedSmart() + l13;
				l13 = k12;
				i13 = data.getUnsignedSmart() + l13;
				l13 = i13;
				k13 = data.getUnsignedSmart() + l13;
				l13 = k13;
				triangle_viewspace_x[i14] = k12;
				triangle_viewspace_y[i14] = i13;
				triangle_viewspace_z[i14] = k13;
			}
			if (j14 == 2) {
				i13 = k13;
				k13 = data.getUnsignedSmart() + l13;
				l13 = k13;
				triangle_viewspace_x[i14] = k12;
				triangle_viewspace_y[i14] = i13;
				triangle_viewspace_z[i14] = k13;
			}
			if (j14 == 3) {
				k12 = k13;
				k13 = data.getUnsignedSmart() + l13;
				l13 = k13;
				triangle_viewspace_x[i14] = k12;
				triangle_viewspace_y[i14] = i13;
				triangle_viewspace_z[i14] = k13;
			}
			if (j14 == 4) {
				int l14 = k12;
				k12 = i13;
				i13 = l14;
				k13 = data.getUnsignedSmart() + l13;
				l13 = k13;
				triangle_viewspace_x[i14] = k12;
				triangle_viewspace_y[i14] = i13;
				triangle_viewspace_z[i14] = k13;
			}
		}
		data.position = mainBufferOffset;
		data_2.position = firstBufferOffset;
		data_3.position = secondBufferOffset;
		data_4.position = thirdBufferOffset;
		data_5.position = fourthBufferOffset;
		data_6.position = fifthBufferOffset;
		for (int tri = 0; tri < textured_triangle_count; tri++) {
			int opcode = texture_fill_attributes[tri] & 0xff;
			if (opcode == 0) {
				texture_map_x[tri] = data.getUnsignedShort();
				texture_map_y[tri] = data.getUnsignedShort();
				texture_map_z[tri] = data.getUnsignedShort();
			}
			if (opcode == 1) {
				texture_map_x[tri] = data_2.getUnsignedShort();
				texture_map_y[tri] = data_2.getUnsignedShort();
				texture_map_z[tri] = data_2.getUnsignedShort();

				if (version < 15) {
					kb[tri] = data_3.getUnsignedShort();

					if (version >= 14) {
						N[tri] = data_3.getMediumInt(-1);
					} else {
						N[tri] = data_3.getUnsignedShort();
					}

					y[tri] = data_3.getUnsignedShort();
				} else {
					kb[tri] = data_3.getMediumInt(-1);
					N[tri] = data_3.getMediumInt(-1);
					y[tri] = data_3.getMediumInt(-1);
				}

				gb[tri] = data_4.getSignedByte();
				lb[tri] = data_5.getSignedByte();
				F[tri] = data_6.getSignedByte();
			}
			if (opcode == 2) {
				texture_map_x[tri] = data_2.getUnsignedShort();
				texture_map_y[tri] = data_2.getUnsignedShort();
				texture_map_z[tri] = data_2.getUnsignedShort();

				if (version >= 15) {
					kb[tri] = data_3.getMediumInt(-1);
					N[tri] = data_3.getMediumInt(-1);
					y[tri] = data_3.getMediumInt(-1);
				} else {
					kb[tri] = data_3.getUnsignedShort();
					if (version < 14) {
						N[tri] = data_3.getUnsignedShort();
					} else {
						N[tri] = data_3.getMediumInt(-1);
					}
					y[tri] = data_3.getUnsignedShort();
				}
				gb[tri] = data_4.getSignedByte();
				lb[tri] = data_5.getSignedByte();
				F[tri] = data_6.getSignedByte();
				cb[tri] = data_6.getSignedByte();
				J[tri] = data_6.getSignedByte();
			}
			if (opcode == 3) {
				texture_map_x[tri] = data_2.getUnsignedShort();
				texture_map_y[tri] = data_2.getUnsignedShort();
				texture_map_z[tri] = data_2.getUnsignedShort();
				if (version < 15) {
					kb[tri] = data_3.getUnsignedShort();
					if (version < 14) {
						N[tri] = data_3.getUnsignedShort();
					} else {
						N[tri] = data_3.getMediumInt(-1);
					}
					y[tri] = data_3.getUnsignedShort();
				} else {
					kb[tri] = data_3.getMediumInt(-1);
					N[tri] = data_3.getMediumInt(-1);
					y[tri] = data_3.getMediumInt(-1);
				}
				gb[tri] = data_4.getSignedByte();
				lb[tri] = data_5.getSignedByte();
				F[tri] = data_6.getSignedByte();
			}
		}
		if (priority != 255) {
			for (int tri = 0; tri < triangle_count; tri++) {
				priorities[tri] = priority;
			}
		}
		this.colors = colors;
		this.vertexCount = vertexCount;
		this.triangle_count = triangle_count;
		this.vertexX = vertexX;
		this.vertexY = vertexY;
		this.vertexZ = vertexZ;
		this.triangle_viewspace_x = triangle_viewspace_x;
		this.triangle_viewspace_y = triangle_viewspace_y;
		this.triangle_viewspace_z = triangle_viewspace_z;
		scale2(4);// 2 is too big -- 3 is almost right
		if (priorities != null) {
			for (int j = 0; j < priorities.length; j++) {
				priorities[j] = 10;
			}
		}
	}

	private void readOldModel(int i) {
		int j = -870;
		aBoolean1618 = true;
		aBoolean1659 = false;
		ModelHeader modelHeader = modelHeaderCache[i];
		vertexCount = modelHeader.anInt369;
		triangle_count = modelHeader.anInt370;
		textured_triangle_count = modelHeader.anInt371;
		vertexX = new int[vertexCount];
		vertexY = new int[vertexCount];
		vertexZ = new int[vertexCount];
		triangle_viewspace_x = new int[triangle_count];
		triangle_viewspace_y = new int[triangle_count];
		while (j >= 0) {
			aBoolean1618 = !aBoolean1618;
		}
		triangle_viewspace_z = new int[triangle_count];
		if (textured_triangle_count > 0) {
			texture_fill_attributes = new byte[textured_triangle_count];
			texture_map_x = new int[textured_triangle_count];
			texture_map_y = new int[textured_triangle_count];
			texture_map_z = new int[textured_triangle_count];
		}
		if (modelHeader.anInt376 >= 0) {
			vertex_skin_types = new int[vertexCount];
		}
		if (modelHeader.anInt380 >= 0) {
			face_fill_attributes = new int[triangle_count];
		}
		if (modelHeader.anInt381 >= 0) {
			priorities = new int[triangle_count];
		} else {
			anInt1641 = -modelHeader.anInt381 - 1;
		}
		if (modelHeader.anInt382 >= 0) {
			alpha = new int[triangle_count];
		}
		if (modelHeader.anInt383 >= 0) {
			triangle_skin_types = new int[triangle_count];
		}
		colors = new int[triangle_count];
		ByteBuffer stream = new ByteBuffer(modelHeader.aByteArray368);
		stream.position = modelHeader.anInt372;
		ByteBuffer stream_1 = new ByteBuffer(modelHeader.aByteArray368);
		stream_1.position = modelHeader.anInt373;
		ByteBuffer stream_2 = new ByteBuffer(modelHeader.aByteArray368);
		stream_2.position = modelHeader.anInt374;
		ByteBuffer stream_3 = new ByteBuffer(modelHeader.aByteArray368);
		stream_3.position = modelHeader.anInt375;
		ByteBuffer stream_4 = new ByteBuffer(modelHeader.aByteArray368);
		stream_4.position = modelHeader.anInt376;
		int k = 0;
		int l = 0;
		int i1 = 0;
		for (int j1 = 0; j1 < vertexCount; j1++) {
			int k1 = stream.getUnsignedByte();
			int i2 = 0;
			if ((k1 & 1) != 0) {
				i2 = stream_1.getUnsignedSmart();
			}
			int k2 = 0;
			if ((k1 & 2) != 0) {
				k2 = stream_2.getUnsignedSmart();
			}
			int i3 = 0;
			if ((k1 & 4) != 0) {
				i3 = stream_3.getUnsignedSmart();
			}
			vertexX[j1] = k + i2;
			vertexY[j1] = l + k2;
			vertexZ[j1] = i1 + i3;
			k = vertexX[j1];
			l = vertexY[j1];
			i1 = vertexZ[j1];
			if (vertex_skin_types != null) {
				vertex_skin_types[j1] = stream_4.getUnsignedByte();
			}
		}
		stream.position = modelHeader.anInt379;
		stream_1.position = modelHeader.anInt380;
		stream_2.position = modelHeader.anInt381;
		stream_3.position = modelHeader.anInt382;
		stream_4.position = modelHeader.anInt383;
		for (int tri = 0; tri < triangle_count; tri++) {
			colors[tri] = stream.getUnsignedShort();
			if (face_fill_attributes != null) {
				face_fill_attributes[tri] = stream_1.getUnsignedByte();
			}
			if (priorities != null) {
				priorities[tri] = stream_2.getUnsignedByte();
			}
			if (alpha != null) {
				alpha[tri] = stream_3.getUnsignedByte();
			}
			if (triangle_skin_types != null) {
				triangle_skin_types[tri] = stream_4.getUnsignedByte();
			}
		}
		stream.position = modelHeader.anInt377;
		stream_1.position = modelHeader.anInt378;
		int j2 = 0;
		int l2 = 0;
		int j3 = 0;
		int k3 = 0;
		for (int l3 = 0; l3 < triangle_count; l3++) {
			int i4 = stream_1.getUnsignedByte();
			if (i4 == 1) {
				j2 = stream.getUnsignedSmart() + k3;
				k3 = j2;
				l2 = stream.getUnsignedSmart() + k3;
				k3 = l2;
				j3 = stream.getUnsignedSmart() + k3;
				k3 = j3;
				triangle_viewspace_x[l3] = j2;
				triangle_viewspace_y[l3] = l2;
				triangle_viewspace_z[l3] = j3;
			}
			if (i4 == 2) {
				l2 = j3;
				j3 = stream.getUnsignedSmart() + k3;
				k3 = j3;
				triangle_viewspace_x[l3] = j2;
				triangle_viewspace_y[l3] = l2;
				triangle_viewspace_z[l3] = j3;
			}
			if (i4 == 3) {
				j2 = j3;
				j3 = stream.getUnsignedSmart() + k3;
				k3 = j3;
				triangle_viewspace_x[l3] = j2;
				triangle_viewspace_y[l3] = l2;
				triangle_viewspace_z[l3] = j3;
			}
			if (i4 == 4) {
				int k4 = j2;
				j2 = l2;
				l2 = k4;
				j3 = stream.getUnsignedSmart() + k3;
				k3 = j3;
				triangle_viewspace_x[l3] = j2;
				triangle_viewspace_y[l3] = l2;
				triangle_viewspace_z[l3] = j3;
			}
		}
		stream.position = modelHeader.anInt384;
		for (int tri = 0; tri < textured_triangle_count; tri++) {
			texture_fill_attributes[tri] = 0;
			texture_map_x[tri] = stream.getUnsignedShort();
			texture_map_y[tri] = stream.getUnsignedShort();
			texture_map_z[tri] = stream.getUnsignedShort();
		}
	}

	public void scale2(int scale) {
		for (int i1 = 0; i1 < vertexCount; i1++) {
			vertexX[i1] = vertexX[i1] / scale;
			vertexY[i1] = vertexY[i1] / scale;
			vertexZ[i1] = vertexZ[i1] / scale;
		}
	}

}