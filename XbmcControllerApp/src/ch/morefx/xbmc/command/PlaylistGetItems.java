package ch.morefx.xbmc.command;

import ch.morefx.xbmc.model.Movie;
import ch.morefx.xbmc.model.Playlist;
import ch.morefx.xbmc.model.Song;

/**
 * Implements the Playlist.GetItems json command
 * See http://wiki.xbmc.org/index.php?title=JSON-RPC_API/v3#Playlist.GetItems
 */
public class PlaylistGetItems extends JsonCommand
	implements CommandResponseHandler{

	private Playlist playlist;
	
	public PlaylistGetItems(Playlist playlist) {
		super("Playlist.GetItems");
		
		this.playlist = playlist;
	}
	
	@Override
	void prepareCommand(CommandBuilder builder) {
		builder.addParams("playlistid", this.playlist.getPlaylistId());
		builder.addParams("properties", this.playlist == Playlist.Audio ? Song.getSongFields() : Movie.getMovieFields());
	}
	
	public void handleResponse(CommandResponse response) {
		
	}
	
//	private String[] getMovieFields(){
//		return new String[] {
//				"comment", "tvshowid", "set", "lyrics", "albumartist", "duration", 
//				"setid", "album",  "votes", "mpaa", "writer", "albumid", "plotoutline", 
//				"track", "season", "musicbrainztrackid", "imdbnumber", "studio", "showlink", 
//				"showtitle", "episode", "musicbrainzartistid", "productioncode", "country", 
//				"premiered", "originaltitle", "cast", "artistid", "firstaired", "tagline", "top250", "trailer"
//		};
//	}
	
}
