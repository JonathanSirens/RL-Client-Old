package org.ikov.client.updates;

import java.net.*;
import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ModelsUpdate {

	private static final int BUFFER = 1024;
	
	private static String cache_location;
	
	public static final String models_file = "https://dl.dropboxusercontent.com/u/344464529/models.zip";
	public static final File models_update_file = new File(System.getProperty("user.home") + "/ikov_cache2/versions/models_version");
	public static final File cache_directory = new File(System.getProperty("user.home") + "/ikov_cache2/");

	public static final int model_version = 15;
	
	public static boolean checkVersion() {
		try {
			if(cache_directory.exists() && cache_directory.isDirectory()) {
				if(!models_update_file.exists()) {
					addComponentsToPane(frame.getContentPane());
					frame.setLocationRelativeTo(null);
					frame.setPreferredSize(new Dimension(400, 80));
					frame.setVisible(true);
					frame.pack();
					download_models(models_file, "models.zip");
					unzip_models(System.getProperty("user.home") + "/ikov_cache2/cache_index_data/models.zip");
					BufferedWriter writer = new BufferedWriter(new FileWriter(models_update_file));
					writer.write(String.valueOf(model_version));
					writer.close();	
				} else {
					final BufferedReader reader = new BufferedReader(new FileReader(models_update_file));
					if(Integer.valueOf(reader.readLine()) != model_version) {
						addComponentsToPane(frame.getContentPane());
						frame.setLocationRelativeTo(null);
						frame.setPreferredSize(new Dimension(400, 80));
						frame.setVisible(true);
						frame.pack();
						download_models(models_file, "models.zip");
						unzip_models(System.getProperty("user.home") + "/ikov_cache2/cache_index_data/models.zip");
						BufferedWriter writer = new BufferedWriter(new FileWriter(models_update_file));
						writer.write(String.valueOf(model_version));
						writer.close();	
					}
					reader.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			frame.setVisible(false);
			frame.dispose();
		}
		return false;
	}
	public static void unzip_models(String update) {
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
	static JFrame frame = new JFrame("Models Updater");
	private static String status = "Checking if there is a model update...";

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
	private static void download_models(String http_address, String models_archive_name) throws MalformedURLException, IOException {
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
				updateStatus("Downloading models : (" + bytesPerSecond + " Kb/s)");
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