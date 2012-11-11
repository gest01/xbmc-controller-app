package ch.morefx.xbmc.model.players;

import ch.morefx.xbmc.net.CommandExecutorAdapter;
import ch.morefx.xbmc.net.JsonCommandExecutor;
import ch.morefx.xbmc.net.XbmcConnector;
import ch.morefx.xbmc.net.commands.JsonCommand;
import ch.morefx.xbmc.net.commands.PlayerGoNextCommand;
import ch.morefx.xbmc.net.commands.PlayerGoPreviousCommand;
import ch.morefx.xbmc.net.commands.PlayerPlayPauseCommand;
import ch.morefx.xbmc.net.commands.PlayerStopCommand;
import ch.morefx.xbmc.util.Check;

public class MediaPlayer {

	private static final int DISABLED_PLAYER_ID = -1;
	
	private int playerId;
	private XbmcConnector connector;
	private boolean isSuspended;
	
	public MediaPlayer(XbmcConnector connector) {
		Check.argumentNotNull(connector, "connector");
		
		this.connector = connector;
		this.playerId = DISABLED_PLAYER_ID;
		this.isSuspended = false;
	}
	
	/**
	 * Gets the underlying xbmc connector.
	 * @return An instance of an object that implements the XbmcConnector interface.
	 */
	public XbmcConnector getConnector(){
		return this.connector;
	}
	
	public void updatePlayer(int playerid){
		this.playerId = playerid;
	}
	
	
	
	public void setSuspended(boolean isSuspended){
		this.isSuspended = isSuspended;
	}
	
	public boolean isSuspended(){
		return this.isSuspended;
	}
	
	/**
	 * Disables the player and stops all.
	 */
	public void disable(){
		updatePlayer(DISABLED_PLAYER_ID);
	}
	
	/**
	 * Gets a flag that indicates wether this player is running and active or not.
	 * @return True when player is active and running (playing something), false otherwise.
	 */
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
	
	protected void execute(JsonCommand command){
		CommandExecutorAdapter adapter = new CommandExecutorAdapter(new JsonCommandExecutor(connector));
		adapter.execute(command);
	}
	
	protected void executeAsync(JsonCommand ... commands){
		CommandExecutorAdapter adapter = new CommandExecutorAdapter(new JsonCommandExecutor(connector));
		adapter.executeAsync(commands);
	}
	
	protected void executeAsync(JsonCommand command){
		CommandExecutorAdapter adapter = new CommandExecutorAdapter(new JsonCommandExecutor(connector));
		adapter.executeAsync(command);
	}
}
