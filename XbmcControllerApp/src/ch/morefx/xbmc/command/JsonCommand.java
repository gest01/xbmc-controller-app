package ch.morefx.xbmc.command;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public abstract class JsonCommand {

	private CommandBuilder builders;
	
	JsonCommand(String method) {
		this.builders = new CommandBuilder(method);
	}
	
	public final String prepareCommand(){
		prepareCommand(this.builders);
		
		Gson gson = new GsonBuilder()
			.setPrettyPrinting()
			.enableComplexMapKeySerialization()
			.create();
		
		return gson.toJson(this.builders);
	}

	void prepareCommand(CommandBuilder builder){
		
	}

}
