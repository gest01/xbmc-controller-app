package ch.morefx.xbmc.activities;

import ch.morefx.xbmc.model.Artist;


public class ContextMenuShowArtistInfoActionCommand
	extends ContextMenuArrayAdapterActionCommand<Artist> {
	
	@Override
	protected boolean execute(IXbmcActivity activity, Artist selectedItem) {
		activity.getConnection().getAudioPlayer().play(selectedItem);
		return true;
	}
}