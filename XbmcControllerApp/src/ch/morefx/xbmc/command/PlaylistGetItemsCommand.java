package ch.morefx.xbmc.command;

import java.util.Arrays;
import java.util.List;

import ch.morefx.xbmc.command.GetSongsCommand.SongInstanceCreator;
import ch.morefx.xbmc.model.Movie;
import ch.morefx.xbmc.model.Playlist;
import ch.morefx.xbmc.model.Song;

/**
 * Implements the Playlist.GetItems json command
 * See http://wiki.xbmc.org/index.php?title=JSON-RPC_API/v3#Playlist.GetItems
 */
public class PlaylistGetItemsCommand extends JsonCommand
	implements JsonCommandResponseHandler{

	private Playlist playlist;
	
	public PlaylistGetItemsCommand(Playlist playlist) {
		super("Playlist.GetItems");
		
		this.playlist = playlist;
	}
	
	@Override
	void prepareCommand(JsonCommandBuilder builder) {
		builder.addParams("playlistid", this.playlist.getPlaylistId());
		builder.addParams("properties", this.playlist == Playlist.Audio ? Song.getSongFields() : Movie.getMovieFields());
	}
	
	public void handleResponse(JsonCommandResponse response) {
		
		if (this.playlist == Playlist.Audio){
			Song[] songsArray = response.asArrayResultWithCreator("items", Song[].class, Song.class, new SongInstanceCreator(null));
			this.songs = Arrays.asList(songsArray);
		}
	}	
	
	private List<Song> songs;
	
	public List<Song> getSongs(){
		return this.songs;
	}
}
