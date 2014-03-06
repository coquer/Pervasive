package dk.jycr753.itu;

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
		System.out.println("Hello World 1");
		setContentView(R.layout.activity_main);
//		new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true) {
//                    try {
//                    	
//                        Thread.sleep(5000);
//                        someHandler.post(new Runnable() {
//                        	
//                            @Override
//                            public void run() {
//                                Criteria criteria = new Criteria();
//            					provider = locationManager.getBestProvider(criteria, false);
//                            	Location deviceLocation = locationManager.getLastKnownLocation(provider);
//                            	System.out.println("Location device :: " + deviceLocation);
//                            	
//                            }
//                        });
//                    } catch (Exception e) {
//                        System.out.println("Error in run() "+ e);
//                    }
//                }
//            }
//        }).start();
		
//		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//		Criteria criteria = new Criteria();
//		provider = locationManager.getBestProvider(criteria, false);
//		android.location.Location androidLocation = locationManager.getLastKnownLocation(provider);
//		
//		if(androidLocation != null){
//			System.out.println("Provider -- " + provider);
//			onLocationChanged(androidLocation);
//		}else{
//			System.out.println("No location affable");
//		}
//		
		System.out.println("Hello World 2");
		//boolean isGPSEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		//boolean isNetworkEnable = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		boolean isGPSEnable = true;
		boolean isNetworkEnable = true;
		System.out.println("Hello World 3 with this bools " + isGPSEnable +" - "+ isNetworkEnable);
		if(isGPSEnable == true && isNetworkEnable == true){
			System.out.println("Hello World 4");
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
			System.out.println("Hello World 5");
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
		TextView textViewAndroidLat = (TextView) findViewById(R.id.set_location_text_view);
		textViewAndroidLat.setText(String.valueOf(lat) + " " + String.valueOf(log));
		
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