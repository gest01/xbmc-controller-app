package ch.morefx.xbmc.net.commands;

import ch.morefx.xbmc.model.FileSource;
import ch.morefx.xbmc.model.Movie;
import ch.morefx.xbmc.model.Playlist;
import ch.morefx.xbmc.model.Song;
import ch.morefx.xbmc.util.Check;

public class PlayerOpenCommandAdapter extends JsonCommand{

	private PlayerOpenCommandAdapter commandImpl = null;
	
	protected PlayerOpenCommandAdapter(){
		super("Player.Open");
	}
	
	public PlayerOpenCommandAdapter(Movie movie){
		super("Player.Open");
		
		Check.argumentNotNull(movie, "movie");
		this.commandImpl = new PlayerOpenCommandForMovie(movie);
	}
	
	public PlayerOpenCommandAdapter(FileSource filesource) {
		super("Player.Open");
		
		Check.argumentNotNull(filesource, "filesource");
		this.commandImpl = new PlayerOpenCommandForFileSource(filesource);
	}
	
	public PlayerOpenCommandAdapter(Song song) {
		super("Player.Open");
		
		Check.argumentNotNull(song, "song");
		this.commandImpl = new PlayerOpenCommandForSong(song);
	}
	
	@Override
	void prepareCommand(JsonCommandBuilder builder) {
		this.commandImpl.prepareCommand(builder);
	}

	private static class PlayerOpenCommandForMovie extends PlayerOpenCommandAdapter {
		private Movie movie;
		
		public PlayerOpenCommandForMovie(Movie movie) {
			this.movie = movie;
		}
		
		@Override
		void prepareCommand(JsonCommandBuilder builder) {
			CommandItemSet itemSet = new CommandItemSet();
			itemSet.add("movieid", this.movie.getMovieId());
			builder.addParams(itemSet);
		}
	}
	private static class PlayerOpenCommandForFileSource extends PlayerOpenCommandAdapter {
		
		private FileSource source;
		
		public PlayerOpenCommandForFileSource(FileSource source) {
			this.source = source;
		}
		
		@Override
		void prepareCommand(JsonCommandBuilder builder) {
			CommandItemSet itemSet = new CommandItemSet();
			itemSet.add("file", this.source.getFile());
			builder.addParams(itemSet);
		}
	}
	private static class PlayerOpenCommandForSong extends PlayerOpenCommandAdapter{
		private Song song;
		
		public PlayerOpenCommandForSong(Song song) {
			this.song = song;
		}
		
		@Override
		void prepareCommand(JsonCommandBuilder builder) {
			CommandItemSet itemSet = new CommandItemSet();
			itemSet.add("playlistid", Playlist.Audio.getPlaylistId());
			itemSet.add("position", song.getPosition());
			builder.addParams(itemSet);
		}
	}
}
