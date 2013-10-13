import java.net.*;
import java.io.*;

public class TCPServer {
	public static void main (String args[])
	{
		// port number needs to be grabbed from the GUI
		int port = 2345;
		
		try {
			ServerSocket listenerSocket = new ServerSocket(port);
			
			System.out.println("Started listening");
			while(true) {
				Socket clientSocket = listenerSocket.accept();
				// print string
				printClientString(clientSocket);
			}
		} catch(IOException e) {
			System.out.println("IOexception: " + e.getMessage());
		}
	}
	
	/*
	 *  Input: clientSocket
	 *  
	 *  Prints string message from client buffer
	 */
	public static void printClientString(Socket clientSocket) {
		try {
			BufferedReader clientBuffer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			String clientMessage = clientBuffer.readLine();
			System.out.println(clientMessage);
		} catch(IOException e) {
			System.out.println("IOexception Server: " + e.getMessage());
		}
	}
}
