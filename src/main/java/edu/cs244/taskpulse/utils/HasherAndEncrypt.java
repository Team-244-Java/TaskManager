package edu.cs244.taskpulse.utils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Properties;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;


public class HasherAndEncrypt{

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
		String path = HasherAndEncrypt.class.getClassLoader().getResource(source).getPath();

		return path;
	}

	public static String getProperty(String key) {

		if (prop == null) {

			String path = getResourcePath(PROPERTY_PATH);

			try (InputStream input = new FileInputStream(path)) {

				HasherAndEncrypt.prop = new Properties();

				prop.load(input);

			} catch (Exception ex) {
				System.out.println("Could not locate the " + HasherAndEncrypt.prop);
			}
		}

		if (prop != null) {
			return prop.getProperty(key);
		}

		return "";
	}
	
	private static Cipher getCipherInstance(int mode) throws Exception {

		byte[] iv = HasherAndEncrypt.getProperty("sec.iv").getBytes();
		IvParameterSpec ivspec = new IvParameterSpec(iv);

		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
		String secKey = HasherAndEncrypt.getProperty("sec.key");
		String secSalt = HasherAndEncrypt.getProperty("sec.salt");
		KeySpec spec = new PBEKeySpec(secKey.toCharArray(), secSalt.getBytes(), 65536, 256);
		SecretKey tmp = factory.generateSecret(spec);
		SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
		cipher.init(mode, secretKey, ivspec);

		return cipher;

	}

	public static String encrypt(String msg) {
		try {
			Cipher cipher = getCipherInstance(Cipher.ENCRYPT_MODE);
			return Base64.getEncoder().encodeToString(cipher.doFinal(msg.getBytes(StandardCharsets.UTF_8)));
		} catch (Exception e) {
			System.out.println("Error encrypting: " + e.toString());
		}
		return null;
	}

	public static String decrypt(String encMsg) {
		try {
			Cipher cipher = getCipherInstance(Cipher.DECRYPT_MODE);
			return new String(cipher.doFinal(Base64.getDecoder().decode(encMsg)));
		} catch (Exception e) {
			System.out.println("Error decrypting: " + e.toString());
		}
		return null;
	}
}
