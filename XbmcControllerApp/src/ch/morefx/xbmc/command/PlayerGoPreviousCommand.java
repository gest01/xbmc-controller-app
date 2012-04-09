package ch.morefx.xbmc.command;

import ch.morefx.xbmc.model.players.MediaPlayer;
import ch.morefx.xbmc.util.Check;

/**
 * Implements the Player.GoPrevious json command
 * See http://wiki.xbmc.org/index.php?title=JSON-RPC_API/v3#Player.GoPrevious
 */
public class PlayerGoPreviousCommand extends JsonCommand {

	private MediaPlayer player;
	
	public PlayerGoPreviousCommand(MediaPlayer player) {
		super("Player.GoPrevious");
		
		Check.argumentNotNull(player, "player");
		this.player = player;
	}
	
	@Override
	void prepareCommand(JsonCommandBuilder builder) {
		builder.addParams("playerid", player.getPlayerId());
	}
}
