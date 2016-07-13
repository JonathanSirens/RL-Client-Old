package org.runelive.client.particles;

import java.util.HashMap;
import java.util.Map;

public class ParticleConfiguration {

	private static final Map<Integer, int[][]> models = new HashMap<>();

	public static final int[][] getParticlesForModel(int modelId) {
		return (int[][]) models.get(modelId);
	}

	static {
		//tokhaarkal
		/*models.put(62575,
				new int[][] { { 0, 1 }, { 1, 1 }, { 3, 1 }, { 131, 1 }, { 132, 1 }, { 133, 1 }, { 134, 1 }, { 135, 1 },
						{ 136, 1 }, { 137, 1 }, { 138, 1 }, { 139, 1 }, { 140, 1 }, { 141, 1 }, { 142, 1 },
						{ 145, 1 } });*/
		
		// Max cape
		models.put(65300,
				new int[][] { { 494, 3 }, { 488, 3 }, { 485, 3 }, { 476, 3 }, { 482, 3 }, { 479, 3 }, { 491, 3 } });
		models.put(65322,
				new int[][] { { 494, 3 }, { 488, 3 }, { 485, 3 }, { 476, 3 }, { 482, 3 }, { 479, 3 }, { 491, 3 } });

		// Regular Comp Cape
		models.put(65297,
				new int[][] { { 494, 3 }, { 488, 3 }, { 485, 3 }, { 476, 3 }, { 482, 3 }, { 479, 3 }, { 491, 3 } });
		models.put(65316,
				new int[][] { { 494, 3 }, { 488, 3 }, { 485, 3 }, { 476, 3 }, { 482, 3 }, { 479, 3 }, { 491, 3 } });

		// Trimmed Comp Cape
		models.put(65295,
				new int[][] { { 494, 4 }, { 488, 4 }, { 485, 4 }, { 476, 4 }, { 482, 4 }, { 479, 4 }, { 491, 4 } });
		models.put(65328,
				new int[][] { { 494, 4 }, { 488, 4 }, { 485, 4 }, { 476, 4 }, { 482, 4 }, { 479, 4 }, { 491, 4 } });

		// Dungeoneering Master Cape
		models.put(59885, new int[][] { { 387, 2 }, { 385, 2 }, { 376, 2 }, { 382, 2 }, { 379, 2 } });
		models.put(59887, new int[][] { { 387, 2 }, { 385, 2 }, { 376, 2 }, { 382, 2 }, { 379, 2 } });

	}

}
