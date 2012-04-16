package ch.morefx.xbmc.net.commands;

import ch.morefx.xbmc.model.Song;

/**
 * Implements the AudioLibrary.GetSongDetails json command
 * See http://wiki.xbmc.org/index.php?title=JSON-RPC_API/v3#AudioLibrary.GetSongDetails
 */
public class AudioLibraryGetSongDetailsCommand extends JsonCommand
	implements JsonCommandResponseHandler{

	private int songid;
	
	public AudioLibraryGetSongDetailsCommand(int songId) {
		super("AudioLibrary.GetSongDetails");
		
		this.songid = songId;
	}
	
	@Override
	void prepareCommand(JsonCommandBuilder builder) {
		builder.addParams("songid", songid);
		builder.addParams("properties", Song.getSongFields());
	}
	
	public void handleResponse(JsonCommandResponse response) {
		song = response.asObjectResult(Song.class, "songdetails");
	}
	
	private Song song;
	
	public Song getSong(){
		return this.song;
	}
}
