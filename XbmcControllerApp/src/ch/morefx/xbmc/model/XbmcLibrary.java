package ch.morefx.xbmc.model;

import ch.morefx.xbmc.command.JsonCommand;
import ch.morefx.xbmc.net.CommandExecutorAdapter;
import ch.morefx.xbmc.net.JsonCommandExecutor;
import ch.morefx.xbmc.net.XbmcConnector;
import ch.morefx.xbmc.util.Check;

/**
 * Defines the base class for all Libraries 
 */
public class XbmcLibrary {
	
	private CommandExecutorAdapter executor;
	
	public XbmcLibrary(XbmcConnector connector) {
		Check.argumentNotNull(connector, "connector");

		this.executor = new CommandExecutorAdapter(new JsonCommandExecutor(connector));
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
