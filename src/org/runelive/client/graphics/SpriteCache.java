package org.runelive.client.graphics;

import org.runelive.client.Client;
import org.runelive.client.cache.ondemand.CacheFileRequester;

public class SpriteCache {

	public static Sprite[] spriteCache;
	public static Sprite[] spriteLink;
	private static CacheFileRequester onDemandFetcher;

	public static void initialise(int total, CacheFileRequester onDemandFetcher_) {
		spriteCache = new Sprite[total + 100];
		spriteLink = new Sprite[total + 100];
		onDemandFetcher = onDemandFetcher_;
	}

	public static void loadSprite(final int spriteId, final Sprite sprite, boolean priority) {
		if (sprite != null) {
			spriteLink[spriteId] = sprite;
		}
		if (spriteCache[spriteId] == null) {
			onDemandFetcher.pushRequest(Client.IMAGE_IDX - 1, spriteId);
		}
	}

	public static void loadSprite(final int spriteId, final Sprite sprite) {
		loadSprite(spriteId, sprite, false);
	}

	public static void fetchIfNeeded(int spriteId) {
		if (spriteCache[spriteId] != null)
			return;
		onDemandFetcher.pushRequest(Client.IMAGE_IDX - 1, spriteId);
	}

	public static Sprite get(int spriteId) {
		fetchIfNeeded(spriteId);
		return spriteCache[spriteId];
	}

	private static Client c;

	public static void load(Client c) {
		SpriteCache.c = c;
		preloadLowPriorityImages();
	}

	public static void preloadLowPriorityImages() {
	}
}
