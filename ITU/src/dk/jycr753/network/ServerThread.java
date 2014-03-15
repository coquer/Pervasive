package dk.jycr753.network;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;


import android.app.Activity;
import android.os.Handler;
import android.util.Log;

public class ServerThread extends Activity implements Runnable {
	private final static String TAG = "Server Thread";
	private static String SERVERIP = getLocalIpAddress();
	private static final int SERVERPORT = 8080;
	private Handler handler = new Handler();
	public static ServerSocket serverSocket;
	
	public void run() {
        try {
            if (SERVERIP != null) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.e(TAG, SERVERIP);
                    }
                });
                serverSocket = new ServerSocket(SERVERPORT);
                while (true) {
                    // LISTEN FOR INCOMING CLIENTS
                    Socket client = serverSocket.accept();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Log.i(TAG, "Connected.");
                        }
                    });

                    try {
                        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                        String line = null;
                        while ((line = in.readLine()) != null) {
                            Log.d("ServerActivity", line);
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    // DO WHATEVER YOU WANT TO THE FRONT END
                                    // THIS IS WHERE YOU CAN BE CREATIVE
                                }
                            });
                        }
                        break;
                    } catch (Exception e) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Log.e(TAG, "Oops. Connection interrupted. Please reconnect your phones.");
                            }
                        });
                        e.printStackTrace();
                    }
                }
            } else {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.e(TAG, "Couldn't detect internet connection.");
                    }
                });
            }
        } catch (Exception e) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Log.e(TAG, "Error");
                }
            });
            e.printStackTrace();
        }

	}
	
	private static String getLocalIpAddress() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface itf = en.nextElement();
				for (Enumeration<InetAddress> enuIpAdd = itf.getInetAddresses(); enuIpAdd
						.hasMoreElements();) {
					InetAddress initAdd = enuIpAdd.nextElement();
					if (!initAdd.isLoopbackAddress()) {
						return initAdd.getHostAddress().toString();
					}

				}

			}

		} catch (SocketException ex) {
			Log.e(TAG, "He is some error -- " + ex);
		}

		return null;
	}

}
