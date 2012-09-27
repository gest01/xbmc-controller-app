package ch.morefx.xbmc.model.players;

import ch.morefx.xbmc.model.Movie;
import ch.morefx.xbmc.model.Playlist;
import ch.morefx.xbmc.net.XbmcConnector;
import ch.morefx.xbmc.net.commands.PlayerOpenCommandAdapter;
import ch.morefx.xbmc.net.commands.PlaylistAddCommand;
import ch.morefx.xbmc.net.commands.PlaylistClearCommand;
import ch.morefx.xbmc.util.Check;

public class VideoPlayer extends MediaPlayer{
	public VideoPlayer(XbmcConnector connector) {
		super(connector);
	}
	
	public void playMovie(Movie movie) {
		Check.argumentNotNull(movie, "movie");
		
		executeAsync(
				 new PlaylistClearCommand(Playlist.Video),
				 new PlaylistAddCommand(movie),
				 new PlayerOpenCommandAdapter(movie));
	}
}
