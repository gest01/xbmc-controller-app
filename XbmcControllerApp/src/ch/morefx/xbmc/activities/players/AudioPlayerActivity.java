package ch.morefx.xbmc.activities.players;

import android.os.Bundle;
import ch.morefx.xbmc.activities.XbmcActivity;

public class AudioPlayerActivity extends XbmcActivity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		super.getAudioLibrary().getPlayer().next();
		
	}
}
