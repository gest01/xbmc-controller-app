package ch.morefx.xbmc.model.remotecontrol;

import ch.morefx.xbmc.net.commands.InputCommand;
import ch.morefx.xbmc.net.commands.JsonCommand;
import ch.morefx.xbmc.net.commands.SystemCommand;

public class RemoteController {

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

	private void sendCommand(JsonCommand command){
		command.executeAsync();
	}
}
