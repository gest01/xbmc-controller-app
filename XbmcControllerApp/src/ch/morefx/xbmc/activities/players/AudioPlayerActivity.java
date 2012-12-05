package ch.morefx.xbmc.activities.players;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import ch.morefx.xbmc.R;
import ch.morefx.xbmc.activities.XbmcActivity;
import ch.morefx.xbmc.activities.home.HomeScreenActivity;
import ch.morefx.xbmc.model.PlayerProperties;
import ch.morefx.xbmc.model.Song;
import ch.morefx.xbmc.net.commands.ApplicationSetVolumeCommand;
import ch.morefx.xbmc.util.ExtrasHelper;
import ch.morefx.xbmc.util.ThumbnailLoader;

public class AudioPlayerActivity extends XbmcActivity {
	
	private ImageButton btnPause;
	private SynchronisationThread syncthread;
	private ProgressBar progressBar;
	private TextView currentView, totalView, txtSongname, txtArtistname;
	private ImageView ivAlbumCover;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.player_activity_layout);
		
		this.syncthread = new AudioPlayerSyncThread();
		
		SeekBar sb =(SeekBar) findViewById(R.id.skbVolume);
		sb.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			public void onStopTrackingTouch(SeekBar seekBar) {
				ApplicationSetVolumeCommand penner = new ApplicationSetVolumeCommand(seekBar.getProgress());
				penner.executeAsync();
			}
			
			public void onStartTrackingTouch(SeekBar seekBar) {
			}
			
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
			}
		});
				
				
				
		progressBar = (ProgressBar) findViewById(R.id.progressBar1);
		btnPause = (ImageButton) findViewById(R.id.btnPause);
		currentView = (TextView) findViewById(R.id.txtCurrentTime);
		totalView = (TextView) findViewById(R.id.txtTotalTime);
		
		ivAlbumCover = (ImageView)findViewById(R.id.albumCover);
		txtSongname = (TextView)findViewById(R.id.txtSongname);
		txtArtistname = (TextView)findViewById(R.id.txtArtist);
		
		
		ImageButton btnPrevious = (ImageButton) findViewById(R.id.btnPrevious);
		ImageButton btnNext = (ImageButton) findViewById(R.id.btnNext);
		
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
	protected void onResume() {
		super.onResume();
		this.syncthread.start(new Handler() {
			@Override public void handleMessage(Message msg) {
				PlayerProperties propeties = ExtrasHelper.tryGetExtra(msg.getData(), AudioPlayerSyncThread.BUNDLE_ARGS, PlayerProperties.class);
				if (propeties != null){
					updateSongStatus(propeties);	
				}
			}
		} );
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		this.syncthread.stop();
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
	
	
	private void updateSongStatus(PlayerProperties propeties){
		
		PlayerProperties.TimeStamp total = propeties.getTotalTime();
		PlayerProperties.TimeStamp current = propeties.getCurrentTime();

		progressBar.setMax(total.getTotalSeconds());
		progressBar.setProgress(current.getTotalSeconds());
		
		currentView.setText(current.toString());
		totalView.setText(total.toString());
	}
	

	private void updateSongInformation(){

		if (!getAudioPlayer().isActive()){

			Intent intent = new Intent(this, HomeScreenActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);

			
		} else {
			
			Song song = getAudioPlayer().getCurrentSong();
			
			txtSongname.setText(song.getLabel());
			txtArtistname.setText(song.getArtistString());
			
			ThumbnailLoader loader = new ThumbnailLoader(song, this);
			loader.loadIntoView(ivAlbumCover);
		}
	}
}
