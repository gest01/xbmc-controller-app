package ch.morefx.xbmc.activities.players;

import android.os.Handler;
import android.util.Log;
import ch.morefx.xbmc.BuildConfig;

/**
 * Implements base functionality for Synchronisation Threads
 */
public abstract class SynchronisationThread {

	private boolean isStopRequested;
	private static final String TAG = SynchronisationThread.class.getSimpleName();
	

	/**
	 * Starts the Synchronisation Thread
	 * @param handler Handler object for callbacks back into UI thread
	 */
	public void start(Handler handler){
		if (BuildConfig.DEBUG){
			Log.v(TAG, "Starting...");
		}
		
		this.isStopRequested = false;
		startSyncThread(handler);
	}
	
	/**
	 * Stops the Synchronisation Thread
	 */
	public void stop(){
		if (BuildConfig.DEBUG){
			Log.v(TAG, "Stopping...");
		}
		
		this.isStopRequested = true;
	}
	
	/**
	 * worker method running in a thread
	 * @param handler  Handler object for callbacks back into UI thread
	 */
	protected abstract void doSync(Handler handler);
	
	/**
	 * Gets a flag that indicates whether stop is requested for leaving the thread.
	 * @return true / false
	 */
	protected boolean isStopRequested(){
		return isStopRequested;
	}
	
	private void startSyncThread(final Handler handler){
		Thread th = new Thread(new Runnable() {
			public void run() {
				doSync(handler);
			}
		});
		
		th.start();
	}
	

}
