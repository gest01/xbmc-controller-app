package ch.morefx.xbmc.activities.home;

import java.util.ArrayList;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import ch.morefx.xbmc.R;
import ch.morefx.xbmc.activities.XbmcListActivity;
import ch.morefx.xbmc.activities.musiclibrary.ArtistActivity;
import ch.morefx.xbmc.activities.players.AudioPlayerActivity;
import ch.morefx.xbmc.activities.sourcebrowser.SourceBrowserActivity;
import ch.morefx.xbmc.activities.videolibrary.VideoActivity;
import ch.morefx.xbmc.services.PlayerService;

/**
 * Defines the main activity for the Home Screen
 */
public class HomeScreenActivity extends XbmcListActivity {
	
	private BroadcastReceiver receiver;
	private HomeScreenMenuItemAdapter adapter;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		startPlayerService();
		
		adapter = new HomeScreenMenuItemAdapter(this, R.layout.song_list_item);		
		setListAdapter(adapter);
		
		populateMenuItem();
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		HomeScreenMenuItem menuItem = (HomeScreenMenuItem)getListAdapter().getItem(position);
		Intent intent = menuItem.getIntent();
		startActivity(intent);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		receiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				populateMenuItem();
			}
 		};
		
		this.registerReceiver(receiver, new IntentFilter(PlayerService.REFRESH_AUDIO_LIBRARY));
	}
	
	@Override
	protected void onDestroy() {
		stopPlayerService();
		super.onDestroy();
	}
		
	private void populateMenuItem(){
		ArrayList<HomeScreenMenuItem> items = new ArrayList<HomeScreenMenuItem>();
		items.add(new HomeScreenMenuItem(R.drawable.audioibrary, "Music Library", "Listen to your Music", new Intent(this, ArtistActivity.class)));
		items.add(new HomeScreenMenuItem(R.drawable.videolibrary, "Video Library", "Watch your Movies", new Intent(this, VideoActivity.class)));
		items.add(new HomeScreenMenuItem(R.drawable.filebrowser, "File Browser", "Browse your Media Files", new Intent(this, SourceBrowserActivity.class)));
		
		if (getXbmcApplication().isConnected()) {
			items.add(new HomeScreenMenuItem(R.drawable.filebrowser, "RemoteControl", "Control Xbmc", new Intent(this, SourceBrowserActivity.class)));	
		}
		
		if (getXbmcApplication().getCurrentConnection().getAudioLibrary().getPlayer().isActive()) {
			items.add(new HomeScreenMenuItem(R.drawable.filebrowser, "Audio Player", "----", new Intent(this, AudioPlayerActivity.class)));	
		}
		
		this.adapter.clear();
		this.adapter.addAll(items);
	}
}

