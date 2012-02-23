package ch.morefx.xbmc.model.loaders;

import java.util.List;

import android.content.Context;
import ch.morefx.xbmc.model.Album;
import ch.morefx.xbmc.model.Song;

public class SongLoader 
	extends AsyncTaskLoader<Album, Void, List<Song>> {

	public SongLoader(Context context) {
		super(context);
	}
	
	@Override
	protected List<Song> doInBackground(Album... params) {
		Album album = params[0];
		return getAudioLibrary().getSongs(album);
	}
}
