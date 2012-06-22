package ch.morefx.xbmc.model;

import java.util.List;

import ch.morefx.xbmc.model.players.AudioPlayer;
import ch.morefx.xbmc.net.commands.AudioLibraryGetAlbumsCommand;
import ch.morefx.xbmc.net.commands.AudioLibraryGetArtistsCommand;
import ch.morefx.xbmc.net.commands.AudioLibraryGetSongsCommand;
import ch.morefx.xbmc.net.commands.PlayerOpenCommandAdapter;
import ch.morefx.xbmc.net.commands.PlaylistAddCommand;
import ch.morefx.xbmc.net.commands.PlaylistClearCommand;
import ch.morefx.xbmc.util.Check;

/**
 *
 */
public final class AudioLibrary extends XbmcLibrary {

	
	private AudioPlayer audioplayer;

	/**
	 * 
	 * @param player
	 */
	public AudioLibrary(AudioPlayer player) {
		super(player);
		
		Check.argumentNotNull(player, "player");
		this.audioplayer = player;
	}
	
	
	/**
	 * Starts playing all albums and songs of a specific artist without adding into a new Playlist.
	 * @param artist The Artist to play.
	 */
	public void play(Artist artist){
		Check.argumentNotNull(artist, "artist");
		
		executeAsync(new PlayerOpenCommandAdapter(artist));
	}
	
	/**
	 * Starts playing all songs from a specific album without adding into a new Playlist.
	 * @param album The album to play.
	 */
	public void play(Album album){
		Check.argumentNotNull(album, "album");
		
		executeAsync(new PlayerOpenCommandAdapter(album));
	}

	/**
	 * Starts playing a specific song
	 * @param song The song to play
	 */
	public void play(Song song){
		Check.argumentNotNull(song, "song");
		executeAsync(new PlaylistClearCommand(Playlist.Audio),
					 new PlaylistAddCommand(song.getAlbum()),
					 new PlayerOpenCommandAdapter(song));
		
		this.audioplayer.setPlaying(song);
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
