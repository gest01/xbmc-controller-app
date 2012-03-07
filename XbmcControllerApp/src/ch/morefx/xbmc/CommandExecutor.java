package ch.morefx.xbmc;

import ch.morefx.xbmc.command.JsonCommand;

public interface CommandExecutor {

	void execute(JsonCommand command);
	
	void executeAsync(JsonCommand command);
	
	void executeAsync(JsonCommand ... commands);
	
	String executeDirect(String command);
}
