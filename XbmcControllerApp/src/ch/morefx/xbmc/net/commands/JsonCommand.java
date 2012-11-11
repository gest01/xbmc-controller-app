package ch.morefx.xbmc.net.commands;

import ch.morefx.xbmc.XbmcConnection;
import ch.morefx.xbmc.XbmcRemoteControlApplication;
import ch.morefx.xbmc.net.CommandExecutorAdapter;
import ch.morefx.xbmc.net.JsonCommandExecutor;
import ch.morefx.xbmc.util.Check;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public abstract class JsonCommand {

	private JsonCommandBuilder builders;
	
	JsonCommand(String method) {
		this.builders = new JsonCommandBuilder(method);
	}
	
	/**
	 * Executes the command against a xbmc connection
	 * @param connection the connection
	 */
	public void execute(XbmcConnection connection){
		Check.argumentNotNull(connection, "connection");
		
		JsonCommandExecutor executor = new JsonCommandExecutor(connection.getConnector());
		CommandExecutorAdapter adapter = new CommandExecutorAdapter(executor);
		adapter.execute(this);
	}
	
	/**
	 * Execute this command using the current active connection 
	 */
	public void execute(){
		XbmcRemoteControlApplication application = XbmcRemoteControlApplication.getInstance();
		XbmcConnection connection = application.getCurrentConnection();
		execute(connection);
	}
	
	public final String prepareCommand(){
		prepareCommand(this.builders);
		
		Gson gson = new GsonBuilder()
			.setPrettyPrinting()
			.enableComplexMapKeySerialization()
			.create();
		
		return gson.toJson(this.builders);
	}

	void prepareCommand(JsonCommandBuilder builder){
		
	}
}
