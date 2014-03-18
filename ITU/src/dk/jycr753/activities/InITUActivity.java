package dk.jycr753.activities;

import java.util.ArrayList;
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
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import dk.jycr753.bluetooth.AcceptThread;
import dk.jycr753.bluetooth.ConnectThread;
import dk.jycr753.bluetooth.ConnectionThread;
import dk.jycr753.bluetooth.GetDeviceBluetoothInfo;
import dk.jycr753.itu.R;
import dk.jycr753.network.JsonParser;
import dk.jycr753.strings.BlipURi;

public class InITUActivity extends Activity {

	private static final String TAG = "InITUActivity";
	JSONArray locationsArray = null;
	private static final int REQUEST_ENABLE_BT = 0;
	private static final int SELECT_SERVER = 1;
	public static final int DATA_RECEIVED = 3;
	public static final int SOCKET_CONNECTED = 4;
	public static final UUID APP_UUID = UUID
			.fromString("aeb9f938-a1a3-4947-ace2-9ebd0c67adf1");

	private boolean mServerMode;
	private String data;

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
			startAsServer();
			mServerMode = true;

		} else {
			readytext.setText("Enable Bluetooth");
			finish();
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
	

	public Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SOCKET_CONNECTED: {
				mBluetoothConnection = (ConnectionThread) msg.obj;
				if (!mServerMode)
					mBluetoothConnection.write("this is a message".getBytes());
				break;
			}
			case DATA_RECEIVED: {
				data = (String) msg.obj;
				Log.i(TAG, "Message - " + data);
				if (mServerMode)
					mBluetoothConnection.write(data.getBytes());
			}
			default:
				break;
			}
		}
	};

	private void selectServer() {

		Set<BluetoothDevice> pairedDevices = mBluetoothAdapter
				.getBondedDevices();
		ArrayList<String> pairedDeviceStrings = new ArrayList<String>();
		if (pairedDevices.size() > 0) {
			for (BluetoothDevice device : pairedDevices) {
				pairedDeviceStrings.add(device.getName() + "\n"
						+ device.getAddress());
			}
		}
		Intent showDevicesIntent = new Intent(this, ShowDevices.class);
		showDevicesIntent.putStringArrayListExtra("devices",
				pairedDeviceStrings);
		startActivityForResult(showDevicesIntent, SELECT_SERVER);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_ENABLE_BT && resultCode == RESULT_OK) {
		} else if (requestCode == SELECT_SERVER && resultCode == RESULT_OK) {
			BluetoothDevice device = data
					.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
			connectToBluetoothServer(device.getAddress());
		}
	}
	
	private void connectToBluetoothServer(String id) {
		new ConnectThread(id, mHandler).start();
	}
	
	private void startAsServer() {
		new AcceptThread(mHandler).start();
	}

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
