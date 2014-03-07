package itu.janettholst;

import java.rmi.RemoteException;

import dk.pervasive.jcaf.ContextEvent;
import dk.pervasive.jcaf.EntityListener;
import dk.pervasive.jcaf.entity.Person;
import dk.pervasive.jcaf.impl.RemoteEntityListenerImpl;
import dk.pervasive.jcaf.util.AbstractContextClient;

public class Listener extends AbstractContextClient implements EntityListener {
	
	private RemoteEntityListenerImpl listener;
	public String currentLocation = "";
	public String currentName = "";
	
	public Listener(String service_uri) {
		super(service_uri);
		try {
			listener = new RemoteEntityListenerImpl();
			listener.addEntityListener(this);
		} catch (RemoteException ignored) {}
		
		try {
        	getContextService().addEntityListener(listener, Person.class);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	
	public void contextChanged(ContextEvent event) {
		//Reading the location item id. I dont know if the best way is to pass the information as Location.id, 
		System.out.println("change occured");
		currentLocation = event.getItem().getId().toString();
		System.out.println("Current location: " + currentLocation);
		currentName = event.getEntity().getId();
		System.out.println("Current name: " + currentName);
		
		if (currentLocation.equals("itu.zone0.zonedorsyd")){
			//Show display with the current User's name.
			System.out.println("Welcome " + currentName + "!");
		}
		else {
			//Show default display
			System.out.println("ITUITUITUITUITUITU");

		}
	}

	@Override
	public void run() {
		
	}

}
