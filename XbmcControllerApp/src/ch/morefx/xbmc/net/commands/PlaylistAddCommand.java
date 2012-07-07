package ch.morefx.xbmc.net.commands;

import ch.morefx.xbmc.model.Album;
import ch.morefx.xbmc.model.Artist;
import ch.morefx.xbmc.model.FileSource;
import ch.morefx.xbmc.model.Movie;
import ch.morefx.xbmc.model.Playlist;
import ch.morefx.xbmc.model.Song;

public class PlaylistAddCommand extends JsonCommand {

	private Playlist playlist;
	
	private CommandItemSet itemSet = new CommandItemSet();
	
	public PlaylistAddCommand(Movie movie) {
		super("Playlist.Add");
		
		this.playlist = Playlist.Video;
		itemSet.add("movieid", movie.getMovieId());
	}
	
	public PlaylistAddCommand(Album album) {
		super("Playlist.Add");
		
		this.playlist = Playlist.Audio;
		itemSet.add("albumid", album.getAlbumId());
	}
	
	public PlaylistAddCommand(Artist artist) {
		super("Playlist.Add");
		
		this.playlist = Playlist.Audio;
		itemSet.add("artistid", artist.getArtistId());
	}
	
	public PlaylistAddCommand(Song song) {
		super("Playlist.Add");
		
		this.playlist = Playlist.Audio;
		itemSet.add("songid", song.getSongId());
	}
	
	public PlaylistAddCommand(FileSource filesource) {
		super("Playlist.Add");
		
		this.playlist = Playlist.Audio;
		itemSet.add("albumid", filesource.getAlbumId());
	}
	
	@Override
	void prepareCommand(JsonCommandBuilder builder) {
		builder.addParams("playlistid", this.playlist.getPlaylistId());
		builder.addParams(itemSet);
	}
}
