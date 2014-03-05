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

	
	//Using the predefined classes which the ContextService allready knows about:
	final Person itu_person = new Person("http://pit.itu.dk:7331/location-of/18002D9E5518", "Janett Holst");
	Located locatedAt = null;
	Location position = new Location("hello");
//	final String intendedLocation = "itu.zone4.zone4e";
	public String person_location = "";
	

	public Monitor(String service_uri) throws RemoteException {
		super(service_uri);	
		
		load();
		locatedAt = new Located(this.getClass().getName());
		
		//Looping the check of location every 5 seconds.
		ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
		exec.scheduleAtFixedRate(new Runnable() {
		  @Override
		  public void run() {
			  String previous = person_location;
				person_location = RestfulClient.getLocation("http://pit.itu.dk:7331/location-of/18002D9E5518");
				
				//Only update the entity if it is different.
//				if (! previous.equals(person_location)){
					
					System.out.println("Found a change");
					System.out.println(person_location);
					
					//The resetTime function can be used if time is relevant. However, the setId is enough to trigger the contextChanged method
//					locatedAt.resetTime();
					position.setId(person_location);
					
					try {
						getContextService().addContextItem(itu_person.getId(), locatedAt, position);
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
