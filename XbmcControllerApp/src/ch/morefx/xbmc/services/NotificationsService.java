package ch.morefx.xbmc.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.IBinder;
import android.util.Log;
import ch.morefx.xbmc.BuildConfig;
import ch.morefx.xbmc.ResourceProvider;
import ch.morefx.xbmc.XbmcConnection;
import ch.morefx.xbmc.XbmcExceptionHandler;
import ch.morefx.xbmc.net.notifications.Notification;
import ch.morefx.xbmc.net.notifications.NotificationParser;
import ch.morefx.xbmc.net.notifications.NotificationParserException;

public class NotificationsService extends XbmcService
	implements ResourceProvider {
	
	private static final String TAG = NotificationsService.class.getName();
	
	private boolean running;
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if (BuildConfig.DEBUG){
			Log.d(TAG, "Starting Service...");
		}
		
		running = true;
		
		startService();
		return START_STICKY;
	}
	
	private void startService(){
		
		new Thread(new Runnable() {
			
			public void run() {
				
				if (BuildConfig.DEBUG){
					Log.d(TAG, "Starting Service Thread...");
				}
				
				XbmcConnection connection = getXbmcApplication().getCurrentConnection();
				
				int port = connection.getJsonTcpPort();
				String host = connection.getHost();
				
				try{
					
					Socket socket = new Socket(host, port);
					BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

					while(running)
					{
						try {
							char[] buffer = new char[1024];
							
							int read = in.read(buffer);
							String msg = new String(buffer, 0, read);
							
							if (BuildConfig.DEBUG){
								Log.d(TAG, "RECV : " + msg);	
							}
							
							Notification notification = NotificationParser.parse(msg);
							String action = notification.handle(connection, NotificationsService.this);
							if (!Notification.NONE.equals(action)){
								sendBroadcast(new Intent(action));
								
								running = !Notification.CONNECTION_LOST.equals(action);
							}

						} catch (NotificationParserException e) {
							XbmcExceptionHandler.handleException(TAG, e);
							Intent intent = new Intent(Notification.NOTIFICATION_PARSER_ERROR);
							intent.putExtra(Notification.NOTIFICATION_PARSER_ERROR,  e);
							sendBroadcast(intent);
						} catch (Exception ex){
							running = false;
							XbmcExceptionHandler.handleException(TAG, ex);
							Intent intent = new Intent(Notification.CONNECTION_LOST);
							sendBroadcast(intent);
						}
					}
					
					if (BuildConfig.DEBUG){
						Log.d(TAG, "Exiting Notification Thread...");
					}
					
					if (in != null) { in.close(); }
					if (socket != null){ socket.close(); }
					
				} catch (Exception ex){
					
					// Unable to create connection
					XbmcExceptionHandler.handleException(TAG, ex);
					running = false;
				}
			}
		}).start();
	}
	

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
	
	public Drawable getDrawable(int resourceId){
		return getResources().getDrawable(resourceId);
	}
}
