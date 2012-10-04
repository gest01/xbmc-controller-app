package ch.morefx.xbmc.activities.testing;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ListAdapter;
import ch.morefx.xbmc.R;
import ch.morefx.xbmc.XbmcConnection;
import ch.morefx.xbmc.XbmcRemoteControlApplication;
import ch.morefx.xbmc.activities.ContextMenuAdapter;
import ch.morefx.xbmc.activities.ContextMenuAddToPlaylistActionCommand;
import ch.morefx.xbmc.activities.ContextMenuPlayItemActionCommand;
import ch.morefx.xbmc.activities.ContextMenuShowAlbumInfoActionCommand;
import ch.morefx.xbmc.activities.IXbmcActivity;
import ch.morefx.xbmc.activities.testing.TestArtistListFragment.Callbacks2;
import ch.morefx.xbmc.model.Album;
import ch.morefx.xbmc.model.Artist;
import ch.morefx.xbmc.model.loaders.AlbumLoader;
import ch.morefx.xbmc.util.Check;
import ch.morefx.xbmc.util.ExtrasHelper;

public class TestArtistDetailFragment extends Fragment 
	implements IXbmcActivity, ListAdapterProvider {

    private static final String ARG_ARTIST = "arg_artist";

    public static TestArtistDetailFragment newInstance(Artist artist){
    	Check.argumentNotNull(artist, "artist");
    	
    	Bundle arguments = new Bundle();
        arguments.putSerializable(ARG_ARTIST, artist);
    	
    	TestArtistDetailFragment fragment = new TestArtistDetailFragment();
    	fragment.setArguments(arguments);
    	return fragment;
    }
    
    private AlbumArrayAdapter2 adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_testartist_detail, container, false);
        
        GridView gridview = (GridView)rootView.findViewById(R.id.album_gridview);
        
        adapter = new AlbumArrayAdapter2(getActivity(), android.R.layout.simple_list_item_1);
        gridview.setAdapter(adapter);
        
        registerForContextMenu(gridview);
        
        gridview.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            	Album album = adapter.getItem(position);
            	arschpenner(album);
            }
        });
        
        Artist artist = ExtrasHelper.getExtra(this, ARG_ARTIST, Artist.class);
        new AlbumLoader(adapter).execute(artist);
        
        return rootView;
    }
    
    private void arschpenner(Album album){
    	
    	if (mCallbacks != null){
    		mCallbacks.onItemSelected(album);
    	} else {
        	
        	FragmentTransaction ft = getFragmentManager().beginTransaction();
        	ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);

        	ft.replace( R.id.testartist_detail_container , new AlbumDetailFragment(), "detailFragment");

        	// Start the animated transition.
        	ft.commit();
    	}

    }
    
	
	Callbacks2 mCallbacks;
	
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (!(activity instanceof Callbacks2)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }

        mCallbacks = (Callbacks2) activity;
    }
    
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
		Album album = (Album)getListAdapter().getItem(info.position);
		
		ContextMenuAdapter cmadapter = new ContextMenuAdapter(menu, this);
		cmadapter.setTitle(album.getArtist().toString() + " (" + album.toString() + ")");
		cmadapter.add("Show album info", new ContextMenuShowAlbumInfoActionCommand());
		cmadapter.add("Play all songs", new ContextMenuPlayItemActionCommand());
		cmadapter.add("Add to playlist", new ContextMenuAddToPlaylistActionCommand());
	}


	public Context getContext() {
		return getActivity();
	}

	public XbmcConnection getConnection() {
		return XbmcRemoteControlApplication.getInstance().getCurrentConnection();
	}

	public ListAdapter getListAdapter() {
		return ((GridView)getView().findViewById(R.id.album_gridview)).getAdapter();
	}
}
