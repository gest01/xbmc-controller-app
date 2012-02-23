package ch.morefx.xbmc.screens.musiclibrary;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import ch.morefx.xbmc.R;
import ch.morefx.xbmc.model.Song;

// http://www.ryanharter.com/blog/?p=108

public class SongArrayAdapter extends ArrayAdapter<Song> {

	private LayoutInflater inflater;
	
	public SongArrayAdapter(Context context, int textViewResourceId, List<Song> songs) {
		super(context, textViewResourceId, songs);
		
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
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		Song song = getItem(position);
		holder.title.setText(song.getTrack() + ". " + song.getLabel() );
		holder.detail.setText(song.getFilename());
		
		return convertView;
	}
	
  	private static class ViewHolder {
 		TextView title;
 		TextView detail;
 	}
}
