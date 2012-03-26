package ch.morefx.xbmc.model;

public class PlayerInfo {

	public enum PlayerType {
		Audio, Video, Picture
	};
	
	private int playerid;
	private PlayerType type;
	
	public PlayerInfo(PlayerType type, int playerid) {
		this.type = type;
		this.playerid = playerid;
	}
	
	public int getPlayerId(){
		return playerid;
	}
	
	public PlayerType getType(){
		return this.type;
	}
}
