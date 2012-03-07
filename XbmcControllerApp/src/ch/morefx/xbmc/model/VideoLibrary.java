package ch.morefx.xbmc.model;

import java.util.List;

import ch.morefx.xbmc.XbmcConnection;
import ch.morefx.xbmc.command.GetMoviesCommand;

public class VideoLibrary extends XbmcLibrary {
	
	public VideoLibrary(XbmcConnection connection) {
		super(connection);
	}
	
	public List<Movie> getMovies(){
		GetMoviesCommand command = new GetMoviesCommand();
		execute(command);
		return command.getMovies();
	}
}
