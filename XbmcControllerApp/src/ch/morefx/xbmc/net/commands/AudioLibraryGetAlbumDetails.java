package ch.morefx.xbmc.net.commands;

import ch.morefx.xbmc.model.Album;

/**
 * Implements the AudioLibrary.GetAlbumDetails json command
 * See http://wiki.xbmc.org/index.php?title=JSON-RPC_API/v3#AudioLibrary.GetAlbumDetails
 */
public class AudioLibraryGetAlbumDetails extends JsonCommand
	implements JsonCommandResponseHandler{
	
	private int albumid;
	
	public AudioLibraryGetAlbumDetails(int albumid) {
		super("AudioLibrary.GetAlbumDetails");
		
		this.albumid = albumid;
	}
	
	@Override
	void prepareCommand(JsonCommandBuilder builder) {
		builder.addParams("albumid", albumid);
		builder.addParams("properties", Album.getAlbumFields() );
	}
	
	public void handleResponse(JsonCommandResponse response) {
		album = response.asObjectResult(Album.class, "albumdetails");
	}
	
	private Album album;
	
	public Album getAlbum(){
		return album;
	}
}
