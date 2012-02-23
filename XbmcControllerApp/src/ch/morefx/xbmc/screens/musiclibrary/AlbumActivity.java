package ch.morefx.xbmc.screens.musiclibrary;

import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import ch.morefx.xbmc.model.Album;
import ch.morefx.xbmc.model.Artist;
import ch.morefx.xbmc.model.loaders.AlbumLoader;
import ch.morefx.xbmc.model.loaders.PostExecuteHandler;
import ch.morefx.xbmc.util.Check;

public class AlbumActivity 
	extends ListActivity {
	
	public static final String EXTRA_ARTIST = "EXTRA_ARTIST";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getListView().setTextFilterEnabled(true);
		
		Artist artist = (Artist)getIntent().getExtras().getSerializable(EXTRA_ARTIST);
		Check.notNull(artist, "Artist object missing from Intent extras");
		
		setTitle(artist.getLabel());
		
		
		new AlbumLoader(this)
		 .setPostExecuteHandler(new PostExecuteHandler<List<Album>>() {
			public void onPostExecute(List<Album> result) {
				populateList(result);
			}})
		.execute(artist);
		
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
	}
	
	private void populateList(List<Album> result){
		if (result != null){
			setListAdapter(new AlbumArrayAdapter(getApplication(), android.R.layout.simple_list_item_1, result));
		}
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Album album = (Album)getListAdapter().getItem(position);
		Intent intent = new Intent(this, SongActivity.class);
		intent.putExtra(SongActivity.EXTRA_ALBUM, album);
		startActivity(intent);
	}
}
