package ch.morefx.xbmc.activities.testing;

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
import ch.morefx.xbmc.util.ExtrasHelper;

public class TestArtistDetailFragment extends Fragment 
	implements IXbmcActivity, ListAdapterProvider {

    public static final String ARG_ITEM_ID = "item_id";

    private AlbumArrayAdapter2 adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_testartist_detail, container, false);
        
        GridView gridview = (GridView)rootView.findViewById(R.id.mygridview);
        
        adapter = new AlbumArrayAdapter2(getActivity(), android.R.layout.simple_list_item_1);
        gridview.setAdapter(adapter);
        
        registerForContextMenu(gridview);
        
        gridview.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            	Album album = adapter.getItem(position);
            }
        });
        
        Artist artist = ExtrasHelper.getExtra(this, ARG_ITEM_ID, Artist.class);
        new AlbumLoader(adapter).execute(artist);
        
        return rootView;
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
		return ((GridView)getView().findViewById(R.id.mygridview)).getAdapter();
	}
}
