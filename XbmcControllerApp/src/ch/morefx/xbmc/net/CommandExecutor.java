package ch.morefx.xbmc.net;

import ch.morefx.xbmc.command.JsonCommand;


public interface CommandExecutor {

	void execute(JsonCommand command) throws CommandExecutorException;
	
	void executeAsync(JsonCommand command);
	
	void executeAsync(JsonCommand ... commands);
}
