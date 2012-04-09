package ch.morefx.xbmc;

import android.os.Handler;
import ch.morefx.xbmc.command.CommandExecutor;
import ch.morefx.xbmc.command.CommandExecutorException;
import ch.morefx.xbmc.command.JsonCommandExecutor;
import ch.morefx.xbmc.command.PingCommand;
import ch.morefx.xbmc.util.Check;

public class XbmcConnectionTester {

	public static final int CONNECTION_OK = 1;
	public static final int CONNECTION_NOK = 0;
	
	private boolean isOk = false;
	
	public void canConnect(final XbmcConnection connection, final Handler handler){ 
		Check.argumentNotNull(connection, "connection");
		
		isOk = false;

		Thread connectionThread = new Thread(new Runnable() {
			public void run() {
				try {
					CommandExecutor e = new JsonCommandExecutor(connection);
					PingCommand ping = new PingCommand();
					e.execute(ping);
					isOk = ping.isConnectionOk();
				} catch (CommandExecutorException e1) {
					isOk = false;
				}
				
				handler.sendEmptyMessage(isOk ? CONNECTION_OK : CONNECTION_NOK);
			}
		});
		connectionThread.start();

	}
}
