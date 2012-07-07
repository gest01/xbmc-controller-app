package ch.morefx.xbmc.activities.musiclibrary;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ListView;
import ch.morefx.xbmc.activities.ContextMenuAddToPlaylistActionCommand;
import ch.morefx.xbmc.activities.ContextMenuAdapter;
import ch.morefx.xbmc.activities.ContextMenuPlayItemActionCommand;
import ch.morefx.xbmc.activities.ShowArtistInfoActionCommand;
import ch.morefx.xbmc.activities.XbmcListActivity;
import ch.morefx.xbmc.model.Album;
import ch.morefx.xbmc.model.Artist;
import ch.morefx.xbmc.model.loaders.AlbumLoader;
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
		
		registerForContextMenu();
		
		onPlayerUpdate();
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		ContextMenuAdapter cmadapter = new ContextMenuAdapter(menu, this);
		cmadapter.add("Show album info", new ShowArtistInfoActionCommand());
		cmadapter.add("Play all songs", new ContextMenuPlayItemActionCommand());
		cmadapter.add("Add to playlist", new ContextMenuAddToPlaylistActionCommand());
	}
	
	@Override
	public void onPlayerUpdate() {
		new AlbumLoader(adapter).execute(artist);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Album album = this.adapter.getItem(position);
		Intent intent = new Intent(this, SongActivity.class);
		intent.putExtra(SongActivity.EXTRA_ALBUM, album);
		startActivity(intent);
	}
}
