package ch.morefx.xbmc.model;

import java.io.Serializable;

public class FileSource 
	implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String file;
	private String filetype;
	private String label;
	
	private int albumid, artistid;
	
	private String mediaType;
	private FileSource parent;
	
	public FileSource(String mediaType, FileSource parent) {
		this.mediaType = mediaType;
		this.parent = parent;
	}
	
	/**
	 * Gets TRUE when this FileSource instance has a parent relation.
	 * @return True / False
	 */
	public boolean hasParent(){
		return this.parent != null;
	}
	
	/**
	 * Gets the parent FileSource of this FileSource instance
	 * @return Parent or NULL when no parent is defined.
	 */
	public FileSource getParent(){
		return this.parent;
	}
	
	
	/**
	 * Gets the mediatype (files, video, music or pictures) which this filesource is representing.
	 * Possible media types see CFileOperations::GetDirectory
	 * http://forum.xbmc.org/showthread.php?t=124667
	 * @return the media type
	 */
	public String getMediaType(){
		if (this.mediaType == null || this.mediaType.length() == 0)
			this.mediaType = "files"; // this is the default when nothing else is set
		return this.mediaType;
	}
	
	public int getAlbumId(){
		return this.albumid;
	}
	
	public int getArtistId(){
		return this.artistid;
	}
	
	
	@Override
	public String toString() {
		return String.format("%s (%s)", getLabel(), getFile());
	}
	
	public String getLabel(){
		return this.label;
	}
	
	public String getFile(){
		return this.file;
	}
	
	public String getFileType(){
		return this.filetype;
	}
	
	public boolean isAddonSource(){
		return getFile().startsWith("addons://");
	}
	
	public boolean isDirectory(){
		return filetype.equalsIgnoreCase("directory");
	}
	
	public boolean isFile(){
		return filetype.equalsIgnoreCase("file");
	}
	
	public static String[] getFields(){
		return new String[] {
			"title", "artist", "albumartist", "genre", "year", "rating",
			"album", "track", "duration", "comment", "lyrics", "musicbrainztrackid",
			"musicbrainzartistid", "musicbrainzalbumid", "musicbrainzalbumartistid",
			"playcount", "fanart", "director", "trailer", "tagline", "plot",
			"plotoutline", "originaltitle", "lastplayed", "writer", "studio",
			"mpaa", "cast", "country", "imdbnumber", "premiered", "productioncode",
			"runtime", "set", "showlink", "streamdetails", "top250", "votes",
			"firstaired", "season", "episode", "showtitle", "thumbnail", "file",
			"resume", "artistid", "albumid", "tvshowid", "setid"
		};
	}	
}