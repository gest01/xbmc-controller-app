package ch.morefx.xbmc.model;

import java.io.Serializable;

import android.graphics.drawable.Drawable;

public class Album extends LibraryItem
	implements Serializable, ThumbnailHolder{

	private static final long serialVersionUID = 1L;

	private String title;
    private String thumbnail;
    private int albumid;
    private Artist relatedArtist;
     
    
    public Album(Artist artist) {
    	this.relatedArtist = artist;
	}
    
    public Artist getArtist(){
    	return this.relatedArtist;
    }
    
    public int getAlbumId(){
    	return this.albumid;
    }
    
    public int getThumbnailFallbackResourceId() {
    	return ch.morefx.xbmc.R.drawable.cdicon;
    }
    
    public String getThumbnailUri() {
    	return this.thumbnail;
    }
    
    public void setThumbnail(Drawable drawable) {
    	this.drawableThumbnail = drawable;
    }
    
    // Marked as transient due activity transition
    private transient Drawable drawableThumbnail;
    
    public Drawable getThumbnail(){
    	return this.drawableThumbnail;
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
		// xbmc/xbmc/interfaces/json-rpc/ServiceDescription.h
		// line 311 "Audio.Fields.Album"
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
