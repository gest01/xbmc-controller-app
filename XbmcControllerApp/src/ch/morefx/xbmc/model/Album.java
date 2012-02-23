package ch.morefx.xbmc.model;

import java.io.Serializable;

import android.graphics.drawable.Drawable;
import ch.morefx.xbmc.XbmcConnection;

public class Album extends LibraryItem
	implements Serializable{

	private static final long serialVersionUID = 1L;

	private String title;
    private String thumbnail;
    private int albumid;
    private Artist relatedArtist;
    
    public Album( Artist artist) {
    	this.relatedArtist = artist;
	}
    
    public Artist getArtist(){
    	return this.relatedArtist;
    }
    
    public int getAlbumId(){
    	return this.albumid;
    }
    
    // Marked as transient due activity transition
    private transient Drawable drawableThumbnail;
    
    public Drawable getThumbnail(){
    	return this.drawableThumbnail;
    }

	public void loadThumbnail(XbmcConnection connection, Drawable defaultThumbnail){
		String thumbnailUrl = connection.getThumbnailUrl(this.thumbnail);
		this.drawableThumbnail = connection.getDrawableManager().fetchDrawableWithFallback(thumbnailUrl, defaultThumbnail);
	}
    
    @Override
    public int getId() {
    	return getAlbumId();
    }
    
    @Override
    public String getLabel() {
    	return this.title;
    }

	
    public static String[] getAlbumFields()
    {
		// Siehe
		// /home/stef/dev/xbmc/xbmc/interfaces/json-rpc/ServiceDescription.h
		// Zeile 311 "Audio.Fields.Album"
            return new  String[]{
            		"title",
		            "description",
		            "artist",
		            "genre",
		            "theme",
		            "mood",
		            "style",
		            "type",
		            "albumlabel",
		            "rating",
		            "year",
		            "musicbrainzalbumid",
		            "musicbrainzalbumartistid",
		            "fanart",
		            "thumbnail",
		            "artistid"
            };
    }
}
