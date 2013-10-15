import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class GUIClient extends JFrame {

	/** Variables **/
	private Toolkit toolkit;
	private Dimension screen;
	
	private JLabel frame_title;
	private JLabel hostname_label;
	private JTextField hostname;
	private JTextField port;
	private JLabel port_label;
	private JTextField send_message;
	private JLabel send_message_label;
	private JTextArea receive_message;
	private JLabel receive_message_label;
	private JLabel shared_key_label;
	private JTextField shared_key;
	private JButton message_send;
	private JButton message_connect;
	
	
	private JPanel frame_grid;
	
	/** End Variables **/
	
	public GUIClient() {
		
		setSize(800, 600);
		toolkit = getToolkit();
		screen = toolkit.getScreenSize();
		setLocation(screen.width / 2 - getWidth() / 2, screen.height / 2 - getHeight() / 2);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		define_client_gui();
		setTitle(frame_title.getText());
		setVisible(true);
		gui_client_listener();
	}
	
	/**
	 * define_server_gui function
	 * This function defines the values for Swing elements
	 */
	private void define_client_gui() {
		// labels
		frame_title = new JLabel("Client");
		hostname_label = new JLabel("Home Name (IP address):");
		port_label = new JLabel("Port:");
		send_message_label = new JLabel("Message to send:");
		receive_message_label = new JLabel("Message received:");
		message_connect = new JButton("Connect to Server");
		message_send = new JButton("Send Message");
		shared_key_label = new JLabel("Shared Secret Key:");
		
		// textfields/textareas
		hostname = new JTextField();
		port = new JTextField(5);
		send_message = new JTextField();
		//send_message.setEditable(false);
		receive_message = new JTextArea();
		receive_message.setEditable(false);
		shared_key = new JTextField();
		
		// buttons
		
		// set grid panels
		JPanel frame_grid = new JPanel();
		frame_grid.setLayout(new GridLayout(6, 2));
		
		// add the elements to the panel
		frame_grid.add(hostname_label);
		frame_grid.add(hostname);
		frame_grid.add(port_label);
		frame_grid.add(port);
		frame_grid.add(shared_key_label);
		frame_grid.add(shared_key);
		frame_grid.add(send_message_label);
		frame_grid.add(send_message);
		frame_grid.add(message_connect);
		frame_grid.add(message_send);
		frame_grid.add(receive_message_label);
		frame_grid.add(receive_message);
		
		// add the panel to the frame
		add(frame_grid);
	}
	
	/**
	 * gui_client_listener function
	 * 
	 */
	private void gui_client_listener() {

		message_connect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    String [] args = new String[2];
				args[0] =  hostname.getText();
			    args[1] = port.getText();
				System.out.println("connect");
				TCPClient tcp_client = new TCPClient();
				tcp_client.main(args);
			}
		});
		
		message_send.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("send");
			}
		});
		
	}

}