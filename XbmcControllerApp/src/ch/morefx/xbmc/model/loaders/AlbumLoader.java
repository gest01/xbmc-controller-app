package ch.morefx.xbmc.model.loaders;

import java.util.List;

import android.content.Context;
import ch.morefx.xbmc.model.Album;
import ch.morefx.xbmc.model.Artist;
import ch.morefx.xbmc.util.Check;

public class AlbumLoader extends AsyncTaskLoader<Artist, Void, List<Album>>{
	
	public AlbumLoader(Context context) {
		super(context);
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

}