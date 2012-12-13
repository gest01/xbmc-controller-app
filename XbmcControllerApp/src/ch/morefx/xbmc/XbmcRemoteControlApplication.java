package ch.morefx.xbmc;

import android.app.Application;
import android.util.Log;

public class XbmcRemoteControlApplication extends Application {
	
	private static final String TAG = XbmcRemoteControlApplication.class.getName();
	
	private XbmcConnectionManager connectionManager;
	private XbmcConnection currentConnection;
	
	private static XbmcRemoteControlApplication _instance;
	
	public void onCreate() {
		super.onCreate();

		_instance = this;
		
		Thread.setDefaultUncaughtExceptionHandler(new XbmcExceptionHandler());
	}
	
	/**
	 * Gets the application
	 * @return XbmcRemoteControlApplication
	 */
	public static XbmcRemoteControlApplication getInstance() { return _instance; }
	
	/**
	 * Gets the current active connection or null when no connection was selected
	 * @return XbmcConnection or null 
	 */
	public XbmcConnection getCurrentConnection(){
		return this.currentConnection;
	}
	
	/**
	 * Initializes a new connection using the bootstraper and sets the connection as active.
	 * @param descriptor The connection to be set up.
	 */
	public void setupConnection(ConnectionDescriptor descriptor){
		if (this.currentConnection != null){
			this.currentConnection.close();
			this.currentConnection = null;
		}
		
		// Create connection
		XbmcConnection connection = new XbmcConnection(descriptor);
		
    	Bootstrapper bootstrapper = new Bootstrapper();
    	bootstrapper.settingUpConnection(connection);
		
		this.currentConnection = connection;
		
		Log.d(TAG, "Current XbmcConnection : " + connection.toString() + "(" + descriptor.getConnectionUri() + ")");
	}
	
	/**
	 * Closes the current connection.
	 */
	public void closeCurrentConnection(){
		if (this.currentConnection != null){
			this.currentConnection.close();
			this.currentConnection = null;
			
			Log.i(TAG, "Connection closed!");
		}
	}
		
	/**
	 * Get the connection manager
	 * @return
	 */
	public synchronized XbmcConnectionManager getConnectionManager(){
		if (this.connectionManager == null){
			this.connectionManager = new XbmcConnectionManager(this);
		}
		
		return this.connectionManager;
	}
}
