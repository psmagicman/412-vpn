import java.net.*;
import java.io.*;

public class TCPClient {
	public static void main (String args[])
	{
		// values specified from the GUI
		String clientMessage = "hello world";
		int port = 2345;
		String hostname = "localhost";
		
		try {
			Socket clientSocket = new Socket(hostname, port);
			DataOutputStream toServer = new DataOutputStream(clientSocket.getOutputStream());
			toServer.writeBytes(clientMessage);
			
			clientSocket.close();
	
		} catch (IOException e){
			System.out.println("IOException Client: " + e.getMessage());
		}
	}
}