package org.runelive.client.updates;

import java.net.*;
import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class MapUpdate {

	private static final int BUFFER = 1024;
	
	private static String cache_location;
	
	public static final String map_file = "https://dl.dropboxusercontent.com/u/344464529/index4.zip";
	public static final File map_update_file = new File(System.getProperty("user.home") + "/ikov_cache2/versions/map_version");
	public static final File map_repack_file = new File(System.getProperty("user.home") + "/ikov_cache2/versions/map_repack");
	public static final File cache_directory = new File(System.getProperty("user.home") + "/ikov_cache2/");

	public static final int map_version = 35;
	
	public static int REPACK = 0;
	
	public static boolean checkVersion() {
		try {
			if(cache_directory.exists() && cache_directory.isDirectory()) {
				if(!map_update_file.exists()) {
					addComponentsToPane(frame.getContentPane());
					frame.setLocationRelativeTo(null);
					frame.setPreferredSize(new Dimension(400, 80));
					frame.setVisible(true);
					frame.pack();
					REPACK = 1;
					download_maps(map_file, "index4.zip");
					unzip_maps(System.getProperty("user.home") + "/ikov_cache2/cache_index_data/index4.zip");
					BufferedWriter writer = new BufferedWriter(new FileWriter(map_update_file));
					writer.write(String.valueOf(map_version));
					writer.close();	
					BufferedWriter writer2 = new BufferedWriter(new FileWriter(map_repack_file));
					writer2.write(String.valueOf(REPACK));
					writer2.close();
				} else {
					final BufferedReader reader = new BufferedReader(new FileReader(map_update_file));
					if(Integer.valueOf(reader.readLine()) != map_version) {
						addComponentsToPane(frame.getContentPane());
						frame.setLocationRelativeTo(null);
						frame.setPreferredSize(new Dimension(400, 80));
						frame.setVisible(true);
						frame.pack();
						REPACK = 1;
						download_maps(map_file, "index4.zip");
						unzip_maps(System.getProperty("user.home") + "/ikov_cache2/cache_index_data/index4.zip");
						BufferedWriter writer = new BufferedWriter(new FileWriter(map_update_file));
						writer.write(String.valueOf(map_version));
						writer.close();	
						BufferedWriter writer2 = new BufferedWriter(new FileWriter(map_repack_file));
						writer2.write(String.valueOf(REPACK));
						writer2.close();
						reader.close();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			frame.setVisible(false);
			frame.dispose();
		}
		return false;
	}
	public static void unzip_maps(String update) {
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(update));
			ZipInputStream zin = new ZipInputStream(in);
			ZipEntry e;
			while ((e = zin.getNextEntry()) != null) {
				if (e.isDirectory()) {
					(new File(System.getProperty("user.home") + "/ikov_cache2/cache_index_data/" + e.getName())).mkdir();
				} else {
					if (e.getName().equals(update)) {
						unzip(zin, update);
						break;
					}
					unzip(zin, System.getProperty("user.home") + "/ikov_cache2/cache_index_data/"+ e.getName());
				}
			}
			zin.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void unzip(ZipInputStream zin, String s) throws IOException {
		
		FileOutputStream out = new FileOutputStream(s);
		byte[] b = new byte[BUFFER];
		int len = 0;
		while ((len = zin.read(b)) != -1) {
			out.write(b, 0, len);
		}
		out.close();
	}
	
	static JLabel label1 = new JLabel();
	static JLabel label2 = new JLabel("Total percent");
	static JProgressBar progressBar,  progressBar1;
	static JFrame frame = new JFrame("Maps Updater");
	private static String status = "Checking if there is a maps update...";

	private static void updateStatus(String newStatus) {
		status = newStatus;
		label1.setText(status);
	}
		
	public static void addComponentsToPane(Container pane) {
		pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
		label1.setAlignmentX(Component.CENTER_ALIGNMENT);
		pane.add(label1);
		progressBar = new JProgressBar(0, 100);
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
		progressBar.setAlignmentX(Component.CENTER_ALIGNMENT);
		pane.add(progressBar);
		updateStatus(status);
	}
	private static void download_maps(String http_address, String models_archive_name) throws MalformedURLException, IOException {
		cache_location = System.getProperty("user.home") + "/ikov_cache2/cache_index_data/";
		BufferedInputStream in = null;
		FileOutputStream fout = null;
				URLConnection conn;
		try {
			in = new BufferedInputStream(new URL(http_address).openStream());
			fout = new FileOutputStream(cache_location + models_archive_name);
			URL url = new URL(http_address);
			conn = url.openConnection();
			final byte data[] = new byte[1024];
			int count;
			int numRead;
			int numWritten = 0;
			int length = conn.getContentLength();
			int lastp = -1;
			int previousAmount = 0, bytesPerSecond = 0;
			while ((count = in.read(data, 0, 1024)) != -1) {
				fout.write(data, 0, count);
				numWritten += count;
            	int percentage = (int)(((double)numWritten / (double)length) * 100D);
				bytesPerSecond = (numWritten-previousAmount);
				previousAmount = numWritten;
				if (percentage > lastp) {
					lastp = percentage;
				}
				progressBar.setValue(percentage);
				updateStatus("Downloading maps : (" + bytesPerSecond + " Kb/s)");
			}
			System.out.println("[Downloader] Download Complete");
			System.out.println("[Downloader] Total Bytes Downloaded: "+numWritten);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (fout != null) {
					fout.close();
				}
				frame.setVisible(false);
				frame.dispose();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}

	public static void download_update(String http_address, String models_archive_name) throws MalformedURLException, IOException {
		cache_location = System.getProperty("user.home") + "/ikov_cache2/cache_index_data/";
		BufferedInputStream in = null;
		FileOutputStream fout = null;
				URLConnection conn;
		try {
			in = new BufferedInputStream(new URL(http_address).openStream());
			fout = new FileOutputStream(System.getProperty("user.home") + "/cache_index_data/" + models_archive_name);
			URL url = new URL(http_address);
			conn = url.openConnection();
			final byte data[] = new byte[1024];
			int count;
			int numRead;
			int numWritten = 0;
			int length = conn.getContentLength();
			int lastp = -1;
			int previousAmount = 0, bytesPerSecond = 0;
			while ((count = in.read(data, 0, 1024)) != -1) {
				fout.write(data, 0, count);
				numWritten += count;
            	int percentage = (int)(((double)numWritten / (double)length) * 100D);
				bytesPerSecond = (numWritten-previousAmount);
				previousAmount = numWritten;
				if (percentage > lastp) {
					lastp = percentage;
				}
				//progressBar.setValue(percentage);
			//	updateStatus("Downloading models : (" + bytesPerSecond + " Kb/s)");
			}
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (fout != null) {
					fout.close();
				}
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}

	@SuppressWarnings("unused")
	private void alert(String msg){
		alert("Message",msg,false);
	}
	
	private void alert(String title,String msg,boolean error){
		JOptionPane.showMessageDialog(null,
			   msg,
			   title,
			    (error ? JOptionPane.ERROR_MESSAGE : JOptionPane.PLAIN_MESSAGE));
	}
}