package ch.morefx.xbmc.activities.home;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import ch.morefx.xbmc.R;
import ch.morefx.xbmc.XbmcConnection;
import ch.morefx.xbmc.XbmcRemoteControlApplication;
import ch.morefx.xbmc.activities.XbmcActivity;
import ch.morefx.xbmc.activities.browser.BrowserActivity;
import ch.morefx.xbmc.activities.musiclibrary.ArtistActivity;
import ch.morefx.xbmc.command.JsonCommandExecutor;

/**
 * Defines the main activity for the Home Screen
 * @author stef
 *
 */
public class HomeScreenActivity extends XbmcActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.homescreenactivity_layout);
		
		HomeScreenMenuItemAdapter adapter = new HomeScreenMenuItemAdapter(this, R.layout.homescreenactivity_layout, populateMenuItem());		
		ListView listView = (ListView) findViewById(R.id.homescreenactivity_list);
		listView.setAdapter(adapter);
		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);

		
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				HomeScreenMenuItem menuItem = (HomeScreenMenuItem)parent.getItemAtPosition(position);
				
				if (menuItem.getTitle().equals("Pictures")){
					XbmcRemoteControlApplication app = (XbmcRemoteControlApplication)getApplicationContext();
					XbmcConnection connection = app.getCurrentConnection();
					JsonCommandExecutor executor = new JsonCommandExecutor(connection);
					String result = executor.executeDirect("{\"jsonrpc\": \"2.0\", \"method\": \"Player.GetActivePlayers\", \"id\": 1}");
					System.out.println("RESULT = " + result);
					
					result = executor.executeDirect("{\"jsonrpc\": \"2.0\", \"method\": \"Playlist.GetItems\", \"params\": { \"playlistid\": 0, \"properties\": [ \"title\", \"album\", \"artist\", \"duration\", \"thumbnail\" ] }, \"id\": 1}");
					System.out.println("RESULT = " + result);
					
					result = executor.executeDirect("{\"jsonrpc\": \"2.0\", \"method\": \"Player.GetProperties\", \"params\": { \"playerid\": 0, \"properties\": [ \"playlistid\", \"speed\", \"position\", \"totaltime\", \"time\" ] }, \"id\": 1}");
					System.out.println("RESULT = " + result);
					
					
				} else {
					Intent intent = menuItem.getIntent();
					startActivity(intent);
				}
			}
		});
	}
	
	private ArrayList<HomeScreenMenuItem> populateMenuItem(){
		ArrayList<HomeScreenMenuItem> items = new ArrayList<HomeScreenMenuItem>();
		items.add(new HomeScreenMenuItem("Music Library", "Browse your Music Library", new Intent(this, ArtistActivity.class)));
		items.add(new HomeScreenMenuItem("Music Browser", "Browse your Music Files", new Intent(this, BrowserActivity.class)));
		//items.add(new HomeScreenMenuItem("Videos", "Video Library", new Intent(this, MusicLibraryActivity.class)));
		//items.add(new HomeScreenMenuItem("Pictures", "Pictures Library", new Intent(this, MusicLibraryActivity.class)));
		return items;
	}
}

