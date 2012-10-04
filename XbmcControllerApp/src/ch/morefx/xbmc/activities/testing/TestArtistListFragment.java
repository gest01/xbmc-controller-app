package ch.morefx.xbmc.activities.testing;


import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import ch.morefx.xbmc.XbmcConnection;
import ch.morefx.xbmc.XbmcRemoteControlApplication;
import ch.morefx.xbmc.activities.ContextMenuAdapter;
import ch.morefx.xbmc.activities.ContextMenuAddToPlaylistActionCommand;
import ch.morefx.xbmc.activities.ContextMenuPlayItemActionCommand;
import ch.morefx.xbmc.activities.ContextMenuShowArtistInfoActionCommand;
import ch.morefx.xbmc.activities.IXbmcActivity;
import ch.morefx.xbmc.activities.musiclibrary.ArtistArrayAdapter;
import ch.morefx.xbmc.model.Album;
import ch.morefx.xbmc.model.Artist;
import ch.morefx.xbmc.model.loaders.ArtistLoader;

public class TestArtistListFragment extends ListFragment 
	implements IXbmcActivity{

    private static final String STATE_ACTIVATED_POSITION = "activated_position";

    private Callbacks mCallbacks;
    private int mActivatedPosition = ListView.INVALID_POSITION;

    
    private ArtistArrayAdapter adapter;
    
    public interface Callbacks {

        public void onItemSelected(Artist artist);
    }
    
    public interface Callbacks2 extends Callbacks {

        public void onItemSelected(Album album);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
		this.adapter = new ArtistArrayAdapter(this.getActivity(), android.R.layout.simple_list_item_1);
		setListAdapter(adapter);
        	
		onPlayerUpdate() ;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
		registerForContextMenu(getListView());
        
        if (savedInstanceState != null && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }

        mCallbacks = (Callbacks) activity;
    }
    
	public void onPlayerUpdate() {
		new ArtistLoader(adapter).execute();
	}
	
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
		Artist artist = (Artist)super.getListAdapter().getItem(info.position);
		
		ContextMenuAdapter cmadapter = new ContextMenuAdapter(menu, this);
		cmadapter.setTitle(artist.toString());
		cmadapter.add("Show artist info", new ContextMenuShowArtistInfoActionCommand());
		cmadapter.add("Play all titles", new ContextMenuPlayItemActionCommand());
		cmadapter.add("Add to playlist", new ContextMenuAddToPlaylistActionCommand());
	}

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
		Artist artist = (Artist)super.getListAdapter().getItem(position);
		mCallbacks.onItemSelected(artist);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mActivatedPosition != ListView.INVALID_POSITION) {
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }
    }

    public void setActivateOnItemClick(boolean activateOnItemClick) {
        getListView().setChoiceMode(activateOnItemClick
                ? ListView.CHOICE_MODE_SINGLE
                : ListView.CHOICE_MODE_NONE);
    }

    private void setActivatedPosition(int position) {
        if (position == ListView.INVALID_POSITION) {
            getListView().setItemChecked(mActivatedPosition, false);
        } else {
            getListView().setItemChecked(position, true);
        }

        mActivatedPosition = position;
    }

	public Context getContext() {
		return getActivity();
	}

	public XbmcConnection getConnection() {
		return XbmcRemoteControlApplication.getInstance().getCurrentConnection();
	}
}
