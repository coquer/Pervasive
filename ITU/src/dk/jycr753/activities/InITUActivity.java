package dk.jycr753.activities;

import java.util.Set;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import dk.jycr753.bluetooth.ConnectionThread;
import dk.jycr753.bluetooth.DeviceListActivity;
import dk.jycr753.bluetooth.GetDeviceBluetoothInfo;
import dk.jycr753.itu.R;
import dk.jycr753.network.JsonParser;
import dk.jycr753.strings.BlipURi;

public class InITUActivity extends Activity {
	
	private static final String TAG = "InITUActivity";
	JSONArray locationsArray = null;
	public static final int DATA_RECEIVED = 3;
	public static final int SOCKET_CONNECTED = 4;
	public static final UUID APP_UUID = UUID.fromString("aeb9f938-a1a3-4947-ace2-9ebd0c67adf1");
	
	private BluetoothAdapter mBluetoothAdapter = null;
	private ConnectionThread mBluetoothConnection = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_itu_layout);
		TextView readytext = (TextView) findViewById(R.id.itu_text_view_title);

		String macAddress = GetDeviceBluetoothInfo.getDeviceMacAddress();
		readytext.setText(macAddress);

		String finalMacAddressNoColums = GetDeviceBluetoothInfo
				.removeColumnsFromMacAddress(macAddress);
		readytext.setText(finalMacAddressNoColums);

		// **************************************
		// ******** START BLUETOOTH CALLS
		// **************************************

		if (GetDeviceBluetoothInfo.isBluetoothEnable()) {
			
			
			
		} else {
			readytext.setText("Enable Bluetooth");
			// call blue tooth activity
		}

		// Button getMacAddress = (Button) findViewById(R.id.getMacAddress);
		// getMacAddress.setOnClickListener(new View.OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		//
		// TextView mac = (TextView) findViewById(R.id.macaddress);
		// mac.setText("Getting mac address of current device!");
		// new ConnectToBluetooth().execute();
		//
		// }
		// });
		//
		// **************************************
		// ******** JSON PARSER
		// **************************************

		Button getThing = (Button) findViewById(R.id.button1);
		getThing.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				new ParseJson().execute();
			}
		});
	}

	// **************************************
	// ****** RUN BLUETOOTH SCANNER
	// **************************************
//	public class ConnectToBluetooth extends AsyncTask<String, Void, String> { 
//
//		@Override
//		protected void onPreExecute() {
//			super.onPreExecute();
//			
//		}
//
//		@Override
//		protected String doInBackground(String... params) {
//			System.out.println("doInBackground");
//			boolean isConnected = 
//			
//			return "J";
//			
//		}
//
//		@Override
//		protected void onPostExecute(String result) {
//
//			TextView readytext = (TextView) findViewById(R.id.itu_text_view_title);
//			readytext.setText("result " + result);
//			System.out.println("result - " + result);
//		}
//
//	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	// **************************************
	// ****** RUN JSON GETTER IN THE BACK
	// **************************************

	public class ParseJson extends AsyncTask<String, String, JSONArray> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			// pDialog.setMessage("Getting Data ...");
			// pDialog.setIndeterminate(false);
			// pDialog.setCancelable(true);
			// pDialog.show();
		}

		@Override
		protected JSONArray doInBackground(String... args) {
			JsonParser jParser = new JsonParser();
			// Getting JSON from URL
			String url = BlipURi.allLocationsUrl();
			JSONArray json = jParser.getJSONFromUrl(url);
			return json;
		}

		@Override
		protected void onPostExecute(JSONArray json) {
			try {
				String mac = GetDeviceBluetoothInfo.getDeviceMacAddress();
				TextView readytext = (TextView) findViewById(R.id.itu_text_view_title);
				readytext.setText(mac);
				@SuppressWarnings("unused")
				String locationID;
				for (int i = 0; i < json.length(); i++) {
					JSONObject row = json.getJSONObject(i);
					locationID = row.getString("location-id");

				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
