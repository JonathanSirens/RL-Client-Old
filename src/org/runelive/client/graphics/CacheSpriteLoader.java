package org.runelive.client.graphics;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.runelive.client.Signlink;
import org.runelive.client.io.ByteBuffer;

public final class CacheSpriteLoader {

    private static final List<Integer> cachedSpriteIdList = new ArrayList<>();
    private static final HashMap<Integer, byte[]> cachedSpriteData = new HashMap<>();

    static {
        /**
         * These sprites cache the byte[] containing the sprite data when loaded
         * for the HP bar, for example.
         */
        cachedSpriteIdList.add(348);
        cachedSpriteIdList.add(397);
    }

    private static int[] cachedSpritePositions;
    private static int[] cachedSpriteSizes;
    private static Sprite[] cachedSprites;
    private static int dataFileOffset;
    private static RandomAccessFile dataFile;

    private static int[] cachedSpritePositions2;
    private static int[] cachedSpriteSizes2;
    private static Sprite[] cachedSprites2;
    private static int dataFile2Offset;
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

    public static void loadCachedSpriteDefinitions() {
        try {
            dataFile = new RandomAccessFile(Signlink.getCacheDirectory() + "sprites.data", "r");

            int spriteCount = dataFile.readShort();

            int idxSize = dataFile.readShort();
            dataFileOffset = 4 + idxSize;
            byte[] idx = new byte[idxSize];
            dataFile.read(idx);

            ByteBuffer index = new ByteBuffer(idx);
            int totalSprites = index.getShort();

            cachedSprites = new Sprite[totalSprites];
            cachedSpritePositions = new int[totalSprites];
            cachedSpriteSizes = new int[totalSprites];

            for (int file = 0; file < totalSprites; file++) {
                cachedSpritePositions[file] = index.getInt();
                cachedSpriteSizes[file] = index.getInt();
            }
        } catch (IOException e) {

        }
    }

    public static void loadCachedSpriteDefinitions2() {
        try {
            dataFile2 = new RandomAccessFile(Signlink.getCacheDirectory() + "sprites2.data", "r");

            int spriteCount = dataFile2.readShort();

            int idxSize = dataFile2.readShort();
            dataFile2Offset = 4 + idxSize;
            byte[] idx = new byte[idxSize];
            dataFile2.read(idx);

            ByteBuffer index = new ByteBuffer(idx);
            int totalSprites = index.getShort();

            cachedSprites2 = new Sprite[totalSprites];
            cachedSpritePositions2 = new int[totalSprites];
            cachedSpriteSizes2 = new int[totalSprites];

            for (int file = 0; file < totalSprites; file++) {
                cachedSpritePositions2[file] = index.getInt();
                cachedSpriteSizes2[file] = index.getInt();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Sprite decodeSprite(int index) {
        int pos = cachedSpritePositions[index];
        int size = cachedSpriteSizes[index];
        try {
            dataFile.seek(dataFileOffset + pos);
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
            dataFile2.seek(dataFile2Offset + pos);
            byte[] data = new byte[size];
            dataFile2.read(data);
            return new Sprite(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}