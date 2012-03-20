package ch.morefx.xbmc.command;

import ch.morefx.xbmc.model.Album;
import ch.morefx.xbmc.model.FileSource;
import ch.morefx.xbmc.model.Movie;
import ch.morefx.xbmc.model.Playlist;

public class PlaylistAddCommand extends JsonCommand {

	private Playlist playlist;
	private int id;
	
	public PlaylistAddCommand(Movie movie) {
		super("Playlist.Add");
		
		this.playlist = Playlist.Video;
		this.id = movie.getMovieId();
	}
	
	public PlaylistAddCommand(Album album) {
		super("Playlist.Add");
		
		this.playlist = Playlist.Audio;
		this.id = album.getAlbumId();
	}
	
	public PlaylistAddCommand(FileSource filesource) {
		super("Playlist.Add");
		
		this.playlist = Playlist.Audio;
		this.id = filesource.getAlbumId();
	}
	
	@Override
	void prepareCommand(CommandBuilder builder) {
		
		builder.addParams("playlistid", this.playlist.getPlaylistId());
		CommandItemSet itemSet = new CommandItemSet();
		
		if ( this.playlist == Playlist.Audio)
			itemSet.add("albumid", this.id);
		if (this.playlist == Playlist.Video)
			itemSet.add("movieid", this.id);
		
		builder.addParams(itemSet);
	}
}
