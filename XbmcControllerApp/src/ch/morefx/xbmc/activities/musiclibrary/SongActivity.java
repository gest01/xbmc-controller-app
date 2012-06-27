package ch.morefx.xbmc.activities.musiclibrary;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import ch.morefx.xbmc.activities.XbmcListActivity;
import ch.morefx.xbmc.model.Album;
import ch.morefx.xbmc.model.AudioLibrary;
import ch.morefx.xbmc.model.Song;
import ch.morefx.xbmc.model.loaders.SongLoader;
import ch.morefx.xbmc.util.Check;

public final class SongActivity 
	extends XbmcListActivity {

	public static final String EXTRA_ALBUM = "EXTRA_ALBUM";
	
	private Album album;
	private SongArrayAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		album = (Album)getIntent().getExtras().getSerializable(EXTRA_ALBUM);
		Check.notNull(album, "Album object missing from Intent extras");
		
		setTitle(album.getLabel());
		
		this.adapter = new SongArrayAdapter(this, android.R.layout.simple_list_item_1);
		setListAdapter(this.adapter);
		
		onPlayerUpdate();
	}
	
	@Override
	public void onListItemClick(ListView listView, View v, int position, long id) {
		Song song = this.adapter.getItem(position);
		AudioLibrary library = getAudioLibrary();
		library.play(song);
		
		onPlayerUpdate();
	}
	

	@Override
	public void onPlayerUpdate() {
		new SongLoader(adapter).execute(album);
	}
}
