package ch.morefx.xbmc.command;

import ch.morefx.xbmc.model.players.MediaPlayer;
import ch.morefx.xbmc.util.Check;

/**
 * Implements the Player.GoNext json command
 * See http://wiki.xbmc.org/index.php?title=JSON-RPC_API/v3#Player.GoNext
 */
public class PlayerGoNextCommand extends JsonCommand{

	private MediaPlayer player;
	
	public PlayerGoNextCommand(MediaPlayer player) {
		super("Player.GoNext");
		
		Check.argumentNotNull(player, "player");
		this.player = player;
	}
	
	@Override
	void prepareCommand(JsonCommandBuilder builder) {
		builder.addParams("playerid", player.getPlayerId());
	}
}
