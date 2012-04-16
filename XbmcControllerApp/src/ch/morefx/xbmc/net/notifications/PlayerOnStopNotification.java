package ch.morefx.xbmc.net.notifications;

import ch.morefx.xbmc.XbmcConnection;

/**
 * Implements the Player.OnStop notification event
 * See http://wiki.xbmc.org/index.php?title=JSON-RPC_API/v3#Player.OnStop
 */
public class PlayerOnStopNotification extends XbmcNotification {
	public static final String METHOD = "Player.OnStop";
	
	@Override
	public String handle(XbmcConnection connection) {
		return PLAYER_UPDATE;
	}
}
