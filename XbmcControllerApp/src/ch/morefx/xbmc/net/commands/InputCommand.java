package ch.morefx.xbmc.net.commands;


/**
 * Implements an input comamnd for navigating xbmc
 * See http://wiki.xbmc.org/index.php?title=JSON-RPC_API/v3#Input
 */
public class InputCommand extends JsonCommand {
	
	InputCommand(String inputCommand) {
		super(inputCommand);
	}

	/**
	 * Navigate down in GUI
	 * @return an InputCommand 
	 */
	public static InputCommand downCommand(){
		return new InputCommand("Input.Down");
	}
	
	/**
	 * Navigate up in GUI
	 * @return an InputCommand 
	 */
	public static InputCommand upCommand(){
		return new InputCommand("Input.Up");
	}
	
	/**
	 * Navigate left in GUI
	 * @return an InputCommand 
	 */
	public static InputCommand leftCommand(){
		return new InputCommand("Input.Left");
	}
	
	/**
	 * Navigate right in GUI
	 * @return an InputCommand 
	 */
	public static InputCommand rightCommand(){
		return new InputCommand("Input.Right");
	}
	
	/**
	 * Goes back in GUI.
	 * @return an InputCommand 
	 */
	public static InputCommand backCommand(){
		return new InputCommand("Input.Back");
	}
	
	/**
	 * Goes to home window in GUI
	 * @return an InputCommand 
	 */
	public static InputCommand homeCommand(){
		return new InputCommand("Input.Home");
	}
	
	/**
	 * Select current item in GUI
	 * @return an InputCommand 
	 */
	public static InputCommand selectCommand(){
		return new InputCommand("Input.Select");
	}
}
