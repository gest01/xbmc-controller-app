package ch.morefx.xbmc.command;

import ch.morefx.xbmc.model.Playlist;
import ch.morefx.xbmc.model.Song;

public class PlayerOpenCommand extends JsonCommand{

	private Playlist playlist;
	private Song song;
	
	public PlayerOpenCommand(Playlist playlist, Song song) {
		super("Player.Open");
		
		this.playlist = playlist;
		this.song = song;
	}
	
	@Override
	void prepareCommand(CommandBuilder builder) {
		CommandItemSet itemSet = new CommandItemSet();
		itemSet.add("playlistid", this.playlist.getPlaylistId());
		itemSet.add("position", song.getPosition());
		builder.addParams(itemSet);
	}
}
