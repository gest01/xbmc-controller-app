package ch.morefx.xbmc.services;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class PlayerService extends XbmcService {

	private static final String TAG = "PlayerService";

	private Timer timer = new Timer();
	private static long UPDATE_INTERVAL = 1000 * 5;
	private static long DELAY_INTERVAL = 0;

	@Override
	public void onCreate() {
		Log.i(TAG, "onCreate");
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		Log.i(TAG, "onStartCommand");
		startService();
		return START_STICKY;
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public boolean onUnbind(Intent intent) {
		return super.onUnbind(intent);
	}

	@Override
	public void onDestroy() {
		Log.i(TAG, "onDestroy");

		super.onDestroy();
	}

	private void startService() {
		timer.scheduleAtFixedRate(

		new TimerTask() {
			public void run() {
				try {

					
					Log.i(TAG, "update....");
					Thread.sleep(UPDATE_INTERVAL);

				} catch (InterruptedException ie) {
					Log.e(getClass().getSimpleName(),
							"InterruptedException" + ie.toString());
				}
			}
		}, 
		DELAY_INTERVAL, 
		UPDATE_INTERVAL);
	}
}
