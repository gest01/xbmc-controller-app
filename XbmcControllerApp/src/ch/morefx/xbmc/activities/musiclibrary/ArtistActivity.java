package ch.morefx.xbmc.activities.musiclibrary;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import ch.morefx.xbmc.activities.XbmcListActivity;
import ch.morefx.xbmc.model.Artist;
import ch.morefx.xbmc.model.loaders.ArtistLoader;
import ch.morefx.xbmc.model.loaders.PostExecuteHandler;

public class ArtistActivity 
	extends XbmcListActivity {

	private ArtistArrayAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getListView().setTextFilterEnabled(true);
		
		this.adapter = new ArtistArrayAdapter(this, android.R.layout.simple_list_item_1);
		setListAdapter(adapter);
		
		new ArtistLoader(this)
			.setPostExecuteHandler(new PostExecuteHandler<List<Artist>>() {
				public void onPostExecute(List<Artist> result) {
					populateList(result);
				}})
			.execute();
	}
	
	private void populateList(List<Artist> result){
		if (result != null){
			this.adapter.addAll(result);
		}
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Artist artist = (Artist)super.getListAdapter().getItem(position);
		Intent intent = new Intent(this, AlbumActivity.class);
		intent.putExtra(AlbumActivity.EXTRA_ARTIST, artist);
		startActivity(intent);
	}
}
