package ch.morefx.xbmc.activities;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import ch.morefx.xbmc.R;
import ch.morefx.xbmc.XbmcRemoteControlApplication;
import ch.morefx.xbmc.model.AudioLibrary;
import ch.morefx.xbmc.preferences.ApplicationPreferenceActivity;
import ch.morefx.xbmc.services.NotificationsService;


public class XbmcActivity extends Activity{
	
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
        	   Intent intent = new Intent(XbmcActivity.this, ApplicationPreferenceActivity.class);
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
    
    protected XbmcRemoteControlApplication getXbmcApplication(){
		return (XbmcRemoteControlApplication)getApplicationContext();
    }
    
    protected AudioLibrary getAudioLibrary(){
		XbmcRemoteControlApplication app = getXbmcApplication();
		return app.getCurrentConnection().getAudioLibrary();
    }
}
