package ch.morefx.xbmc.model.players;

import ch.morefx.xbmc.model.Album;
import ch.morefx.xbmc.model.Artist;
import ch.morefx.xbmc.model.Song;
import ch.morefx.xbmc.net.XbmcConnector;
import ch.morefx.xbmc.util.Check;

public class AudioPlayer extends MediaPlayer {
	
	private Song playingSong;
	
	public AudioPlayer(XbmcConnector connector) {
		super(connector);
	}
	
	public void setPlaying(Song song){
		playingSong = song;
	}
	
	public boolean isPlaying(Artist artist){
		Check.argumentNotNull(artist, "artist");
		
		if(playingSong != null && isActive()){
			return playingSong.getArtistId() == artist.getArtistId();
		}
		
		return false;
	}
	
	public boolean isPlaying(Album album){
		Check.argumentNotNull(album, "album");
		
		if(playingSong != null && isActive()){
			return playingSong.getAlbumId() == album.getAlbumId();
		}
		
		return false;
	}

	public boolean isPlaying(Song song){
		Check.argumentNotNull(song, "song");
		
		if(playingSong != null && isActive()){
			return playingSong.equals(song);
		}
		
		return false;
	}
	
	public Song getCurrentSong(){
		return this.playingSong;
	}
}
