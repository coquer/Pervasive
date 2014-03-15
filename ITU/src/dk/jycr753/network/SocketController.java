package dk.jycr753.network;



import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class SocketController extends Activity {
	
	private static final String TAG = "SocketController";
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Thread th = new Thread(new ServerThread());
		th.start();
	}
	
	@Override
	protected void onStop(){
		super.onStop();
	
		try {
		
			ServerThread.serverSocket.close();
		
		} catch (IOException e) {
			e.printStackTrace();
			
			Log.e(TAG,"Error "+ e);
		}
	}

}
