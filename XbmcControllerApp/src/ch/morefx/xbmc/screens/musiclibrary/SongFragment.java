package ch.morefx.xbmc.screens.musiclibrary;

import java.util.List;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import ch.morefx.xbmc.model.Album;
import ch.morefx.xbmc.model.Song;
import ch.morefx.xbmc.model.loaders.PostExecuteHandler;
import ch.morefx.xbmc.model.loaders.SongLoader;

public class SongFragment extends ListFragment {
	
	private OnSongSelectedListener songListener;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
        try {
        	this.songListener = (OnSongSelectedListener) activity;
        } catch (ClassCastException e) { }
	}
	
	public void loadFromAlbum(Album album){
		new SongLoader(getActivity())
			.setPostExecuteHandler(new PostExecuteHandler<List<Song>>() {
				public void onPostExecute(List<Song> result) {
					if (result != null){
						SongFragment.this.setListAdapter(new SongArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, result));
					}
				}})
			.execute(album);
	}

	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Song song = (Song)getListAdapter().getItem(position);
		fireOnSongSelected(song);
	}
	
	/**
	 * Fires the onSongSelected event when a listener interface was attached to it.
	 * @param song The song selected by the user.
	 */
	private void fireOnSongSelected(Song song){
		if (this.songListener != null){
			this.songListener.onSongSelected(song);
		}
	}
	
	public interface OnSongSelectedListener {
        public void onSongSelected(Song song);
    }
}
