package ch.morefx.xbmc.model;

import java.io.File;
import java.io.Serializable;

public class Song extends LibraryItem implements Serializable {

	private static final long serialVersionUID = 1L;

	private String title;
	private int albumid;
	private String track;
	private int position;
	private String thumbnail;
	private String file;
	private int artistid;
	
	private String artist;
	private String album;
	
	private Album relatedAlbum;
	
	
	public String getAlbumString() { return this.album; }
	public String getArtistString() { return this.artist; }
	
	
	public Song() {
		// TODO Auto-generated constructor stub
	}
	
	public Song(int position, Album relatedAlbum) {
		this.position = position;
		this.relatedAlbum = relatedAlbum;
	}
	
	public String getFilepath(){
		return this.file;
	}
	
	public String getFilename(){
		String filepath = getFilepath();
		if (filepath == null || filepath.equals(""))
			return "";
		
		return new File(getFilepath()).getName();
	}
	
	@Override
	public String getLabel() {
		return this.title;
	}
	
	public String getThumbnail(){
		return this.thumbnail;
	}
	
	public Album getAlbum(){
		return this.relatedAlbum;
	}
	
	public String getTrack(){
		return this.track;
	}
	
	public int getAlbumId(){
		return this.albumid;
	}
	
	public int getArtistId(){
		return this.artistid;
	}
	
	public int getPosition(){
		return this.position;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null) return false;
		if (this == o) return true;
		if (!(o instanceof Song))
			return false;
		
		Song song = (Song)o;
		return song.getAlbumId() == getAlbumId() &&
				song.getArtistId() == getArtistId() &&
				song.getPosition() == getPosition();
		
	}
	
	@Override
	public int hashCode() {
		return getAlbumId() ^ getArtistId() ^ getId();
	}
	

	
	public static String[] getSongFields() {
		
		// Siehe
		// /home/stef/dev/xbmc/xbmc/interfaces/json-rpc/ServiceDescription.h
		// Zeile 321 "Audio.Fields.Song"
		return new String[] { "title", "artist", "albumartist", "genre",
				"year", "rating", "album", "track", "duration", "comment",
				"lyrics", "musicbrainztrackid", "musicbrainzartistid",
				"musicbrainzalbumid", "musicbrainzalbumartistid", "playcount",
				"fanart", "thumbnail", "file", "artistid", "albumid"};
	}
}
