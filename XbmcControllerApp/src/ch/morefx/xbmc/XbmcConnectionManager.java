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

	public static final String TAG = "XbmcConnectionManager";
	
	private List<XbmcConnection> connections;
	private Context context;
	
	public XbmcConnectionManager(Context context) {
		this.connections = new ArrayList<XbmcConnection>();
		this.context = context;
		load(context);
	}
	
	@SuppressWarnings("unchecked")
	private void load(Context context) {
		try {
			FileInputStream fis = context.openFileInput("xbmc_connections");
			ObjectInputStream is = new ObjectInputStream(fis);
			this.connections = (List<XbmcConnection>) is.readObject();
			fis.close();
		} catch (FileNotFoundException e) {
			// That's ok..probably the first time
		} catch (Exception e) {
			XbmcExceptionHandler.handleException(TAG, "Error while opening xbmc_connections - settingsfile", e);
		}
	}
	
	public void persist(){
		try {
			FileOutputStream fos = this.context.openFileOutput("xbmc_connections", Context.MODE_PRIVATE);
			ObjectOutputStream os = new ObjectOutputStream(fos);
			os.writeObject(this.connections);
			os.close();
		} catch (FileNotFoundException e) {
			/// That's ok..probably the first time
		} catch (Exception e) {
			XbmcExceptionHandler.handleException(TAG, "Error while saving xbmc_connections", e);
		}
	}
	
	public void deleteConnection(long connectionId){	
		XbmcConnection connectionToDelete = null;
		for(XbmcConnection connection : this.connections){
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
	
	public void add(XbmcConnection connection){
		if (connection == null){
			throw new IllegalArgumentException("connection is null");
		}
		
		Log.d(TAG, "New Connection added : " + connection.getConnectionName());
		connection.setId();
		this.connections.add(connection);
	}
	
	/**
	 * Gets all available connections
	 * @return List of Type <code>XbmcConnection</code>
	 */
	public List<XbmcConnection> getConnections(){
		return this.connections;
	}
}
