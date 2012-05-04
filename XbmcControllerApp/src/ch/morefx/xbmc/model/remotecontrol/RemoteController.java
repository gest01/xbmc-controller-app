package ch.morefx.xbmc.model.remotecontrol;

import ch.morefx.xbmc.net.CommandExecutorAdapter;
import ch.morefx.xbmc.net.JsonCommandExecutor;
import ch.morefx.xbmc.net.XbmcConnector;
import ch.morefx.xbmc.net.commands.InputCommand;
import ch.morefx.xbmc.net.commands.SystemCommand;
import ch.morefx.xbmc.util.Check;

public class RemoteController {

	private CommandExecutorAdapter commandadapter;
	
	public RemoteController(XbmcConnector connector) {
		Check.argumentNotNull(connector, "connector");
		
		commandadapter = new CommandExecutorAdapter(new JsonCommandExecutor(connector));
	}
	
	public void up(){
		sendCommand(InputCommand.upCommand());
	}
	
	public void down(){
		sendCommand(InputCommand.downCommand());
	}
	
	public void left(){
		sendCommand(InputCommand.leftCommand());
	}
	
	public void right(){
		sendCommand(InputCommand.rightCommand());
	}
	
	public void home(){
		sendCommand(InputCommand.homeCommand());
	}
	
	public void back(){
		sendCommand(InputCommand.backCommand());
	}
	
	public void select(){
		sendCommand(InputCommand.selectCommand());
	}
	
	public void reboot(){
		sendCommand(SystemCommand.rebootCommand());
	}
	
	public void shutdown(){
		sendCommand(SystemCommand.shutdownCommand());
	}

	private void sendCommand(InputCommand command){
		commandadapter.executeAsync(command);
	}
	
	private void sendCommand(SystemCommand systemcommand){
		commandadapter.executeAsync(systemcommand);
	}
}
