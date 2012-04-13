package ch.morefx.xbmc;

import java.io.Serializable;

import ch.morefx.xbmc.model.AudioLibrary;
import ch.morefx.xbmc.model.VideoLibrary;
import ch.morefx.xbmc.net.XbmcConnector;
import ch.morefx.xbmc.net.XbmcConnectorFactory;
import ch.morefx.xbmc.util.DrawableManager;


public class XbmcConnection implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private static final String JSONRPC_URL = "http://%s:%s/jsonrpc";
	private static final String THUMBNAIL_URL = "http://%s:%s/vfs/%s";
	
	public static final int DEFAULT_PORT = 8080;
	public static final long DEFAULT_NOID = -1;
	
	public static final String DEFAULT_USERNAME = "xbmc";
	
	
	private String username, password, host, connectionName;
	private int port;
	private long id;
	
	private AudioLibrary audioLibrary;
	private VideoLibrary videoLibrary;
	
	private XbmcConnector connector;
	
	public XbmcConnection() {
		setPort(DEFAULT_PORT);
		setUsername(DEFAULT_USERNAME);
		id = DEFAULT_NOID;
		this.audioLibrary = null;
		this.videoLibrary = null;
	}
	
	/**
	 * 
	 * @return
	 */
	public synchronized XbmcConnector getConnector(){
		if (connector == null){
			connector = XbmcConnectorFactory.create(this);
		}
		return connector;
	}
	
	private DrawableManager drawableManager;
	
	public synchronized DrawableManager getDrawableManager(){
		if (this.drawableManager == null){
			this.drawableManager = new DrawableManager(getConnector());
		}
		
		return this.drawableManager;
	}
	
	public void close(){
		this.drawableManager = null;
		this.audioLibrary = null;
	}
	
	/**
	 * Gets the AudioLibrary connected by this Connection.
	 * @return AudioLibrary
	 */
	public synchronized AudioLibrary getAudioLibrary(){
		if (this.audioLibrary == null){
			this.audioLibrary = new AudioLibrary(getConnector());
		}
		
		return this.audioLibrary;
	}
	
	/**
	 * Gets the VideoLibrary connected by this Connection.
	 * @return VideoLibrary
	 */
	public synchronized VideoLibrary getVideoLibrary(){
		if (this.videoLibrary == null){
			this.videoLibrary = new VideoLibrary(getConnector());
		}
		
		return this.videoLibrary;
	}
	
	public String getXbmcConnectionUri() {
		return String.format(JSONRPC_URL, getHost(), getPort());
	}
	
	public String formatThumbnailUrl(String thumbnail) {
		return String.format(THUMBNAIL_URL, getHost(), getPort(), thumbnail);
	}
	
	@Override
	public String toString() {
		return getConnectionName();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getConnectionName() {
		return connectionName;
	}

	public void setConnectionName(String connectionName) {
		this.connectionName = connectionName;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
		
	public boolean isNew(){
		return this.id == DEFAULT_NOID;
	}
	
	public long getId(){
		return this.id;
	}
	
	void initializeId(){
		this.id = System.currentTimeMillis();
	}
}
