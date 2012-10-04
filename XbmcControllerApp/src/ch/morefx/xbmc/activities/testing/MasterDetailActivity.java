package ch.morefx.xbmc.activities.testing;

import android.app.FragmentTransaction;
import android.os.Bundle;
import ch.morefx.xbmc.R;
import ch.morefx.xbmc.activities.XbmcActivity;
import ch.morefx.xbmc.activities.testing.TestArtistListFragment.Callbacks2;
import ch.morefx.xbmc.model.Album;
import ch.morefx.xbmc.model.Artist;

public class MasterDetailActivity extends XbmcActivity 
	implements Callbacks2{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
        setContentView(R.layout.masterdetail_layout);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        
        
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.master_container,  new TestArtistListFragment());
        ft.commit();
        
	}

	public void onItemSelected(Artist artist) {
		
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
        ft.replace(R.id.detail_container,  TestArtistDetailFragment.newInstance(artist));
        ft.commit();
	}

	public void onItemSelected(Album album) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
        
        ft.replace(R.id.master_container,  AlbumListFragment.newInstance(album));
        ft.replace(R.id.detail_container,  SongListFragment.newInstance(album));
        
        ft.commit();
	}
}
