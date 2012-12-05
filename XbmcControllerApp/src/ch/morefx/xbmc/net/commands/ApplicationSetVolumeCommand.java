package ch.morefx.xbmc.net.commands;


/**
 * Implements the Application.SetMute json command
 * See http://wiki.xbmc.org/index.php?title=JSON-RPC_API/v3#Application.SetVolume
 */
public class ApplicationSetVolumeCommand extends JsonCommand
	implements JsonCommandResponseHandler{
	
	private int volume;
	
	public ApplicationSetVolumeCommand(int volume) {
		super("Application.SetVolume");
		
		this.volume = volume;
	}
	
	@Override
	void prepareCommand(JsonCommandBuilder builder) {
		builder.addParams("volume", this.volume);
	}
	
	public void handleResponse(JsonCommandResponse response) {
		this.volume = Integer.parseInt(response.getRawResult());
	}
	
	public int getVolume(){ 
		return this.volume;
	}
}
