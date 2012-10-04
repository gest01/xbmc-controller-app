package ch.morefx.xbmc.activities.testing;

import android.app.FragmentTransaction;
import android.os.Bundle;
import ch.morefx.xbmc.R;
import ch.morefx.xbmc.activities.XbmcActivity;
import ch.morefx.xbmc.model.Artist;

public class TestArtistListActivity extends XbmcActivity
        implements TestArtistListFragment.Callbacks {

    private boolean mTwoPane;

    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       
        setContentView(R.layout.activity_testartist_list);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        
        if (findViewById(R.id.testartist_detail_container) != null) {
            mTwoPane = true;
            ((TestArtistListFragment) getFragmentManager().findFragmentById(R.id.testartist_list)).setActivateOnItemClick(true);
        }
    }

    public void onItemSelected(Artist artist) {
        if (mTwoPane) {
            
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.testartist_detail_container, TestArtistDetailFragment.newInstance(artist));
            ft.commit();

        } else {
         //   Intent detailIntent = new Intent(this, TestArtistDetailActivity.class);
         //   detailIntent.putExtra(TestArtistDetailFragment.ARG_ITEM_ID, artist);
         //   startActivity(detailIntent);
        }
    }
}
