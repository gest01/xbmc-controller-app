package ch.morefx.xbmc.command;

import ch.morefx.xbmc.model.players.MediaPlayer;
import ch.morefx.xbmc.util.Check;

/**
 * Implements the Player.PlayPause json command
 * http://wiki.xbmc.org/index.php?title=JSON-RPC_API/v3#Player.PlayPause
 */
public class PlayerPlayPauseCommand extends JsonCommand
	implements JsonCommandResponseHandler{

	private MediaPlayer player;
	
	public PlayerPlayPauseCommand(MediaPlayer player) {
		super("Player.PlayPause");
		
		Check.argumentNotNull(player, "player");
		this.player = player;
	}
	
	@Override
	void prepareCommand(JsonCommandBuilder builder) {
		builder.addParams("playerid", player.getPlayerId());
	}
	
	public void handleResponse(JsonCommandResponse response) {
	}
}
