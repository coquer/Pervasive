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
	private String display_Id;
	private String standardMessage;
	private GUI display;
	
	public Listener(String service_uri, String displayId) {
		super(service_uri);
		display_Id = displayId;
		
		standardMessage = "Welcome to " + display_Id  + ".\nDownload our Android app if you want us to assist you here on campus.";
		
		//Create an instance of the display that we use...
		display = new GUI(displayId, standardMessage);
		
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
		
		if (currentLocation.equals(display_Id)){
			//Show display with the current User's name.
			//System.out.println("Welcome " + currentName + "!");
			display.setTextToDisplay("Welcome " + currentName + " to zone "+ display_Id + "!");
		}
		else {
			//Show default display
			display.setTextToDisplay(standardMessage);

		}
	}

	@Override
	public void run() {
		
	}

}


