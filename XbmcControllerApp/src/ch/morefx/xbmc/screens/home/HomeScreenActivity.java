package ch.morefx.xbmc.screens.home;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import ch.morefx.xbmc.R;
import ch.morefx.xbmc.screens.musiclibrary.MusicLibraryActivity;
import ch.morefx.xbmc.screens.musiclibrary.ArtistActivity;

/**
 * Defines the main activity for the Home Screen
 * @author stef
 *
 */
public class HomeScreenActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.homescreenactivity_layout);
		
		HomeScreenMenuItemAdapter adapter = new HomeScreenMenuItemAdapter(this, R.layout.homescreenactivity_layout, populateMenuItem());		
		ListView listView = (ListView) findViewById(R.id.homescreenactivity_list);
		listView.setAdapter(adapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				HomeScreenMenuItem menuItem = (HomeScreenMenuItem)parent.getItemAtPosition(position);
				Intent intent = menuItem.getIntent();
				startActivity(intent);
			}
		});
	}
	
	private ArrayList<HomeScreenMenuItem> populateMenuItem(){
		ArrayList<HomeScreenMenuItem> items = new ArrayList<HomeScreenMenuItem>();
		items.add(new HomeScreenMenuItem("Music", "Music Library 2", new Intent(this, ArtistActivity.class)));
		items.add(new HomeScreenMenuItem("Music", "Music Library", new Intent(this, MusicLibraryActivity.class)));
		items.add(new HomeScreenMenuItem("Videos", "Video Library", new Intent(this, MusicLibraryActivity.class)));
		items.add(new HomeScreenMenuItem("Pictures", "Pictures Library", new Intent(this, MusicLibraryActivity.class)));
		return items;
	}
}

