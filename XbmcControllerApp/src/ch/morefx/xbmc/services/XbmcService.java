package ch.morefx.xbmc.services;

import android.app.Service;
import ch.morefx.xbmc.XbmcRemoteControlApplication;

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
