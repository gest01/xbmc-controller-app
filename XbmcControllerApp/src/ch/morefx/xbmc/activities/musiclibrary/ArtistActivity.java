package ch.morefx.xbmc.activities.musiclibrary;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import ch.morefx.xbmc.activities.XbmcListActivity;
import ch.morefx.xbmc.model.Artist;
import ch.morefx.xbmc.model.loaders.ArtistLoader;

public class ArtistActivity 
	extends XbmcListActivity {

	private ArtistArrayAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.adapter = new ArtistArrayAdapter(this, android.R.layout.simple_list_item_1);
		setListAdapter(adapter);
		
		registerForContextMenu(getListView());
		
		new ArtistLoader(adapter).execute();
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
	    menu.setHeaderTitle("My cool context menu ;-)");
	    menu.add("Hello World");
	    menu.add(android.view.Menu.NONE, 666, android.view.Menu.NONE, "FUCKER");
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo i = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
		Artist artist = this.adapter.getItem(i.position);
		getAudioLibrary().play(artist);
		return true;
	}
	
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Artist artist = (Artist)super.getListAdapter().getItem(position);
		Intent intent = new Intent(this, AlbumActivity.class);
		intent.putExtra(AlbumActivity.EXTRA_ARTIST, artist);
		startActivity(intent);
	}
}
