/**
 * 
 * This class is responsible for following the steps that
 * the program takes from connection to sending data to receiving data...
 *
 */

import java.util.*;

public class Step_Tracer {

	/** Variables **/
	
	private ArrayList<String> wire_data;
	private int continue_counter;
	private int num_steps;
	
	/** End Variables **/
	
	public Step_Tracer() {
		reset_step_tracer();
		System.out.println("num_steps = " + num_steps + " continue_counter = " + continue_counter);
	}
	
	private void reset_step_tracer() {
		wire_data = new ArrayList<String>();
		continue_counter = 0;
		num_steps = 0;
	}
	
	public void insert_steps(String step) {
		//wire_data.append(step);
		System.out.println(step);
		wire_data.add(num_steps, step);
		//num_steps++;
	}
	
	public String print_steps() throws NullPointerException {
		System.out.println(wire_data.get(continue_counter));
		String data = wire_data.get(continue_counter);
		//continue_counter++;
		return data;
	}
	
	
}
