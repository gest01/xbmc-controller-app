package ch.morefx.xbmc.net;

import ch.morefx.xbmc.net.commands.JsonCommand;


public interface CommandExecutor {

	void execute(JsonCommand command) throws CommandExecutorException;
	
	void executeAsync(JsonCommand command);
	
	void executeAsync(JsonCommand ... commands);
}
