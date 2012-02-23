package ch.morefx.xbmc.model;

public enum Playlist {
	
	Video(1), Audio(0);
	
	private int playlistId;
	
	private Playlist(int playlistId){
		this.playlistId = playlistId;
	}
	
	public int getPlaylistId(){
		return this.playlistId;
	}
}
