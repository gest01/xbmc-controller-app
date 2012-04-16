package ch.morefx.xbmc.net.notifications;

import ch.morefx.xbmc.XbmcConnection;

public interface Notification {
	
	public static final String NONE = "ch.morefx.xbmc.net.notifications.NONE";
	public static final String PLAYER_UPDATE = "ch.morefx.xbmc.net.notifications.PLAYER_UPDATE";
	
	
	String handle(XbmcConnection connection);
}
