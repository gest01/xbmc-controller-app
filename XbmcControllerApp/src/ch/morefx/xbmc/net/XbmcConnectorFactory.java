package ch.morefx.xbmc.net;

import ch.morefx.xbmc.XbmcConnection;
import ch.morefx.xbmc.util.Check;

/**
 * Factory for creating an instance of a class that implements the <code>XbmcConnector</code> interface.
 */
public final class XbmcConnectorFactory {

	/**
	 * Creates an instance of a class that implements the <code>XbmcConnector</code> interface by using a specified XbmcConnection for creation.
	 * @param connection The Connection
	 * @return XbmcConnector
	 */
	public static XbmcConnector create(XbmcConnection connection){
		Check.argumentNotNull(connection, "connection");
		
		return new XbmcHttpConnector(connection);
	}
}
