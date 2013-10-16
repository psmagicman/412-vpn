import java.net.*;
import java.security.Key;
import java.io.*;

import javax.crypto.SecretKey;

public class TCPServer {
	String sharedSecret;
	Key publicKey;
	SecretKey aesKey;
	
	ServerSocket listenerSocket;
	public void main (String args[])
	{
		// port number needs to be grabbed from the GUI
		int port = Integer.parseInt(args[0]);
		sharedSecret = args[1];
		
		try {
			listenerSocket = new ServerSocket(port);
			
			System.out.println("Started listening");
			Socket clientSocket = listenerSocket.accept();
			//getClientString(clientSocket);
		} catch(IOException e) {
			System.out.println("IOexception: " + e.getMessage());
		}
	}
	
	
	/*
	 *  Input: clientSocket
	 *  
	 *  Return: String
	 */
	public String getClientString(Socket clientSocket) {
		String clientMessage = "";
		try {
			BufferedReader clientBuffer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			clientMessage = clientBuffer.readLine();
		} catch(IOException e) {
			System.out.println("IOexception Server: " + e.getMessage());
		}
		
		return clientMessage;
	}
	
	/**
	 * This function will be called in tandem with the corresponding auth in TCPCLient.java
	 * @param skt
	 * @return
	 */
	public boolean auth(Socket skt) {
		try {
			aesKey = VPNCrypto.generate_secret_key("WHAT IS SUPPOSED TO GO HERE?");
			ObjectInputStream ois = new ObjectInputStream(skt.getInputStream());
			ObjectOutputStream oos = new ObjectOutputStream(skt.getOutputStream());

			//receive the public key from the client who generates the keypair first
			publicKey = (Key) ois.readObject();
			//then, send back the shared secret to confirm identity and send over symmetric key
			byte[] encrypted = VPNCrypto.encrypt_RSA(publicKey, sharedSecret);
			oos.writeInt(encrypted.length);
			oos.write(encrypted);
			oos.writeObject(aesKey);
			//expect back a new byte array which says if auth failed, or if it passed then the secret phase encoded with the symm key
			//send back second phase pass/fail
			int respSize = ois.readInt();
			byte[] resp = new byte[respSize];
			ois.read(resp);
			if (new String(resp, "UTF8").equals("FAILED")){
				return false;
			}
			else {
				if( VPNCrypto.decrypt_AES(aesKey, resp).equals(sharedSecret)){
					oos.writeUTF("PASS");
					return true;
				}
				else {
					oos.writeUTF("FAILED");
					return false;
				}
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}
}
