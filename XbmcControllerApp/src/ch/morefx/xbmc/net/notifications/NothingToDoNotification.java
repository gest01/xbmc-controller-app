package ch.morefx.xbmc.net.notifications;

import ch.morefx.xbmc.XbmcConnection;

/**
 * Handles all Notifications from XBMC which doesnt required any further actions .
 */
public class NothingToDoNotification extends XbmcNotification{
	
	private static final String GUIOnScreensaverActivate = "GUI.OnScreensaverActivate";
	private static final String GUIOnScreensaverDeactivated = "GUI.OnScreensaverDeactivated";
	
	private static final String SystemOnSleep = "System.OnSleep";
	private static final String SystemOnWake = "System.OnWake";
	
	public static boolean isNothingToDo(String method){
		return method.equals(GUIOnScreensaverActivate)
			|| method.equals(GUIOnScreensaverDeactivated)
			|| method.equals(SystemOnSleep)
			|| method.equals(SystemOnWake);
	}
	
	@Override
	public String handle(XbmcConnection connection) {
		return NONE;
	}
}
