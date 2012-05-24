package ch.morefx.xbmc.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import ch.morefx.xbmc.NotificationBroadcastReceiver;
import ch.morefx.xbmc.NotificationListener;
import ch.morefx.xbmc.R;
import ch.morefx.xbmc.XbmcRemoteControlApplication;
import ch.morefx.xbmc.activities.home.HomeScreenActivity;
import ch.morefx.xbmc.model.AudioLibrary;
import ch.morefx.xbmc.model.VideoLibrary;
import ch.morefx.xbmc.model.players.AudioPlayer;
import ch.morefx.xbmc.preferences.ApplicationPreferenceActivity;
import ch.morefx.xbmc.services.NotificationsService;

public class XbmcListActivity extends ListActivity
	implements NotificationListener {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_options_menu, menu);
        return true;
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
    
    public void onPlayerUpdate() { }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
      switch (item.getItemId()) {
      
      case android.R.id.home:
    	  System.out.println("GOOGLE !!!");
    	  return true;
      
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
    
    protected void startPlayerService(){
        startService(new Intent(this, NotificationsService.class));
	}
	
	protected void stopPlayerService(){
		stopService(new Intent(this, NotificationsService.class));
	}
    
    protected XbmcRemoteControlApplication getXbmcApplication(){
		return (XbmcRemoteControlApplication)getApplicationContext();
    }
    
    /**
     * Gets the connected Audio Library
     * @return AudioLibrary
     */
    protected AudioLibrary getAudioLibrary(){
		XbmcRemoteControlApplication app = getXbmcApplication();
		return app.getCurrentConnection().getAudioLibrary();
    }
    
    /**
     * 
     * @return AudioLibrary
     */
    protected AudioPlayer getAudioPlayer(){
		XbmcRemoteControlApplication app = getXbmcApplication();
		return app.getCurrentConnection().getAudioPlayer();
    }
    
    /**
     * Gets the connected Video Library
     * @return VideoLibrary
     */
    protected VideoLibrary getVideoLibrary(){
		XbmcRemoteControlApplication app = getXbmcApplication();
		return app.getCurrentConnection().getVideoLibrary();
    }
}
