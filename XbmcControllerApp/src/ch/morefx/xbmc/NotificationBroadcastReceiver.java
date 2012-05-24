package ch.morefx.xbmc;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import ch.morefx.xbmc.activities.XbmcControllerMainActivity;
import ch.morefx.xbmc.net.notifications.Notification;
import ch.morefx.xbmc.util.Check;
import ch.morefx.xbmc.util.DialogUtility;

/**
 * Implements a broadcast receiver that handles all incoming notifications sent by xbmc
 */
public class NotificationBroadcastReceiver extends BroadcastReceiver {
	private static final String TAG = NotificationBroadcastReceiver.class.getName(); 
	
	private NotificationListener notificationlistener;
	private Activity activity;
	
	/**
	 * Creates and initializes a new instance of the <code>NotificationBroadcastReceiver</code> class.
	 * @param activity The calling activity
	 */
	NotificationBroadcastReceiver(Activity activity) {
		Check.argumentNotNull(activity, "activity");
		
		this.activity = activity;
		
		if (activity instanceof NotificationListener){
			this.notificationlistener = (NotificationListener)activity;	
		}
	}
	
	/**
	 * 
	 * @param activity
	 * @return
	 */
	public static NotificationBroadcastReceiver registerWithDefaultFilters(Activity activity){
		Check.argumentNotNull(activity, "activity");
		
		NotificationBroadcastReceiver receiver = new NotificationBroadcastReceiver(activity);
		activity.registerReceiver(receiver, new IntentFilter(Notification.PLAYER_UPDATE));
		activity.registerReceiver(receiver, new IntentFilter(Notification.CONNECTION_LOST));
		activity.registerReceiver(receiver, new IntentFilter(Notification.NOTIFICATION_PARSER_ERROR));
		
		return receiver;
	}
	
	@Override
	public void onReceive(Context context, Intent intent) {
		if (BuildConfig.DEBUG)
			Log.d(TAG, "onReceive " + intent.getAction());
			
		if (intent.getAction().equals(Notification.CONNECTION_LOST)){
			onConnectionLost();
		}
		
		if (intent.getAction().equals(Notification.PLAYER_UPDATE)){
			onPlayerUpdate();
		}
		
		if (intent.getAction().equals(Notification.NOTIFICATION_PARSER_ERROR)){
			Exception error = (Exception)intent.getSerializableExtra(Notification.NOTIFICATION_PARSER_ERROR);
			
			DialogUtility.showErrorWithYesNo(
					activity, 
					String.format(activity.getResources().getString(R.string.notification_parser_error), error.toString()),
					activity.getResources().getString(R.string.connection_error), 
					new OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();
								if (which == DialogInterface.BUTTON_POSITIVE){
									getXbmcApplication().closeCurrentConnection();
									
							    	Intent intent = new Intent(activity, XbmcControllerMainActivity.class);
							    	intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							    	activity.startActivity(intent);
								}
							}
			});
		}
	}
	
	private void onPlayerUpdate(){
		if (notificationlistener != null){
			notificationlistener.onPlayerUpdate();	
		}
	}
	
	private void onConnectionLost(){
    	getXbmcApplication().closeCurrentConnection();
	   	
    	DialogUtility.showError(
    			activity, 
    			activity.getResources().getString(R.string.connection_lost),  
    			activity.getResources().getString(R.string.connection_error),  
    			new OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						
				    	Intent intent = new Intent(activity, XbmcControllerMainActivity.class);
				    	intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				    	activity.startActivity(intent);
					}
		});

		if (notificationlistener != null){
			notificationlistener.onConnectionLost();
		}
	}
	
	private XbmcRemoteControlApplication getXbmcApplication(){
		return (XbmcRemoteControlApplication)activity.getApplicationContext();
    }
}
