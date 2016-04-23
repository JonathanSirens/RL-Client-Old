package org.ikov.client.graphics;

import org.ikov.client.Client;
import org.ikov.client.cache.ondemand.OnDemandFetcher;

public class SpriteCache {
	
	public static Sprite[] spriteCache;
	public static Sprite[] spriteLink;
	private static OnDemandFetcher onDemandFetcher;
	
	public static void initialise(int total, OnDemandFetcher onDemandFetcher_) {
		spriteCache = new Sprite[total+100];
		spriteLink = new Sprite[total+100];
		onDemandFetcher = onDemandFetcher_;
	}
	
	public static void loadSprite(final int spriteId, final Sprite sprite, boolean priority) {
		if(sprite != null) {
			spriteLink[spriteId] = sprite;
		}
		if(spriteCache[spriteId] == null) {
			onDemandFetcher.requestFileData(Client.IMAGE_IDX-1, spriteId);
		}
	}
	
	public static void loadSprite(final int spriteId, final Sprite sprite) {
		loadSprite(spriteId, sprite, false);
	}
	
	public static void fetchIfNeeded(int spriteId) {
		if(spriteCache[spriteId] != null)
			return;
		onDemandFetcher.requestFileData(Client.IMAGE_IDX-1, spriteId);
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
