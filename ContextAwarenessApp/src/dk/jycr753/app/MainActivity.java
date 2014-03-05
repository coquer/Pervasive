package dk.jycr753.app;

import java.rmi.RemoteException;


public class MainActivity {
	
	
	public static void main(String[] args) throws RemoteException{
		
		String location = "";
		Listener listener = new Listener("rmi://10.25.252.253/myService");
		
	}
}
