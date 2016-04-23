package org.ikov.client.particles;

import java.util.HashMap;
import java.util.Map;

public class ParticleConfigurationComp {

	private static final Map<Integer, int[][]> models = new HashMap<>();

	public static final int[][] I(int modelId) {
		return (int[][]) models.get(Integer.valueOf(modelId));
	}

	static {
		//Tokhaar kal
		models.put(62575,
				new int[][] { { 0, 1 }, { 1, 1 }, { 3, 1 }, { 131, 1 }, { 132, 1 }, { 133, 1 }, { 134, 1 }, { 135, 1 },
						{ 136, 1 }, { 137, 1 }, { 138, 1 }, { 139, 1 }, { 140, 1 }, { 141, 1 }, { 142, 1 },
						{ 145, 1 } });
		models.put(65297,
				new int[][] { { 494, 3 }, { 488, 3 }, { 485, 3 }, { 476, 3 }, { 482, 3 }, { 479, 3 }, { 491, 3 } });
		models.put(65328,
				new int[][] { { 494, 3 }, { 488, 3 }, { 485, 3 }, { 476, 3 }, { 482, 3 }, { 479, 3 }, { 491, 3 } });
		models.put(40122, new int[][] { { 235, 0 }, { 229, 0 }, { 241, 0 }, { 238, 0 }, { 232, 0 } });
		models.put(59885, new int[][] { { 387, 2 }, { 385, 2 }, { 376, 2 }, { 382, 2 }, { 379, 2 } });

	}

}
