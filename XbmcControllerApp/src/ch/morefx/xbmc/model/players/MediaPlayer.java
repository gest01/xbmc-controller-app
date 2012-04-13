package ch.morefx.xbmc.model.players;

import android.util.Log;
import ch.morefx.xbmc.command.PlayerGoNextCommand;
import ch.morefx.xbmc.command.PlayerGoPreviousCommand;
import ch.morefx.xbmc.command.PlayerPlayPauseCommand;
import ch.morefx.xbmc.command.PlayerStopCommand;
import ch.morefx.xbmc.net.CommandExecutorAdapter;
import ch.morefx.xbmc.net.JsonCommandExecutor;
import ch.morefx.xbmc.net.XbmcConnector;
import ch.morefx.xbmc.util.Check;

public class MediaPlayer {

	private static final String TAG = "MediaPlayer";
	
	private static final int DISABLED_PLAYER_ID = -1;
	
	private int playerId;
	private XbmcConnector connector;
	
	public MediaPlayer(XbmcConnector connector) {
		Check.argumentNotNull(connector, "connector");
		
		this.connector = connector;
		this.playerId = DISABLED_PLAYER_ID;
	}
	
	public void updatePlayer(int playerid){
		
		Log.i(TAG, "Old playerID : " + this.playerId + "; new playerId :" + playerid);
		
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
		CommandExecutorAdapter adapter = new CommandExecutorAdapter(new JsonCommandExecutor(connector));
		adapter.executeAsync(new PlayerGoNextCommand(this));
	}
	
	public void previous(){
		CommandExecutorAdapter adapter = new CommandExecutorAdapter(new JsonCommandExecutor(connector));
		adapter.executeAsync(new PlayerGoPreviousCommand(this));
	}
	
	public void suspend(){
		CommandExecutorAdapter adapter = new CommandExecutorAdapter(new JsonCommandExecutor(connector));
		adapter.executeAsync(new PlayerPlayPauseCommand(this));
	}
	
	public void resume(){
		CommandExecutorAdapter adapter = new CommandExecutorAdapter(new JsonCommandExecutor(connector));
		adapter.executeAsync(new PlayerPlayPauseCommand(this));
	}
	
	public void stop(){
		CommandExecutorAdapter adapter = new CommandExecutorAdapter(new JsonCommandExecutor(connector));
		adapter.executeAsync(new PlayerStopCommand(this));
		
		disable();
	}
}
