package dk.jycr753.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import dk.jycr753.bluetooth.BluetoothListener;
import dk.jycr753.bluetooth.GetDeviceBluetoothInfo;
import dk.jycr753.bluetooth.PossibleBluetoothDevices;
import dk.jycr753.itu.R;
import dk.jycr753.location.GetCurrentZone;
import dk.jycr753.network.JsonParser;
import dk.jycr753.strings.BlipURi;

public class InITUActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_itu_layout);
		final ProgressBar progressBar = (ProgressBar) findViewById(R.id.gettingContentProgressBar);
		progressBar.setVisibility(View.GONE);
		TextView readytext = (TextView)this.findViewById(R.id.itu_text_view_title);
		String macAddress = GetDeviceBluetoothInfo.getDeviceMacAddress();
		String finalMacAddressNoColums = GetDeviceBluetoothInfo.removeColumnsFromMacAddress(macAddress);
		if(!GetDeviceBluetoothInfo.isBluetoothEnable()){
			Intent enableBtIntent = new Intent(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS);
		    startActivityForResult(enableBtIntent, 0);
		}
		if(finalMacAddressNoColums != null){
		
			readytext.setText(finalMacAddressNoColums);
			//from here Activate Listener to any available BLIP
			if(BluetoothListener.isThereAnyConnectionAlive()){
				progressBar.setVisibility(View.VISIBLE);
				//so if there is any connection...
				//get current location of device
				String deviceCurrentZone = GetCurrentZone.getDeviceCurrentZone(finalMacAddressNoColums);
				readytext.setText(deviceCurrentZone);
				
				
				/***************/
				//temporal work to test devices.....
				String testMacAddress = "00:a0:96:09:1c:36";
				boolean testIfProvidedMacAddressIsValid = PossibleBluetoothDevices.isDeviceLegalToConnect(testMacAddress);
				//boolean dumbassAndroid = true;
				if(testIfProvidedMacAddressIsValid){
					
					readytext.setText("true --- ");
					progressBar.setVisibility(View.GONE);
				
				}else{
					progressBar.setVisibility(View.GONE);
					readytext.setText("false");
				
				}
				
				
			}else{
				//something wrong in the connection.... try again!
				readytext.setText("No Connected 2 bluetooth");
			}
		
		}else{
			
			//Handle All Errors, in Case this methods fails...
			readytext.setText("No Data");
		}
		
	}
	
}
