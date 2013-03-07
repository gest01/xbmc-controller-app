package ch.morefx.xbmc;

import java.util.Comparator;

/**
 * Compares ConnectionDescriptor objects by ConnectionName
 */
public class ConnectionDescriptorComparator 
	implements Comparator<ConnectionDescriptor>{

	public int compare(ConnectionDescriptor lhs, ConnectionDescriptor rhs) {
		return lhs.getConnectionName().compareTo(rhs.getConnectionName());
	}
}
