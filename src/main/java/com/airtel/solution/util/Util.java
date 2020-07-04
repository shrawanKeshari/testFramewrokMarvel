package com.airtel.solution.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.testng.Reporter;

public class Util {
	
	public static int getIntVal(Object value) {
		String appId = String.valueOf(value);
		int intVal = Integer.parseInt(appId.substring(0, appId.length() - 2));
		
		return intVal;
	}
	
	public static File writeByte(byte[] contentBytes, String fileName) {
		File file = new File(fileName);

		try {

			// Initialize a pointer
			// in file using OutputStream
			OutputStream os = new FileOutputStream(file);

			// Starts writing the bytes in it
			os.write(contentBytes);
			Reporter.log("Successfully" + " byte inserted");

			// Close the file
			os.close();
		}

		catch (Exception e) {
			Reporter.log("Exception: " + e);
			return null;
		}

		return file;
	}
}
