package dk.jycr753.activities;

//import java.text.DecimalFormat;
//import java.util.Locale;

import dk.jycr753.itu.R;
import dk.jycr753.location.CalculateDistance;
import dk.jycr753.location.GetLocation;
import dk.jycr753.location.GetLocation.LocationResult;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;
import android.location.Criteria;


public class MainActivity extends Activity implements LocationListener {
	
	private LocationManager locationManager;
	private String provider;
	private Location currentLocation = null; 
	private final double minDistanceToActivateProgram = 0.210;
//	private Handler handlerRunnable = new Handler();
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
		if(lm.isProviderEnabled(LocationManager.GPS_PROVIDER)){
			LocationResult locationResult = new LocationResult(){
			    @Override
			    public void gotLocation(Location location){
			    	locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
					Criteria criteria = new Criteria();
					provider = locationManager.getBestProvider(criteria, false);
					Location androidCurrentLocation = locationManager.getLastKnownLocation(provider);
					if(androidCurrentLocation != null){
						currentLocation = androidCurrentLocation;
					}
//					else if(!currentLocation.equals(androidCurrentLocation)){
//						
//						Toast.makeText(getApplicationContext(), "Looking "+currentLocation,
//								   Toast.LENGTH_LONG).show();
//						
//						
//					}else {
//						
//						new Thread(new Runnable() {
//				        
//							@Override
//				            public void run() {
//				                while (true) {
//				                    try {
//				                    	
//				                        Thread.sleep(4000);
//				                        handlerRunnable.post(new Runnable() {
//
//											@Override
//											public void run() {
//												System.out.println("Looking...");
//												locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//												Criteria criteria = new Criteria();
//												provider = locationManager.getBestProvider(criteria, false);
//												Location androidCurrentLocation = locationManager.getLastKnownLocation(provider);
//												currentLocation = androidCurrentLocation;
//											}
//				                        
//				                        
//				                        });
//				                    } catch (Exception e) {
//				                        System.out.println("Error in run() "+ e);
//				                    }
//				                }
//				            }
//				        }).start();
//					}
					
			        TextView gotLocation = (TextView)findViewById(R.id.set_location_text_view);
			        gotLocation.setText("Got it");
			        onLocationChanged(location);
					
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
		double distanceToITU = CalculateDistance.getDistance(lat, log );
		
		TextView textViewAndroidLat = (TextView) findViewById(R.id.set_location_text_view);
		int finalDistanceMobileFromITU = Double.compare(distanceToITU, minDistanceToActivateProgram);
		
		if(finalDistanceMobileFromITU < 0){ 
		
			textViewAndroidLat.setText(String.valueOf(finalDistanceMobileFromITU) + " KM from ITU");
		
		}else{
			
			Intent changeToNextIntent = new Intent(MainActivity.this, InITUActivity.class);
			MainActivity.this.startActivity(changeToNextIntent);
			
			
		}
		
		
		
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