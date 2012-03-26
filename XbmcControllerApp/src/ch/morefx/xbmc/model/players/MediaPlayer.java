package ch.morefx.xbmc.model.players;

public class MediaPlayer {

	private static final int DISABLED_PLAYER_ID = -1;
	
	private int playerId;
	
	public MediaPlayer() {
		this.playerId = DISABLED_PLAYER_ID;
	}
	
	public void updatePlayer(int playerid){
		this.playerId = playerid;
	}
	
	public void disable(){
		updatePlayer(DISABLED_PLAYER_ID);
	}
	
	public boolean isActive(){
		return this.playerId != DISABLED_PLAYER_ID;
	}
	
	public int getPlayerId(){
		return this.playerId;
	}
	
	public void next(){
		
	}
	
	public void previous(){
		
	}
	
	public void suspend(){
		
	}
	
	public void resume(){
		
	}
	
	public void stop(){
		
	}
}
