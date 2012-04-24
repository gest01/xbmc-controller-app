package ch.morefx.xbmc.net.notifications;

import ch.morefx.xbmc.XbmcConnection;
import ch.morefx.xbmc.XbmcRemoteControlApplication;

/**
 * Implements the Player.OnStop notification event
 * See http://wiki.xbmc.org/index.php?title=JSON-RPC_API/v3#Player.OnStop
 */
public class PlayerOnStopNotification extends XbmcNotification {
	public static final String METHOD = "Player.OnStop";
	
	@Override
	public String handle(XbmcRemoteControlApplication application) {
		
		XbmcConnection connection = application.getCurrentConnection();
		connection.getAudioPlayer().disable();
		
		return PLAYER_UPDATE;
	}
}
