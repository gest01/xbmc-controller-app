package ch.morefx.xbmc.activities.musiclibrary;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import ch.morefx.xbmc.R;
import ch.morefx.xbmc.model.Album;

public class AlbumArrayAdapter extends ArrayAdapter<Album>{
	
	private LayoutInflater inflater;
	
	public AlbumArrayAdapter(Context context, int textViewResourceId, List<Album> albums) {
		super(context, textViewResourceId, albums);
		this.inflater = LayoutInflater.from(context);
	}

	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		
		if (convertView == null) {
			convertView = this.inflater.inflate(R.layout.album_list_item, parent, false);
			holder = new ViewHolder();
			holder.image = (ImageView) convertView.findViewById(R.id.image);
			holder.title = (TextView) convertView.findViewById(R.id.title);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		Album album = getItem(position);
		
		holder.title.setText(album.getLabel());
		holder.image.setImageDrawable(album.getThumbnail());
		
		return convertView;
	}
	
  	private static class ViewHolder {
 		ImageView image;
 		TextView title;
 	}
}
