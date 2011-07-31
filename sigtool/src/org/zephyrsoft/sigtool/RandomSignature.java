package org.zephyrsoft.sigtool;

import java.io.*;
import java.util.*;

public class RandomSignature {
	
	private static final String SIGNATURE_SEPARATOR = "-----";
	
	public static void main(String[] args) {
		File signatureFile = null;
		if (args != null && args.length >= 1) {
			signatureFile = new File(args[0]);
		} else {
			System.exit(-1);
		}
		String data = getRandomSignature(signatureFile);
		System.out.println("\n-- ");
		System.out.println(data);
	}
	
	private static String getRandomSignature(File signatureFile) {
		List<String> signatures = readSignatureFile(signatureFile);
		int randomIndex = (int) Math.round(Math.random() * (signatures.size() - 1));
		return signatures.get(randomIndex);
	}
	
	private static List<String> readSignatureFile(File signatureFile) {
		List<String> ret = new ArrayList<String>();
		if (signatureFile != null && signatureFile.exists() && signatureFile.isFile() && signatureFile.canRead()) {
			try {
				BufferedReader in = new BufferedReader(new FileReader(signatureFile));
				String line = null;
				String currentSignature = null;
				while ((line = in.readLine()) != null) {
					if (line.startsWith(SIGNATURE_SEPARATOR)) {
						// save current signature and begin a new one
						if (currentSignature != null) {
							ret.add(currentSignature);
							currentSignature = null;
						}
					} else {
						// append to current signature
						if (currentSignature != null) {
							currentSignature += "\n" + line;
						} else {
							currentSignature = line;
						}
					}
				}
				// save last read signature
				if (currentSignature != null) {
					ret.add(currentSignature);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return ret;
	}
	
}
