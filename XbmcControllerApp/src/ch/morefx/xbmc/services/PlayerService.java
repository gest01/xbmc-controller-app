package ch.morefx.xbmc.services;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import ch.morefx.xbmc.XbmcConnection;
import ch.morefx.xbmc.XbmcExceptionHandler;
import ch.morefx.xbmc.command.CommandExecutor;
import ch.morefx.xbmc.command.CommandExecutorException;
import ch.morefx.xbmc.command.JsonCommandExecutor;
import ch.morefx.xbmc.command.PlayerGetActivePlayersCommand;
import ch.morefx.xbmc.command.PlayerGetPropertiesCommand;
import ch.morefx.xbmc.command.PlaylistGetItemsCommand;
import ch.morefx.xbmc.model.PlayerInfo;
import ch.morefx.xbmc.model.Playlist;
import ch.morefx.xbmc.model.Song;
import ch.morefx.xbmc.model.players.AudioPlayer;

// http://stackoverflow.com/questions/2463175/how-to-have-android-service-communicate-with-activity

public class PlayerService extends XbmcService {

	public static final String REFRESH_AUDIO_LIBRARY = "ch.morefx.xbmc.services.REFRESH_AUDIO_LIBRARY";
	public static final String REFRESH_VIDEO_LIBRARY = "ch.morefx.xbmc.services.REFRESH_VIDEO_LIBRARY";
	
	private static final String TAG = "PlayerService";

	private Timer timer = new Timer();
	private static long UPDATE_INTERVAL = 1000 * 6;
	private static long DELAY_INTERVAL = 0;

	private XbmcConnection connection;
	private CommandExecutor executor;
	
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


//	@Override
//	public void onDestroy() {
//		Log.i(TAG, "onDestroy");
//
//		if (this.timer != null){
//			this.timer.cancel();	
//		}
//		
//		super.onDestroy();
//	}
	

	private void startService() {

		TimerTask task = new TimerTask() {
			public void run() {
				try {

					connection = getXbmcApplication().getCurrentConnection();				
					if (connection != null) {
						executor = new JsonCommandExecutor(connection);
						
						PlayerGetActivePlayersCommand command = new PlayerGetActivePlayersCommand();
						executor.execute(command); 
						
						updateAudioPlayer(command.getAudioPlayerInfo());
								
					}

				} catch (Exception ex) {
					System.out.println("SHIT!!!!!!!!!!!!!!!!!!");
					XbmcExceptionHandler.handleException(TAG, ex);
				}
			}
		};

		this.timer.scheduleAtFixedRate(task, DELAY_INTERVAL, UPDATE_INTERVAL);
	}
	
	private void updateAudioPlayer(PlayerInfo info) throws CommandExecutorException{
		
		boolean sendRefreshEvent = false;
		
		AudioPlayer player = connection.getAudioLibrary().getPlayer();
		if (info == null){
			if (player.isActive()){
				player.disable();
				sendRefreshEvent = true;	
			}

		} else {
			
			player.updatePlayer(info.getPlayerId());
			
			PlaylistGetItemsCommand playlistcommand = new PlaylistGetItemsCommand(Playlist.Audio);
			executor.execute(playlistcommand);
			
			PlayerGetPropertiesCommand propertiescommand = new PlayerGetPropertiesCommand(info); 
			executor.execute(propertiescommand);
			
			for(Song song : playlistcommand.getSongs()){
				if (song.getPosition() == propertiescommand.getProperties().getPosition()){
					if (!player.isPlaying(song)){
						player.setPlaying(song);
						sendRefreshEvent = true;
					}
					
					break;
				}
			}
		}
		
		if (sendRefreshEvent){
			sendAudioLibraryRefreshEvent();
		}
	}
	
	
//	/**
//	 * Sends a REFRESH_VIDEO_LIBRARY Broadcast message to all interested BroadcastReceivers
//	 */
//	private void sendVideoLibraryRefreshEvent(){
//		sendBroadcast(new Intent(REFRESH_VIDEO_LIBRARY));
//	}
	
	/**
	 * Sends a REFRESH_AUDIO_LIBRARY Broadcast message to all interested BroadcastReceivers
	 */
	private void sendAudioLibraryRefreshEvent(){
		sendBroadcast(new Intent(REFRESH_AUDIO_LIBRARY));
	}
}
