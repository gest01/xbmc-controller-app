package ch.morefx.xbmc.activities;

import ch.morefx.xbmc.model.Album;
import ch.morefx.xbmc.model.Artist;
import ch.morefx.xbmc.model.LibraryItem;
import ch.morefx.xbmc.model.Song;

public class ContextMenuPlayItemActionCommand 
	extends ContextMenuArrayAdapterActionCommand<LibraryItem> {
	
	@Override
	protected boolean execute(IXbmcActivity activity, LibraryItem selectedItem) {
		if (selectedItem instanceof Artist){
			activity.getConnection().getAudioPlayer().play((Artist)selectedItem);
			return true;
		}
		
		if (selectedItem instanceof Song){
			activity.getConnection().getAudioPlayer().play((Song)selectedItem);
			return true;
		}
		
		if (selectedItem instanceof Album){
			activity.getConnection().getAudioPlayer().play((Album)selectedItem);
			return true;
		}
		
		return false;
	}
}
