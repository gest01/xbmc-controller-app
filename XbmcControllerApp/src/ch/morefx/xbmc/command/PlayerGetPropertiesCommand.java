package ch.morefx.xbmc.command;

import ch.morefx.xbmc.model.PlayerProperties;
import ch.morefx.xbmc.model.players.Player;
import ch.morefx.xbmc.util.Check;

/**
 * Implements the Player.GetProperties json command
 * See http://wiki.xbmc.org/index.php?title=JSON-RPC_API/v3#Player.GetProperties
 */
public class PlayerGetPropertiesCommand extends JsonCommand 
	implements CommandResponseHandler{

	private Player player;
	private PlayerProperties properties;
	
	public PlayerGetPropertiesCommand(Player player) {
		super("Player.GetProperties");
		
		Check.argumentNotNull(player, "player");
		this.player = player;
	}
	
	@Override
	void prepareCommand(CommandBuilder builder) {
		builder.addParams("playerid", player.getPlayerId());
		builder.addParams("properties",  PlayerProperties.getFields());
	}
	
	public void handleResponse(CommandResponse response) {
		properties = response.asObjectResult(PlayerProperties.class);
	}
	
	public PlayerProperties getProperties(){
		return this.properties;
	}
	
}
