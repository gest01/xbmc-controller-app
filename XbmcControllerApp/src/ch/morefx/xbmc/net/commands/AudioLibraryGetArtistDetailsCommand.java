package ch.morefx.xbmc.net.commands;

import ch.morefx.xbmc.model.Artist;

/**
 * Implements the AudioLibrary.GetArtistDetails json command
 * See http://wiki.xbmc.org/index.php?title=JSON-RPC_API/v3#AudioLibrary.GetArtistDetails
 */
public class AudioLibraryGetArtistDetailsCommand  extends JsonCommand
	implements JsonCommandResponseHandler{

	int artistid;
	
	public AudioLibraryGetArtistDetailsCommand(Artist artist) {
		this(artist.getArtistId());
	}
	
	public AudioLibraryGetArtistDetailsCommand(int artistid) {
		super("AudioLibrary.GetArtistDetails");
		
		this.artistid = artistid;
	}
	
	@Override
	void prepareCommand(JsonCommandBuilder builder) {
		builder.addParams("artistid", artistid);
		builder.addParams("properties", Artist.getArtistFields());
	}
	
	public void handleResponse(JsonCommandResponse response) {
		artist = response.asObjectResult(Artist.class, "artistdetails");
	}
	
	private Artist artist;
	
	public Artist getArtistDetails(){
		return this.artist;
	}
}
