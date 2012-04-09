package ch.morefx.xbmc;

import ch.morefx.xbmc.command.CommandExecutor;
import ch.morefx.xbmc.command.CommandExecutorException;
import ch.morefx.xbmc.command.JsonCommandExecutor;
import ch.morefx.xbmc.command.PingCommand;
import ch.morefx.xbmc.util.Check;

public class XbmcConnectionTester {

	private boolean isOk = false;
	
	public boolean canConnect(final XbmcConnection connection){ 
		Check.argumentNotNull(connection, "connection");
		
		isOk = false;
		try {
			Thread connectionThread = new Thread(new Runnable() {
				public void run() {
					runConnectionTest(connection);
				}
			});
			connectionThread.start();
			connectionThread.join();
			
		} catch (InterruptedException e) {
			isOk = false;
		}
		return isOk;
	}
	
	private void runConnectionTest(XbmcConnection connection){

		try {
			CommandExecutor e = new JsonCommandExecutor(connection);
			PingCommand ping = new PingCommand();
			e.execute(ping);
			isOk = ping.isConnectionOk();
		} catch (CommandExecutorException e1) {
			isOk = false;
		}
	}
}
