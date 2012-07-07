package ch.morefx.xbmc.activities;

import ch.morefx.xbmc.XbmcConnection;
import android.content.Context;

public interface IXbmcActivity {

	Context getContext();
	
	XbmcConnection getConnection();
	
}
