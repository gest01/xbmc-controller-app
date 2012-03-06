package ch.morefx.xbmc.activities.musiclibrary;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import ch.morefx.xbmc.R;
import ch.morefx.xbmc.model.FileSource;

public class FileSourceArrayAdapter extends ArrayAdapter<FileSource>{
	
	public LayoutInflater inflater;
	
	public FileSourceArrayAdapter(Context context, int textViewResourceId, List<FileSource> sources) {
		super(context, textViewResourceId, sources);
		this.inflater = LayoutInflater.from(context);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		
		if (convertView == null) {
			convertView = this.inflater.inflate(R.layout.filesource_list_item, parent, false);
			holder = new ViewHolder();
			holder.title = (TextView) convertView.findViewById(R.id.title);
			holder.detail = (TextView) convertView.findViewById(R.id.detail);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		FileSource fileSource = getItem(position);
		
		holder.title.setText(fileSource.getLabel());
		holder.detail.setText(fileSource.getFile());
		
		return convertView;
	}
	
  	private static class ViewHolder {
 		TextView title;
 		TextView detail;
 		
 	}
}
