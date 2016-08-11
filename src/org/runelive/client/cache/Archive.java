package org.runelive.client.cache;

import org.runelive.client.bzip.BZIPDecompressor;
import org.runelive.client.io.ByteBuffer;

public final class Archive {

    private final byte[] archiveData;
    private final int[] entryCompressedSizes;
    private final int[] entryPositions;
    private final int entryCount;
    private final int[] entryHashes;
    private final boolean decompressed;
    private final int[] entrySizes;

    public Archive(byte[] data) {
        ByteBuffer buffer = new ByteBuffer(data);
        int decompressed = buffer.getMediumInt();
        int compressed = buffer.getMediumInt();
        this.decompressed = decompressed != compressed;

        if (this.decompressed) {
            byte[] tmp = new byte[decompressed];
            BZIPDecompressor.decompress(tmp, decompressed, data, compressed, 6);
            archiveData = tmp;
            buffer = new ByteBuffer(archiveData);
        } else {
            archiveData = data;
        }

        entryCount = buffer.getUnsignedShort();
        entryHashes = new int[entryCount];
        entrySizes = new int[entryCount];
        entryCompressedSizes = new int[entryCount];
        entryPositions = new int[entryCount];
        int position = buffer.position + entryCount * 10;

        for (int i = 0; i < entryCount; i++) {
            entryHashes[i] = buffer.getIntLittleEndian();
            entrySizes[i] = buffer.getMediumInt();
            entryCompressedSizes[i] = buffer.getMediumInt();
            entryPositions[i] = position;
            position += entryCompressedSizes[i];
        }
    }

    public byte[] get(String name) {
        /*
		 * byte[] data = null; int hash = getHash(name); for (int i = 0; i <
		 * entryCount; i++) { if (entryHashes[i] == hash) { if (data == null) {
		 * data = new byte[entrySizes[i]]; }
		 * 
		 * if (!decompressed) { BZIPDecompressor.decompress(data, entrySizes[i],
		 * archiveData, entryCompressedSizes[i], entryPositions[i]); } else {
		 * System.arraycopy(archiveData, entryPositions[i], data, 0,
		 * entrySizes[i]); }
		 * 
		 * return data; } }
		 * 
		 * return null;
		 */
        byte[] entryData = null;
        int hash = getHash(name);
        for (int index = 0; index < this.entryCount; index++) {
            if (this.entryHashes[index] == hash) {
                entryData = new byte[this.entrySizes[index]];
                if (!this.decompressed) {
                    BZIPDecompressor.decompress(entryData, this.entrySizes[index], this.archiveData, this.entryCompressedSizes[index], this.entryPositions[index]);
                } else {
                    System.arraycopy(this.archiveData, this.entryPositions[index], entryData, 0, this.entrySizes[index]);
                }
            }
        }
        return entryData;
    }

    public static int getHash(String string) {
        int identifier = 0;
        string = string.toUpperCase();
        for (int index = 0; index < string.length(); index++) {
            identifier = (identifier * 61 + string.charAt(index)) - 32;
        }
        return identifier;
    }

}