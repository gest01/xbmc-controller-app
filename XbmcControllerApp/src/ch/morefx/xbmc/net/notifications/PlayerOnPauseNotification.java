package ch.morefx.xbmc.net.notifications;

import ch.morefx.xbmc.ResourceProvider;
import ch.morefx.xbmc.XbmcConnection;

/**
 * Implements the Player.OnPause notification event
 * See http://wiki.xbmc.org/index.php?title=JSON-RPC_API/v3#Player.OnPause
 */
public class PlayerOnPauseNotification extends XbmcNotification{
	public static final String METHOD = "Player.OnPause";
	
	@Override
	public String handle(XbmcConnection connection, ResourceProvider resourceprovider) {
		return PLAYER_UPDATE;
	}
}
