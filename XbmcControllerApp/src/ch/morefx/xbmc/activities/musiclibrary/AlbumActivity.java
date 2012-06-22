package ch.morefx.xbmc.activities.musiclibrary;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import ch.morefx.xbmc.activities.XbmcListActivity;
import ch.morefx.xbmc.model.Album;
import ch.morefx.xbmc.model.Artist;
import ch.morefx.xbmc.model.loaders.AlbumLoader;
import ch.morefx.xbmc.model.loaders.PostExecuteHandler;
import ch.morefx.xbmc.util.Check;

public class AlbumActivity 
	extends XbmcListActivity {
	
	public static final String EXTRA_ARTIST = "EXTRA_ARTIST";
	
	private AlbumArrayAdapter adapter;
	private Artist artist;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		artist = (Artist)getIntent().getExtras().getSerializable(EXTRA_ARTIST);
		Check.notNull(artist, "Artist object missing from Intent extras");
		
		setTitle(artist.getLabel());
		
		this.adapter = new AlbumArrayAdapter(this, android.R.layout.simple_list_item_1);
		setListAdapter(adapter);		
		
		loadAlbums();
	}
	
	@Override
	public void onPlayerUpdate() {
		loadAlbums();
	}

	private void loadAlbums(){
		new AlbumLoader(this)
		 .setPostExecuteHandler(new PostExecuteHandler<List<Album>>() {
			public void onPostExecute(List<Album> result) {
				populateList(result);
			}})
		.execute(artist);
	}
	
	private void populateList(List<Album> result){
		this.adapter.clear();
		if (result != null){
			this.adapter.addAll(result);
		}
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Album album = this.adapter.getItem(position);
		Intent intent = new Intent(this, SongActivity.class);
		intent.putExtra(SongActivity.EXTRA_ALBUM, album);
		startActivity(intent);
	}
}
