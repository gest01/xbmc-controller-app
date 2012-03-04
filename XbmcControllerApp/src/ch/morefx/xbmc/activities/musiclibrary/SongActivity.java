package ch.morefx.xbmc.activities.musiclibrary;

import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import ch.morefx.xbmc.XbmcRemoteControlApplication;
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Album album = (Album)getIntent().getExtras().getSerializable(EXTRA_ALBUM);
		Check.notNull(album, "Album object missing from Intent extras");
		
		getListView().setTextFilterEnabled(true);
		
		new SongLoader(this)
		 .setPostExecuteHandler(new PostExecuteHandler<List<Song>>() {
			public void onPostExecute(List<Song> result) {
				populateList(result);
			}})
		.execute(album);
	}
	
	private void populateList(List<Song> result){
		if (result != null){
			setListAdapter(new SongArrayAdapter(getApplication(), android.R.layout.simple_list_item_1, result));
		}
	}
	
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		final Song song = (Song)getListAdapter().getItem(position);
	    new Thread(new Runnable() {
	        public void run() {
	    		XbmcRemoteControlApplication app = (XbmcRemoteControlApplication)getApplicationContext();
	    		AudioLibrary library = app.getCurrentConnection().getAudioLibrary();
	    		library.playSong(song);
	        }
	    }).start();
	}
}
