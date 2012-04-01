package ch.morefx.xbmc.command;

import java.util.Arrays;
import java.util.List;

import ch.morefx.xbmc.model.Movie;

/**
 * Implements a JSON-RPC command that queries all movies by using the VideoLibrary.GetMovies command.
 */
public class GetMoviesCommand extends JsonCommand
	implements JsonCommandResponseHandler {
	
	public GetMoviesCommand() {
		super("VideoLibrary.GetMovies");
	}

	@Override
	void prepareCommand(JsonCommandBuilder builder) {
		builder.addParams("properties", Movie.getMovieFields());
	}
	
	public void handleResponse(JsonCommandResponse response) {
		Movie[] moviesArray = response.asArrayResult("movies", Movie[].class);
		this.movies = Arrays.asList(moviesArray);
	}
	
	private List<Movie> movies;
	
	public List<Movie> getMovies(){
		return this.movies;
	}
	
}
