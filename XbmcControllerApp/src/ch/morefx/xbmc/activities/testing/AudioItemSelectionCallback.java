package ch.morefx.xbmc.activities.testing;

import ch.morefx.xbmc.model.Album;
import ch.morefx.xbmc.model.Artist;

public interface AudioItemSelectionCallback {
	
	public void onItemSelected(Artist artist);
	
	public void onItemSelected(Album album);
}
