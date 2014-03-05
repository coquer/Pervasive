package dk.jycr753.itu;

import dk.jycr753.itu.GetLocation.LocationResult;
import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		LocationResult locationResult = new LocationResult(){
		    @Override
		    public void gotLocation(Location location){
		        TextView gotLocation = (TextView)findViewById(R.id.set_location_text_view);
		        gotLocation.setText("Got it");
		    }
		};
		GetLocation myLocation = new GetLocation();
		myLocation.getLocation(this, locationResult);
	}
}