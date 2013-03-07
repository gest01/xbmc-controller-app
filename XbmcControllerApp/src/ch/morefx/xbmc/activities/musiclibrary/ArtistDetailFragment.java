package ch.morefx.xbmc.activities.musiclibrary;

import android.app.Activity;
import android.app.Fragment;
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
import ch.morefx.xbmc.model.Album;
import ch.morefx.xbmc.model.Artist;
import ch.morefx.xbmc.model.loaders.AlbumLoader;
import ch.morefx.xbmc.util.CastHelper;
import ch.morefx.xbmc.util.Check;
import ch.morefx.xbmc.util.ExtrasHelper;

public class ArtistDetailFragment extends Fragment 
	implements IXbmcActivity, ListAdapterProvider {

    private static final String ARG_ARTIST = "arg_artist";
  
    private AudioLibrarySelectionCallback selectionCallback;   
    private AlbumArrayAdapter adapter;
    private Artist artist;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.artist_fragment_detail, container, false);
        
        GridView gridview = (GridView)rootView.findViewById(R.id.album_gridview);
        
        adapter = new AlbumArrayAdapter(getActivity(), 
        		android.R.layout.simple_list_item_1, 
        		R.layout.album_grid_item);
        
        gridview.setAdapter(adapter);
        
        registerForContextMenu(gridview);
        
        gridview.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            	Album album = adapter.getItem(position);
            	selectionCallback.onItemSelected(album);
            }
        });
        
        artist = ExtrasHelper.getExtra(this, ARG_ARTIST, Artist.class);
       
        onPlayerUpdate();
        return rootView;
    }
    
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        selectionCallback = CastHelper.cast(activity, AudioLibrarySelectionCallback.class);
    }
    
    public void onPlayerUpdate() {
    	 new AlbumLoader(adapter).execute(artist);
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
	
	/**
	 * 
	 * @param artist
	 * @return
	 */
    public static ArtistDetailFragment newInstance(Artist artist){
    	Check.argumentNotNull(artist, "artist");
    	
    	Bundle arguments = new Bundle();
        arguments.putSerializable(ARG_ARTIST, artist);
    	
    	ArtistDetailFragment fragment = new ArtistDetailFragment();
    	fragment.setArguments(arguments);
    	return fragment;
    }
}
