package ch.morefx.xbmc.model.loaders;

import java.util.List;

import ch.morefx.xbmc.activities.musiclibrary.SongArrayAdapter;
import ch.morefx.xbmc.model.Album;
import ch.morefx.xbmc.model.Song;

public class SongLoader 
	extends AsyncTaskLoader<Album, Void, List<Song>> {

	private SongArrayAdapter adapter;
	
	public SongLoader(SongArrayAdapter adapter){
		super(adapter.getContext());
		
		this.adapter = adapter;
	}
	
	@Override
	protected List<Song> doInBackground(Album... params) {
		Album album = params[0];
		return getAudioLibrary().getSongs(album);
	}
	
	@Override
	protected void onPostExecute(List<Song> result) {
		this.adapter.clear();
		if (result != null){
			this.adapter.addAll(result);
		}
	}
}
