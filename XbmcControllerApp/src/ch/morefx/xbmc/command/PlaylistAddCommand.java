package ch.morefx.xbmc.command;

import ch.morefx.xbmc.model.Album;
import ch.morefx.xbmc.model.Playlist;

public class PlaylistAddCommand extends JsonCommand {

	private Playlist playlist;
	private Album album;
	
	public PlaylistAddCommand(Playlist playlist, Album album) {
		super("Playlist.Add");
		
		this.playlist = playlist;
		this.album = album;
	}
	
	@Override
	void prepareCommand(CommandBuilder builder) {
		
		builder.addParams("playlistid", this.playlist.getPlaylistId());
		CommandItemSet itemSet = new CommandItemSet();
		itemSet.add("albumid", this.album.getAlbumId());
		builder.addParams(itemSet);
	}
}
