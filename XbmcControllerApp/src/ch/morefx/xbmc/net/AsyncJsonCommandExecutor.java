package ch.morefx.xbmc.net;

import android.os.Handler;
import ch.morefx.xbmc.XbmcExceptionHandler;
import ch.morefx.xbmc.net.commands.JsonCommand;
import ch.morefx.xbmc.util.Check;

public final class AsyncJsonCommandExecutor {
	
	private static final String TAG ="AsyncJsonCommandExecutor";
	
	private CommandExecutor executor;
	
	public AsyncJsonCommandExecutor(CommandExecutor executor) {
		Check.argumentNotNull(executor, "executor");
		this.executor = executor;
	}
	
	public void execute(JsonCommand command){
		Check.argumentNotNull(command, "command");
		
		executeAsync(command, false);
	}
	
	public void execute(final JsonCommand command, final Handler handler){
		Check.argumentNotNull(command, "command");
		Check.argumentNotNull(handler, "handler");
		
		Thread executionScope = new Thread(new Runnable() {
			public void run() {
				try {
					executor.execute(command);
					handler.sendEmptyMessage(0);
				} catch(CommandExecutorException cex){
					XbmcExceptionHandler.handleException(TAG, "*** InterruptedException ***", cex);
				}
			}
		});
		
		executionScope.start();
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
				try {
					executor.execute(command);
				} catch(CommandExecutorException cex){
					XbmcExceptionHandler.handleException(TAG, "*** InterruptedException ***", cex);
				}
			}
		});
		executionScope.start();
		
		if (waitForCompletion){
			try {
				executionScope.join();
			} catch(InterruptedException iex){
				XbmcExceptionHandler.handleException(TAG, "*** InterruptedException ***", iex);
			}
		}
	}
}
