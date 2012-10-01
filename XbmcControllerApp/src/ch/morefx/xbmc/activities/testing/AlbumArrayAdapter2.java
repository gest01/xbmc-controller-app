package ch.morefx.xbmc.activities.testing;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import ch.morefx.xbmc.R;
import ch.morefx.xbmc.XbmcConnection;
import ch.morefx.xbmc.XbmcRemoteControlApplication;
import ch.morefx.xbmc.activities.musiclibrary.AlbumArrayAdapter;
import ch.morefx.xbmc.model.Album;
import ch.morefx.xbmc.model.players.AudioPlayer;

public class AlbumArrayAdapter2 extends AlbumArrayAdapter{
	
	private LayoutInflater inflater;
	
	public AlbumArrayAdapter2(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
		this.inflater = LayoutInflater.from(context);
	}

	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		
		if (convertView == null) {
			convertView = this.inflater.inflate(R.layout.album_grid_item, parent, false);
			holder = new ViewHolder();
			holder.image = (ImageView) convertView.findViewById(R.id.image);
			holder.title = (TextView) convertView.findViewById(R.id.title);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		Album album = getItem(position);
		
		XbmcRemoteControlApplication app = (XbmcRemoteControlApplication)getContext().getApplicationContext();
		XbmcConnection connection = app.getCurrentConnection();
		AudioPlayer player = connection.getAudioPlayer();
		boolean isPlaying = player.isPlaying(album);
		
		holder.title.setTypeface(null, isPlaying ? Typeface.BOLD_ITALIC : Typeface.NORMAL);
		holder.title.setText(album.getLabel());
		holder.image.setImageDrawable(album.getThumbnail());
		
		return convertView;
	}
	
  	private static class ViewHolder {
 		ImageView image;
 		TextView title;
 	}
}
