package ch.morefx.xbmc.command;


/**
 * Implements the Application.SetMute json command
 * See http://wiki.xbmc.org/index.php?title=JSON-RPC_API/v3#Application.SetMute
 */
public class ApplicationSetMuteCommand extends JsonCommand
	implements JsonCommandResponseHandler{

	private boolean isMuted;
	
	public ApplicationSetMuteCommand() {
		super("Application.SetMute");
	}
	

	@Override
	void prepareCommand(JsonCommandBuilder builder) {
		builder.addParams("mute", "toggle");
	}
	
	
	public void handleResponse(JsonCommandResponse response) {
		this.isMuted = response.getRawResult().equals("true");
	}
	
	public boolean isMuted(){
		return this.isMuted;
	}
}
