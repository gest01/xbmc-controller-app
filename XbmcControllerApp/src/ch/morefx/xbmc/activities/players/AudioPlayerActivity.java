package ch.morefx.xbmc.activities.players;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import ch.morefx.xbmc.R;
import ch.morefx.xbmc.activities.XbmcActivity;
import ch.morefx.xbmc.model.PlayerInfo;
import ch.morefx.xbmc.model.PlayerProperties;
import ch.morefx.xbmc.model.Song;
import ch.morefx.xbmc.model.PlayerInfo.PlayerType;
import ch.morefx.xbmc.net.CommandExecutorAdapter;
import ch.morefx.xbmc.net.JsonCommandExecutor;
import ch.morefx.xbmc.net.commands.PlayerGetPropertiesCommand;
import ch.morefx.xbmc.util.ThumbnailLoader;

public class AudioPlayerActivity extends XbmcActivity {
	
	private ImageButton btnPause;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.player_activity_layout);
		
		final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar1);
		
		ImageButton btnPrevious = (ImageButton) findViewById(R.id.btnPrevious);
		ImageButton btnNext = (ImageButton) findViewById(R.id.btnNext);
		btnPause = (ImageButton) findViewById(R.id.btnPause);
		
		final TextView current = (TextView) findViewById(R.id.txtCurrentTime);
		final TextView total = (TextView) findViewById(R.id.txtTotalTime);
		
		btnPrevious.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				progressBar.setProgress(0);
				getAudioPlayer().previous();
			}
		});
		btnNext.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				progressBar.setProgress(0);
				getAudioPlayer().next();
			}
		});
		btnPause.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				toggleStopPlay();
			}
		});

		updateSongStatus(progressBar, current, total);
		updateSongInformation();
	}
	
	private void toggleStopPlay(){
			
		if (getAudioPlayer().isSuspended()){
			getAudioPlayer().resume();
		} else {
			getAudioPlayer().suspend();
		}
	}
	
	@Override
	public void onPlayerUpdate() {
		updateSongInformation();
		
		if (getAudioPlayer().isSuspended()){
			btnPause.setImageResource(android.R.drawable.ic_media_play);
		} else {
			btnPause.setImageResource(android.R.drawable.ic_media_pause);
		}
	}
	
	private void updateSongStatus(final ProgressBar progressBar, final TextView currentView, final TextView totalView ){
		Thread th = new Thread(new Runnable() {
			public void run() {
				
				while(true){
					
					PlayerGetPropertiesCommand command = new
							PlayerGetPropertiesCommand( new PlayerInfo(PlayerType.Audio, 0) );
					
					JsonCommandExecutor executor = new JsonCommandExecutor(getConnection().getConnector());
					CommandExecutorAdapter adapter = new CommandExecutorAdapter(executor);
					adapter.execute(command);
					
					final PlayerProperties.TimeStamp total = command.getProperties().getTotalTime();
					final PlayerProperties.TimeStamp current = command.getProperties().getCurrentTime();

					progressBar.setMax(total.getTotalSeconds());
					progressBar.setProgress(current.getTotalSeconds());
					
					currentView.post(new Runnable() {
						public void run() {
							currentView.setText(current.toString());
						}
					});
					
					totalView.post(new Runnable() {
						public void run() {
							totalView.setText(total.toString());
						}
					});
					
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		
		th.start();
	}
	
	private void updateSongInformation(){
		
		ImageView iv = (ImageView)findViewById(R.id.albumCover);
		TextView txtSongname = (TextView)findViewById(R.id.txtSongname);
		TextView txtArtistname = (TextView)findViewById(R.id.txtArtist);
		
		Song song = getAudioPlayer().getCurrentSong();
		
		txtSongname.setText(song.getLabel());
		txtArtistname.setText(song.getArtistString());
		
		ThumbnailLoader loader = new ThumbnailLoader(song, this);
		loader.loadIntoView(iv);
		
	}
}
