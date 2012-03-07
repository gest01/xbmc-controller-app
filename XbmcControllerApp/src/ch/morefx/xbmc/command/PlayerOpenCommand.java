package ch.morefx.xbmc.command;

import ch.morefx.xbmc.model.FileSource;
import ch.morefx.xbmc.model.Playlist;
import ch.morefx.xbmc.model.Song;
import ch.morefx.xbmc.util.Check;

public class PlayerOpenCommand extends JsonCommand{

	private PlayerOpenCommand commandAdapter = null;
	
	protected PlayerOpenCommand(){
		super("Player.Open");
	}
	
	public PlayerOpenCommand(FileSource filesource) {
		super("Player.Open");
		
		Check.argumentNotNull(filesource, "filesource");
		this.commandAdapter = new PlayerOpenCommandForFileSource(filesource);
	}
	
	public PlayerOpenCommand(Playlist playlist, Song song) {
		super("Player.Open");
		
		Check.argumentNotNull(song, "song");
		this.commandAdapter = new PlayerOpenCommandForSong(playlist, song);
	}
	
	@Override
	void prepareCommand(CommandBuilder builder) {
		this.commandAdapter.prepareCommand(builder);
	}


	private static class PlayerOpenCommandForFileSource extends PlayerOpenCommand{
		
		private FileSource source;
		
		public PlayerOpenCommandForFileSource(FileSource source) {
			this.source = source;
		}
		
		@Override
		void prepareCommand(CommandBuilder builder) {
			CommandItemSet itemSet = new CommandItemSet();
			itemSet.add("file", this.source.getFile());
			builder.addParams(itemSet);
		}
	}
	private static class PlayerOpenCommandForSong extends PlayerOpenCommand{
		private Playlist playlist;
		private Song song;
		
		public PlayerOpenCommandForSong(Playlist playlist, Song song) {
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
}
