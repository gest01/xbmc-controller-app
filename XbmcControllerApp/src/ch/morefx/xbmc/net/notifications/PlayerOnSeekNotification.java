package ch.morefx.xbmc.net.notifications;

import ch.morefx.xbmc.XbmcConnection;

/**
 * Implements the Player.OnSeek notification event
 * See http://wiki.xbmc.org/index.php?title=JSON-RPC_API/v3#Player.OnSeek
 */
public class PlayerOnSeekNotification extends XbmcNotification{

	public static final String METHOD = "Player.OnSeek";
	
	@Override
	public String handle(XbmcConnection connection) {
		return PLAYER_UPDATE;
	}
}
