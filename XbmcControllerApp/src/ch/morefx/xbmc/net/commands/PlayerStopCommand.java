package ch.morefx.xbmc.net.commands;

import ch.morefx.xbmc.model.players.MediaPlayer;
import ch.morefx.xbmc.util.Check;

/**
 * Implements the Player.Stop json command
 * See http://wiki.xbmc.org/index.php?title=JSON-RPC_API/v3#Player.Stop
 */
public class PlayerStopCommand extends JsonCommand{

	private MediaPlayer player;
	
	public PlayerStopCommand(MediaPlayer player) {
		super("Player.Stop");
		
		Check.argumentNotNull(player, "player");
		this.player = player;
	}
	
	@Override
	void prepareCommand(JsonCommandBuilder builder) {
		builder.addParams("playerid", player.getPlayerId());
	}
}
