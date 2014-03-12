package dk.jycr753.bluetooth;

import android.bluetooth.BluetoothAdapter;

public class GetDeviceBluetoothInfo {
	
	public static String getDeviceMacAddress(){
		String deviceMacAddress = BluetoothAdapter.getDefaultAdapter().getAddress();
		if(!deviceMacAddress.isEmpty() && isBluetoothEnable()){
			return deviceMacAddress;
		}
		return null;
	}
	
	
	public static boolean isBluetoothEnable(){
		BluetoothAdapter bluetoothAdapater = BluetoothAdapter.getDefaultAdapter();
		if(bluetoothAdapater == null){
			return false;
		}else{
			if(!bluetoothAdapater.isEnabled()){
				return false;
			}
		}
		return true;
	}
	
	public static String removeColumnsFromMacAddress(String deviceMacAddress){
		if(deviceMacAddress.isEmpty()){
			return null;
		}
		String finalNonColumnsMacAddress = deviceMacAddress.replaceAll(":", "");
		return finalNonColumnsMacAddress;
	}
	
}
