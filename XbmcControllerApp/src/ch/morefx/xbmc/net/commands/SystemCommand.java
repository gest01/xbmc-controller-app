package ch.morefx.xbmc.net.commands;

/**
 * Implements a system command
 * See http://wiki.xbmc.org/index.php?title=JSON-RPC_API/v3#System
 */
public class SystemCommand extends JsonCommand {

	SystemCommand(String systemCommand) {
		super(systemCommand);
	}
	
	/**
	 * Reboots the system running XBMC
	 * @return SystemCommand
	 */
	public static SystemCommand rebootCommand(){
		return new SystemCommand("System.Reboot");
	}
	
	/**
	 * Shuts the system running XBMC down
	 * @return SystemCommand
	 */
	public static SystemCommand shutdownCommand(){
		return new SystemCommand("System.Shutdown");
	}
}
