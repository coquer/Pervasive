package itu.janettholst;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
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
	public String socketInfoName = "no name";
	public String socketPre = "http://pit.itu.dk:7331/location-of/";
//	public String socketInfoID = "http://pit.itu.dk:7331/location-of/18002D9E5518";
	public String socketInfoID = "http://pit.itu.dk:7331/location-of/no_ID";
	public static String socketInfo = null;
	
	public Person itu_person = new Person(socketInfoName, socketInfoName);
	public Located locatedAt = null;
	public Location position = new Location("location_id");
	public String person_location = "";
//	public boolean presentDevices = false;
	public boolean presentDevices = true;
	private static int portNumber = Integer.parseInt("44444");
	String previous;
	Person itu_person2;

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
			  
			  
			  if (socketInfo != null){ {
				  
				  if (presentDevices == true){
					  socketInfoName = socketInfo.split(",")[1];
					  socketInfoID = socketPre + socketInfo.split(",")[0];
					  
					  itu_person2 = new Person(socketInfoName, socketInfoName);
					  try {
						getContextService().addEntity(itu_person2);
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						System.out.println("It did not load person2");
					} 
					  presentDevices = false;
				  }
//					  socketInfoName = socketInfo;
				  
				  
				  previous = person_location;
				  person_location = RestfulClient.getLocation(socketInfoID);
				  position.setId(person_location);
//				  locatedAt = new Located(this.getClass().getName());
//				  locatedAt.resetTime();
//				  position.setId(person_location);
				  System.out.println("Found a change");
				  System.out.println(itu_person2.getName() + " is at " + person_location);
				  try {
					  getContextService().addContextItem(itu_person2.getName(), locatedAt, position);
					  System.out.println("Added item");
				  } catch (RemoteException e) {
						// TODO Auto-generated catch block
//					  e.printStackTrace();
					  System.out.println("didn't add item");
				  }
			  }
		  }}
		  
		//Checked every 5 seconds
		}, 0, 5, TimeUnit.SECONDS);
		
	}
	
	private void load() {
        try {
        	System.out.println("Loading");
            
            getContextService().addEntity(itu_person); 
        } catch (RemoteException e) {
//            e.printStackTrace();
            System.out.println("It did not load");
        }
    }
	
	
	@Override
	public void run() {
		}
	
	
	public static void main(String[] args) throws IOException {
			new Monitor("rmi://10.25.252.253/MyService");
			getSocketInfo();
	}

	@Override
	public void monitor(String arg0) throws RemoteException {
		
		
	}
	
	public static void getSocketInfo(){
		new Thread(new Runnable() {
            public void run() {
		try (
            ServerSocket serverSocket =
                new ServerSocket(portNumber);
            Socket clientSocket = serverSocket.accept();     
            PrintWriter out =
                new PrintWriter(clientSocket.getOutputStream(), true);                   
            BufferedReader in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
        ) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                out.println(inputLine);
                System.out.println(inputLine);
                socketInfo = inputLine;
            }
            
            
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
		 }).start();
	}
}
