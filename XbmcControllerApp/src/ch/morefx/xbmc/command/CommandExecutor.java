package ch.morefx.xbmc.command;


public interface CommandExecutor {

	void execute(JsonCommand command) throws CommandExecutorException;
	
	void executeAsync(JsonCommand command);
	
	void executeAsync(JsonCommand ... commands);
}
