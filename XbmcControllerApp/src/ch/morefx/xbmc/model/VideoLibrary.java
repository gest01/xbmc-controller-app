package ch.morefx.xbmc.model;

import java.util.List;

import ch.morefx.xbmc.net.XbmcConnector;
import ch.morefx.xbmc.net.commands.GetMoviesCommand;

public class VideoLibrary extends XbmcLibrary {
	public VideoLibrary(XbmcConnector connector) {
		super(connector);
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
}
