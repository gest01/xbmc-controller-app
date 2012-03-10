package ch.morefx.xbmc.activities.videolibrary;

import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import ch.morefx.xbmc.activities.XbmcListActivity;
import ch.morefx.xbmc.model.Movie;
import ch.morefx.xbmc.model.loaders.MovieLoader;
import ch.morefx.xbmc.model.loaders.PostExecuteHandler;

public class VideoActivity 
	extends XbmcListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		new MovieLoader(this)
		.setPostExecuteHandler(new PostExecuteHandler<List<Movie>>() {
			public void onPostExecute(List<Movie> result) {
				populateList(result);
			}})
		.execute();
	}
	
	private void populateList(List<Movie> result){
		if (result != null){
			setListAdapter(new MovieArrayAdapter(getApplication(), android.R.layout.simple_list_item_1, result));
		}
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Movie movie = (Movie)super.getListAdapter().getItem(position);
		getVideoLibrary().playMovie(movie);
	}
}
