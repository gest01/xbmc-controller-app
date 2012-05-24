package ch.morefx.xbmc.activities.musiclibrary;

import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import ch.morefx.xbmc.activities.XbmcListActivity;
import ch.morefx.xbmc.model.Album;
import ch.morefx.xbmc.model.AudioLibrary;
import ch.morefx.xbmc.model.Song;
import ch.morefx.xbmc.model.loaders.PostExecuteHandler;
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
		
		loadSongs();
	}

	@Override
	public void onPlayerUpdate() {
		loadSongs();
	}

	private void loadSongs(){
		new SongLoader(this)
		 .setPostExecuteHandler(new PostExecuteHandler<List<Song>>() {
			public void onPostExecute(List<Song> result) {
				populateList(result);
			}})
		.execute(album);
	}
	
	private void populateList(List<Song> result){
		this.adapter.clear();
		if (result != null){
			this.adapter.addAll(result);
		}
	}
	
	
	@Override
	public void onListItemClick(ListView listView, View v, int position, long id) {
		Song song = this.adapter.getItem(position);
		AudioLibrary library = getAudioLibrary();
		library.playSong(song);
		
		loadSongs();
	}
}
