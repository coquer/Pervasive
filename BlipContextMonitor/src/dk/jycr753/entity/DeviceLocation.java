package dk.jycr753.entity;

import dk.pervasive.jcaf.item.Location;

@SuppressWarnings("serial")
public class DeviceLocation extends Location{
	
	private String firstLocationParameter;
	private String secondLocationParameter;
	
	public DeviceLocation(String deviceLocationId){
		super(deviceLocationId);
	}
	
	public  DeviceLocation(String deviceLocationId,
			  String firstLocationParameter)
	{
		super(deviceLocationId);
		this.firstLocationParameter = firstLocationParameter;
	}
	
	public DeviceLocation(String deviceLocationId,
						  String firstLocationParameter, 
						  String secondLocationParameter)
	{
		super(deviceLocationId);
		this.firstLocationParameter = firstLocationParameter;
		this.secondLocationParameter = secondLocationParameter;
	}
	
	public String getFirsLocationParameter(){
		return firstLocationParameter;
	}
	
	public String getSecondLocationParameter(){
		return secondLocationParameter;
	}
	
	@Override
	public String toXML(){
		//Do something here
		
		return null;
	}

}
