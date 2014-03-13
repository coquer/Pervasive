package dk.jycr753.bluetooth;

import java.io.IOException;
import java.net.URISyntaxException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dk.jycr753.network.JsonParser;
import dk.jycr753.strings.BlipURi;

public class PossibleBluetoothDevices {
	
	public static boolean isDeviceLegalToConnect(String deviceMacAddress)
			throws URISyntaxException, IllegalStateException, IOException {

		String finalMacAddressTest = GetDeviceBluetoothInfo
				.removeColumnsFromMacAddress(deviceMacAddress);
		String allLocationUrl = BlipURi.allLocationsUrl();
		//JSONObject getJsonString = JsonParser.getJSONFromUrl(allLocationUrl, null, 1);
		String getJsonString = JsonParser.createJsonParserObject(allLocationUrl);
		if (getJsonString != null) {
			try {
			
				if(JsonParser.isThisJSONAnObject(getJsonString) == false){
					JSONArray jsonObject = JsonParser.jsonArray(getJsonString);
					for(int aa = 0; aa < jsonObject.length(); aa++){
						boolean allLocationMacAddress = jsonObject.getString(aa).matches(finalMacAddressTest);
						System.out.println("COOL ____----- " +allLocationMacAddress);
					}
					return true;
				}else{
					System.out.println("No cool");
					return false;
				}	
			
			} catch (JSONException e) {
				System.out.println(" JSONException");
				e.printStackTrace();
			}

		} else {

			return false;

		}
		return false;
	}

}
