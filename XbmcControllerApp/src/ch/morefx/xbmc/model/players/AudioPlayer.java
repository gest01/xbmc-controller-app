package ch.morefx.xbmc.model.players;

import ch.morefx.xbmc.model.Album;
import ch.morefx.xbmc.model.Artist;
import ch.morefx.xbmc.model.FileSource;
import ch.morefx.xbmc.model.Playlist;
import ch.morefx.xbmc.model.Song;
import ch.morefx.xbmc.net.XbmcConnector;
import ch.morefx.xbmc.net.commands.PlayerOpenCommandAdapter;
import ch.morefx.xbmc.net.commands.PlaylistAddCommand;
import ch.morefx.xbmc.net.commands.PlaylistClearCommand;
import ch.morefx.xbmc.util.Check;

public class AudioPlayer extends MediaPlayer {
	
	private Song playingSong;
	
	public AudioPlayer(XbmcConnector connector) {
		super(connector);
	}
	
	@Override
	public void disable() {
		super.disable();
		playingSong = null;
	}
	
	public void setPlaying(Song song){
		playingSong = song;
		setSuspended(false);
	}
	
	public boolean isPlaying(Artist artist){
		Check.argumentNotNull(artist, "artist");
		
		if(playingSong != null && isActive()){
			return playingSong.getArtistId() == artist.getArtistId();
		}
		
		return false;
	}
	
	public boolean isPlaying(Album album){
		Check.argumentNotNull(album, "album");
		
		if(playingSong != null && isActive()){
			return playingSong.getAlbumId() == album.getAlbumId();
		}
		
		return false;
	}

	public boolean isPlaying(Song song){
		Check.argumentNotNull(song, "song");
		
		if(playingSong != null && isActive()){
			return playingSong.equals(song);
		}
		
		return false;
	}
	
	public Song getCurrentSong(){
		return this.playingSong;
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
		
		executeAsync(new PlayerOpenCommandAdapter(song));
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
	public void addToPlaylist(Song song){
		Check.argumentNotNull(song, "song");
		
		executeAsync(new PlaylistAddCommand(song));
	}
	
	/**
	 * 
	 * @param album
	 */
	public void addToPlaylist(Album album){
		Check.argumentNotNull(album, "album");
		
		executeAsync(new PlaylistAddCommand(album));
	}
	
	/**
	 * 
	 * @param artist
	 */
	public void addToPlaylist(Artist artist){
		Check.argumentNotNull(artist, "artist");
		
		executeAsync(new PlaylistAddCommand(artist));
	}
	
}
