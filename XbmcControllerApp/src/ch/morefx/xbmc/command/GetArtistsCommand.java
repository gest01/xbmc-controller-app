package ch.morefx.xbmc.command;

import java.util.Arrays;
import java.util.List;

import ch.morefx.xbmc.model.Artist;

public class GetArtistsCommand extends JsonCommand 
	implements CommandResponseHandler {

	public GetArtistsCommand() {
		super("AudioLibrary.GetArtists");
	}

	@Override
	void prepareCommand(CommandBuilder builder) {
		builder.addParams("properties", Artist.getArtistFields());
		builder.setSortMethod("artist");
	}
	
	public void handleResponse(CommandResponse response) {
		Artist[] artistArray = response.asArrayResult("artists", Artist[].class);
		this.artists = Arrays.asList(artistArray);
	}

	private List<Artist> artists;

	public List<Artist> getArtists() {
		return this.artists;
	}
}
