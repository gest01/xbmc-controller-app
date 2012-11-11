package ch.morefx.xbmc.activities.musiclibrary;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import ch.morefx.xbmc.BuildConfig;
import ch.morefx.xbmc.R;
import ch.morefx.xbmc.activities.IXbmcActivity;
import ch.morefx.xbmc.activities.XbmcActivity;
import ch.morefx.xbmc.activities.home.HomeScreenActivity;
import ch.morefx.xbmc.model.Album;
import ch.morefx.xbmc.model.Artist;

public class MusicLibraryActivity extends XbmcActivity 
	implements AudioLibrarySelectionCallback {

	private final static String TAG = MusicLibraryActivity.class.getSimpleName();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
        setContentView(R.layout.masterdetail_layout);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        //getActionBar().setDisplayShowHomeEnabled(true);
        
        
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.master_container,  new ArtistListFragment());
        ft.commit();
	}
	
	@Override
	public void onPlayerUpdate() {
		
		IXbmcActivity sl;
		sl = (IXbmcActivity)getFragmentManager().findFragmentById(R.id.detail_container);
		if (sl != null){
			sl.onPlayerUpdate();
		}
		
		sl = (IXbmcActivity)getFragmentManager().findFragmentById(R.id.master_container);
		if (sl != null){
			sl.onPlayerUpdate();
		}
	}
	
	public void onItemSelected(Artist artist) {
		if (BuildConfig.DEBUG){
			Log.v(TAG, "Selecting Artist " + artist.getLabel());
		}
		
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
        ft.replace(R.id.detail_container, ArtistDetailFragment.newInstance(artist));
        ft.addToBackStack(null);
        ft.commit();
	}

	public void onItemSelected(Album album) {
		if (BuildConfig.DEBUG){
			Log.v(TAG, "Selecting Album " + album.getLabel());
		}
		
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
        ft.replace(R.id.master_container, AlbumListFragment.newInstance(album));
        ft.replace(R.id.detail_container, SongListFragment.newInstance(album));
        ft.addToBackStack(null);
        ft.commit();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
        case android.R.id.home:
        	
            // This is called when the Home (Up) button is pressed
            // in the Action Bar.
            Intent parentActivityIntent = new Intent(this, HomeScreenActivity.class);
            parentActivityIntent.addFlags(
                    Intent.FLAG_ACTIVITY_CLEAR_TOP |
                    Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(parentActivityIntent);
            finish();
            return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
}
