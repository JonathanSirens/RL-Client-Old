package org.runelive.client;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.net.*;
import java.io.*;
import org.runelive.client.updates.*;


/**
 * Enchanced cache downloader
 * Handles cache downloading & unzipping
 * @author Gabriel Hannason
 */
public class CacheDownloader {
	
	public static final String cache_version = "1";
	public static final File cache_version_file = new File(Signlink.getCacheDirectory() + "versions/cache_version");
	public static final String fileToExtract = Signlink.getCacheDirectory() + "ikov_dl.zip";

	public static void writeVersions() {
	try {
		if(!new File(Signlink.getCacheDirectory() +"versions").exists()) {
			new File(Signlink.getCacheDirectory() +"versions").mkdir();
		}
		BufferedWriter writer = new BufferedWriter(new FileWriter(cache_version_file));
		writer.write(String.valueOf(cache_version));
		writer.close();	
		BufferedWriter writer2 = new BufferedWriter(new FileWriter(MapUpdate.map_update_file));
		writer2.write(String.valueOf(MapUpdate.map_version));
		writer2.close();
		BufferedWriter writer3 = new BufferedWriter(new FileWriter(SpriteUpdate.sprite_update_file));
		writer3.write(String.valueOf(SpriteUpdate.sprite_version));
		writer3.close();
		BufferedWriter writer4 = new BufferedWriter(new FileWriter(MapUpdate.map_repack_file));
		writer4.write(String.valueOf(MapUpdate.REPACK));
		writer4.close();
		BufferedWriter writer5 = new BufferedWriter(new FileWriter(AnimationsUpdate.animation_repack_file));
		writer5.write(String.valueOf(AnimationsUpdate.REPACK));
		writer5.close();
		BufferedWriter writer6 = new BufferedWriter(new FileWriter(AnimationsUpdate.animation_update_file));
		writer6.write(String.valueOf(AnimationsUpdate.animation_version));
		writer6.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void init() {
		try {
			//writeVersions();
			if(!cache_version_file.exists()) {
				downloadCache();
				unZip();
				writeVersions();
				Signlink.startpriv(InetAddress.getLocalHost());
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	private static void drawLoadingText(int amount, String text) {
		Client.loadingPercentage = amount;
		Client.loadingText = text;
	}
	public static void downloadCache() throws IOException {
		String fileURL = "https://dl.dropboxusercontent.com/u/344464529/ikov_dl.zip";
		URL url = new URL(fileURL);
		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
		httpConn.addRequestProperty("User-Agent", "Mozilla/4.76");
		int responseCode = httpConn.getResponseCode();

		// always check HTTP response code first
		if (responseCode == HttpURLConnection.HTTP_OK) {
			String fileName = "";
			String disposition = httpConn.getHeaderField("Content-Disposition");

			if (disposition != null) {
				// extracts file name from header field
				int index = disposition.indexOf("filename=");
				if (index > 0) {
					fileName = "ikov_dl.zip";
				}
			} else {
				// extracts file name from URL
				fileName = "ikov_dl.zip";
			}

			// opens input stream from the HTTP connection
			InputStream inputStream = httpConn.getInputStream();
			String saveFilePath = Signlink.getCacheDirectory() + File.separator + fileName;

			// opens an output stream to save into file
			FileOutputStream outputStream = new FileOutputStream(saveFilePath);

			int bytesRead = -1;
			byte[] buffer = new byte[4096];
			long startTime = System.currentTimeMillis();
			int downloaded = 0;
			long numWritten = 0;
			int length = httpConn.getContentLength();
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
				numWritten += bytesRead;
				downloaded += bytesRead;
				int percentage = (int)(((double)numWritten / (double)length) * 100D);
				int downloadSpeed = (int) ((downloaded / 1024) / (1 + ((System.currentTimeMillis() - startTime) / 1000)));
				drawLoadingText(percentage, (new StringBuilder()).append("Downloading cache: "+percentage+"% ").append("@ "+downloadSpeed+"Kb/s").toString());
			}

			outputStream.close();
			inputStream.close();

		} else {
			System.out.println("ikov2.org replied HTTP code: " + responseCode);
		}
		httpConn.disconnect();
	}

	public static void unZip() {
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(fileToExtract));
			ZipInputStream zin = new ZipInputStream(in);
			ZipEntry e;
			while ((e = zin.getNextEntry()) != null) {
				if (e.isDirectory()) {
					(new File(Signlink.getCacheDirectory() + e.getName())).mkdir();
				} else {
					if (e.getName().equals(fileToExtract)) {
						unzip(zin, fileToExtract);
						break;
					}
					unzip(zin, Signlink.getCacheDirectory() + e.getName());
				}
			}
			zin.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void unzip(ZipInputStream zin, String s) throws IOException {
		FileOutputStream out = new FileOutputStream(s);
		byte[] b = new byte[1024];
		int len = 0;
		while ((len = zin.read(b)) != -1) {
			out.write(b, 0, len);
		}
		out.close();
	}
}
