package ch.morefx.xbmc.services;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import ch.morefx.xbmc.CommandExecutor;
import ch.morefx.xbmc.XbmcConnection;
import ch.morefx.xbmc.XbmcExceptionHandler;
import ch.morefx.xbmc.command.JsonCommandExecutor;
import ch.morefx.xbmc.command.PlayerGetActivePlayersCommand;
import ch.morefx.xbmc.command.PlayerGetPropertiesCommand;
import ch.morefx.xbmc.command.PlaylistGetItems;
import ch.morefx.xbmc.model.Playlist;
import ch.morefx.xbmc.model.players.AudioPlayer;
import ch.morefx.xbmc.model.players.PicturePlayer;
import ch.morefx.xbmc.model.players.Player;
import ch.morefx.xbmc.model.players.VideoPlayer;

public class PlayerService extends XbmcService {

	private static final String TAG = "PlayerService";

	private Timer timer = new Timer();
	private static long UPDATE_INTERVAL = 1000 * 5;
	private static long DELAY_INTERVAL = 0;

	@Override
	public void onCreate() {
		Log.i(TAG, "onCreate");
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		Log.i(TAG, "onStartCommand");
		startService();
		return START_STICKY;
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}


	@Override
	public void onDestroy() {
		Log.i(TAG, "onDestroy");

		if (this.timer != null){
			this.timer.cancel();	
		}
		
		
		super.onDestroy();
	}
	
	

	private void startService() {

		TimerTask task = new TimerTask() {
			public void run() {
				try {

					XbmcConnection connection = getXbmcApplication().getCurrentConnection();
					if (connection != null) {
						CommandExecutor executor = new JsonCommandExecutor(connection);

						PlayerGetActivePlayersCommand command = new PlayerGetActivePlayersCommand();
						executor.execute(command);

						if (command.isPlaying()) {
							for (Player player : command.getPlayers()) {
								if (player instanceof AudioPlayer)
									executor.execute(new PlaylistGetItems(Playlist.Audio));
								if (player instanceof VideoPlayer)
									executor.execute(new PlaylistGetItems(Playlist.Video));
								if (player instanceof PicturePlayer)
									executor.execute(new PlaylistGetItems(Playlist.Picture));

								executor.execute(new PlayerGetPropertiesCommand(player));
							}
						}
					}

				} catch (Exception ex) {
					XbmcExceptionHandler.handleException(TAG, ex);
				}
			}
		};

		this.timer.scheduleAtFixedRate(task, DELAY_INTERVAL, UPDATE_INTERVAL);
	}
}
