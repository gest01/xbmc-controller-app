package ch.morefx.xbmc.net.commands;

/**
 * Implements the Application.Quit json command
 * See http://wiki.xbmc.org/index.php?title=JSON-RPC_API/v3#Application.Quit
 */
public class ApplicationQuitCommand extends JsonCommand {

	public ApplicationQuitCommand() {
		super("Application.Quit");
	}
}
