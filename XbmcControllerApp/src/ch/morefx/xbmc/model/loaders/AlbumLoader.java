package ch.morefx.xbmc.model.loaders;

import java.util.List;

import ch.morefx.xbmc.activities.musiclibrary.AlbumArrayAdapter;
import ch.morefx.xbmc.model.Album;
import ch.morefx.xbmc.model.Artist;
import ch.morefx.xbmc.util.Check;

public class AlbumLoader extends AsyncTaskLoader<Artist, Void, List<Album>>{
	
	private AlbumArrayAdapter adapter;
	
	public AlbumLoader(AlbumArrayAdapter adapter){ 
		super(adapter.getContext());
		
		this.adapter = adapter;
	}
	
	@Override
	protected List<Album> doInBackground(Artist... params) {
		Check.notNull(params, "albums");
		Check.assertion(params.length == 1, "An artist is required!");

		Artist artist = params[0];
		
		List<Album> albums = getAudioLibrary().getAlbums(artist);
		loadThumbnails(albums);
		return albums;
	}
	
	@Override
	protected void onPostExecute(List<Album> result) {
		this.adapter.clear();
		if (result != null){
			this.adapter.addAll(result);
		}
	}
}