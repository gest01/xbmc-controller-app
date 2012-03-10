package ch.morefx.xbmc.services;

import ch.morefx.xbmc.XbmcRemoteControlApplication;
import android.app.Service;

/**
 * Defines the base class for all services
 */
public abstract class XbmcService extends Service {
	
	@Override
	public void onCreate() {
		super.onCreate();
	}
	
    protected XbmcRemoteControlApplication getXbmcApplication(){
		return (XbmcRemoteControlApplication)getApplicationContext();
    }
}
