package org.runelive.client.updates;

import java.net.*;
import java.io.*;
import java.awt.*;
import javax.swing.*;

public class ClientUpdate {

	private static final int BUFFER = 1024;

	public static final String infoBuffer = "https://dl.dropboxusercontent.com/u/344464529/RuneLive/update.txt";
	public static String clientVersion = "2.40";
	public static String newVersion;
	public static String clientLink;
	public static String pathJarFile;
	
	public static boolean checkLogin() {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(infoBuffer).openStream()));
			String line;
			for (int i = 0; i < 2; i++) {
				line = reader.readLine();
				if (line != null) {
					System.out.println(line);
					if (i == 0) {
						if (line.equals(clientVersion)) {
							System.out.println("RuneLive client is up to date");
							return false;
						}
					}
					if (i == 1) {
						return true;
					}
				}
			}
		} catch (Exception e) {
			clientVersion = "invalid_connection";
			e.printStackTrace();
		}
		return false;		
	}
	public static boolean checkVersion() {
		try {
			//Adding the update gui

			addComponentsToPane(frame.getContentPane());
			frame.setLocationRelativeTo(null);
			frame.setPreferredSize(new Dimension(400, 80));
			frame.setVisible(true);
			frame.pack();

			//Checking the updater file
			BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(infoBuffer).openStream()));
			String line;
			for (int i = 0; i < 2; i++) {
				line = reader.readLine();
				if (line != null) {
					System.out.println(line);
					if (i == 0) {
						if (line.equals(clientVersion)) {
							System.out.println("RuneLive client is up to date");
							frame.setVisible(false);
							frame.dispose();
							return false;
						}
						newVersion = line;
						//Settings.version = newVersion;
						System.out.println("Outdated client version "+clientVersion+" has been detected.");
						System.out.println("Installing RuneLive client version "+newVersion+"...");
					}
					if (i == 1) {
						clientUpdate(line);
						return true;
					}
				}
			}
		} catch (Exception e) {
			clientVersion = "invalid_connection";
			e.printStackTrace();
			frame.setVisible(false);
			frame.dispose();
		}
		return false;
	}

	private static void clientUpdate(String clientDownload) throws MalformedURLException, IOException {
		clientLink = clientDownload;
		try {
			String path = ClientUpdate.class.getProtectionDomain().getCodeSource().getLocation().getPath();
			pathJarFile = URLDecoder.decode(path, "UTF-8");
		} catch (UnsupportedEncodingException exception) {
			exception.printStackTrace();
		}
		downloadFile(clientDownload, getArchivedName());
	}
	
	static JLabel label1 = new JLabel();
	static JLabel label2 = new JLabel("Total percent");
	static JProgressBar progressBar,  progressBar1;
	static JFrame frame = new JFrame("Client Updater");
	private static String status = "Checking if there is a client update...";

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

	private static void downloadFile(String clientDownload, String clientName) throws MalformedURLException, IOException {
		
		boolean failed = false;		
		BufferedInputStream in = null;
		FileOutputStream fout = null;
		URLConnection conn;
		String desktopLoc = null;
		try {
			desktopLoc = pathJarFile;//System.getProperty("user.home") + "/Desktop/"
			System.out.println("Location of file: "+desktopLoc);
			//clientName = clientName.replaceAll("%20", " ");
                        if (desktopLoc.endsWith("/"))
                            desktopLoc += clientName.replaceAll("%20", " ");
			in = new BufferedInputStream(new URL("https://dl.dropboxusercontent.com/u/344464529/RuneLive/RuneLive.jar").openStream());
			fout = new FileOutputStream(pathJarFile);
			URL url = new URL("https://dl.dropboxusercontent.com/u/344464529/RuneLive/RuneLive.jar");
			conn = url.openConnection();
			final byte data[] = new byte[1024];
			int count;
			int numRead;
			int numWritten = 0;
			int length = conn.getContentLength();
			int lastp = -1;
			int previousAmount = 0, bytesPerSecond = 0;
			long startedDownloading = System.currentTimeMillis();
			while ((count = in.read(data, 0, 1024)) != -1) {
				if (System.currentTimeMillis() - startedDownloading > 15000) {
					failed = true;
					break;
				}
				fout.write(data, 0, count);
				numWritten += count;
            	int percentage = (int)(((double)numWritten / (double)length) * 100D);
				bytesPerSecond = (numWritten-previousAmount);
				previousAmount = numWritten;
				if (percentage > lastp) {
					lastp = percentage;
				}
				progressBar.setValue(percentage);
				updateStatus("Downloading new client: (" + bytesPerSecond + " Kb/s)");
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
				if (!failed) {
					new ProcessBuilder("java", "-jar", desktopLoc.substring(1)).start();
					System.exit(0); //calling the method is a must
				}
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}

	private static String getArchivedName() {
		int lastSlashIndex = clientLink.lastIndexOf('/');
		if (lastSlashIndex >= 0 
			&& lastSlashIndex < clientLink.length() -1) { 
			return clientLink.substring(lastSlashIndex + 1);
		} else {
			System.err.println("error retreiving archivaed name.");
		}
		return "";
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