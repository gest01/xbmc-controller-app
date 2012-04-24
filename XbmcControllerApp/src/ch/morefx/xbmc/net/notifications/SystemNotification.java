package ch.morefx.xbmc.net.notifications;

import ch.morefx.xbmc.XbmcConnection;
import ch.morefx.xbmc.XbmcRemoteControlApplication;

/**
 * Generic notification handler for System.XXX events
 */
public class SystemNotification extends XbmcNotification {

	private static final String SystemOnQuit = "System.OnQuit";
	private static final String SystemOnRestart = "System.OnRestart";
	
	public static boolean isSystemNotification(String method){
		return SystemOnQuit.equals(method) || SystemOnRestart.equals(method);
	}
	
	@Override
	public String handle(XbmcRemoteControlApplication application) {
		
		XbmcConnection connection = application.getCurrentConnection();
		if (connection.getAudioPlayer().isActive()){
			connection.getAudioPlayer().disable();
			return PLAYER_UPDATE;
		}
		
		return NONE;
	}
}
