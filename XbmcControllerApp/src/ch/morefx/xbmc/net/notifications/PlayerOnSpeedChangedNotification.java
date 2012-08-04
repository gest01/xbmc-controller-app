package ch.morefx.xbmc.net.notifications;

import ch.morefx.xbmc.XbmcConnection;

/**
 * Implements the Player.OnPause notification event
 * See http://wiki.xbmc.org/index.php?title=JSON-RPC_API/v3#Player.OnSpeedChanged
 */
public class PlayerOnSpeedChangedNotification  extends XbmcNotification{
	public static final String METHOD = "Player.OnSpeedChanged";
	
	@Override
	public String handle(XbmcConnection connection) {
		return PLAYER_UPDATE;
	}
}
