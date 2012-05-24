package ch.morefx.xbmc.net.notifications;

/**
 * Exception class for notification parser errors.
 */
public class NotificationParserException 
	extends Exception {

	private static final long serialVersionUID = 1L;

	public NotificationParserException(String detailMessage, Throwable error) {
		super(detailMessage, error);
	}
	
	public NotificationParserException(String detailMessage) {
		super(detailMessage);
	}
	
	public NotificationParserException(Throwable error) {
		super(error);
	}
}
