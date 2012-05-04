package ch.morefx.xbmc;

import android.os.Handler;
import ch.morefx.xbmc.net.CommandExecutor;
import ch.morefx.xbmc.net.CommandExecutorException;
import ch.morefx.xbmc.net.JsonCommandExecutor;
import ch.morefx.xbmc.net.XbmcConnector;
import ch.morefx.xbmc.net.commands.PingCommand;
import ch.morefx.xbmc.util.Check;

public class XbmcConnectionTester {

	public static final int CONNECTION_OK = 1;
	public static final int CONNECTION_NOK = 0;
	
	private boolean isOk = false;
	
	public void canConnect(final XbmcConnector connector, final Handler handler){ 
		Check.argumentNotNull(connector, "connector");
		
		isOk = false;

		Thread connectionThread = new Thread(new Runnable() {
			public void run() {
				try {
					CommandExecutor e = new JsonCommandExecutor(connector);
					PingCommand ping = new PingCommand();
					e.execute(ping);
					isOk = ping.isConnectionOk();
				} catch (CommandExecutorException ex) {
					XbmcExceptionHandler.handleException("XbmcConnectionTester", ex);
					isOk = false;
				}
				
				handler.sendEmptyMessage(isOk ? CONNECTION_OK : CONNECTION_NOK);
			}
		});
		connectionThread.start();

	}
}
