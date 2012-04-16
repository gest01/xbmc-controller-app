package ch.morefx.xbmc.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import ch.morefx.xbmc.net.notifications.Notification;
import ch.morefx.xbmc.net.notifications.NotificationParser;

public class NotificationsService extends XbmcService{
	
	private static final String TAG = NotificationsService.class.getName();

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(TAG, "Starting Service...");
		startService();
		return START_STICKY;
	}
	
	/*
	 * {"jsonrpc":"2.0","method":"Player.OnPlay","params":{"data":{"item":{"id":3430,"type":"song"},"player":{"playerid":0,"speed":1}},"sender":"xbmc"}}
	 * {"jsonrpc":"2.0","method":"Player.OnStop","params":{"data":{"item":{"id":3429,"type":"song"}},"sender":"xbmc"}}
	 * {"jsonrpc":"2.0","method":"Player.OnSpeedChanged","params":{"data":{"item":{"id":3433,"type":"song"},"player":{"playerid":0,"speed":2}},"sender":"xbmc"}}
	 * {"jsonrpc":"2.0","method":"Player.OnSpeedChanged","params":{"data":{"item":{"id":3433,"type":"song"},"player":{"playerid":0,"speed":1}},"sender":"xbmc"}}
	 * {"jsonrpc":"2.0","method":"Player.OnPause","params":{"data":{"item":{"id":3433,"type":"song"},"player":{"playerid":0,"speed":0}},"sender":"xbmc"}}
	 * {"jsonrpc":"2.0","method":"GUI.OnScreensaverActivated","params":{"data":null,"sender":"xbmc"}}
	 * {"jsonrpc":"2.0","method":"GUI.OnScreensaverDeactivated","params":{"data":null,"sender":"xbmc"}}
	 * {"jsonrpc":"2.0","method":"System.OnQuit","params":{"data":null,"sender":"xbmc"}}
	 * {"jsonrpc":"2.0","method":"Player.OnSeek","params":{"data":{"item":{"id":2430,"type":"song"},"player":{"playerid":0,"seekoffset":{"hours":0,"milliseconds":-640,"minutes":0,"seconds":-7},"speed":1,"time":{"hours":0,"milliseconds":0,"minutes":0,"seconds":0}}},"sender":"xbmc"}}
	 * 
	 */
	
	private void startService(){
		
		new Thread(new Runnable() {
			
			public void run() {
				
				Log.d(TAG, "Starting Service Thread...");
				
				int port = 9090;
				String host = "192.168.1.25";
				
				try{
					
					Socket socket = new Socket(host, port);
					BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

					while(true)
					{
						try {
							char[] buffer = new char[1024];
							
							int read = in.read(buffer);
							String msg = new String(buffer, 0, read);
							
							Log.d(TAG, "RECV : " + msg);
							
							Notification notification = NotificationParser.parse(msg);
							String action = notification.handle(getXbmcApplication().getCurrentConnection());
							if (!Notification.NONE.equals(action)){
								sendBroadcast(new Intent(action));
							}
							
							
							
							//JSONObject jo = new JSONObject(msg);
							//JSONObject o =  (JSONObject)jo.get("params");
							//System.out.println("o = " + o);
							
							//JSONObject o1 = (JSONObject)o.get("data");
							//System.out.println("o1 = " + o1);
							
							//JSONObject o2 = (JSONObject)o1.get("item");
							//System.out.println("o2 = " + o2);
							
							//int songid = o2.getInt("id");
							//System.out.println("songid = " + songid);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					
				} catch (Exception ex){
					ex.printStackTrace();
				}
			}
		}).start();
	}
	
}
