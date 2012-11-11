package ch.morefx.xbmc.activities.home;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import ch.morefx.xbmc.R;
import ch.morefx.xbmc.activities.XbmcListActivity;
import ch.morefx.xbmc.activities.musiclibrary.MusicLibraryActivity;
import ch.morefx.xbmc.activities.players.AudioPlayerActivity;
import ch.morefx.xbmc.activities.sourcebrowser.SourceBrowserActivity;
import ch.morefx.xbmc.activities.videolibrary.VideoActivity;
import ch.morefx.xbmc.model.Song;

/**
 * Defines the main activity for the Home Screen
 */
public class HomeScreenActivity extends XbmcListActivity {
	
	private HomeScreenMenuItemAdapter adapter;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(false);
		getActionBar().setDisplayHomeAsUpEnabled(false);
		
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
	public void onPlayerUpdate() {
		populateMenuItem();
	}
	
	
	@Override
	protected void onDestroy() {
		stopPlayerService();
		super.onDestroy();
	}
		
	private void populateMenuItem(){
		
		ArrayList<HomeScreenMenuItem> items = new ArrayList<HomeScreenMenuItem>();
		items.add(new HomeScreenMenuItem(getResources().getDrawable(R.drawable.audioibrary), "Music Library", "Listen to your Music", new Intent(this, MusicLibraryActivity.class)));
		items.add(new HomeScreenMenuItem(getResources().getDrawable(R.drawable.videolibrary), "Video Library", "Watch your Movies", new Intent(this, VideoActivity.class)));
		items.add(new HomeScreenMenuItem(getResources().getDrawable(R.drawable.filebrowser), "File Browser", "Browse your Media Files", new Intent(this, SourceBrowserActivity.class)));
		items.add(new HomeScreenMenuItem(getResources().getDrawable(R.drawable.remotecontrol), "Remote Control", "Control Xbmc", new Intent(this, RemoteControlActivity.class)));
		
		if (getAudioPlayer().isActive()) {
			Song sucker = getAudioPlayer().getCurrentSong();
			String s =  sucker.getAlbumString() + " - " + sucker.getLabel();
			items.add(new HomeScreenMenuItem(sucker, sucker.getArtistString(), s, new Intent(this, AudioPlayerActivity.class)));
		}
		
		this.adapter.clear();
		this.adapter.addAll(items);
	}
}

