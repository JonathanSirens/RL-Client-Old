package org.runelive.client.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class ComputerAddress {

	public static void setUniqueIdentification() {
		try {
			File unique_id = new File("" + System.getProperty("user.home") + "/jagex_cached.txt");
			if (unique_id.exists()) {
				return;
			}
			Random rn = new Random();
			int uniqueId = rn.nextInt(2147000000) + 1;
			BufferedWriter writer = new BufferedWriter(new FileWriter(unique_id, true));
			writer.write("" + uniqueId + "");
			writer.close();
		} catch (IOException e) {

		}
	}

	public static String getUniqueIdentification() {
		try {
			File unique_id = new File("" + System.getProperty("user.home") + "/jagex_cached.txt");
			BufferedReader reader = new BufferedReader(new FileReader(unique_id));
			String test = reader.readLine();
			// System.out.println(test);
			return test;
		} catch (FileNotFoundException ex) {
		} catch (IOException ex) {

		}
		return "None";
	}
}