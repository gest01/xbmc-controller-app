package ch.morefx.xbmc.model;

import java.util.List;

import ch.morefx.xbmc.model.players.AudioPlayer;
import ch.morefx.xbmc.net.XbmcConnector;
import ch.morefx.xbmc.net.commands.AudioLibraryGetAlbumsCommand;
import ch.morefx.xbmc.net.commands.AudioLibraryGetArtistsCommand;
import ch.morefx.xbmc.net.commands.AudioLibraryGetSongsCommand;
import ch.morefx.xbmc.net.commands.PlayerOpenCommandAdapter;
import ch.morefx.xbmc.net.commands.PlaylistAddCommand;
import ch.morefx.xbmc.net.commands.PlaylistClearCommand;
import ch.morefx.xbmc.util.Check;

public final class AudioLibrary extends XbmcLibrary {

	
	private AudioPlayer audioplayer;
	
	public AudioLibrary(XbmcConnector connector) {
		super(connector);
		
		this.audioplayer = new AudioPlayer(connector);
	}
	
	
	/**
	 * 
	 * @return
	 */
	public AudioPlayer getPlayer(){
		return this.audioplayer;
	}
	
	/**
	 * 
	 * @param filesource
	 */
	public void playSong(FileSource filesource) {
		Check.argumentNotNull(filesource, "filesource");
		
		executeAsync(new PlaylistClearCommand(Playlist.Audio),
				     new PlaylistAddCommand(filesource),
				     new PlayerOpenCommandAdapter(filesource));
	}
	
	/**
	 * 
	 * @param song
	 */
	public void playSong(Song song){
		Check.argumentNotNull(song, "song");
		executeAsync(new PlaylistClearCommand(Playlist.Audio),
					 new PlaylistAddCommand(song.getAlbum()),
					 new PlayerOpenCommandAdapter(song));
		
		getPlayer().setPlaying(song);
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
