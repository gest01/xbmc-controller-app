package ch.morefx.xbmc.net.commands;

import java.util.Arrays;
import java.util.List;

import ch.morefx.xbmc.model.Artist;


/**
 * Implements the AudioLibrary.GetArtists json command
 * See http://wiki.xbmc.org/index.php?title=JSON-RPC_API/v3#AudioLibrary.GetArtists
 */
public class AudioLibraryGetArtistsCommand extends JsonCommand 
	implements JsonCommandResponseHandler {

	public AudioLibraryGetArtistsCommand() {
		super("AudioLibrary.GetArtists");
	}

	@Override
	void prepareCommand(JsonCommandBuilder builder) {
		builder.addParams("properties", Artist.getArtistFields());
		builder.setSortMethod("artist");
	}
	
	public void handleResponse(JsonCommandResponse response) {
		Artist[] artistArray = response.asArrayResult("artists", Artist[].class);
		this.artists = Arrays.asList(artistArray);
	}

	private List<Artist> artists;

	public List<Artist> getArtists() {
		return this.artists;
	}
}
