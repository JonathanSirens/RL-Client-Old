package org.runelive.client.util;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import static java.nio.file.FileVisitResult.CONTINUE;

/**
 * Static utilities used for assisting with files.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class FileUtilities {

    public static boolean isGzipped(byte[] bytes) {
        if ((bytes == null) || (bytes.length < 2)) {
            return false;
        } else {
            return ((bytes[0] == (byte) (GZIPInputStream.GZIP_MAGIC)) && (bytes[1] == (byte) (GZIPInputStream.GZIP_MAGIC >> 8)));
        }
    }

    /**
     * Reads the specified {@link File} and returns its contents represented as
     * a {@code byte[]}.
     *
     * @param file The file to read.
     * @return The {@code byte[]} representation of the file.
     * @throws IOException If some I/O exception occurs.
     */
    protected static byte[] getBytesFromFile(File file) throws IOException {
        byte[] buffer = new byte[(int) file.length()];
        try (DataInputStream dis = new DataInputStream(new FileInputStream(file))) {
            dis.readFully(buffer);
            return buffer;
        }
    }

    public static byte[] compressGZ(byte[] data, int off, int len) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            GZIPOutputStream gzo = new GZIPOutputStream(bos);
            try {
                gzo.write(data, off, len);
            } finally {
                gzo.close();
                bos.close();
            }
            return bos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Attempts to delete the files within the specified root directory.
     *
     * @param root The root directory.
     * @throws IOException If some I/O exception occurs.
     */
    public static void delete(String root) throws IOException {
        Files.walkFileTree(Paths.get(root), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
                Files.delete(path);
                return CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path path, IOException cause) throws IOException {
                if (cause == null) {
                    Files.delete(path);
                    return CONTINUE;
                } else {
                    throw cause;
                }
            }
        });
    }

    public static final byte[] ReadFile(String s) {
        try {
            File file = new File(s);
            int i = (int) file.length();
            byte abyte0[] = new byte[i];
            DataInputStream datainputstream = new DataInputStream(new BufferedInputStream(new FileInputStream(s)));
            datainputstream.readFully(abyte0, 0, i);
            datainputstream.close();
            TotalRead++;
            return abyte0;
        } catch (Exception exception) {
        }
        return null;
    }

    public static final void WriteFile(String s, byte abyte0[]) {
        try {
            (new File((new File(s)).getParent())).mkdirs();
            FileOutputStream fileoutputstream = new FileOutputStream(s);
            fileoutputstream.write(abyte0, 0, abyte0.length);
            fileoutputstream.close();
            TotalWrite++;
            CompleteWrite++;
        } catch (Throwable throwable) {
            System.out.println((new StringBuilder()).append("Write Error: ").append(s).toString());
        }
    }

    public static boolean FileExists(String file) {
        File f = new File(file);
        if (f.exists())
            return true;
        else
            return false;
    }

    public static byte[] readFile(String name) {
        try {
            RandomAccessFile raf = new RandomAccessFile(name, "r");
            ByteBuffer buf = raf.getChannel().map(
                    FileChannel.MapMode.READ_ONLY, 0, raf.length());
            try {
                if (buf.hasArray()) {
                    return buf.array();
                } else {
                    byte[] array = new byte[buf.remaining()];
                    buf.get(array);
                    return array;
                }
            } finally {
                raf.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int TotalRead = 0;
    public static int TotalWrite = 0;
    public static int CompleteWrite = 0;

}