package ch.morefx.xbmc.util;

import ch.morefx.xbmc.XbmcConnection;
import ch.morefx.xbmc.model.Playlist;
import ch.morefx.xbmc.net.CommandExecutorAdapter;
import ch.morefx.xbmc.net.JsonCommandExecutor;
import ch.morefx.xbmc.net.XbmcConnector;
import ch.morefx.xbmc.net.commands.PlayerGetActivePlayersCommand;
import ch.morefx.xbmc.net.commands.PlaylistGetItemsCommand;

public class CurrentPlayingThread {
	
	private XbmcConnection connection;
	
	public CurrentPlayingThread(XbmcConnection connection) {
		Check.argumentNotNull(connection, "connection");
		
		this.connection = connection;
	}
	
	public void whatIsCurrentPlaying(){
		Thread thread = new Thread(new Runnable() {
			public void run() {
				
				XbmcConnector connector = CurrentPlayingThread.this.connection.getConnector();
				PlayerGetActivePlayersCommand command = new PlayerGetActivePlayersCommand();
				
				CommandExecutorAdapter c = new CommandExecutorAdapter(new JsonCommandExecutor(connector));
				c.execute(command);
				
				PlaylistGetItemsCommand p = new PlaylistGetItemsCommand(Playlist.Audio);
				c.execute(p);
				
			}
		});
		
		thread.start();
	}
}
