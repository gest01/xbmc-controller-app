package ch.morefx.xbmc.model.loaders;

import java.util.List;

import ch.morefx.xbmc.activities.musiclibrary.ArtistArrayAdapter;
import ch.morefx.xbmc.model.Artist;

public class ArtistLoader extends AsyncTaskLoader<Void, Void, List<Artist>>{

	private ArtistArrayAdapter adapter;
	
	public ArtistLoader(ArtistArrayAdapter adapter) {
		super(adapter.getContext());
		
		this.adapter = adapter;
	}
	
	@Override
	protected List<Artist> doInBackground(Void... params) {
		return getAudioLibrary().getArtists();
	}
	
	@Override
	protected void onPostExecute(List<Artist> result) {
		if (result != null){
			this.adapter.addAll(result);
		}
	}
}
