package ch.morefx.xbmc.activities.musiclibrary;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import ch.morefx.xbmc.R;
import ch.morefx.xbmc.XbmcConnection;
import ch.morefx.xbmc.XbmcRemoteControlApplication;
import ch.morefx.xbmc.model.Song;
import ch.morefx.xbmc.model.players.AudioPlayer;

// http://www.ryanharter.com/blog/?p=108

public class SongArrayAdapter extends ArrayAdapter<Song> {

	private LayoutInflater inflater;
	
	public SongArrayAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
		
		this.inflater = LayoutInflater.from(context);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		
		if (convertView == null) {
			convertView = this.inflater.inflate(R.layout.song_list_item, parent, false);
			holder = new ViewHolder();
			holder.title = (TextView) convertView.findViewById(R.id.title);
			holder.detail = (TextView) convertView.findViewById(R.id.detail);
			holder.image = (ImageView) convertView.findViewById(R.id.image);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		
		Song song = getItem(position);
		
		XbmcRemoteControlApplication app = (XbmcRemoteControlApplication)getContext().getApplicationContext();
		XbmcConnection connection = app.getCurrentConnection();
		AudioPlayer player = connection.getAudioPlayer();
		boolean isPlaying = player.isPlaying(song);

		holder.title.setTypeface(null, isPlaying ? Typeface.BOLD_ITALIC : Typeface.NORMAL);
		holder.title.setText(song.getTrack() + ". " + song.getLabel() );
		holder.detail.setText(song.getFilename());
		
		if (isPlaying){
			holder.title.setTypeface(null, Typeface.BOLD_ITALIC );
			holder.image.setImageDrawable(getContext().getResources().getDrawable(R.drawable.playing));
		} else {
			holder.image.setImageDrawable(getContext().getResources().getDrawable(R.drawable.songicon));
			holder.title.setTypeface(null, Typeface.NORMAL);
		}
		
		return convertView;
	}
	
  	private static class ViewHolder {
  		ImageView image;
 		TextView title;
 		TextView detail;
 	}
}
