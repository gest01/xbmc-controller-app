package ch.morefx.xbmc.model;

import java.util.List;

import ch.morefx.xbmc.XbmcConnection;
import ch.morefx.xbmc.command.GetAlbumsCommand;
import ch.morefx.xbmc.command.GetArtistsCommand;
import ch.morefx.xbmc.command.GetSongsCommand;
import ch.morefx.xbmc.command.PlayerOpenCommandAdapter;
import ch.morefx.xbmc.command.PlaylistAddCommand;
import ch.morefx.xbmc.command.PlaylistClearCommand;
import ch.morefx.xbmc.model.players.AudioPlayer;
import ch.morefx.xbmc.util.Check;

public final class AudioLibrary extends XbmcLibrary {

	
	private AudioPlayer audioplayer;
	
	public AudioLibrary(XbmcConnection connection) {
		super(connection);
		
		this.audioplayer = new AudioPlayer();
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
		GetArtistsCommand command = new GetArtistsCommand();
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
		
		GetAlbumsCommand command = new GetAlbumsCommand(artist);
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
		
		GetSongsCommand command = new GetSongsCommand(album);
		execute(command);
		return command.getSongs();
	}
}
