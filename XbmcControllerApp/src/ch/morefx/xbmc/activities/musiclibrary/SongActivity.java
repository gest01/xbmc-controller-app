package ch.morefx.xbmc.activities.musiclibrary;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import ch.morefx.xbmc.activities.ContextMenuAdapter;
import ch.morefx.xbmc.activities.ContextMenuAddToPlaylistActionCommand;
import ch.morefx.xbmc.activities.ContextMenuPlayItemActionCommand;
import ch.morefx.xbmc.activities.XbmcListActivity;
import ch.morefx.xbmc.model.Album;
import ch.morefx.xbmc.model.Song;
import ch.morefx.xbmc.model.loaders.SongLoader;
import ch.morefx.xbmc.model.players.AudioPlayer;
import ch.morefx.xbmc.util.ExtrasHelper;

public final class SongActivity 
	extends XbmcListActivity {

	public static final String EXTRA_ALBUM = "EXTRA_ALBUM";
	
	private Album album;
	private SongArrayAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		album = ExtrasHelper.getExtra(this, EXTRA_ALBUM, Album.class);
		
		setTitle(album.getLabel());
		
		this.adapter = new SongArrayAdapter(this, android.R.layout.simple_list_item_1);
		setListAdapter(this.adapter);
		
		registerForContextMenu();
		
		onPlayerUpdate();
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
		Song song = (Song)super.getListAdapter().getItem(info.position);
		
		ContextMenuAdapter cmadapter = new ContextMenuAdapter(menu, this);
		cmadapter.setTitle(song.toString());
		cmadapter.add("Play song", new ContextMenuPlayItemActionCommand());
		cmadapter.add("Add to playlist", new ContextMenuAddToPlaylistActionCommand());
	}
	
	@Override
	public void onListItemClick(ListView listView, View v, int position, long id) {
		Song song = this.adapter.getItem(position);
		AudioPlayer player = getAudioPlayer();
		player.play(song);
		
		onPlayerUpdate();
	}
	

	@Override
	public void onPlayerUpdate() {
		new SongLoader(adapter).execute(album);
	}
}
