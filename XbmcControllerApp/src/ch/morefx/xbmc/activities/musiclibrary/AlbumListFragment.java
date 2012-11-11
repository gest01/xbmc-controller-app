package ch.morefx.xbmc.activities.musiclibrary;

import java.util.List;

import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import ch.morefx.xbmc.R;
import ch.morefx.xbmc.XbmcConnection;
import ch.morefx.xbmc.XbmcRemoteControlApplication;
import ch.morefx.xbmc.activities.ContextMenuAdapter;
import ch.morefx.xbmc.activities.ContextMenuAddToPlaylistActionCommand;
import ch.morefx.xbmc.activities.ContextMenuPlayItemActionCommand;
import ch.morefx.xbmc.activities.ContextMenuShowAlbumInfoActionCommand;
import ch.morefx.xbmc.activities.IXbmcActivity;
import ch.morefx.xbmc.model.Album;
import ch.morefx.xbmc.model.loaders.AlbumLoader;
import ch.morefx.xbmc.model.loaders.AsyncTaskCompleteListener;
import ch.morefx.xbmc.util.Check;
import ch.morefx.xbmc.util.ExtrasHelper;

public class AlbumListFragment extends ListFragment 
	implements IXbmcActivity, AsyncTaskCompleteListener<List<Album>>{
	
	private static final String ARG_ALBUM = "ARG_ALBUM";

	private AlbumArrayAdapter adapter;
	private Album album;
	
	private int mActivatedPosition = ListView.INVALID_POSITION;
	private static final String STATE_ACTIVATED_POSITION = "activated_position";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
		adapter = new AlbumArrayAdapter(getActivity(), android.R.layout.simple_list_item_1);
		setListAdapter(adapter);
		
		album = ExtrasHelper.getExtra(this, ARG_ALBUM, Album.class);
		onPlayerUpdate();
    }
    

	public void onTaskComplete(List<Album> result) {
		int position = adapter.getPosition(album);
		getListView().setItemChecked(position, true);
		getListView().setSelection(position);
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
	
    public void onPlayerUpdate() {
		new AlbumLoader(adapter)
		.setOnTaskCompleteListener(this)
		.execute(album.getArtist());
    }    
    
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
    	Album album = adapter.getItem(position);
    	
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.addToBackStack(null);
        ft.replace(R.id.detail_container,  SongListFragment.newInstance(album));
        ft.commit();
    }
    
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
		Album album = (Album)super.getListAdapter().getItem(info.position);
		
		ContextMenuAdapter cmadapter = new ContextMenuAdapter(menu, this);
		cmadapter.setTitle(album.getArtist().toString() + " (" + album.toString() + ")");
		cmadapter.add("Show album info", new ContextMenuShowAlbumInfoActionCommand());
		cmadapter.add("Play all songs", new ContextMenuPlayItemActionCommand());
		cmadapter.add("Add to playlist", new ContextMenuAddToPlaylistActionCommand());
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

	public Context getContext() {
		return getActivity();
	}

	public XbmcConnection getConnection() {
		return XbmcRemoteControlApplication.getInstance().getCurrentConnection();
	}

}
