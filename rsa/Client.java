import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;


public class Client {

	public static void main(String[] args) throws UnknownHostException, IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		
		Cipher rsa;
		String me = "chubby otter";
		rsa = Cipher.getInstance("RSA");
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(512);
        KeyPair keys = keyGen.genKeyPair();
        System.out.println(keys.getPublic());
		rsa.init(Cipher.ENCRYPT_MODE, keys.getPublic());
		byte[] encrypted  = rsa.doFinal(me.getBytes());
		
		Socket skt = new Socket("localhost", 1234);
		ObjectOutputStream oos = new ObjectOutputStream(skt.getOutputStream());
		oos.writeObject(keys.getPublic());
        BufferedReader in = new BufferedReader(new
           InputStreamReader(skt.getInputStream()));
	}
}
