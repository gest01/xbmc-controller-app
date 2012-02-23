package ch.morefx.xbmc.screens.home;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import ch.morefx.xbmc.R;

public class HomeScreenMenuItemAdapter extends ArrayAdapter<HomeScreenMenuItem> {

	private List<HomeScreenMenuItem> items;

	public HomeScreenMenuItemAdapter(Context context, int textViewResourceId, ArrayList<HomeScreenMenuItem> items) {
		super(context, textViewResourceId, items);
		this.items = items;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View v = convertView;
		if (v == null) {
			LayoutInflater vi = LayoutInflater.from(getContext());
			v = vi.inflate(R.layout.homescreenactivity_item_layout, null);
		}

		HomeScreenMenuItem mnuItem = this.items.get(position);
		if (mnuItem != null) {
			TextView description = (TextView) v.findViewById(R.id.homescreenactivity_item_description);
			TextView title = (TextView)v.findViewById(R.id.homescreenactivity_item_title);
			description.setText(mnuItem.getDescription());
			title.setText(mnuItem.getTitle());
		}
		return v;
	}
}
