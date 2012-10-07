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
import ch.morefx.xbmc.model.Album;
import ch.morefx.xbmc.model.players.AudioPlayer;

public class AlbumArrayAdapter extends ArrayAdapter<Album>{
	
	private LayoutInflater inflater;
	private int viewResourceId;
	
	public AlbumArrayAdapter(Context context, int textViewResourceId) {
		this(context, textViewResourceId, R.layout.album_list_item);
	}

	public AlbumArrayAdapter(Context context, int textViewResourceId, int viewResourceId) {
		super(context, textViewResourceId);
		this.inflater = LayoutInflater.from(context);
		this.viewResourceId = viewResourceId;
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		
		if (convertView == null) {
			convertView = this.inflater.inflate(this.viewResourceId, parent, false);
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
	
	@Override
	public int getPosition(Album item) {
		for(int position = 0 ; position < getCount(); position++){
			Album a = getItem(position);
			if (a.getAlbumId() == item.getAlbumId())
				return position;
		}
		
		return super.getPosition(item);
	}
	
  	private static class ViewHolder {
 		ImageView image;
 		TextView title;
 	}
}
