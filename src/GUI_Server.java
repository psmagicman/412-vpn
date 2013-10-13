import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class GUI_Server extends JFrame {

	/** Variables **/
	private Toolkit toolkit;
	private Dimension screen;
	
	private JLabel frame_title;
	private JTextField port;
	private JLabel port_label;
	private JTextArea send_message;
	private JLabel send_message_label;
	private JTextArea receive_message;
	private JLabel receive_message_label;
	private JButton port_button;
	
	private JPanel frame_grid;
	
	/** End Variables **/
	
	public GUI_Server() {
		
		setSize(800, 600);
		toolkit = getToolkit();
		screen = toolkit.getScreenSize();
		setLocation(screen.width / 2 - getWidth() / 2, screen.height / 2 - getHeight() / 2);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		define_server_gui();
		setTitle(frame_title.getText());
		setVisible(true);
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
		
		// textfields/textareas
		port = new JTextField(5);
		send_message = new JTextArea();
		send_message.setEditable(false);
		receive_message = new JTextArea();
		receive_message.setEditable(false);
		
		// buttons
		port_button = new JButton("Open Port");
		
		// set grid panels
		JPanel frame_grid = new JPanel();
		JPanel text_grid = new JPanel();
		JPanel button_grid = new JPanel();
		frame_grid.setLayout(new GridLayout(4, 1));
		text_grid.setLayout(new GridLayout(3, 2));
		button_grid.setLayout(new GridLayout(1, 2));
		
		// add the elements to text_grid panel
		text_grid.add(port_label);				// (1,1)
		text_grid.add(port);					// (1,2)
		text_grid.add(send_message_label);		// (2,1)
		text_grid.add(send_message);			// (2,2)
		text_grid.add(receive_message_label);	// (3,1)
		text_grid.add(receive_message);			// (3,2)
		
		// add the button to button_grid panel
		button_grid.add(port_button);
		
		// add the sub-grids to the main grid
		frame_grid.add(text_grid);
		// row 2 
		// row 3
		frame_grid.add(port_button);
		
		// add to grid to the frame
		add(frame_grid);
	}

}
