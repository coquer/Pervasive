package Main;

import Listener.Listener;

public class DisplayApp {

	/**
	 * @param args Takes on input parameter where you specify which zone the display is in. 
	 */
	public static void main(String[] args) 
	{
		new Listener(args[0], args[1]);
	}

}
