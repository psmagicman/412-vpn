import java.net.*;
import java.io.*;

public class TCPClient {
	private Socket clientSocket;
	public void main (String args[])
	{
		// values specified from the GUI
		String clientMessage = "hello world";
		String hostname = args[0];
		int port = Integer.parseInt(args[1]);
		String sharedKey = args[2];
		
		try {
			clientSocket = new Socket(hostname, port);
			DataOutputStream toServer = new DataOutputStream(clientSocket.getOutputStream());
			toServer.writeBytes(clientMessage);
			
			//clientSocket.close();
	
		} catch (IOException e){
			System.out.println("IOException Client: " + e.getMessage());
		}
	}
	
	public Socket getClientSocket(){
		return clientSocket;
	}
	
	public void writeMessage(){
		
	}
}