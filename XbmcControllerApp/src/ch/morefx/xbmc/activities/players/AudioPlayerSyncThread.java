package ch.morefx.xbmc.activities.players;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import ch.morefx.xbmc.model.PlayerInfo;
import ch.morefx.xbmc.model.PlayerInfo.PlayerType;
import ch.morefx.xbmc.net.commands.PlayerGetPropertiesCommand;

/**
 * Synchronisation thread for playing songs.
 */
public class AudioPlayerSyncThread 
	extends SynchronisationThread {

	public static final String BUNDLE_ARGS = "PLAYER_PROPERTIES";

	@Override
	protected void doSync(Handler handler) {
		while(!isStopRequested()){
			PlayerGetPropertiesCommand command = new PlayerGetPropertiesCommand( new PlayerInfo(PlayerType.Audio, 0) );
			command.execute();
			
			Bundle bundle = new Bundle();
			bundle.putSerializable(BUNDLE_ARGS, command.getProperties());
			Message message = new Message();
			message.setData(bundle);
			handler.sendMessage(message);
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
