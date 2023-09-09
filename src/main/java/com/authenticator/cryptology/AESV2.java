package com.authenticator.cryptology;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

@Component
public class AESV2 {

	private final String IV;
	private final String SALT;
	private final String PSWD;
	private final byte[] IVARR = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	private IvParameterSpec ivspec;
	private SecretKeySpec secret;
	private Cipher cipher;

	/**
	 * Calling this constructor would set the default secret key
	 * 
	 * @throws Exception
	 */
	public AESV2() {
		System.out.println("In AES V2");
		PSWD = "48d6b976d7135745b47b407cd8e659a45d8ebaca4ee95f87d5d939604f472268";
		SALT = "dc0da04af8fee58593442bf834b30739";
		IV = "dc0da04af8fee58593442bf834b30739";
		ivspec = new IvParameterSpec(IVARR);

		try {
			secret = getSecretKeySpec();
			cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private SecretKeySpec getSecretKeySpec() throws NoSuchAlgorithmException, InvalidKeySpecException {
		byte[] saltBytes = SALT.getBytes();

		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
		KeySpec spec = new PBEKeySpec(PSWD.toCharArray(), saltBytes, 65556, 256);
		SecretKey secretKey = factory.generateSecret(spec);
		SecretKeySpec secret = new SecretKeySpec(secretKey.getEncoded(), "AES");

		return secret;
	}

	/**
	 * This method would encrypt the given content
	 * 
	 * @param strToEncrypt The content to encrypt
	 * @return The encrypted format of the content
	 * @throws InvalidKeySpecException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidAlgorithmParameterException
	 * @throws InvalidKeyException
	 * @throws UnsupportedEncodingException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 */
	public String encrypt(String strToEncrypt) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
		byte[] ivBytes = IV.getBytes();

		cipher.init(Cipher.ENCRYPT_MODE, secret, ivspec);

		byte[] encryptedTextBytes = cipher.doFinal(strToEncrypt.getBytes("UTF-8"));

		return Base64.getEncoder().encodeToString(encryptedTextBytes);
	}

	/**
	 * This method would decrypt the given content
	 * 
	 * @param strToDecrypt The content to decrypt
	 * @return The decrypted format of the content
	 * @throws InvalidKeySpecException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidAlgorithmParameterException
	 * @throws InvalidKeyException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 */
	public String decrypt(String strToDecrypt) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		byte[] ivBytes = IV.getBytes();

		cipher.init(Cipher.DECRYPT_MODE, secret, ivspec);

		return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
	}

}