package ch.morefx.xbmc.screens.musiclibrary;

import java.util.List;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import ch.morefx.xbmc.model.Album;
import ch.morefx.xbmc.model.Artist;
import ch.morefx.xbmc.model.loaders.AlbumLoader;
import ch.morefx.xbmc.model.loaders.PostExecuteHandler;

public class AlbumFragment extends ListFragment {
	
	private ArrayAdapter<Album> listAdapter;
	
	private OnAlbumSelectedListener albumListener;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
        try {
        	this.albumListener = (OnAlbumSelectedListener) activity;
        } catch (ClassCastException e) { }
	}
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.listAdapter = new ArrayAdapter<Album>(getActivity(), android.R.layout.simple_list_item_1);
		setListAdapter(this.listAdapter);
	}
	
	
	public void loadFromArtist(Artist artist){
		this.listAdapter.clear();
		
		new AlbumLoader(getActivity())
			.setPostExecuteHandler(new PostExecuteHandler<List<Album>>() {
				public void onPostExecute(List<Album> result) { 
					AlbumFragment.this.listAdapter.addAll(result); 
				}})
			.execute(artist);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Album album = this.listAdapter.getItem(position);
		fireOnAlbumSelected(album);
	}
	
	/**
	 * Fires the onAlbumSelected event when a listener interface was attached to it.
	 * @param album The album selected by the user.
	 */
	private void fireOnAlbumSelected(Album album){
		if (this.albumListener != null){
			this.albumListener.onAlbumSelected(album);
		}
	}

	public interface OnAlbumSelectedListener {
        public void onAlbumSelected(Album album);
    }
}
