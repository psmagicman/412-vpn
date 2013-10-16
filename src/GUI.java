import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.*;

import java.util.*;

public class GUI extends JFrame {
	
	/** Variables **/
	private JLabel frame_title;
	private JRadioButton client_toggle;
	private JRadioButton server_toggle;
	private JButton toggle_confirm;
	private JButton crypto_test;
	private JPanel buttons_area;
	private Toolkit toolkit;
	private Dimension screen;
	private enum toggle { Client, Server };
	
	private toggle toggle_switch = toggle.Client;
	
	public static Queue<String> trace_steps;

	/** End of Variables **/
	
	/** Test Variables **/
	/*private JTextField test_insert;
	private JButton test_send_step;
	private JTextField test_get;
	private JButton test_get_step;
	private JPanel test_panel;
	private JPanel super_test_panel;*/
	/** End Test Variables **/
	
	public GUI() {
		
		// set the frame size
		setSize(300, 200);
		toolkit = getToolkit();
		screen = toolkit.getScreenSize();
		// set the location of the frame to the middle of the screen
		setLocation(screen.width / 2 - getWidth() / 2, screen.height / 2 - getHeight() / 2);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		define_variables();
		setTitle(frame_title.getText());
		
		setVisible(true);
		
		//trace = new StepTracer();
		trace_steps = new LinkedList<String>();
		gui_listener();
	}
	
	/**
	 * define_variables function
	 * This function defines the variables for the GUI
	 */
	private void define_variables() {
		JPanel buttons_area = new JPanel();
		buttons_area.setBorder(new EmptyBorder(10, 10, 10, 10) );
		buttons_area.setLayout(new GridLayout(4, 1));
		
		// define the buttons and title
		frame_title = new JLabel("VPN Client/Server Toggle");
		client_toggle = new JRadioButton("Client");
		server_toggle = new JRadioButton("Server");
		toggle_confirm = new JButton("Start");
		crypto_test = new JButton("Test Crypto");
		
		// add the radios to a button group
		ButtonGroup group = new ButtonGroup();
		group.add(client_toggle);
		group.add(server_toggle);
		
		// add the buttons to the frame
		buttons_area.add(client_toggle);
		buttons_area.add(server_toggle);
		buttons_area.add(toggle_confirm);
		buttons_area.add(crypto_test);
		add(buttons_area);
		
		/*JPanel test_panel = define_test();
		
		JPanel super_test_panel = new JPanel();
		super_test_panel.setLayout(new GridLayout(2,1));
		super_test_panel.add(buttons_area);
		super_test_panel.add(test_panel);
		add(super_test_panel);*/
		
	}
	
	/**
	 * gui_listener function
	 * This function is responsible for actionlistener of the radio buttons
	 */
	private void gui_listener() {
		
		DataTracer data_frame = new DataTracer();
		
		client_toggle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				toggle_switch = toggle.Client;
			}
		});
		
		server_toggle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				toggle_switch = toggle.Server;
			}
		});
		
		toggle_confirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switch (toggle_switch) {
					case Client:
						setVisible(false);
						GUIClient client_gui = new GUIClient();
						break;
					case Server:
						setVisible(false);
						GUIServer server_gui = new GUIServer();
						break;
				}
			}
		});
		
		crypto_test.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VPNCrypto crypto = new VPNCrypto();
			}
		});
		
		/*test_send_step.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String test_step = test_insert.getText();
				try {
					trace_steps.add(test_step);
				}
				catch(IllegalStateException i) {
					System.out.println("ISE Message: " + i.getMessage());
				}
			}
		});
		
		test_get_step.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = null;
				try {
					text = trace_steps.poll();
				}
				catch(NullPointerException n) {
					System.out.println("NPE Message: " + n.getMessage());
				}
				//JTextArea temp = new JTextArea(text);
				test_get.setText(text);
			}
		});*/
		
	}
	
	/*private JPanel define_test() {
		JPanel test_panel = new JPanel();
		test_panel.setLayout(new GridLayout(2,2));
		
		test_insert = new JTextField(null);
		test_send_step = new JButton("Test Insert");
		test_get = new JTextField("hurrr");
		test_get.setEditable(false);
		test_get_step = new JButton("Test Get");
		
		test_panel.add(test_insert);
		test_panel.add(test_send_step);
		test_panel.add(test_get);
		test_panel.add(test_get_step);
		
		add(test_panel);
		
		return test_panel;
		
	}*/
}
