package ch.morefx.xbmc.command;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import ch.morefx.xbmc.model.Album;
import ch.morefx.xbmc.model.Song;

import com.google.gson.InstanceCreator;

public class GetSongsCommand extends JsonCommand
	implements JsonCommandResponseHandler {
	
	private Album album;
	
	public GetSongsCommand(Album album) {
		super("AudioLibrary.GetSongs");
		
		this.album = album;
	}
	
	@Override
	void prepareCommand(JsonCommandBuilder builder) {
		builder.addParams("albumid", this.album.getId());
		builder.addParams("properties", Song.getSongFields());
		builder.setSortMethod("track");
	}
	
	public void handleResponse(JsonCommandResponse response) {
		Song[] songsArray = response.asArrayResultWithCreator("songs", Song[].class, Song.class, new SongInstanceCreator(this.album));
		this.songs = Arrays.asList(songsArray);
	}
	
	private List<Song> songs;
	
	public List<Song> getSongs(){
		return this.songs;
	}
	
	
	public static final class SongInstanceCreator implements InstanceCreator<Song>{
		private int position = 0; 
		private Album album;
		public SongInstanceCreator(Album album) {
			this.album = album;
		}
		
		public Song createInstance(Type type) {
			return new Song(position++, this.album);
		}
	}
}
