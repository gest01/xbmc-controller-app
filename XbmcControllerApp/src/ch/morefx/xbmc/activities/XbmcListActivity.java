package ch.morefx.xbmc.activities;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import ch.morefx.xbmc.R;
import ch.morefx.xbmc.XbmcConnection;
import ch.morefx.xbmc.XbmcRemoteControlApplication;
import ch.morefx.xbmc.activities.home.HomeScreenActivity;
import ch.morefx.xbmc.model.AudioLibrary;
import ch.morefx.xbmc.model.VideoLibrary;
import ch.morefx.xbmc.model.players.AudioPlayer;
import ch.morefx.xbmc.model.remotecontrol.RemoteController;
import ch.morefx.xbmc.preferences.ApplicationPreferenceActivity;
import ch.morefx.xbmc.services.NotificationBroadcastReceiver;
import ch.morefx.xbmc.services.NotificationListener;
import ch.morefx.xbmc.services.NotificationsService;

public class XbmcListActivity extends ListActivity
	implements NotificationListener, IXbmcActivity {
	
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

    protected void registerForContextMenu(){
    	registerForContextMenu(getListView());
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
      
	      case R.id.mnu_settings:
	    	  startActivity(new Intent(this, ApplicationPreferenceActivity.class));
	        return true;
	      case R.id.mnu_home:
	       	   startActivity(new Intent(this, HomeScreenActivity.class));
	        return true;
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
    
    protected void startPlayerService(){
        startService(new Intent(this, NotificationsService.class));
	}
	
	protected void stopPlayerService(){
		stopService(new Intent(this, NotificationsService.class));
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
		return  XbmcRemoteControlApplication.getInstance().getCurrentConnection().getAudioPlayer();
	}


	public VideoLibrary getVideoLibrary() {
		return XbmcRemoteControlApplication.getInstance().getCurrentConnection().getVideoLibrary();
	}

	public Context getContext() {
		return this;
	}

}
