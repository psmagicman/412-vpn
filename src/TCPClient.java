import java.net.*;
import java.security.KeyPair;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.io.*;

import javax.crypto.BadPaddingException;
import javax.crypto.SecretKey;
import javax.swing.JOptionPane;

public class TCPClient {
	String sharedSecret;
	KeyPair clientKeyPair;
	PublicKey serverPublicKey;
	SecretKey aesKey;
	private Socket clientSocket;
	public void main (String args[])
	{
		// values specified from the GUI
		//String clientMessage = "hello world";
		String hostname = args[0];
		int port = Integer.parseInt(args[1]);
		boolean ret = false;
		sharedSecret = args[2];
		
		try {
			clientSocket = new Socket(hostname, port);
			GUI.trace_steps.add("client socket created");
			ret = auth(clientSocket);
			if (ret == false) {
				clientSocket.close();
				JOptionPane.showMessageDialog(null, "Connection Failed -Please restart client.");
				
			}
		} catch (IOException e){
			System.out.println("IOException Client: " + e.getMessage());
		}
	}
	

	
	/**
	 *  This function will be called in tandem with the corresponding auth in TCPServer.java
	 * @param skt
	 * @return
	 */
	/**
	 *  This function will be called in tandem with the corresponding auth in TCPServer.java
	 *  This function is based on slide 37 of Authentication/Key establishment lecture slides
	 *  Mutual authentication with symmetric key
	 * @param skt
	 * @return
	 * @throws  
	 * @throws Exception 
	 */
	public boolean auth(Socket skt) {
		System.out.println("Running auth");
		try{
			ObjectInputStream ois = new ObjectInputStream(skt.getInputStream());
			ObjectOutputStream oos = new ObjectOutputStream(skt.getOutputStream());
			//for generating nonces
			SecureRandom sr = new SecureRandom();
			byte[] nonceBytes = new byte[4];
			sr.nextBytes(nonceBytes);
			int nonce = byteArrayToInt(nonceBytes);
			aesKey = VPNCrypto.generate_secret_key(sharedSecret);

			//first, send over hello string and nonce
			oos.writeUTF("CLIENT");
			oos.writeInt(nonce);
			oos.flush();

			//expect back the server nonce and encrypted message E("SERVER", clientNonce, symmetricKey)
			int serverNonce = ois.readInt();
			AESCipher serverTest = (AESCipher) ois.readObject();
			//perform checks to see if nonce and entity name (SERVER) are corrrect 
			//if so then send back E("CLIENT", serverNonce, symmetricKey) so server can authenticate identity
			String serverTestString = VPNCrypto.decryptAES(aesKey, serverTest);
			String identity = serverTestString.substring(0, "SERVER".length());
			int serverTestNonce = Integer.parseInt(serverTestString.substring("SERVER".length()));
			if (identity.equals("SERVER") && serverTestNonce==nonce){
				//send response status 1 = passed first stage auth
				oos.writeInt(1);
				oos.writeObject(VPNCrypto.encryptAES(aesKey, "CLIENT"+serverNonce));
				oos.flush();
			}
			else{
				oos.writeInt(0);
				oos.flush();
				return false;
			}
			//wait for server authentication result - same code (0 = failure)
			//if successful exchange RSA pub keys for signature validation during conversations
			if (ois.readInt()==1){
				clientKeyPair = VPNCrypto.generateRSAKeys();
				oos.writeObject(clientKeyPair.getPublic());
				oos.flush();
				serverPublicKey = (PublicKey) ois.readObject();
				return true;

			}
			else{
				return false;
			}
		} catch (EOFException e) {
			System.out.println("Connection termminated due to mismatched Shared Secret Value");
			JOptionPane.showMessageDialog(null, "Connection termminated due to mismatched Shared Secret Value");
			return false;
		} catch(IOException e){	
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch(BadPaddingException e){
			JOptionPane.showMessageDialog(null, "Bad key - mismatched Shared Secret Value");
			System.out.println("Bad key - mismatched Shared Secret Value");
			return false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public int byteArrayToInt(byte[] b) 
	{
		return   b[3] & 0xFF |
				(b[2] & 0xFF) << 8 |
				(b[1] & 0xFF) << 16 |
				(b[0] & 0xFF) << 24;
	}
	public Socket getClientSocket(){
		return clientSocket;
	}
	
	public void send(String args[])
	{
		// values specified from the GUI
		String clientMessage = args[0];
		MessageGram send_package;
		
		try {
			send_package = VPNCrypto.sendMessage(clientMessage, aesKey, clientKeyPair.getPrivate());
			ObjectOutputStream toServer = new ObjectOutputStream(clientSocket.getOutputStream());
			toServer.writeObject(send_package);
			toServer.flush();
		} catch (IOException e){
			System.out.println("IOException Client: " + e.getMessage());
		} catch (Exception e){
			System.out.println("Exception Client: " + e.getMessage());
		}
	}
	
	public String getClientString(Socket clientSocket) {
		String clientMessage = "";
		MessageGram recieve_package;
		try {
			ObjectInputStream clientBuffer = new ObjectInputStream(clientSocket.getInputStream());
			recieve_package = (MessageGram) clientBuffer.readObject();
			clientMessage = VPNCrypto.receiveMessage(recieve_package, aesKey, serverPublicKey);
		} catch(IOException e) {
			System.out.println("IOexception Server: " + e.getMessage());
		} catch(Exception e) {
			System.out.println("Exception Server: " + e.getMessage());
		}
		
		GUI.trace_steps.add("message ready to send");
		return clientMessage;
	}
}