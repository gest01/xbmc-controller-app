package ch.morefx.xbmc.net;

import ch.morefx.xbmc.ConnectionDescriptor;
import ch.morefx.xbmc.util.Check;

/**
 * Factory for creating an instance of a class that implements the <code>XbmcConnector</code> interface.
 */
public final class XbmcConnectorFactory {

	/**
	 * Creates an instance of a class that implements the <code>XbmcConnector</code> interface by using a specified XbmcConnection for creation.
	 * @param descriptor The ConnectionDescriptor
	 * @return XbmcConnector
	 */
	public static XbmcConnector create(ConnectionDescriptor descriptor){
		Check.argumentNotNull(descriptor, "descriptor");
		
		return new XbmcHttpConnector(descriptor);
	}
}
