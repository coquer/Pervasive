package dk.jycr753.entity;

import dk.pervasive.jcaf.entity.GenericEntity;

public class CurrentLocation extends GenericEntity{
	
	private int currentFloor;
	private char currentSector;
	private int currentRoom;
	
	public CurrentLocation(){
		super();
	}
	
	public CurrentLocation(String locationId){
		super(locationId);
	}
	public CurrentLocation(String locationId,
						   int currentFloor,
						   char currentSector,
						   int currentRoom)
	{	
		super(locationId);
		this.currentFloor = currentFloor;
		this.currentSector = currentSector;
		this.currentRoom = currentRoom;
	}
	
	public int getCurrentFloor(){
		return currentFloor;
	}
	
	public char getCurrentSector(){
		return currentSector;
	}
	
	public int getcurrentRoom(){
		return currentRoom;
	}
	
}
