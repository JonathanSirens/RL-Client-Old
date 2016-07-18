package org.runelive.client.particles;

import java.util.HashMap;
import java.util.Map;

public class ParticleConfiguration {

	private static final Map<Integer, int[][]> models = new HashMap<>();

	public static final int[][] getParticlesForModel(int modelId) {
		return (int[][]) models.get(modelId);
	}

	static {
		//tokhaar
		/*models.put(62575,
				new int[][] { { 0, 1 }, { 1, 1 }, { 3, 1 }, { 131, 1 }, { 132, 1 }, { 133, 1 }, { 134, 1 }, { 135, 1 },
						{ 136, 1 }, { 137, 1 }, { 138, 1 }, { 139, 1 }, { 140, 1 }, { 141, 1 }, { 142, 1 },
						{ 145, 1 } });*/

		//Black completionist cape
		models.put(77009, new int[][] { { 494, 5 }, { 488, 5 }, { 485, 5 }, { 476, 5 }, { 482, 5 }, { 479, 5 }, { 491, 5 } });

		//Grey completionist cape
		models.put(77008, new int[][] { { 494, 6 }, { 488, 6 }, { 485, 6 }, { 476, 6 }, { 482, 6 }, { 479, 6 }, { 491, 6 } });

		//White completionist cape
		models.put(77007, new int[][] { { 494, 7 }, { 488, 7 }, { 485, 7 }, { 476, 7 }, { 482, 7 }, { 479, 7 }, { 491, 7 } });

		//Blue completionist cape
		models.put(77006, new int[][] { { 494, 8 }, { 488, 8 }, { 485, 8 }, { 476, 8 }, { 482, 8 }, { 479, 8 }, { 491, 8 } });

		//Green completionist cape
		models.put(77005, new int[][] { { 494, 9 }, { 488, 9 }, { 485, 9 }, { 476, 9 }, { 482, 9 }, { 479, 9 }, { 491, 9 } });

		//Aqua completionist cape
		models.put(77004, new int[][] { { 494, 10 }, { 488, 10 }, { 485, 10 }, { 476, 10 }, { 482, 10 }, { 479, 10 }, { 491, 10 } });

		//Red completionist cape
		models.put(77003, new int[][] { { 494, 11 }, { 488, 11 }, { 485, 11 }, { 476, 11 }, { 482, 11 }, { 479, 11 }, { 491, 11 } });

		//Purple completionist cape
		models.put(77000, new int[][] { { 494, 12 }, { 488, 12 }, { 485, 12 }, { 476, 12 }, { 482, 12}, { 479, 12 }, { 491, 12 } });

		//Yellow completionist cape
		models.put(77001, new int[][] { { 494, 13 }, { 488, 13 }, { 485, 13 }, { 476, 13 }, { 482, 13}, { 479, 13 }, { 491, 13 } });

		//Orange completionist cape
		models.put(77002, new int[][] { { 494, 14 }, { 488, 14 }, { 485, 14 }, { 476, 14 }, { 482, 14}, { 479, 14 }, { 491, 14 } });

		//models.put(65316,
				//new int[][] { { 494, 5 }, { 488, 5 }, { 485, 5 }, { 476, 5 }, { 482, 5 }, { 479, 5 }, { 491, 5 } });


		/*
		// Trimmed Comp Cape
		models.put(65295,
				new int[][] { { 494, 4 }, { 488, 4 }, { 485, 4 }, { 476, 4 }, { 482, 4 }, { 479, 4 }, { 491, 4 } });
		models.put(65328,
				new int[][] { { 494, 4 }, { 488, 4 }, { 485, 4 }, { 476, 4 }, { 482, 4 }, { 479, 4 }, { 491, 4 } });
*/
		//Max Cape
		//models.put(65300, new int[][] { { 194, 0 }, { 188, 0 }, { 185, 0 }, {176, 0 }, { 182, 0 }, { 179, 0 }, { 291, 0 } });


		/*
		// Dungeoneering Master Cape
		models.put(59885, new int[][] { { 387, 2 }, { 385, 2 }, { 376, 2 }, { 382, 2 }, { 379, 2 } });
		models.put(59887, new int[][] { { 387, 2 }, { 385, 2 }, { 376, 2 }, { 382, 2 }, { 379, 2 } });
*/
	}
}