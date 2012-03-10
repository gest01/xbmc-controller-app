package ch.morefx.xbmc.activities.videolibrary;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import ch.morefx.xbmc.R;
import ch.morefx.xbmc.model.Movie;

public class MovieArrayAdapter extends ArrayAdapter<Movie> {
	
	private LayoutInflater inflater;
	
	public MovieArrayAdapter(Context context, int textViewResourceId, List<Movie> movies) {
		super(context, textViewResourceId, movies);
		this.inflater = LayoutInflater.from(context);
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder holder;
		if (convertView == null) {
			convertView = this.inflater.inflate(R.layout.movie_list_item, parent, false);
			holder = new ViewHolder();
			holder.image = (ImageView) convertView.findViewById(R.id.image);
			holder.title = (TextView) convertView.findViewById(R.id.title);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		Movie movie = getItem(position);
		holder.title.setText(movie.getLabel());
		holder.image.setImageDrawable(movie.getThumbnail());
		
		return convertView;
	}
	
	
  	private static class ViewHolder {
 		ImageView image;
 		TextView title;
 	}
}
