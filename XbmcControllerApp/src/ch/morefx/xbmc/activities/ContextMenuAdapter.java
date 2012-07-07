package ch.morefx.xbmc.activities;

import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import ch.morefx.xbmc.util.Check;

public class ContextMenuAdapter {

	private ContextMenu menu;
	private IXbmcActivity activity;
	
	public ContextMenuAdapter(ContextMenu menu, IXbmcActivity activity) {
		Check.argumentNotNull(menu, "menu");
		Check.argumentNotNull(activity, "activity");
		
		this.menu = menu;
		this.activity = activity;
	}
	
	public void setTitle(String menuTitle){
		this.menu.setHeaderTitle(menuTitle);
	}
	
	public void add(String title, final ContextMenuAction action){
		this.menu.add(title).setOnMenuItemClickListener(new OnMenuItemClickListener() {
			public boolean onMenuItemClick(MenuItem item) {
				return action.execute(item, activity);
			}
		});
	}
}
