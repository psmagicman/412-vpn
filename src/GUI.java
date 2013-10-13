import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI extends JFrame {
	
	/** Variables **/
	private JLabel frame_title;
	private JRadioButton client_toggle;
	private JRadioButton server_toggle;
	private JButton toggle_confirm;
	private JPanel buttons_area;
	private Toolkit toolkit;
	private Dimension screen;
	/** End of Variables **/
	
	public GUI() {
		
		// set the window
		setSize(300, 200);
		toolkit = getToolkit();
		screen = toolkit.getScreenSize();
		// set the location of the window to the middle of the screen
		setLocation(screen.width / 2 - getWidth() / 2, screen.height / 2 - getHeight() / 2);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		define_variables();
		setTitle(frame_title.getText());
		setVisible(true);
		
		gui_listener();
	}
	
	/**
	 * define_variables function
	 * This function defines the variables for the GUI
	 */
	private void define_variables() {
		JPanel buttons_area = new JPanel();
		buttons_area.setLayout(new GridLayout(3, 1));
		
		// define the buttons and title
		frame_title = new JLabel("VPN Client/Server Toggle");
		//frame_title.setHorizontalAlignment(frame_title.CENTER);
		client_toggle = new JRadioButton("Client");
		server_toggle = new JRadioButton("Server");
		toggle_confirm = new JButton("Submit");
		
		// add the radios to a button group
		ButtonGroup group = new ButtonGroup();
		group.add(client_toggle);
		group.add(server_toggle);
		
		// add the buttons and the title to the window
		//add(frame_title, BorderLayout.NORTH);
		buttons_area.add(client_toggle);
		buttons_area.add(server_toggle);
		buttons_area.add(toggle_confirm);
		add(buttons_area);
	}
	
	/**
	 * gui_listener function
	 * This function is responsible for actionlistener of the radio buttons
	 */
	private void gui_listener() {
		
		client_toggle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("client");
			}
		});
		
		server_toggle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("server");
			}
		});
		
	}
}
