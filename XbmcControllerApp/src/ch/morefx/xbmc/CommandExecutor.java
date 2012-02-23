package ch.morefx.xbmc;

import ch.morefx.xbmc.command.JsonCommand;

public interface CommandExecutor {

	void execute(JsonCommand command);
	
	String executeDirect(String command);
}
