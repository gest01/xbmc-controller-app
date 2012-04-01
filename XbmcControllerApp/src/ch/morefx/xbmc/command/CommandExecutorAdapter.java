package ch.morefx.xbmc.command;

import ch.morefx.xbmc.XbmcExceptionHandler;
import ch.morefx.xbmc.util.Check;

public class CommandExecutorAdapter {
	private CommandExecutor executor;
	
	private static final String TAG = "CommandExecutorAdapter";
	
	public CommandExecutorAdapter(CommandExecutor executor) {
		Check.argumentNotNull(executor, "executor");
		
		this.executor = executor;
	}
	
	public void execute(JsonCommand command){
		Check.argumentNotNull(command, "command");
		try {
			
			this.executor.execute(command);
			
		} catch (CommandExecutorException ex) {
			XbmcExceptionHandler.handleException(TAG, ex);
		 
		}
	}
	
	public void executeAsync(JsonCommand ... commands){
		this.executor.executeAsync(commands);
	}
	
	public void executeAsync(JsonCommand command){
		this.executor.executeAsync(command);
	}
}
