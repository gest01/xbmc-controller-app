package ch.morefx.xbmc.command;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import ch.morefx.xbmc.model.Album;
import ch.morefx.xbmc.model.Artist;

import com.google.gson.InstanceCreator;

public class GetAlbumsCommand extends JsonCommand
	implements CommandResponseHandler {

	private Artist artist;

	public GetAlbumsCommand(Artist artist) {
		super("AudioLibrary.GetAlbums");

		this.artist = artist;
	}

	@Override
	void prepareCommand(CommandBuilder builder) {
		builder.addParams("artistid", this.artist.getId());
		builder.addParams("properties", Album.getAlbumFields());
		builder.setSortMethod("album");
	}
		
	public void handleResponse(CommandResponse response) {
		Album[] albumsArray = response.asArrayResultWithCreator("albums", Album[].class, Album.class, new AlbumInstanceCreator(artist));
		this.albums = Arrays.asList(albumsArray);
	}

	private List<Album> albums;
	
	public List<Album> getAlbums(){
		return this.albums;
	}
	
	
	private final class AlbumInstanceCreator implements InstanceCreator<Album>{
		private Artist artist;
		public AlbumInstanceCreator(Artist artist) {
			this.artist = artist;
		}
		
		public Album createInstance(Type type) {
			return new Album(artist);
		}
	}
}

