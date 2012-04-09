package ch.morefx.xbmc.model.players;

import android.util.Log;
import ch.morefx.xbmc.XbmcConnection;
import ch.morefx.xbmc.command.CommandExecutorAdapter;
import ch.morefx.xbmc.command.JsonCommandExecutor;
import ch.morefx.xbmc.command.PlayerGoNextCommand;
import ch.morefx.xbmc.command.PlayerGoPreviousCommand;
import ch.morefx.xbmc.command.PlayerPlayPauseCommand;
import ch.morefx.xbmc.command.PlayerStopCommand;
import ch.morefx.xbmc.util.Check;

public class MediaPlayer {

	private static final String TAG = "MediaPlayer";
	
	private static final int DISABLED_PLAYER_ID = -1;
	
	private int playerId;
	private XbmcConnection connection;
	
	public MediaPlayer(XbmcConnection connection) {
		Check.argumentNotNull(connection, "connection");
		this.connection = connection;
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
		CommandExecutorAdapter adapter = new CommandExecutorAdapter(new JsonCommandExecutor(connection));
		adapter.execute(new PlayerGoNextCommand(this));
	}
	
	public void previous(){
		CommandExecutorAdapter adapter = new CommandExecutorAdapter(new JsonCommandExecutor(connection));
		adapter.execute(new PlayerGoPreviousCommand(this));
	}
	
	public void suspend(){
		CommandExecutorAdapter adapter = new CommandExecutorAdapter(new JsonCommandExecutor(connection));
		adapter.execute(new PlayerPlayPauseCommand(this));
	}
	
	public void resume(){
		CommandExecutorAdapter adapter = new CommandExecutorAdapter(new JsonCommandExecutor(connection));
		adapter.execute(new PlayerPlayPauseCommand(this));
	}
	
	public void stop(){
		CommandExecutorAdapter adapter = new CommandExecutorAdapter(new JsonCommandExecutor(connection));
		adapter.execute(new PlayerStopCommand(this));
		
		disable();
	}
}
