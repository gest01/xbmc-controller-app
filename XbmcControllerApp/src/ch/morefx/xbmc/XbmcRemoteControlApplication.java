package ch.morefx.xbmc;

import android.app.Application;

public class XbmcRemoteControlApplication extends Application {
	
	private XbmcConnectionManager connectionManager;
	private XbmcConnection currentConnection;
	
	public void onCreate() {
		super.onCreate();

		Thread.setDefaultUncaughtExceptionHandler(new XbmcExceptionHandler());
	}
	

	/**
	 * Gets the current active connection or null when no connection was selected
	 * @return XbmcConnection or null 
	 */
	public XbmcConnection getCurrentConnection(){
		return this.currentConnection;
	}
	
	/**
	 * Sets the current active connection.
	 * @param connection
	 */
	public void setCurrentConnection(XbmcConnection connection){
		if (this.currentConnection != null){
			this.currentConnection.close();
			this.currentConnection = null;
		}
		
		this.currentConnection = connection;
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
