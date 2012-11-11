package ch.morefx.xbmc.net.commands;

import ch.morefx.xbmc.model.Playlist;
import ch.morefx.xbmc.model.Song;

/**
 * 
 */
public class PlayerGetItemCommand extends JsonCommand 
	implements JsonCommandResponseHandler{

	private Playlist playlist;
	
	public PlayerGetItemCommand(Playlist playlist) {
		super("Player.GetItem");
		
		this.playlist = playlist;
	}
	
	@Override
	void prepareCommand(JsonCommandBuilder builder) {
		if (playlist == Playlist.Audio){
			builder.addParams("playerid", playlist.getPlaylistId());
			builder.addParams("properties", Song.getSongFields());
		} else {
			throw new Error("unhandled playlist " + playlist);
		}
	}			
	public void handleResponse(JsonCommandResponse response) {
		song = response.asObjectResult(Song.class, "item");
	}
	
	private Song song;
	
	/**
	 * Gets the current playing song from the audio player.
	 * @return Song currently playing, or null
	 */
	public Song getCurrentSong(){
		return song;
	}
}
