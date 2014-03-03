package dk.jycr753.instances;

import dk.pervasive.jcaf.ContextEvent;
import dk.pervasive.jcaf.ContextItem;
import dk.pervasive.jcaf.item.AbstractContextItem;


public class Destination extends AbstractContextItem {
	
	private String userDestinationName;
	
	public Destination(String userDestinationId, String userDestinationName){
		super(userDestinationId);
		this.userDestinationName = userDestinationName;
	}
	
	public String getUserDestinationName(){
		return userDestinationName;
	}
	
	@Override
	public double getAccuracy(){
		return super.getAccuracy();
	}
	
	
	@Override
	public String toXML() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
