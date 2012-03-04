//package ch.morefx.xbmc.activities.musiclibrary;
//
//import android.app.Activity;
//import android.os.Bundle;
//import ch.morefx.xbmc.R;
//import ch.morefx.xbmc.XbmcRemoteControlApplication;
//import ch.morefx.xbmc.model.Album;
//import ch.morefx.xbmc.model.Artist;
//import ch.morefx.xbmc.model.AudioLibrary;
//import ch.morefx.xbmc.model.Song;
//import ch.morefx.xbmc.util.Check;
//
//public class MusicLibraryActivity extends Activity
//	implements ArtistFragment.OnArtistSelectedListener, 
//			   AlbumFragment.OnAlbumSelectedListener,
//			   SongFragment.OnSongSelectedListener{
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		 setContentView(R.layout.musiclibrary_activity_layout);
//	}
//	
//	public void onSongSelected(Song song) {
//		Check.notNull(song, "song is null");
//		
//		XbmcRemoteControlApplication app = (XbmcRemoteControlApplication)getApplicationContext();
//		AudioLibrary library = app.getCurrentConnection().getAudioLibrary();
//		library.playSong(song);
//	}
//
//	public void onAlbumSelected(Album album) {
//		Check.notNull(album, "album is null");
//		SongFragment f = (SongFragment)getFragmentManager().findFragmentById(R.id.musiclibrary_songfragment);
//		f.loadFromAlbum(album);
//	}
//	
//	public void onArtistSelected(Artist artist) {
//		Check.notNull(artist, "artist is null");
//		AlbumFragment f = (AlbumFragment)getFragmentManager().findFragmentById(R.id.musiclibrary_albumfragment);
//		f.loadFromArtist(artist);
//	}
//}
//
