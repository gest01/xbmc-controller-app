package ch.morefx.xbmc.net.notifications;

import ch.morefx.xbmc.XbmcRemoteControlApplication;

/**
 * Implements the Player.OnPause notification event
 * See http://wiki.xbmc.org/index.php?title=JSON-RPC_API/v3#Player.OnPause
 */
public class PlayerOnPauseNotification extends XbmcNotification{
	public static final String METHOD = "Player.OnPause";
	
	@Override
	public String handle(XbmcRemoteControlApplication application) {
		return PLAYER_UPDATE;
	}
}
