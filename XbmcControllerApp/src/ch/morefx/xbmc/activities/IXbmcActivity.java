package ch.morefx.xbmc.activities;

import android.content.Context;
import ch.morefx.xbmc.XbmcConnection;

public interface IXbmcActivity {

	Context getContext();
	
	XbmcConnection getConnection();
	
}
