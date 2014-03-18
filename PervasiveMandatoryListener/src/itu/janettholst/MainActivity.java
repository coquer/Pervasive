package itu.janettholst;

import java.rmi.RemoteException;


public class MainActivity {
	
	
	public static void main(String[] args) throws RemoteException{
		
		Listener listener = new Listener("rmi://10.25.252.253/MyService", "itu.zone0.zonedorsyd");
		
	}
}
