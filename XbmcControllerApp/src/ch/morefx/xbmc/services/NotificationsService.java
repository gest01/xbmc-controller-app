package ch.morefx.xbmc.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import ch.morefx.xbmc.BuildConfig;
import ch.morefx.xbmc.XbmcConnection;
import ch.morefx.xbmc.XbmcExceptionHandler;
import ch.morefx.xbmc.net.notifications.Notification;
import ch.morefx.xbmc.net.notifications.NotificationParser;
import ch.morefx.xbmc.net.notifications.NotificationParserException;

public class NotificationsService extends XbmcService {
	
	private static final String TAG = NotificationsService.class.getSimpleName();
	
	private boolean running;
	private Thread workerThread;
	private Object sync = new Object();
	private Socket socket;
	private BufferedReader reader; 
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		workerThread = new Thread(new Runnable() {
			public void run() {
				doWork();
			}
		});
	}
	
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		synchronized(sync){
			if (!running){
				running = true;
				workerThread.start();
			}
			
			return START_STICKY;
		}
	}
	
	private void doWork(){
		
		try{
			
			XbmcConnection connection = getXbmcApplication().getCurrentConnection();
			socket = new Socket(connection.getHost(), connection.getJsonTcpPort());
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			while(running)
			{
				try {
					char[] buffer = new char[1024];
					
					int read = reader.read(buffer);
					String msg = new String(buffer, 0, read);
					
					if (BuildConfig.DEBUG){
						Log.d(TAG, "RECV : " + msg);	
					}
					
					Notification notification = NotificationParser.parse(msg);
					String action = notification.handle(connection);
					if (!Notification.NONE.equals(action)){
						sendBroadcast(new Intent(action));
						
						running = !Notification.CONNECTION_LOST.equals(action);
					}

				} catch (NotificationParserException e) {
					XbmcExceptionHandler.handleException(TAG, e);
					Intent intent = new Intent(Notification.NOTIFICATION_PARSER_ERROR);
					intent.putExtra(Notification.NOTIFICATION_PARSER_ERROR,  e);
					sendBroadcast(intent);
				} catch(IOException ioex){
					running = false;
				} catch (Exception ex){
					running = false;
					XbmcExceptionHandler.handleException(TAG, ex);
					Intent intent = new Intent(Notification.CONNECTION_LOST);
					sendBroadcast(intent);
				}
			}
			
			closeSocket();
			
		} catch (Exception ex){
			// Unable to create connection
			XbmcExceptionHandler.handleException(TAG, ex);
			running = false;
			
			closeSocket(); // Clean up socket...
		}
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		synchronized(sync){
			if (running){
				running = false;
				workerThread.interrupt();
				workerThread = null;
				closeSocket();
			}
		}
	}
	
	private void closeSocket(){
		try {
			
			if (socket != null){ 
				socket.close(); 
			}
			
			if (reader != null) { 
				reader.close(); 
			}
			
			reader = null;
			socket = null;
			
		} catch (IOException ioex){ }
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
}
