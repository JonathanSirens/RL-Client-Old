package org.runelive.client.graphics;

import org.runelive.client.Signlink;
import org.runelive.client.io.ByteBuffer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class CacheSpriteLoader {

    private static final List<Integer> cachedSpriteIdList = new ArrayList<>();
    private static final HashMap<Integer, byte[]> cachedSpriteData = new HashMap<>();

    static {
        /**
         * These sprites cache the byte[] containing the sprite data when loaded for the HP bar, for example.
         */
        cachedSpriteIdList.add(348);
        cachedSpriteIdList.add(397);
    }

    private static int[] cachedSpritePositions;
    private static int[] cachedSpriteSizes;
    private static Sprite[] cachedSprites;
    private static RandomAccessFile dataFile;

    private static int[] cachedSpritePositions2;
    private static int[] cachedSpriteSizes2;
    private static Sprite[] cachedSprites2;
    private static RandomAccessFile dataFile2;

    public static byte[] getCacheSpriteData(int index) {
        if (!cachedSpriteData.containsKey(index) || cachedSpriteData.get(index) == null) {
            getCacheSprite(index);
        }
        return cachedSpriteData.get(index);
    }

    public static Sprite getCacheSprite(int index) {
        if (cachedSprites[index] == null) {
            cachedSprites[index] = decodeSprite(index);
        }
        return cachedSprites[index];
    }

    public static Sprite getCacheSprite2(int index) {
        if (cachedSprites2[index] == null) {
            cachedSprites2[index] = decodeSprite2(index);
        }
        return cachedSprites2[index];
    }

    public static void loadCachedSpriteDefinitions(byte[] idx) {
        ByteBuffer index = new ByteBuffer(idx);
        try {
            dataFile = new RandomAccessFile(Signlink.getCacheDirectory() + "sprites.dat", "r");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        int totalSprites = index.getShort();

        cachedSprites = new Sprite[totalSprites];
        cachedSpritePositions = new int[totalSprites];
        cachedSpriteSizes = new int[totalSprites];

        for (int file = 0; file < totalSprites; file++) {
            cachedSpritePositions[file] = index.getInt();
            cachedSpriteSizes[file] = index.getInt();
        }
    }

    public static void loadCachedSpriteDefinitions2(byte[] idx) {
        ByteBuffer index = new ByteBuffer(idx);
        try {
            dataFile2 = new RandomAccessFile(Signlink.getCacheDirectory() + "sprites2.dat", "r");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        int totalSprites = index.getShort();

        cachedSprites2 = new Sprite[totalSprites];
        cachedSpritePositions2 = new int[totalSprites];
        cachedSpriteSizes2 = new int[totalSprites];

        for (int file = 0; file < totalSprites; file++) {
            cachedSpritePositions2[file] = index.getInt();
            cachedSpriteSizes2[file] = index.getInt();
        }
    }

    private static Sprite decodeSprite(int index) {
        int pos = cachedSpritePositions[index];
        int size = cachedSpriteSizes[index];
        try {
            dataFile.seek(pos);
            byte[] data = new byte[size];
            dataFile.read(data);
            if (cachedSpriteIdList.contains(index)) {
                cachedSpriteData.put(index, data);
            }
            return new Sprite(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Sprite decodeSprite2(int index) {
        int pos = cachedSpritePositions2[index];
        int size = cachedSpriteSizes2[index];
        try {
            dataFile2.seek(pos);
            byte[] data = new byte[size];
            dataFile2.read(data);
            return new Sprite(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}