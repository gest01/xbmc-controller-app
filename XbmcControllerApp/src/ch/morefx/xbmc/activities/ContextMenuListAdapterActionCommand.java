package ch.morefx.xbmc.activities;

import android.app.ListActivity;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListAdapter;

public abstract class ContextMenuListAdapterActionCommand implements ContextMenuAction{

	private ListAdapter listadaper;
	
	public boolean execute(MenuItem menuitem, IXbmcActivity activity) {
		
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuitem.getMenuInfo();
		ListAdapter listadapter = getListAdapter(activity);
		Object selectedItem = listadapter.getItem(info.position);
		return execute(menuitem, activity, selectedItem);
	}

	
	private ListAdapter getListAdapter(IXbmcActivity activity){
		if (listadaper == null){
			if (activity instanceof ListActivity){
				listadaper =  ((ListActivity)activity).getListAdapter();
			}
		}
		
		return listadaper;
	}
	
	
	protected abstract boolean execute(MenuItem menuitem, IXbmcActivity activity, Object selectedItem);
}

