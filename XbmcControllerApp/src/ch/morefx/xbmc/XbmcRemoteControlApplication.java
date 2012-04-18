package ch.morefx.xbmc;

import android.app.Application;
import android.graphics.drawable.Drawable;
import android.util.Log;
import ch.morefx.xbmc.model.ThumbnailHolder;

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
	 * Gets a flag that indicates whether a connection is established or not.
	 * @return True when connected, false otherwise
	 */
	public boolean isConnected(){
		return getCurrentConnection() != null;
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
		
		Log.d("XbmcRemoteControlApplication", "Current XbmcConnection : " + connection.toString() + "(" + connection.getXbmcConnectionUri() + ")");
	}
	
	
	/**
	 * Loads a thumbnail into a ThumbnailHolder instance
	 * @param holder ThumbnailHolder to load.
	 */
	public void loadThumbnail(ThumbnailHolder holder){
		if (holder.getThumbnail() != null)
			return;
		
		XbmcConnection connection = getCurrentConnection();
		Drawable fallback = getResources().getDrawable(holder.getThumbnailFallbackResourceId());
		
		String thumbnailUrl = connection.formatThumbnailUrl(holder.getThumbnailUri());
		Drawable drawableThumbnail = connection.getDrawableManager().fetchDrawableWithFallback(thumbnailUrl, fallback);
		holder.setThumbnail(drawableThumbnail);
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
