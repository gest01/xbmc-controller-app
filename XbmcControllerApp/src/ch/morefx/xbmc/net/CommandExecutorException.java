package ch.morefx.xbmc.net;

public class CommandExecutorException extends Exception{

	private static final long serialVersionUID = 1L;

	public CommandExecutorException() {
		super();
	}
	
	public CommandExecutorException(String detailMessage) {
		super(detailMessage);
	}
	
	public CommandExecutorException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}
	
	public CommandExecutorException(Throwable throwable) {
		super(throwable);
	}
}
