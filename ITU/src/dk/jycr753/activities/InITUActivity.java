package dk.jycr753.activities;

import android.app.Activity;
import dk.jycr753.bluetooth.GetDeviceBluetoothInfo;
import dk.jycr753.itu.R;
import android.os.Bundle;
import android.widget.TextView;

public class InITUActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_itu_layout);
		TextView readytext = (TextView)this.findViewById(R.id.itu_text_view_title);
		String macAddress = GetDeviceBluetoothInfo.getDeviceMacAddress();
		String finalMacAddressNoColums = GetDeviceBluetoothInfo.removeColumnsFromMacAddress(macAddress);
		if(finalMacAddressNoColums != null){
		
			readytext.setText(finalMacAddressNoColums);
		
		}else{
			
			//Handle All Errors, in Case this methods fails...
			readytext.setText("No Data");
		}
		
	}
	
}
