package dk.jycr753.activities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import dk.jycr753.bluetooth.DeviceListActivity;
import dk.jycr753.bluetooth.GetDeviceBluetoothInfo;
import dk.jycr753.itu.R;
import dk.jycr753.network.JsonParser;
import dk.jycr753.strings.BlipURi;

public class InITUActivity extends Activity {

	JSONArray locationsArray = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_itu_layout);
		final ProgressBar progressBar = (ProgressBar) findViewById(R.id.gettingContentProgressBar);
		progressBar.setVisibility(View.GONE);
		TextView readytext = (TextView) findViewById(R.id.itu_text_view_title);

		String macAddress = GetDeviceBluetoothInfo.getDeviceMacAddress();
		readytext.setText(macAddress);
		String finalMacAddressNoColums = GetDeviceBluetoothInfo
				.removeColumnsFromMacAddress(macAddress);
		readytext.setText(finalMacAddressNoColums);
		
		 //call activity temporal for now
		
		readytext.setText("Good now");
		
		Button getMacAddress = (Button) findViewById(R.id.getMacAddress);
		getMacAddress.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				TextView mac = (TextView) findViewById(R.id.macaddress);
				mac.setText("Getting mac address of current device!");
				
				
				Intent changeToNextIntent = new Intent(InITUActivity.this, DeviceListActivity.class);
				InITUActivity.this.startActivity(changeToNextIntent);
			}
		});
		
		
		Button getThing = (Button) findViewById(R.id.button1);
		getThing.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				new ParseJson().execute();
			}
		});
	}
	
	public class ParseJson extends AsyncTask<String, String, JSONArray> {
		// private ProgressDialog pDialog;

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
