package ch.morefx.xbmc.activities.home;

import android.os.Bundle;
import ch.morefx.xbmc.R;
import ch.morefx.xbmc.activities.XbmcActivity;

public class RemoteControlActivity extends XbmcActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.remotecontrol_activity_layout);
	}
}
