package ch.morefx.xbmc.model;

import java.io.Serializable;

import android.graphics.drawable.Drawable;

public class Artist extends LibraryItem 
	implements Serializable, ThumbnailHolder {

	private static final long serialVersionUID = 1L;
	
	private int artistid;
	private String thumbnail;
	private String description;
//	private String mood;
//	private String died;
//	private String instrument, style, genre;
//	private String fanart;
//	private String formed;
	
	
	
	@Override
	public int getId() {
		return getArtistId();
	}
	
	public int getArtistId(){
		return this.artistid;
	}
	
	public String getDescription(){
		return this.description;
	}
	
	@Override
	public String getLabel() {
		return label;
	}
	
    // Marked as transient due activity transition
    private transient Drawable drawableThumbnail;
	
	public Drawable getThumbnail() {
		return this.drawableThumbnail;
	}
	
	public int getThumbnailFallbackResourceId() {
		return ch.morefx.xbmc.R.drawable.cdicon;
	}
	
	public String getThumbnailUri() {
		return thumbnail;
	}
	
	public void setThumbnail(Drawable thumbail) {
		this.drawableThumbnail = thumbail;
		
	}
	

	public static String[] getArtistFields() {
		
		// See
		// /xbmc/xbmc/interfaces/json-rpc/ServiceDescription.h
		// Line 302 "Audio.Fields.Artist"
		return new String[] {
				"instrument", "style", "mood", "born", "formed",
		        "description", "genre", "died", "disbanded",
		        "yearsactive", "musicbrainzartistid", "fanart",
		        "thumbnail"  
        };
	}
}