package ch.morefx.xbmc.model;

import java.util.List;

import ch.morefx.xbmc.model.players.VideoPlayer;
import ch.morefx.xbmc.net.XbmcConnector;
import ch.morefx.xbmc.net.commands.GetMoviesCommand;
import ch.morefx.xbmc.net.commands.PlayerOpenCommandAdapter;
import ch.morefx.xbmc.net.commands.PlaylistAddCommand;
import ch.morefx.xbmc.net.commands.PlaylistClearCommand;
import ch.morefx.xbmc.util.Check;

public class VideoLibrary extends XbmcLibrary {
	
	private VideoPlayer videoplayer;
	
	public VideoLibrary(XbmcConnector connector) {
		super(connector);
		
		this.videoplayer = new VideoPlayer(connector);
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
