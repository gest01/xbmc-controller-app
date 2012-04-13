package ch.morefx.xbmc.net.commands;

/**
 * Implements the Ping command by using the JSONRPC.Ping method. Returns 'pong' when connection is succesfull
 * See http://wiki.xbmc.org/index.php?title=JSON-RPC_API/v3#JSONRPC.Ping
 */
public class PingCommand extends JsonCommand
	implements JsonCommandResponseHandler{

	private boolean ok;
	
	public PingCommand() {
		super("JSONRPC.Ping");
	}
	
	public void handleResponse(JsonCommandResponse response) {
		this.ok = response.getRawResult().equals("pong");
	}
	
	public boolean isConnectionOk(){
		return this.ok;
	}
}
