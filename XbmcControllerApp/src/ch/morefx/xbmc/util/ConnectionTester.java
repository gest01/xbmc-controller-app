package ch.morefx.xbmc.util;

import android.os.Handler;
import ch.morefx.xbmc.ConnectionDescriptor;
import ch.morefx.xbmc.XbmcConnection;
import ch.morefx.xbmc.XbmcExceptionHandler;
import ch.morefx.xbmc.net.CommandExecutor;
import ch.morefx.xbmc.net.CommandExecutorException;
import ch.morefx.xbmc.net.JsonCommandExecutor;
import ch.morefx.xbmc.net.commands.PingCommand;

public class ConnectionTester {

	public static final int CONNECTION_OK = 1;
	public static final int CONNECTION_NOK = 0;
	
	private boolean isOk = false;
	
	public void canConnect(final ConnectionDescriptor descriptor, final Handler handler){ 
		Check.argumentNotNull(descriptor, "descriptor");
		Check.argumentNotNull(handler, "handler");
		
		isOk = false;

		Thread connectionThread = new Thread(new Runnable() {
			public void run() {
				try {
					
					XbmcConnection connection = new XbmcConnection(descriptor);
					
					CommandExecutor executor = new JsonCommandExecutor(connection.getConnector());
					PingCommand ping = new PingCommand();
					executor.execute(ping);
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
