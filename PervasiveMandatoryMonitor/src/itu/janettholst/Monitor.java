package itu.janettholst;


import java.io.IOException;
import java.rmi.RemoteException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Handler;


import dk.pervasive.jcaf.entity.Person;
import dk.pervasive.jcaf.item.Location;
import dk.pervasive.jcaf.relationship.Located;
import dk.pervasive.jcaf.util.AbstractMonitor;

public class Monitor extends AbstractMonitor{

	//Initiated Socket variables
	public String socketInfoName = "Janett Holst";
	public String socketInfoID = "http://pit.itu.dk:7331/location-of/18002D9E5518";
	
	public Person itu_person = new Person(socketInfoName, socketInfoName);
	public Located locatedAt = null;
	public Location position = new Location("location_id");
	public String person_location = "";
//	public boolean presentDevices = false;
	public boolean presentDevices = true;

	public Monitor(String service_uri) throws RemoteException {
		super(service_uri);	
		
		load();
		locatedAt = new Located(this.getClass().getName());
		
		//Looping the check of location every 5 seconds.
		ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
		exec.scheduleAtFixedRate(new Runnable() {
		  @Override
		  public void run() {
			  
			//Check socket thingy:
			  ////////////////////////////////
			  // set socketInfoID
			  // set socketInfoName
			  
			  
			  String previous = person_location;
			  person_location = RestfulClient.getLocation(socketInfoID);
				
			  itu_person.setId(socketInfoName);
			  
			  if (presentDevices == false){
				  position.setId("No people near display");
			  }
			  else {
				  position.setId(person_location);
			  }
			  
			  
			  //Only update the entity if it is different.
//			  if (! previous.equals(person_location)){
				  System.out.println("Found a change");
				  System.out.println(itu_person.getName() + " is at " + person_location);
				  
				  //The resetTime function can be used if time is relevant. However, the setId is enough to trigger the contextChanged method
//					locatedAt.resetTime();
				  
				  
					
				  try {
					  getContextService().addContextItem(itu_person.getName(), locatedAt, position);
				  } catch (RemoteException e) {
						// TODO Auto-generated catch block
					  e.printStackTrace();
				  }
//				}
		  }
		  
		//Checked every 5 seconds
		}, 0, 5, TimeUnit.SECONDS);
         
	}
	
	private void load() {
        try {
        	System.out.println("Loading");
            
            getContextService().addEntity(itu_person); 
        } catch (RemoteException e) {
            e.printStackTrace();
            System.out.println("It did not load");
        }
    }
	
	
	@Override
	public void run() {
		}
	
	
	public static void main(String[] args) throws IOException {
			new Monitor("rmi://10.25.252.253/myService");
	
	}

	@Override
	public void monitor(String arg0) throws RemoteException {
		
		
	}

}
