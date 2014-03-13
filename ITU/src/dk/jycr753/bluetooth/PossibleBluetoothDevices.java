package dk.jycr753.bluetooth;

import java.net.URISyntaxException;

import org.json.JSONObject;

import dk.jycr753.network.JsonParser;
import dk.jycr753.strings.BlipURi;

public class PossibleBluetoothDevices {

	public static boolean isDeviceLegalToConnect(String deviceMacAddress)
			throws URISyntaxException {
		@SuppressWarnings("unused")
		String finalMacAddressTest = GetDeviceBluetoothInfo
				.removeColumnsFromMacAddress(deviceMacAddress);// good
		String allLocationUrl = BlipURi.allLocationsUrl();// good
		JSONObject getJsonString = JsonParser.getJSONFromUrl(allLocationUrl, null, 1);//good?

		if (getJsonString == null) {

			return true;

		} else {

			return false;

		}

		// try{
		//
		// JSONArray allLocationJSONArray = null;
		// for(int ja = 0; ja < allLocationJSONArray.length(); ja ++){
		// String avalibleDevicesMacAddres = allLocationJSONArray.getString(ja);
		// if(avalibleDevicesMacAddres.equals(removedColumsDeviceToBeConnected)){
		// return true;
		// }
		// }
		//
		// }catch(JSONException e){
		// //Log.println("Error", e);
		// }

	}

}
