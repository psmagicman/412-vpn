import java.net.*;
import java.security.KeyPair;
import java.io.*;

import javax.crypto.SecretKey;

public class TCPClient {
	String sharedSecret;
	KeyPair keys;
	SecretKey aesKey;
	private Socket clientSocket;
	public void main (String args[])
	{
		// values specified from the GUI
		//String clientMessage = "hello world";
		String hostname = args[0];
		int port = Integer.parseInt(args[1]);
		sharedSecret = args[2];
		
		try {
			clientSocket = new Socket(hostname, port);
			//DataOutputStream toServer = new DataOutputStream(clientSocket.getOutputStream());
			//toServer.writeBytes(clientMessage);
			
			//clientSocket.close();
	
		} catch (IOException e){
			System.out.println("IOException Client: " + e.getMessage());
		}
	}
	

	
	/**
	 *  This function will be called in tandem with the corresponding auth in TCPServer.java
	 * @param skt
	 * @return
	 */
	public boolean auth(Socket skt) {
        try {
			ObjectInputStream ois = new ObjectInputStream(skt.getInputStream());
			ObjectOutputStream oos = new ObjectOutputStream(skt.getOutputStream());

        	//first generate the RSA keypair
			keys = VPNCrypto.generateRSAKeys();
			//next ,need to send over public key so that first phase auth (server send back secret) can occur
			oos.writeObject(keys.getPublic());
			//receive the encrypted secret pass phrase and symm key and check its validity, if it checks out, 
			//then respond with the secret encoded with symm key
			int encryptedSize = ois.readInt();
			byte[] encrypted = new byte[encryptedSize];
			ois.read(encrypted);
			aesKey = (SecretKey) ois.readObject();
			if(VPNCrypto.decrypt_RSA(keys.getPrivate(), encrypted).equals(sharedSecret)){
				byte[] respEncoded = VPNCrypto.encrypt_AES(aesKey, sharedSecret);
				oos.writeInt(respEncoded.length);
				oos.write(respEncoded);
			}
			else {
				byte[] failMessage = "FAILED".getBytes();
				oos.writeInt(failMessage.length);
				oos.write(failMessage);
				return false;
			}
			//second phase - expect back response from server seeing if secret phrase encoded with symm key was right
			String secondPhaseStatus = ois.readUTF();
			if (secondPhaseStatus.equals("PASS"))
				return true;
			else
				return false;
				
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
	public Socket getClientSocket(){
		return clientSocket;
	}
	
	public void send (String args[])
	{
		// values specified from the GUI
		String clientMessage = args[0];
		
		try {
			DataOutputStream toServer = new DataOutputStream(clientSocket.getOutputStream());
			toServer.writeBytes(clientMessage);
		
	
		} catch (IOException e){
			System.out.println("IOException Client: " + e.getMessage());
		}
	}
}