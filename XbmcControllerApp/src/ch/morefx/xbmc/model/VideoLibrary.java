package ch.morefx.xbmc.model;

import java.util.List;

import ch.morefx.xbmc.XbmcConnection;
import ch.morefx.xbmc.command.GetMoviesCommand;
import ch.morefx.xbmc.command.PlayerOpenCommandAdapter;
import ch.morefx.xbmc.command.PlaylistAddCommand;
import ch.morefx.xbmc.command.PlaylistClearCommand;
import ch.morefx.xbmc.model.players.VideoPlayer;
import ch.morefx.xbmc.util.Check;

public class VideoLibrary extends XbmcLibrary {
	
	private VideoPlayer videoplayer;
	
	public VideoLibrary(XbmcConnection connection) {
		super(connection);
		
		this.videoplayer = new VideoPlayer();
	}
	
	
	/**
	 * 
	 * @return
	 */
	public VideoPlayer getPlayer(){
		return this.videoplayer;
	}
	
	/**
	 * Gets a list of all available movies from this Library.
	 * @return List of Movies.
	 */
	public List<Movie> getMovies(){
		
		GetMoviesCommand command = new GetMoviesCommand();
		execute(command);
		return command.getMovies();
	}

	/**
	 * Plays a specific movie
	 * @param movie Movie to start playing
	 */
	public void playMovie(Movie movie) {
		Check.argumentNotNull(movie, "movie");
		
		executeAsync(
				 new PlaylistClearCommand(Playlist.Video),
				 new PlaylistAddCommand(movie),
				 new PlayerOpenCommandAdapter(movie));
	}
}
