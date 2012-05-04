package ch.morefx.xbmc;

import java.io.Serializable;

import android.graphics.drawable.Drawable;
import ch.morefx.xbmc.model.AudioLibrary;
import ch.morefx.xbmc.model.ThumbnailHolder;
import ch.morefx.xbmc.model.VideoLibrary;
import ch.morefx.xbmc.model.players.AudioPlayer;
import ch.morefx.xbmc.model.players.VideoPlayer;
import ch.morefx.xbmc.model.remotecontrol.RemoteController;
import ch.morefx.xbmc.net.XbmcConnector;
import ch.morefx.xbmc.net.XbmcConnectorFactory;
import ch.morefx.xbmc.util.Check;
import ch.morefx.xbmc.util.DrawableManager;


public class XbmcConnection implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private static final String JSONRPC_URL = "http://%s:%s/jsonrpc";
	private static final String THUMBNAIL_URL = "http://%s:%s/vfs/%s";
	
	public static final int DEFAULT_PORT = 8080;
	public static final int DEFAULT_JSON_RPC_PORT = 9090;
	public static final long DEFAULT_NOID = -1;
	
	public static final String DEFAULT_USERNAME = "xbmc";
	
	
	private String username, password, host, connectionName;
	private int port, jsonTcpPort;
	private long id;
	
	private DrawableManager drawableManager;
	
	private AudioLibrary audioLibrary;
	private VideoLibrary videoLibrary;
	
	private AudioPlayer audioplayer;
	private VideoPlayer videoplayer;
	
	private RemoteController remotecontrol;
	
	private XbmcConnector connector;
	
	public XbmcConnection() {
		setPort(DEFAULT_PORT);
		setJsonTcpPort(DEFAULT_JSON_RPC_PORT);
		setUsername(DEFAULT_USERNAME);
		id = DEFAULT_NOID;
		this.audioLibrary = null;
		this.videoLibrary = null;
	}
	
	/**
	 * 
	 * @return
	 */
	public XbmcConnector getConnector(){
		if (connector == null){
			connector = XbmcConnectorFactory.create(this);
		}
		return connector;
	}

	/**
	 * Loads a Drawable into an instance of an object that implements the ThumbnailHolder interface.
	 * @param holder The ThumbnailHolder
	 * @param resourceprovider A Source that provides access to the resource system.
	 */
	public void loadThumbnail(ThumbnailHolder holder, ResourceProvider resourceprovider){
		Check.notNull(resourceprovider, "resourceprovider");
		Check.notNull(holder, "holder");
		
		if (holder.getThumbnail() != null)
			return;
		
		Drawable fallback = resourceprovider.getDrawable(holder.getThumbnailFallbackResourceId());
		
		String thumbnailUrl = String.format(THUMBNAIL_URL, getHost(), getPort(), holder.getThumbnailUri());
		
		if (this.drawableManager == null){
			this.drawableManager = new DrawableManager(getConnector());
		}
		
		Drawable drawableThumbnail = this.drawableManager.fetchDrawableWithFallback(thumbnailUrl, fallback);
		holder.setThumbnail(drawableThumbnail);
	}
	
	public void close(){
		this.drawableManager = null;
		this.audioLibrary = null;
	}
	
	/**
	 * Gets the AudioLibrary connected by this Connection.
	 * @return AudioLibrary
	 */
	public AudioLibrary getAudioLibrary(){
		if (this.audioLibrary == null){
			this.audioLibrary = new AudioLibrary(getAudioPlayer());
		}
		
		return this.audioLibrary;
	}
	
	/**
	 * 
	 * @return
	 */
	public AudioPlayer getAudioPlayer(){
		if (this.audioplayer == null){
			this.audioplayer = new AudioPlayer(getConnector());
		}
		return this.audioplayer;
	}
	
	/**
	 * 
	 * @return
	 */
	public VideoPlayer getVideoPlayer(){
		if (this.videoplayer == null){
			this.videoplayer = new VideoPlayer(getConnector());
		}
		return this.videoplayer;
	}
	
	
	/**
	 * Gets the Remote Control Object for this xbmc connection
	 * @return RemoteController
	 */
	public RemoteController getRemoteControl(){
		if (this.remotecontrol == null){
			this.remotecontrol = new RemoteController(getConnector());
		}
		
		return this.remotecontrol;
	}
	
	/**
	 * Gets the VideoLibrary connected by this Connection.
	 * @return VideoLibrary
	 */
	public VideoLibrary getVideoLibrary(){
		if (this.videoLibrary == null){
			this.videoLibrary = new VideoLibrary(getVideoPlayer());
		}
		
		return this.videoLibrary;
	}
	
	public String getXbmcConnectionUri() {
		return String.format(JSONRPC_URL, getHost(), getPort());
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
		
	public void setJsonTcpPort(int jsonTcpPort){
		this.jsonTcpPort = jsonTcpPort;
	}
	
	public int getJsonTcpPort(){
		return DEFAULT_JSON_RPC_PORT;
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
