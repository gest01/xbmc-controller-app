package ch.morefx.xbmc.command;

import ch.morefx.xbmc.model.Playlist;

public class PlaylistClearCommand extends JsonCommand {

	private Playlist playlist;
	
	public PlaylistClearCommand(Playlist playlist) {
		super("Playlist.Clear");
			
		this.playlist = playlist;
	}

	@Override
	void prepareCommand(JsonCommandBuilder builder) {
		builder.addParams("playlistid", this.playlist.getPlaylistId());
	}
}
