package ch.morefx.xbmc.activities.musiclibrary;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import ch.morefx.xbmc.R;
import ch.morefx.xbmc.XbmcConnection;
import ch.morefx.xbmc.XbmcRemoteControlApplication;
import ch.morefx.xbmc.model.Artist;
import ch.morefx.xbmc.model.players.AudioPlayer;

public class ArtistArrayAdapter extends ArrayAdapter<Artist>{

	private LayoutInflater inflater;
	
	public ArtistArrayAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
		
		this.inflater = LayoutInflater.from(context);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		
		if (convertView == null) {
			convertView = this.inflater.inflate(R.layout.artist_list_item, parent, false);
			holder = new ViewHolder();
			holder.title = (TextView) convertView.findViewById(R.id.title);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		Artist artist = getItem(position);
		
		XbmcRemoteControlApplication app = (XbmcRemoteControlApplication)getContext().getApplicationContext();
		XbmcConnection connection = app.getCurrentConnection();
		AudioPlayer player = connection.getAudioLibrary().getPlayer();
		boolean isPlaying = player.isPlaying(artist);

		holder.title.setTypeface(null, isPlaying ? Typeface.BOLD_ITALIC : Typeface.NORMAL);
		holder.title.setText(artist.toString());
		
		return convertView;
	}
	
  	private static class ViewHolder {
 		TextView title;
 	}
}
