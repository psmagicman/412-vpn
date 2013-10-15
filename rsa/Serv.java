import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;


public class Serv {
public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, IOException, ClassNotFoundException {

		Cipher rsad;
		rsad = Cipher.getInstance("RSA");

		
	      ServerSocket srvr = new ServerSocket(1234);
	         Socket skt = srvr.accept();
	         ObjectInputStream ois = new ObjectInputStream(skt.getInputStream());
	         Key pubkey = (Key)ois.readObject();
	         System.out.println(pubkey);
	 		rsad.init(Cipher.DECRYPT_MODE, pubkey);

	}
}
