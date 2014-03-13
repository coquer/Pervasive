package dk.jycr753.bluetooth;

import dk.jycr753.network.JsonParser;
import dk.jycr753.strings.BlipURi;


public class PossibleBluetoothDevices {
	
	public static boolean isDeviceLegalToConnect(String deviceMacAddress){
		@SuppressWarnings("unused")
		String finalMacAddressTest = GetDeviceBluetoothInfo.removeColumnsFromMacAddress(deviceMacAddress);
		String allLocationUrl = BlipURi.allLocationsUrl();
		String getJsonString = JsonParser.createJsonParserObject(allLocationUrl);
		
		if(!getJsonString.isEmpty()){
			
			return true;
		
		}else{
		
//		String getJsonString = JsonParser.createJsonParserObject(allLocationUrl);
		//String getJsonString = GetJsonInBackground.
//		try{
//			
//			JSONArray allLocationJSONArray =  null;
//			for(int ja = 0; ja < allLocationJSONArray.length(); ja ++){
//				String avalibleDevicesMacAddres = allLocationJSONArray.getString(ja);
//				if(avalibleDevicesMacAddres.equals(removedColumsDeviceToBeConnected)){
//					return true;
//				}
//			}
//			
//		}catch(JSONException e){
//			//Log.println("Error", e);
//		}
		
		
			return false;
		}
	}

}
