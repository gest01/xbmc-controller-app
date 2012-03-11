package ch.morefx.xbmc.activities.home;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import ch.morefx.xbmc.R;
import ch.morefx.xbmc.activities.XbmcListActivity;
import ch.morefx.xbmc.activities.musiclibrary.ArtistActivity;
import ch.morefx.xbmc.activities.sourcebrowser.SourceBrowserActivity;
import ch.morefx.xbmc.activities.videolibrary.VideoActivity;

/**
 * Defines the main activity for the Home Screen
 * @author stef
 *
 */
public class HomeScreenActivity extends XbmcListActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		startPlayerService();
		
		HomeScreenMenuItemAdapter adapter = new HomeScreenMenuItemAdapter(this, R.layout.song_list_item, populateMenuItem());		
		setListAdapter(adapter);
				
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		HomeScreenMenuItem menuItem = (HomeScreenMenuItem)getListAdapter().getItem(position);
		Intent intent = menuItem.getIntent();
		startActivity(intent);
	}
	
	@Override
	protected void onDestroy() {
		stopPlayerService();
		super.onDestroy();
	}
		
	private ArrayList<HomeScreenMenuItem> populateMenuItem(){
		ArrayList<HomeScreenMenuItem> items = new ArrayList<HomeScreenMenuItem>();
		items.add(new HomeScreenMenuItem(R.drawable.audioibrary, "Music Library", "Listen to your Music", new Intent(this, ArtistActivity.class)));
		items.add(new HomeScreenMenuItem(R.drawable.videolibrary, "Video Library", "Watch your Movies", new Intent(this, VideoActivity.class)));
		items.add(new HomeScreenMenuItem(R.drawable.filebrowser, "File Browser", "Browse your Media Files", new Intent(this, SourceBrowserActivity.class)));
		//items.add(new HomeScreenMenuItem("Pictures", "Pictures Library", new Intent(this, MusicLibraryActivity.class)));
		return items;
	}
}

