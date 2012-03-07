package ch.morefx.xbmc.model.loaders;

import java.util.List;

import android.content.Context;

import ch.morefx.xbmc.model.Movie;

public class MovieLoader extends AsyncTaskLoader<Void, Void, List<Movie>>{
	
	public MovieLoader(Context context) {
		super(context);
	}

	@Override
	protected List<Movie> doInBackground(Void... params) {
		return getVideoLibrary().getMovies();
	}
}
