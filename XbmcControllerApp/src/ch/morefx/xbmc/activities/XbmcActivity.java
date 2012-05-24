package ch.morefx.xbmc.activities;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import ch.morefx.xbmc.NotificationBroadcastReceiver;
import ch.morefx.xbmc.NotificationListener;
import ch.morefx.xbmc.R;
import ch.morefx.xbmc.XbmcRemoteControlApplication;
import ch.morefx.xbmc.activities.home.HomeScreenActivity;
import ch.morefx.xbmc.model.AudioLibrary;
import ch.morefx.xbmc.model.remotecontrol.RemoteController;
import ch.morefx.xbmc.preferences.ApplicationPreferenceActivity;
import ch.morefx.xbmc.services.NotificationsService;


public class XbmcActivity extends Activity
	implements NotificationListener {
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_options_menu, menu);
        return true;
    }
    
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
      switch (item.getItemId()) {
	      case R.id.mnu_connections:
	    	  startActivity(new Intent(this, ApplicationPreferenceActivity.class));
	        return true;
	      case R.id.mnu_home:
	       	   startActivity(new Intent(this, HomeScreenActivity.class));
	        return true;
	      default:
	        return super.onOptionsItemSelected(item);
       }
    }
    
    private NotificationBroadcastReceiver receiver;
        
    @Override
    protected void onResume() {
    	super.onResume();
    	receiver = NotificationBroadcastReceiver.registerWithDefaultFilters(this);
    }
    
    @Override
    protected void onPause() {
    	super.onPause();
    	unregisterReceiver(receiver);
    }
    
    public void onConnectionLost() { }
    
    public void onPlayerUpdate() { 
    	
    }
    
    protected void startPlayerService(){
        startService(new Intent(this, NotificationsService.class));
	}
	
	protected void stopPlayerService(){
		stopService(new Intent(this, NotificationsService.class));
	}
    
    protected XbmcRemoteControlApplication getXbmcApplication(){
		return (XbmcRemoteControlApplication)getApplicationContext();
    }
    
    protected RemoteController getRemoteController(){
    	return getXbmcApplication().getCurrentConnection().getRemoteControl();
    }
    
    protected AudioLibrary getAudioLibrary(){
		XbmcRemoteControlApplication app = getXbmcApplication();
		return app.getCurrentConnection().getAudioLibrary();
    }
}
