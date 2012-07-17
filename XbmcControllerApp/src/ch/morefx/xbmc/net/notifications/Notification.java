package ch.morefx.xbmc.net.notifications;

import ch.morefx.xbmc.ResourceProvider;
import ch.morefx.xbmc.XbmcConnection;

public interface Notification {
	
	public static final String NONE = "ch.morefx.xbmc.net.notifications.NONE";
	public static final String PLAYER_UPDATE = "ch.morefx.xbmc.net.notifications.PLAYER_UPDATE";
	public static final String VIDEO_LIBRARY_UPDATE = "ch.morefx.xbmc.net.notifications.VIDEO_LIBRARY_UPDATE";
	public static final String CONNECTION_LOST = "ch.morefx.xbmc.net.notifications.CONNECTION_LOST";
	public static final String NOTIFICATION_PARSER_ERROR = "ch.morefx.xbmc.net.notifications.NOTIFICATION_PARSER_ERROR";
	
	
	String handle(XbmcConnection connection, ResourceProvider resourceprovider);
}
