package ch.morefx.xbmc.net.commands;

import ch.morefx.xbmc.model.ApplicationProperties;


/**
 * Implements the AudioLibrary.GetProperties json command
 * See http://wiki.xbmc.org/index.php?title=JSON-RPC_API/v3#Application.GetProperties
 */
public class ApplicationGetPropertiesCommand extends JsonCommand
	implements JsonCommandResponseHandler{

	
	public ApplicationGetPropertiesCommand() {
		super("Application.GetProperties");
	}
	
	@Override
	void prepareCommand(JsonCommandBuilder builder) {
		builder.addParams("properties", new String[] { "muted", "volume", "version" } );
	}
	
	public void handleResponse(JsonCommandResponse response) {
		int volume = response.asIntegerResult("volume");
		boolean muted = response.asBooleanResult("muted");
		ApplicationProperties.Version version = response.asObjectResult(ApplicationProperties.Version.class, "version");
		
		properties = new ApplicationProperties(muted, volume, version);
	}
	
	private ApplicationProperties properties;

	public ApplicationProperties getProperties() {
		return properties;
	}
}
