package ch.morefx.xbmc.model;

import java.io.Serializable;

public class Artist extends LibraryItem 
	implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int artistid;
	
	@Override
	public int getId() {
		return getArtistId();
	}
	
	public int getArtistId(){
		return this.artistid;
	}
	
	@Override
	public String getLabel() {
		return label;
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