import java.net.*;
import java.security.KeyPair;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.io.*;

import javax.crypto.BadPaddingException;
import javax.crypto.SecretKey;
import javax.swing.JOptionPane;

public class TCPServer {
	String sharedSecret;
	PublicKey clientPublicKey;
	KeyPair serverKeyPair;
	SecretKey aesKey;
	
	ServerSocket listenerSocket;
	Socket clientSocket;
	BufferedReader clientBuffer;
	public void main (String args[])
	{
		// port number needs to be grabbed from the GUI
		int port = Integer.parseInt(args[0]);
		boolean ret = false;
		sharedSecret = args[1];
		
		try {
			listenerSocket = new ServerSocket(port);
			
			System.out.println("Started listening");
			while (true) {
				clientSocket = listenerSocket.accept();
				clientBuffer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				GUI.trace_steps.add("connection received");
				ret = auth(clientSocket);
				if (ret == false) {
					
					clientSocket.close();
				} else {
					break;
				}
			}
		} catch(IOException e) {
			System.out.println("IOexception: " + e.getMessage());
		}
	}
	
	public Socket getClientSocket() {
		return clientSocket;
	}
	
	
	/*
	 *  Input: clientSocket
	 *  
	 *  Return: String
	 */
	public String getClientString(Socket clientSocket) {
		String clientString = "";
		try {
			clientString = clientBuffer.readLine();
		} catch(IOException e){
			
		}
		return clientString;
	}
	
	public void send(String args[])
	{
		// values specified from the GUI
		String clientMessage = args[0];
		
		try {
			DataOutputStream toServer = new DataOutputStream(clientSocket.getOutputStream());
			toServer.writeBytes(clientMessage + "\n");
			toServer.flush();
		} catch (IOException e){
			System.out.println("IOException Client: " + e.getMessage());
		}
	}
	
	/**
	 * This function will be called in tandem with the corresponding auth in TCPCLient.java
	 * This function is based on slide 37 of Authentication/Key establishment lecture slides
	 * Mutual authentication with symmetric key
	 * @param skt
	 * @return
	 * @throws Exception 
	 */
	public boolean auth(Socket skt) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(skt.getOutputStream());
			ObjectInputStream ois = new ObjectInputStream(skt.getInputStream());
			SecureRandom sr = new SecureRandom();
			byte[] nonceBytes = new byte[4];
			sr.nextBytes(nonceBytes);
			int nonce = byteArrayToInt(nonceBytes);
			aesKey = VPNCrypto.generate_secret_key(sharedSecret);
			//first, expect hello string and nonce
			String helloString = ois.readUTF();
			int clientNonce = ois.readInt();
			//send back own nonce, and E("SERVER", clientNonce, symmetricKey) to authenticate identity to client
			oos.writeInt(nonce);
			AESCipher serverResp = VPNCrypto.encryptAES(aesKey, "SERVER"+clientNonce);
			oos.writeObject(serverResp);
			oos.flush();

			//check if auth failed at this point (0=failed), 
			//if not, then proceed to authenticate client
			if (ois.readInt()==0){
				return false;
			}
			else {
				String clientResp = VPNCrypto.decryptAES(aesKey, (AESCipher)ois.readObject());
				String identity = clientResp.substring(0, "CLIENT".length());
				int clientRespNonce = Integer.parseInt(clientResp.substring("CLIENT".length()));
				//if successful exchange RSA pub keys for signature validation during conversations
				if (identity.equals("CLIENT")&&clientRespNonce==nonce){
					oos.writeInt(1);
					oos.flush();
					serverKeyPair = VPNCrypto.generateRSAKeys();
					clientPublicKey = (PublicKey) ois.readObject();
					oos.writeObject(serverKeyPair.getPublic());
					oos.flush();
					return true;
				}
				else{
					oos.writeInt(0);
					oos.flush();
					return false;
				}
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

}
