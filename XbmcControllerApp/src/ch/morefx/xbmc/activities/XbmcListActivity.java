package ch.morefx.xbmc.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import ch.morefx.xbmc.R;
import ch.morefx.xbmc.XbmcRemoteControlApplication;
import ch.morefx.xbmc.model.AudioLibrary;
import ch.morefx.xbmc.model.VideoLibrary;
import ch.morefx.xbmc.preferences.ApplicationPreferenceActivity;
import ch.morefx.xbmc.services.PlayerService;

public class XbmcListActivity extends ListActivity{
	
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
        	   Intent intent = new Intent(XbmcListActivity.this, ApplicationPreferenceActivity.class);
        	   startActivity(intent);
	        return true;
	      default:
	        return super.onOptionsItemSelected(item);
       }
    }
    
    protected void startPlayerService(){
        startService(new Intent(this, PlayerService.class));
	}
	
	protected void stopPlayerService(){
		stopService(new Intent(this, PlayerService.class));
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
     * Gets the connected Video Library
     * @return VideoLibrary
     */
    protected VideoLibrary getVideoLibrary(){
		XbmcRemoteControlApplication app = getXbmcApplication();
		return app.getCurrentConnection().getVideoLibrary();
    }
}
