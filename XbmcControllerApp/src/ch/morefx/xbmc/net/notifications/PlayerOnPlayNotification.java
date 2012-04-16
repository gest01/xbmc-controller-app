package ch.morefx.xbmc.net.notifications;

import ch.morefx.xbmc.XbmcConnection;
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
	public String handle(XbmcConnection connection) {

		if (isSong()){
			return handleSongNortification(connection);
		}
		
		return NONE;
	}
	
	private String handleSongNortification(XbmcConnection connection){
		CommandExecutorAdapter executor = new CommandExecutorAdapter(new JsonCommandExecutor(connection.getConnector()));
		AudioLibraryGetSongDetailsCommand command = new AudioLibraryGetSongDetailsCommand(getIdFromItem());
		executor.execute(command);
		Song song = command.getSong();
		
		PlayerInfo info = getPlayerInfo();
		if (info != null){
			connection.getAudioLibrary().getPlayer().updatePlayer(info.getPlayerId());
			connection.getAudioLibrary().getPlayer().setPlaying(song);	
			return PLAYER_UPDATE;
		}
		
		return NONE;
	}
}
