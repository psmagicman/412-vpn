import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import java.io.UnsupportedEncodingException;
import java.security.*;
import java.util.*;

/**
 * 
 * @author Julien
 * This class is responsible for doing all of the encryption and decryption 
 * 
 * Algorithm:
 * 1. get the plain text
 * 2. generate the key
 * 3. pass into the block cipher
 * 4. get the cipher text
 * 
 * 	Example of how to use the crypto java file
 * 		try {
			SecretKey my_key = generate_secret_key("yolo");
			String message = "hello";
			System.out.println("key = yolo");
			System.out.println("message = " + message);
			byte[] cipher = encrypt_AES(my_key, message);
			System.out.println("-------");

			String plaintext = decrypt_AES(my_key, cipher);
			System.out.println(plaintext);

		} 
		catch(Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
 */

public class VPNCrypto {

	public VPNCrypto() {

	}

	// http://stackoverflow.com/questions/4319496/how-to-encrypt-and-decrypt-data-in-java
	// http://www.coderanch.com/t/525677/Security/AES-decryption-InvalidKeyException-Parameters-missing
	/**
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static AESCipher encryptAES(SecretKey key, String message) throws Exception {
		Cipher aes = Cipher.getInstance("AES/CBC/PKCS5Padding");
		aes.init(Cipher.ENCRYPT_MODE, key);
		// need to have the iv carry over or else there will be an invalid key exception thrown at the decryption phase
		return new AESCipher(aes.getIV(), aes.doFinal(message.getBytes()));
	}


	/**
	 * 
	 * @param key
	 * @param ciphertext
	 * @return
	 * @throws Exception
	 */
	public static String decryptAES(SecretKey key, AESCipher cipher) throws Exception {
		Cipher aes = Cipher.getInstance("AES/CBC/PKCS5Padding");
		aes.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(cipher.iv));

		return new String(aes.doFinal(cipher.ciphertext));
	}

	/**
	 * generate_secret_key function
	 * This function uses the SecretKeyFactory to generate a key with a salt
	 * @param input_key
	 * @return
	 * @throws Exception 
	 */
	public static SecretKey generate_secret_key(String input_key) throws Exception {

		// hash the passphrase with some message digest algorithm
		String passphrase = "Julien is the best";		// change the passphrase?
		MessageDigest digest = MessageDigest.getInstance("SHA-512");
		digest.update(passphrase.getBytes());

		// generate key with a salt
		byte[] salt = digest.digest();
		int iterations = 10000;
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		SecretKey temp = factory.generateSecret(new PBEKeySpec(input_key.toCharArray(), salt, iterations, 128));
		return new SecretKeySpec(temp.getEncoded(), "AES");
	}

	/**
	 * Generates an RSA keypair
	 * @return the rsa keypair
	 */
	public static KeyPair generateRSAKeys () {
		KeyPairGenerator keyGen;
		try {
			keyGen = KeyPairGenerator.getInstance("RSA");
			keyGen.initialize(512);
			KeyPair keys = keyGen.genKeyPair();
			return keys;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/** 
	 * Encrypt message with RSA
	 * @param publicKey the public key used for encrypting
	 * @param plaintext the plaintext to encrypt
	 * @return byte array containing ciphertext of plaintext
	 */
	public static byte[] encryptRSA(Key publicKey, String plaintext){
		Cipher rsa;
		try {
			rsa = Cipher.getInstance("RSA");
			rsa.init(Cipher.ENCRYPT_MODE, publicKey);
			byte[] encrypted  = rsa.doFinal(plaintext.getBytes());
			return encrypted;

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Decrypt RSA encrypted ciphertext
	 * @param privateKey the private key to decrypt with
	 * @param ciphertext the ciphertext that will be decrypted
	 * @return the plaintext
	 */
	public static String decryptRSA(Key privateKey, byte[] ciphertext){
		Cipher rsa;
		try {
			rsa = Cipher.getInstance("RSA");
	 		rsa.init(Cipher.DECRYPT_MODE, privateKey);
	 		return new String (rsa.doFinal(ciphertext), "UTF-8");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
