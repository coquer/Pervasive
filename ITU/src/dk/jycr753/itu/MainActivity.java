package dk.jycr753.itu;

import java.text.DecimalFormat;
import java.util.Locale;

import dk.jycr753.itu.GetLocation.LocationResult;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import android.location.Criteria;


public class MainActivity extends Activity implements LocationListener {
	
	private LocationManager locationManager;
	private String provider;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		
		LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
		if(lm.isProviderEnabled(LocationManager.GPS_PROVIDER)){
			LocationResult locationResult = new LocationResult(){
			    @Override
			    public void gotLocation(Location location){
			        TextView gotLocation = (TextView)findViewById(R.id.set_location_text_view);
			        gotLocation.setText("Got it");
			       
			        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
					Criteria criteria = new Criteria();
					provider = locationManager.getBestProvider(criteria, false);
					Location androidLocation = locationManager.getLastKnownLocation(provider);
					if(androidLocation != null){
						onLocationChanged(location);
					}else{
						Toast.makeText(getApplicationContext(), "Error Bitch",
								   Toast.LENGTH_LONG).show();
					}
					
			        
			    }
			};
			GetLocation myLocation = new GetLocation();
			myLocation.getLocation(this, locationResult);
		} else {
			showGPSDisabledAlertToUser();
		}
		
		
	}
	
	@Override
	public void onResume(){
		super.onResume();
		if(locationManager == null){
			locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		}else{
			locationManager.requestLocationUpdates(provider, 400, 1, this);
		}
		
	}
	
	@Override
	public void onPause(){
		super.onPause();
		locationManager.removeUpdates(this);
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), "Getting Data..",
				   Toast.LENGTH_SHORT).show();
		double lat = (double)(location.getLatitude());
		double log = (double)(location.getLongitude());
		double distanceToITU = getDistance(lat, log );
		//distanceToITU = reduceNumberOfDecimals(distanceToITU);
		TextView textViewAndroidLat = (TextView) findViewById(R.id.set_location_text_view);
		DecimalFormat formater = (DecimalFormat)DecimalFormat.getInstance(Locale.US); 
		formater.applyPattern("00.00");
		String tmp = formater.format(distanceToITU);
		if(distanceToITU > 0.2){
			textViewAndroidLat.setText(tmp + " KM from ITU");
		}else{
			textViewAndroidLat.setText("You Are in ITU");
		}
		
		
		
	}
	
	private static double getDistance( double lat2 ,double lon2){
		double lat1 = 55.669669;
		double lon1 = 12.5880716;
		// formula
		//d=acos(sin(lat1)*sin(lat2)+cos(lat1)*cos(lat2)*cos(lon1-lon2));	
		double distanceCalculation = Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));		
		double finalDistance = distanceCalculation * 60 * 1.1515;
		double kmfromlocation = finalDistance * 1.609344;
		 
		 return kmfromlocation;
	 }
	
	private static double reduceNumberOfDecimals(double number){
		DecimalFormat formater = (DecimalFormat)DecimalFormat.getInstance(Locale.US); 
		formater.applyPattern("00.00");
		return Math.round(number * 10000);
	}


	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
	
	 private void showGPSDisabledAlertToUser(){
	        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
	        alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
	        .setCancelable(false)
	        .setPositiveButton("Goto Settings Page To Enable GPS",
	                new DialogInterface.OnClickListener(){
	            public void onClick(DialogInterface dialog, int id){
	                Intent callGPSSettingIntent = new Intent(
	                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
	                startActivity(callGPSSettingIntent);
	            }
	        });
	        alertDialogBuilder.setNegativeButton("Cancel",
	                new DialogInterface.OnClickListener(){
	            public void onClick(DialogInterface dialog, int id){
	                dialog.cancel();
	            }
	        });
	        AlertDialog alert = alertDialogBuilder.create();
	        alert.show();
	 }
}