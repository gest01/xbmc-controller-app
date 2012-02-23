package ch.morefx.xbmc.model.loaders;

import java.util.List;

import android.content.Context;
import ch.morefx.xbmc.model.Artist;

public class ArtistLoader extends AsyncTaskLoader<Void, Void, List<Artist>>{

	public ArtistLoader(Context context) {
		super(context);
	}
	
	@Override
	protected List<Artist> doInBackground(Void... params) {
		return getAudioLibrary().getArtists();
	}
}
