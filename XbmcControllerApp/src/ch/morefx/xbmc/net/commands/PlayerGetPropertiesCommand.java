package ch.morefx.xbmc.net.commands;

import ch.morefx.xbmc.model.PlayerInfo;
import ch.morefx.xbmc.model.PlayerProperties;
import ch.morefx.xbmc.util.Check;

/**
 * Implements the Player.GetProperties json command
 * See http://wiki.xbmc.org/index.php?title=JSON-RPC_API/v3#Player.GetProperties
 */
public class PlayerGetPropertiesCommand extends JsonCommand 
	implements JsonCommandResponseHandler{

	private PlayerInfo player;
	private PlayerProperties properties;
	
	public PlayerGetPropertiesCommand(PlayerInfo player) {
		super("Player.GetProperties");
		
		Check.argumentNotNull(player, "player");
		this.player = player;
	}
	
	@Override
	void prepareCommand(JsonCommandBuilder builder) {
		builder.addParams("playerid", player.getPlayerId());
		builder.addParams("properties",  PlayerProperties.getFields());
	}
	
	public void handleResponse(JsonCommandResponse response) {
		properties = response.asObjectResult(PlayerProperties.class);
	}
	
	public PlayerProperties getProperties(){
		return this.properties;
	}
	
}
