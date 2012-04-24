package ch.morefx.xbmc.net.notifications;

import ch.morefx.xbmc.Globals;
import ch.morefx.xbmc.XbmcConnection;
import ch.morefx.xbmc.XbmcRemoteControlApplication;
import ch.morefx.xbmc.model.Song;
import ch.morefx.xbmc.net.CommandExecutorAdapter;
import ch.morefx.xbmc.net.JsonCommandExecutor;
import ch.morefx.xbmc.net.commands.AudioLibraryGetSongDetailsCommand;

/**
 * Implements the Player.OnPlay notification event
 * See http://wiki.xbmc.org/index.php?title=JSON-RPC_API/v3#Player.OnPlay
 */
public class PlayerOnPlayNotification extends XbmcNotification {
	public static final String METHOD = "Player.OnPlay";
	
	@Override
	public String handle(XbmcRemoteControlApplication application) {

		if (isSong()){
			return handleSongNortification(application);
		}
		
		return NONE;
	}
	
	private String handleSongNortification(XbmcRemoteControlApplication application){

		PlayerInfo info = getPlayerInfo();
		if (info != null){
			
			XbmcConnection connection = application.getCurrentConnection();
			
			CommandExecutorAdapter executor = new CommandExecutorAdapter(new JsonCommandExecutor(connection.getConnector()));
			AudioLibraryGetSongDetailsCommand command = new AudioLibraryGetSongDetailsCommand(getIdFromItem());
			executor.execute(command);
			Song song = command.getSong();
			
			if (Globals.NOTIFICATION_SERVICE_LOAD_SONG_THUMBNAILS){
				application.loadThumbnail(song);	
			}
			
			connection.getAudioPlayer().updatePlayer(info.getPlayerId());
			connection.getAudioPlayer().setPlaying(song);	
			
			return PLAYER_UPDATE;
		}
		
		return NONE;
	}
}
