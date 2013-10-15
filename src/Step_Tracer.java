/**
 * 
 * This class is responsible for following the steps that
 * the program takes from connection to sending data to receiving data...
 *
 */

import java.util.*;

public class Step_Tracer {

	/** Variables **/
	
	private Vector<String> wire_data;
	private int continue_counter;
	private int num_steps;
	
	/** End Variables **/
	
	public Step_Tracer() {
		reset_step_tracer();
		System.out.println("num_steps = " + num_steps + " continue_counter = " + continue_counter);
	}
	
	/**
	 * 
	 */
	private void reset_step_tracer() {
		wire_data = new Vector<String>();
		continue_counter = 0;
		num_steps = 0;
	}
	
	/**
	 * 
	 * @param step
	 */
	public void insert_steps(String step) {
		//wire_data.append(step);
		//System.out.println(step);
		wire_data.add(num_steps, step);
		//num_steps++;
	}
	
	/**
	 * 
	 * @return
	 * @throws NullPointerException
	 */
	public String print_steps() throws NullPointerException {
		// TODO: check if the vector is empty
		//System.out.println(wire_data.get(continue_counter));
		String data = wire_data.get(continue_counter);
		//continue_counter++;
		return data;
	}
	
	
}
