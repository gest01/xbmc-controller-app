package ch.morefx.xbmc.model;

import java.util.List;

import ch.morefx.xbmc.net.XbmcConnector;
import ch.morefx.xbmc.net.commands.AudioLibraryGetAlbumsCommand;
import ch.morefx.xbmc.net.commands.AudioLibraryGetArtistsCommand;
import ch.morefx.xbmc.net.commands.AudioLibraryGetSongsCommand;
import ch.morefx.xbmc.util.Check;

/**
 *
 */
public final class AudioLibrary extends XbmcLibrary {

	/**
	 * 
	 * @param connector
	 */
	public AudioLibrary(XbmcConnector connector) {
		super(connector);
	}


	
	/**
	 * 
	 * @return
	 */
	public List<Artist> getArtists(){
		AudioLibraryGetArtistsCommand command = new AudioLibraryGetArtistsCommand();
		execute(command);
		return command.getArtists();
	}
	
	/**
	 * 
	 * @param artist
	 * @return
	 */
	public List<Album> getAlbums(Artist artist){
		Check.argumentNotNull(artist, "artist");
		
		AudioLibraryGetAlbumsCommand command = new AudioLibraryGetAlbumsCommand(artist);
		execute(command);
		return command.getAlbums();
	}
	
	/**
	 * 
	 * @param album
	 * @return
	 */
	public List<Song> getSongs(Album album){
		Check.argumentNotNull(album, "album");
		
		AudioLibraryGetSongsCommand command = new AudioLibraryGetSongsCommand(album);
		execute(command);
		return command.getSongs();
	}
}
