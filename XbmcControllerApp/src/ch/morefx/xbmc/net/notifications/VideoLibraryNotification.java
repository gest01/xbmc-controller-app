package ch.morefx.xbmc.net.notifications;

import ch.morefx.xbmc.XbmcConnection;

/**
 * Implements the VideoLibrary.OnUpdate and VideoLibrary.OnRemove notification event
 * See http://wiki.xbmc.org/index.php?title=JSON-RPC_API/v3#VideoLibrary.OnUpdate
 */
public class VideoLibraryNotification extends XbmcNotification {

	public static final String VideoLibraryOnUpdate = "VideoLibrary.OnUpdate";
	public static final String VideoLibraryOnRemove = "VideoLibrary.OnRemove";
	
	public static boolean isVideoLibraryNotification(String method){
		return VideoLibraryOnUpdate.equals(method) || VideoLibraryOnRemove.equals(method);
	}
	
	@Override
	public String handle(XbmcConnection connection) {
		return XbmcNotification.VIDEO_LIBRARY_UPDATE;
	}
}
