package ch.morefx.xbmc.model;

import ch.morefx.xbmc.CommandExecutor;
import ch.morefx.xbmc.XbmcConnection;
import ch.morefx.xbmc.command.JsonCommand;
import ch.morefx.xbmc.command.JsonCommandExecutor;
import ch.morefx.xbmc.util.Check;

/**
 * Defines the base class for all Libraries 
 */
public class XbmcLibrary {
	
	private CommandExecutor executor;
	
	public XbmcLibrary(XbmcConnection connection) {
		Check.argumentNotNull(connection, "connection");

		this.executor = new JsonCommandExecutor(connection);
	}
	
	protected void execute(JsonCommand command){
		this.executor.execute(command);
	}
	
	protected void executeAsync(JsonCommand ... commands){
		this.executor.executeAsync(commands);
	}
	
	protected void executeAsync(JsonCommand command){
		this.executor.executeAsync(command);
	}
}
