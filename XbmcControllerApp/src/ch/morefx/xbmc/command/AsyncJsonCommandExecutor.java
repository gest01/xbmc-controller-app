package ch.morefx.xbmc.command;

import ch.morefx.xbmc.CommandExecutor;
import ch.morefx.xbmc.XbmcExceptionHandler;
import ch.morefx.xbmc.util.Check;

final class AsyncJsonCommandExecutor {
	
	private CommandExecutor executor;
	
	public AsyncJsonCommandExecutor(CommandExecutor executor) {
		Check.argumentNotNull(executor, "executor");
		this.executor = executor;
	}
	
	public void execute(JsonCommand command){
		Check.argumentNotNull(command, "command");
		
		executeAsync(command, false);
	}
	
	public void execute(JsonCommand ... commands){
		Check.argumentNotNull(commands, "commands");

		for(JsonCommand command : commands){
			executeAsync(command, true);
		}
	}
	
	private void executeAsync(final JsonCommand command, boolean waitForCompletion){
		Thread executionScope = new Thread(new Runnable() {
			public void run() {
				executor.execute(command);
			}
		});
		executionScope.start();
		
		if (waitForCompletion){
			try {
				executionScope.join();
			} catch(InterruptedException iex){
				XbmcExceptionHandler.handleException("AsyncJsonCommandExecutor", "*** InterruptedException ***", iex);
			}
		}
	}
}
