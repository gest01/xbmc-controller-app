package ch.morefx.xbmc.activities;

import ch.morefx.xbmc.model.Album;
import ch.morefx.xbmc.model.Artist;
import ch.morefx.xbmc.model.LibraryItem;
import ch.morefx.xbmc.model.Song;
import ch.morefx.xbmc.model.players.AudioPlayer;

public class ContextMenuAddToPlaylistActionCommand 
	extends ContextMenuArrayAdapterActionCommand<LibraryItem> {
	
	@Override
	protected boolean execute(IXbmcActivity activity, LibraryItem selectedItem) {
		
		AudioPlayer player = activity.getConnection().getAudioPlayer();
		
		if (selectedItem instanceof Artist){
			player.addToPlaylist((Artist)selectedItem);
			return true;
		}
		
		if (selectedItem instanceof Song){
			player.addToPlaylist((Song)selectedItem);
			return true;
		}
		
		if (selectedItem instanceof Album){
			player.addToPlaylist((Album)selectedItem);
			return true;
		}
		return false;
	}
}
