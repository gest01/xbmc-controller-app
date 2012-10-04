package ch.morefx.xbmc.activities.testing;

import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import ch.morefx.xbmc.R;
import ch.morefx.xbmc.activities.musiclibrary.AlbumArrayAdapter;
import ch.morefx.xbmc.model.Album;
import ch.morefx.xbmc.model.loaders.AlbumLoader;
import ch.morefx.xbmc.util.Check;
import ch.morefx.xbmc.util.ExtrasHelper;

public class AlbumListFragment extends ListFragment {
	
	private static final String ARG_ALBUM = "ARG_ALBUM";

	private AlbumArrayAdapter adapter;
	
	private int mActivatedPosition = ListView.INVALID_POSITION;
	private static final String STATE_ACTIVATED_POSITION = "activated_position";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
		adapter = new AlbumArrayAdapter(getActivity(), android.R.layout.simple_list_item_1);
		setListAdapter(adapter);
		
		Album album = ExtrasHelper.getExtra(this, ARG_ALBUM, Album.class);
		new AlbumLoader(adapter).execute(album.getArtist());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
		registerForContextMenu(getListView());
        
        if (savedInstanceState != null && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
        }
        
        setActivateOnItemClick(true);
    }
    
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mActivatedPosition != ListView.INVALID_POSITION) {
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }
    }

    private void setActivatedPosition(int position) {
        if (position == ListView.INVALID_POSITION) {
            getListView().setItemChecked(mActivatedPosition, false);
        } else {
            getListView().setItemChecked(position, true);
        }

        mActivatedPosition = position;
    }
    
    private void setActivateOnItemClick(boolean activateOnItemClick) {
        getListView().setChoiceMode(activateOnItemClick
                ? ListView.CHOICE_MODE_SINGLE
                : ListView.CHOICE_MODE_NONE);
    }
	
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
    	Album album = adapter.getItem(position);
    	
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.addToBackStack(null);
        ft.replace(R.id.detail_container,  SongListFragment.newInstance(album));
        ft.commit();
    }
    
    /**
     * 
     * @param artist
     * @return
     */
    public static AlbumListFragment newInstance(Album album){
    	Check.argumentNotNull(album, "album");
    	
    	Bundle arguments = new Bundle();
        arguments.putSerializable(ARG_ALBUM, album);
    	
        AlbumListFragment fragment = new AlbumListFragment();
    	fragment.setArguments(arguments);
    	return fragment;
    }
	
}
