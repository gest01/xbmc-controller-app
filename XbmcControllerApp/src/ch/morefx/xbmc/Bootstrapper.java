package ch.morefx.xbmc;

import android.util.Log;
import ch.morefx.xbmc.model.ApplicationProperties;
import ch.morefx.xbmc.model.Playlist;
import ch.morefx.xbmc.model.Song;
import ch.morefx.xbmc.net.commands.ApplicationGetPropertiesCommand;
import ch.morefx.xbmc.net.commands.PlayerGetActivePlayersCommand;
import ch.morefx.xbmc.net.commands.PlayerGetItemCommand;
import ch.morefx.xbmc.util.Check;

/**
 * Does some initial tasks before the connection is setup
 */
public class Bootstrapper {
	
	private static final String TAG = Bootstrapper.class.getSimpleName();
	
	public void settingUpConnection(final XbmcConnection connection){
		Check.argumentNotNull(connection, "connection");
	
		Thread thread = new Thread(new Runnable() {
			public void run() {
				executeBootstrapTasks(connection);
			}
		});
		
		try {
			
			thread.start();
			thread.join(); // wait until all bootstrapper tasks are being completed
			
		} catch (InterruptedException e) {
			XbmcExceptionHandler.handleException(TAG, "Bootstrapper error!", e);

		}
	}
	
	private void executeBootstrapTasks(XbmcConnection connection){
		retrieveApplicationProperties(connection);
		retrieveCurrentPlayingSong(connection);
	}
	
	/**
	 * retrieves version string and volume from a connection
	 * @param connection xbmc connection
	 */
	private void retrieveApplicationProperties(XbmcConnection connection){

		ApplicationGetPropertiesCommand command = new ApplicationGetPropertiesCommand();
		command.execute(connection);
		
		ApplicationProperties properties = command.getProperties();
		if (BuildConfig.DEBUG){
			Log.d(TAG, "Xbmc Version : " + properties.getVersion());
			Log.d(TAG, "Is muted : " + properties.isMuted());
			Log.d(TAG, "Volume : " + properties.getVolume());
		}
		
		connection.initialize(properties);
	}
	
	/**
	 * Retrieves the current playing song
	 * @param connection the xbmc connection.
	 */
	private void retrieveCurrentPlayingSong(XbmcConnection connection)  {
		PlayerGetActivePlayersCommand command = new PlayerGetActivePlayersCommand();
		command.execute(connection);
		
		if (command.getAudioPlayerInfo() != null){
			PlayerGetItemCommand cmd = new PlayerGetItemCommand(Playlist.Audio);
			cmd.execute(connection);

			Song song = cmd.getCurrentSong();
			if (song != null){
				connection.getAudioPlayer().setPlaying(song);
				connection.getAudioPlayer().updatePlayer(command.getAudioPlayerInfo().getPlayerId());
			}
		}
	}
}
