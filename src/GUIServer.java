import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;


public class GUIServer extends JFrame {

	/** Variables **/
	Socket clientSocket = null;
	private Toolkit toolkit;
	private Dimension screen;
	
	private JLabel frame_title;
	private JTextField port;
	private JLabel port_label;
	private JTextField send_message;
	private JLabel send_message_label;
	private JTextField receive_message;
	private JLabel receive_message_label;
	private JLabel shared_key_label;
	private JTextField shared_key;
	private JButton message_connect;
	private JButton message_send;
	
	private JPanel frame_grid;
	
	TCPServer tcp_server = null;

	
	/** End Variables **/
	
	public GUIServer() {
		
		setSize(400, 300);
		toolkit = getToolkit();
		screen = toolkit.getScreenSize();
		setLocation(screen.width / 2 - getWidth() / 2, screen.height / 2 - getHeight() / 2);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		define_server_gui();
		setTitle(frame_title.getText());
		setVisible(true);
		GUI.trace_steps.add("server GUI constructed");
		gui_server_listener();
	}
	
	/**
	 * define_server_gui function
	 * This function defines the values for Swing elements
	 */
	private void define_server_gui() {
		// labels
		frame_title = new JLabel("Server");
		port_label = new JLabel("Port:");
		send_message_label = new JLabel("Message to send:");
		receive_message_label = new JLabel("Message received:");
		shared_key_label = new JLabel("Shared Secret Key:");
		
		// textfields/textareas
		port = new JTextField(5);
		send_message = new JTextField();
		receive_message = new JTextField();
		receive_message.setEditable(false);
		shared_key = new JTextField();
		
		// buttons
		message_connect = new JButton("Open Port");
		message_send = new JButton("Message Send");
		
		// set grid panels
		JPanel frame_grid = new JPanel();
		frame_grid.setBorder(new EmptyBorder(10, 10, 10, 10) );
		frame_grid.setLayout(new GridLayout(5, 1, 0, 3));

		
		// add the elements to text_grid panel
		frame_grid.add(port_label);				// (1,1)
		frame_grid.add(port);					// (1,2)
		frame_grid.add(shared_key_label);
		frame_grid.add(shared_key);
		frame_grid.add(send_message_label);		// (2,1)
		frame_grid.add(send_message);			// (2,2)

		
		// add the button to button_grid panel
		frame_grid.add(message_connect);
		frame_grid.add(message_send);
		frame_grid.add(receive_message_label);	// (3,1)
		frame_grid.add(receive_message);			// (3,2)		
		
		// add to grid to the frame
		add(frame_grid);
		GUI.trace_steps.add("server GUI elements defined");
	}
	
	/**
	 * gui_server_listener function
	 * 
	 */
	private void gui_server_listener() {

		message_connect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tcp_server = new TCPServer();
				String [] args = new String[2];
				args[0] =  port.getText();
				args[1] = shared_key.getText();
				tcp_server.main(args);	
				clientSocket = tcp_server.getClientSocket();
				
			}
		});
		
		message_send.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("send");
				String clientMessage = tcp_server.getClientString(clientSocket);
				GUI.trace_steps.add("message received from client");
				System.out.println(clientMessage);
				receive_message.setText(clientMessage);
				
			}
		});
	}

}
