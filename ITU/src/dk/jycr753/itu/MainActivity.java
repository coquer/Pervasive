package dk.jycr753.itu;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity implements LocationListener {
	private LocationManager locationmanager;
	private String provider;
	private Location location;
	private TextView notifications = (TextView) findViewById(R.id.set_location_text_view);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		locationmanager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		provider = locationmanager.getBestProvider(criteria, false);
		location  = locationmanager.getLastKnownLocation(provider);
		
		if(location != null){
			onLocationChanged(location);
		}else{
			notifications.setText(getString(R.string.try_again));
		}
		
		
	}
	
	@Override
	public void onLocationChanged(Location location){
		double lat = (double)(location.getLatitude());
		double log = (double)(location.getLongitude());
		notifications.setText("Working...." + String.valueOf(lat) + " " + String.valueOf(log));
	}
	
	public double reduceNumberOfDecimals(double number){
		return Math.round(number * 100000);
	}
	
	@Override
	protected void onResume(){
		super.onResume();
	}
	
	@Override
	protected void onPause(){
		super.onPause();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
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

}
