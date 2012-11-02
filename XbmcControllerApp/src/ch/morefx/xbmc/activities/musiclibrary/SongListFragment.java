package ch.morefx.xbmc.activities.musiclibrary;

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
import ch.morefx.xbmc.activities.IXbmcActivity;
import ch.morefx.xbmc.model.Album;
import ch.morefx.xbmc.model.Song;
import ch.morefx.xbmc.model.loaders.SongLoader;
import ch.morefx.xbmc.model.players.AudioPlayer;
import ch.morefx.xbmc.util.Check;
import ch.morefx.xbmc.util.ExtrasHelper;

public class SongListFragment extends ListFragment 
	implements IXbmcActivity {

	private static final String ARG_ALBUM = "ARG_ALBUM"; 
	
	private SongArrayAdapter adapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		adapter = new SongArrayAdapter(getActivity(), android.R.layout.simple_list_item_1);
		setListAdapter(adapter);
		
		Album album = ExtrasHelper.getExtra(this, ARG_ALBUM,  Album.class);
			
		new SongLoader(adapter).execute(album);
	}
	

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
		registerForContextMenu(getListView());
    }
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Song song = this.adapter.getItem(position);
		AudioPlayer player = getConnection().getAudioPlayer();
		player.play(song);
	}
	

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
		Song song = (Song)super.getListAdapter().getItem(info.position);
		ContextMenuAdapter cmadapter = new ContextMenuAdapter(menu, this);
		cmadapter.setTitle(song.toString());
		cmadapter.add("Play song", new ContextMenuPlayItemActionCommand());
		cmadapter.add("Add to playlist", new ContextMenuAddToPlaylistActionCommand());
	}
	
	/**
	 * 
	 * @param album
	 * @return
	 */
    public static SongListFragment newInstance(Album album){
    	Check.argumentNotNull(album, "album");
    	
    	Bundle arguments = new Bundle();
        arguments.putSerializable(ARG_ALBUM, album);
    	
        SongListFragment fragment = new SongListFragment();
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
