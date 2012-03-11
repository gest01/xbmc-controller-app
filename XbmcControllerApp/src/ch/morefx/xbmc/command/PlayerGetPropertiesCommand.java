package ch.morefx.xbmc.command;

import ch.morefx.xbmc.model.players.Player;
import ch.morefx.xbmc.util.Check;

/**
 * Implements the Player.GetProperties json command
 * See http://wiki.xbmc.org/index.php?title=JSON-RPC_API/v3#Player.GetProperties
 */
public class PlayerGetPropertiesCommand extends JsonCommand 
	implements CommandResponseHandler{

	private Player player;
	
	public PlayerGetPropertiesCommand(Player player) {
		super("Player.GetProperties");
		
		Check.argumentNotNull(player, "player");
		this.player = player;
	}
	
	@Override
	void prepareCommand(CommandBuilder builder) {
		builder.addParams("playerid", player.getPlayerId());
		builder.addParams("properties",  getFields());
	}
	
	public void handleResponse(CommandResponse response) {
	}
	
	private static String[] getFields(){
		return new String[] {
				"canrotate"
				,"canrepeat"
				,"speed"
				,"canshuffle"
				,"shuffled"
				,"canmove"
				,"subtitleenabled"
				,"percentage"
				,"type"
				,"repeat"
				,"canseek"
				,"currentsubtitle"
				,"subtitles"
				,"totaltime"
				,"canzoom"
				,"currentaudiostream"
				,"playlistid"
				,"audiostreams"
				,"partymode"
				,"time"
				,"position"
				,"canchangespeed"
		};
	}
	
}
