package ch.morefx.xbmc.command;


/**
 * Implements the Application.SetMute json command
 * See http://wiki.xbmc.org/index.php?title=JSON-RPC_API/v3#Application.SetMute
 */
public class ApplicationSetMuteCommand extends JsonCommand
	implements CommandResponseHandler{

	private boolean isMuted;
	
	public ApplicationSetMuteCommand() {
		super("Application.SetMute");
	}
	

	@Override
	void prepareCommand(CommandBuilder builder) {
		builder.addParams("mute", "toggle");
	}
	
	
	public void handleResponse(CommandResponse response) {
		this.isMuted = response.getRawResult().equals("true");
	}
	
	public boolean isMuted(){
		return this.isMuted;
	}
}
