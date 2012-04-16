package ch.morefx.xbmc.activities.musiclibrary;

import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import ch.morefx.xbmc.activities.XbmcListActivity;
import ch.morefx.xbmc.model.Album;
import ch.morefx.xbmc.model.Artist;
import ch.morefx.xbmc.model.loaders.AlbumLoader;
import ch.morefx.xbmc.model.loaders.PostExecuteHandler;
import ch.morefx.xbmc.net.notifications.Notification;
import ch.morefx.xbmc.util.Check;

public class AlbumActivity 
	extends XbmcListActivity {
	
	public static final String EXTRA_ARTIST = "EXTRA_ARTIST";
	
	private AlbumArrayAdapter adapter;
	private BroadcastReceiver receiver;
	private Artist artist;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getListView().setTextFilterEnabled(true);
		
		artist = (Artist)getIntent().getExtras().getSerializable(EXTRA_ARTIST);
		Check.notNull(artist, "Artist object missing from Intent extras");
		
		setTitle(artist.getLabel());
		
		this.adapter = new AlbumArrayAdapter(this, android.R.layout.simple_list_item_1);
		setListAdapter(adapter);
		
		loadAlbums();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		receiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				loadAlbums();
			}
 		};
		
		this.registerReceiver(receiver, new IntentFilter(Notification.PLAYER_UPDATE));
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		this.unregisterReceiver(receiver);
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
