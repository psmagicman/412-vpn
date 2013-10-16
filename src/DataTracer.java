import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class DataTracer extends JFrame{
	
	/** Variables **/
	
	private JButton continue_button;
	private JTextArea data_area;
	private JPanel data_panel;
	private JLabel data_title;
	private Toolkit toolkit;
	private Dimension screen;
	private JScrollPane scroll;
	
	// http://stackoverflow.com/questions/10177183/java-add-scroll-into-text-area
	
	/** End Variables **/
	
	public DataTracer() {
		setSize(800, 600);
		toolkit = getToolkit();
		screen = toolkit.getScreenSize();
		// set the location of the frame to the middle of the screen
		setLocation(/*screen.width / 2 -*/ getWidth() / 2, screen.height / 2 - getHeight() / 2);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		define_steps_gui();
		setTitle(data_title.getText());
		
		setVisible(true);
		
		data_tracer_listener();
	}
	
	private void define_steps_gui() {
		JPanel data_panel = new JPanel();
		data_panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		data_panel.setLayout(new GridLayout(2, 1));
		
		JPanel scroll_panel = new JPanel();
		scroll_panel.setBorder(new TitledBorder(new EtchedBorder(), "Data Display Area"));
		
		// define button/textarea/label
		data_title = new JLabel("Data Tracer");
		continue_button = new JButton("Continue");
		data_area = new JTextArea(15, 60);
		data_area.setEditable(false);
		scroll = new JScrollPane(data_area);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		// add elements to frame
		scroll_panel.add(scroll);
		
		data_panel.add(scroll_panel);
		data_panel.add(continue_button);
		add(data_panel);
	}
	
	private void data_tracer_listener() {
		// listen for the continue button and print into the textarea
		continue_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent E) {
				String output = GUI.trace_steps.poll() + "\n";
				data_area.append(output);
				data_area.setCaretPosition(data_area.getDocument().getLength());
			}
		});
	}
	
}
