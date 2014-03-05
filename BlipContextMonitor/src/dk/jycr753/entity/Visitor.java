package dk.jycr753.entity;

import dk.jycr753.instances.Destination;
import dk.pervasive.jcaf.ContextEvent;
import dk.pervasive.jcaf.ContextItem;
import dk.pervasive.jcaf.entity.Person;

public class Visitor extends Person {
	
	public Visitor(){
		super();
	}
	
	public Visitor(String userId){
		super(userId);
	}
	public Visitor(String userId, String deviceId){
		super(userId, deviceId);
	}
	
	public Visitor(String userId, String deviceId, String currentLocation){
		super(userId, deviceId, currentLocation);
	}
	
	@Override
	public String getEntityInfo(){
		if(!super.getEntityInfo().isEmpty()){
			return super.getEntityInfo();
		}
		
		return null;
	}
	
	@Override
	public void contextChanged(ContextEvent event){
		super.contextChanged(event);
		ContextItem thiscontextItem = event.getItem();
		if(thiscontextItem != null){
			if(thiscontextItem instanceof Destination){
				//lets do something here...
				String getContextChangedName = getName();
				String getEventItemName = ((Person) event.getItem()).getName();
				System.out.println("Name::"+getContextChangedName +" "+" Item::"+getEventItemName);
			}else if(thiscontextItem instanceof CurrentLocation){
				CurrentLocation currentlocation = new CurrentLocation();
				int currentFloor = currentlocation.getCurrentFloor();
				char currentSector = currentlocation.getCurrentSector();
				int currentRoom = currentlocation.getcurrentRoom();
				System.out.println("Floor::"+ currentFloor +" Sector::"+currentSector+" Room::"+currentRoom);
				 
			}else{
				//do something if there is not instance of
				//data being receive
			}
			//implement to update 
		}else{
			
			//do some handle in case the item is null
		}
	}
	
}
