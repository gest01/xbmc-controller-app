package ch.morefx.xbmc.net.notifications;

import org.json.JSONException;
import org.json.JSONObject;

import ch.morefx.xbmc.XbmcExceptionHandler;

public class NotificationParser {

	private static final String TAG = NotificationParser.class.getName();

	/**
	 * Creates a Notification object from json.
	 * @param json The json string received from XBMC
	 * @return Notification
	 * @throws Exception
	 */
	public static Notification parse(String json) throws Exception{

		String method = null;
		JSONObject jsonObject = null;

		try 
		{
			jsonObject = new JSONObject(json);
			method = jsonObject.getString("method");
		} catch (JSONException e) {
			XbmcExceptionHandler.handleException(TAG, e);
		}
		
		XbmcNotification notification = null;

		if (method.equals(PlayerOnPlayNotification.METHOD))
			notification = new PlayerOnPlayNotification();

		if (method.equals(PlayerOnPauseNotification.METHOD))
			notification = new PlayerOnPauseNotification();

		if (method.equals(PlayerOnStopNotification.METHOD))
			notification = new PlayerOnStopNotification();
		
		if (method.equals(PlayerOnSeekNotification.METHOD))
			notification = new PlayerOnSeekNotification();
		
		if (SystemNotification.isSystemNotification(method))
			notification =  new SystemNotification();
		
		if (NothingToDoNotification.isNothingToDo(method))
			return new NothingToDoNotification();

		if (notification != null) {
			notification.setJSONObject(jsonObject);
			return notification;
		}
		
		throw new Exception("Unable to create Notification from method : " + method);
	}
}
