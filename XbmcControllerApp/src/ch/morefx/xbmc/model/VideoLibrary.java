package ch.morefx.xbmc.model;

import java.util.List;

import ch.morefx.xbmc.model.players.MediaPlayer;
import ch.morefx.xbmc.net.commands.GetMoviesCommand;
import ch.morefx.xbmc.net.commands.PlayerOpenCommandAdapter;
import ch.morefx.xbmc.net.commands.PlaylistAddCommand;
import ch.morefx.xbmc.net.commands.PlaylistClearCommand;
import ch.morefx.xbmc.util.Check;

public class VideoLibrary extends XbmcLibrary {
	public VideoLibrary(MediaPlayer mediaplayer) {
		super(mediaplayer);
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
