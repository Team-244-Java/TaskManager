package edu.cs244.taskpulse.utils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Properties;

public class Hasher {

	private static final String PROPERTY_PATH = "config.properties";
	private static Properties prop = null;

	public static String getSHA(String input) {

		try {
			MessageDigest md = MessageDigest.getInstance(getProperty("sec.sha"));

			byte[] hash = md.digest(input.getBytes(StandardCharsets.UTF_8));
			BigInteger number = new BigInteger(1, hash);

			// Convert message digest into hex value
			StringBuilder hex = new StringBuilder(number.toString(16));

			// Padding with zeros
			while (hex.length() < 64) {
				hex.insert(0, '0');
			}

			return hex.toString();
		} catch (Exception ex) {
			ex.printStackTrace();

		}
		return null;
	}
	
	public static String getResourcePath(String source) {
		String path = Hasher.class.getClassLoader().getResource(source).getPath();

		return path;
	}

	public static String getProperty(String key) {

		if (prop == null) {

			String path = getResourcePath(PROPERTY_PATH);

			try (InputStream input = new FileInputStream(path)) {

				Hasher.prop = new Properties();

				prop.load(input);

			} catch (Exception ex) {
				System.out.println("Could not locate the " + Hasher.prop);
			}
		}

		if (prop != null) {
			return prop.getProperty(key);
		}

		return "";
	}
}
