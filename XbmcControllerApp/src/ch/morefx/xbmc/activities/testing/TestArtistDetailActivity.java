package ch.morefx.xbmc.activities.testing;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import ch.morefx.xbmc.R;
import ch.morefx.xbmc.model.Artist;
import ch.morefx.xbmc.util.ExtrasHelper;

public class TestArtistDetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testartist_detail);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
        	
        	Artist artist = ExtrasHelper.getExtra(this, TestArtistDetailFragment.ARG_ITEM_ID, Artist.class);
        	
            Bundle arguments = new Bundle();
            arguments.putSerializable(TestArtistDetailFragment.ARG_ITEM_ID, artist);
            
            TestArtistDetailFragment fragment = new TestArtistDetailFragment();
            fragment.setArguments(arguments);
          
          getFragmentManager().beginTransaction()
          .add(R.id.testartist_detail_container, fragment)
          .commit();
            
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
          //  NavUtils.navigateUpTo(this, new Intent(this, TestArtistListActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
