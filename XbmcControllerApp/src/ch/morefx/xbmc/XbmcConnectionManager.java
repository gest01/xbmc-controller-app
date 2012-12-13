package ch.morefx.xbmc;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;

public class XbmcConnectionManager {

	public static final String TAG = XbmcConnectionManager.class.getSimpleName();
	
	private static final String FILE = "xbmc_connections";
	
	private List<ConnectionDescriptor> connections;
	private Context context;
	
	public XbmcConnectionManager(Context context) {
		this.connections = new ArrayList<ConnectionDescriptor>();
		this.context = context;
		load(context);
	}
	
	@SuppressWarnings("unchecked")
	private void load(Context context) {
		try {
			FileInputStream fis = context.openFileInput(FILE);
			ObjectInputStream is = new ObjectInputStream(fis);
			this.connections = (List<ConnectionDescriptor>) is.readObject();
			fis.close();
		} catch (FileNotFoundException e) {
			// That's ok..probably the first time
		} catch (Exception e) {
			
			Log.w(TAG, "Error reading " + FILE);
			context.deleteFile(FILE);
		}
	}
	
	public void persist(){
		try {
			FileOutputStream fos = this.context.openFileOutput(FILE, Context.MODE_PRIVATE);
			ObjectOutputStream os = new ObjectOutputStream(fos);
			os.writeObject(this.connections);
			os.close();
		} catch (FileNotFoundException e) {
			/// That's ok..probably the first time
		} catch (Exception e) {
			XbmcExceptionHandler.handleException(TAG, "Error while saving settingsfile", e);
		}
	}
	
	public void deleteConnection(long connectionId){	
		ConnectionDescriptor connectionToDelete = null;
		for(ConnectionDescriptor connection : this.connections){
			if (connection.getId() == connectionId){
				connectionToDelete = connection; 
				break;
			}
		}
		
		if (connectionToDelete != null){
			Log.d(TAG, "Delete Connection with id " + connectionId);
			this.connections.remove(connectionToDelete);
		}
	}
	
	public void add(ConnectionDescriptor connection){
		if (connection == null){
			throw new IllegalArgumentException("connection is null");
		}
		
		Log.d(TAG, "New Connection added : " + connection.getConnectionName());
		connection.setId(System.currentTimeMillis());
		this.connections.add(connection);
	}
	
	/**
	 * Gets all available connections
	 * @return List of Type <code>ConnectionDescriptor</code>
	 */
	public List<ConnectionDescriptor> getConnections(){
		return this.connections;
	}
}
