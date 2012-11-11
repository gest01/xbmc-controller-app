package ch.morefx.xbmc.net.commands;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import ch.morefx.xbmc.model.Album;
import ch.morefx.xbmc.model.Artist;

import com.google.gson.InstanceCreator;


/**
 * Implements the AudioLibrary.GetAlbums notification event
 * See http://wiki.xbmc.org/index.php?title=JSON-RPC_API/v3#AudioLibrary.GetAlbums
 */
public class AudioLibraryGetAlbumsCommand extends JsonCommand
	implements JsonCommandResponseHandler {

	private Artist artist;

	public AudioLibraryGetAlbumsCommand(Artist artist) {
		super("AudioLibrary.GetAlbums");

		this.artist = artist;
	}

	@Override
	void prepareCommand(JsonCommandBuilder builder) {
		builder.addParams("artistid", this.artist.getId());
		builder.addParams("properties", Album.getAlbumFields());
		builder.setSortMethod("album");
	}
		
	public void handleResponse(JsonCommandResponse response) {
		Album[] albumsArray = response.asArrayResultWithCreator("albums", Album[].class, Album.class, new AlbumInstanceCreator(artist));
		this.albums = Arrays.asList(albumsArray);
	}

	private List<Album> albums;
	
	public List<Album> getAlbums(){
		return this.albums;
	}
	
	
	private static final class AlbumInstanceCreator implements InstanceCreator<Album>{
		private Artist artist;
		public AlbumInstanceCreator(Artist artist) {
			this.artist = artist;
		}
		
		public Album createInstance(Type type) {
			return new Album(artist);
		}
	}
}

