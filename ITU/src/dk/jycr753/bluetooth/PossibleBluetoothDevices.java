package dk.jycr753.bluetooth;

import org.json.JSONArray;
import org.json.JSONException;
import dk.jycr753.network.JsonParser;
import dk.jycr753.strings.BlipURi;

public class PossibleBluetoothDevices {
	
	public static boolean isDeviceLegalToConnect(String deviceMacAddress){
		String removedColumsDeviceToBeConnected = GetDeviceBluetoothInfo.removeColumnsFromMacAddress(deviceMacAddress);//good
		String allLocationUrl = BlipURi.allLocationsUrl(); //good
		String getJsonString = JsonParser.createJsonParserObject(allLocationUrl);
		System.out.println("We are here ------- "+getJsonString);
		
		try{
			
			JSONArray allLocationJSONArray =  null;
			for(int ja = 0; ja < allLocationJSONArray.length(); ja ++){
				String avalibleDevicesMacAddres = allLocationJSONArray.getString(ja);
				if(avalibleDevicesMacAddres.equals(removedColumsDeviceToBeConnected)){
					return true;
				}
			}
			
		}catch(JSONException e){
			//Log.println("Error", e);
		}
		
		
		return false;
	}

}
