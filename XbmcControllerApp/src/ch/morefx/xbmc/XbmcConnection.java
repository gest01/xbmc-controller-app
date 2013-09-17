package ch.morefx.xbmc;

import java.io.Serializable;

import android.graphics.drawable.Drawable;
import ch.morefx.xbmc.model.ApplicationProperties;
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
	private static final String THUMBNAIL_URL = "http://%s:%s/vfs/%s";

	
	private DrawableManager drawableManager;
	private ApplicationProperties properties;
	private AudioLibrary audioLibrary;
	private VideoLibrary videoLibrary;
	private AudioPlayer audioplayer;
	private VideoPlayer videoplayer;
	private RemoteController remotecontrol;
	private XbmcConnector connector;
	
	private ConnectionDescriptor descriptor;
		
	/**
	 * Creates a new instance of the <code>XbmcConnection</code> class
	 * @param descriptor
	 */
	public XbmcConnection(ConnectionDescriptor descriptor) {
		this.descriptor = descriptor;
	}
	
	public ConnectionDescriptor getConnectionDescriptor(){
		return this.descriptor;
	}
		
	/**
	 * Initializes this connection with dynamic information 
	 * @param properties ApplicationProperties
	 */
	void initialize(ApplicationProperties properties){
		this.properties = properties;
	}
	
	/**
	 * Gets the Application Properties for this connection.
	 * @return An instance of the class<code>ApplicationProperties</code>
	 */
	public ApplicationProperties getProperties(){
		return this.properties;
	}
	
	/**
	 * Gets the underlying connector interface
	 * @return XbmcConnector
	 */
	public XbmcConnector getConnector(){
		if (connector == null){
			connector = XbmcConnectorFactory.create(this.descriptor);
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
		
		String thumbnailUrl = String.format(THUMBNAIL_URL, this.descriptor.getHost(), this.descriptor.getPort(), holder.getThumbnailUri());
		
		if (this.drawableManager == null){
			this.drawableManager = new DrawableManager(getConnector());
		}
		
		Drawable drawableThumbnail = this.drawableManager.fetchDrawableWithFallback(thumbnailUrl, fallback);
		holder.setThumbnail(drawableThumbnail);
	}
	
	/**
	 * Closes the current connection and releases all resources held by this connection.
	 */
	public void close(){
		this.drawableManager = null;
		this.audioLibrary = null;
		this.videoLibrary = null;
		this.audioplayer = null;
		this.videoplayer = null;
	}
	
	/**
	 * Gets the AudioLibrary connected by this Connection.
	 * @return AudioLibrary
	 */
	public AudioLibrary getAudioLibrary(){
		if (this.audioLibrary == null){
			this.audioLibrary = new AudioLibrary(getConnector());
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
			this.remotecontrol = new RemoteController();
		}
		
		return this.remotecontrol;
	}
	
	/**
	 * Gets the VideoLibrary connected by this Connection.
	 * @return VideoLibrary
	 */
	public VideoLibrary getVideoLibrary(){
		if (this.videoLibrary == null){
			this.videoLibrary = new VideoLibrary(getConnector());
		}
		
		return this.videoLibrary;
	}

}
