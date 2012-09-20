package ch.morefx.xbmc.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import ch.morefx.xbmc.R;
import ch.morefx.xbmc.XbmcConnection;
import ch.morefx.xbmc.XbmcRemoteControlApplication;
import ch.morefx.xbmc.activities.home.HomeScreenActivity;
import ch.morefx.xbmc.activities.preferences.ApplicationPreferenceActivity;
import ch.morefx.xbmc.model.AudioLibrary;
import ch.morefx.xbmc.model.VideoLibrary;
import ch.morefx.xbmc.model.players.AudioPlayer;
import ch.morefx.xbmc.model.remotecontrol.RemoteController;
import ch.morefx.xbmc.services.NotificationBroadcastReceiver;
import ch.morefx.xbmc.services.NotificationListener;
import ch.morefx.xbmc.services.NotificationsService;


public class XbmcActivity extends Activity
	implements NotificationListener, IXbmcActivity {
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_options_menu, menu);
        return true;
    }
    
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
      switch (item.getItemId()) {
	      case R.id.mnu_settings:
	    	  startActivity(new Intent(this, ApplicationPreferenceActivity.class)); return true;
	      case R.id.mnu_home:
	       	   startActivity(new Intent(this, HomeScreenActivity.class)); return true;
	      case R.id.mnu_close_connection:
	    	  	XbmcRemoteControlApplication.getInstance().closeCurrentConnection();
				stopPlayerService();
				  
				Intent intent = new Intent(this, XbmcControllerMainActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
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
    
	public XbmcRemoteControlApplication getXbmcApplication(){
		return (XbmcRemoteControlApplication)getApplicationContext();
    }
    
    public RemoteController getRemoteController(){
    	return XbmcRemoteControlApplication.getInstance().getCurrentConnection().getRemoteControl();
    }
    
    public AudioLibrary getAudioLibrary(){
		return XbmcRemoteControlApplication.getInstance().getCurrentConnection().getAudioLibrary();
    }


	public XbmcConnection getConnection() {
		return XbmcRemoteControlApplication.getInstance().getCurrentConnection();
	}

	public AudioPlayer getAudioPlayer() {
		return XbmcRemoteControlApplication.getInstance().getCurrentConnection().getAudioPlayer();
	}


	public VideoLibrary getVideoLibrary() {
		return XbmcRemoteControlApplication.getInstance().getCurrentConnection().getVideoLibrary();
	}

	public Context getContext() {
		return this;
	}
}
