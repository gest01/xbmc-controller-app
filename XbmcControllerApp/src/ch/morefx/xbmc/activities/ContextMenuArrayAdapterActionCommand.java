package ch.morefx.xbmc.activities;

import android.view.MenuItem;


public abstract class ContextMenuArrayAdapterActionCommand<T>
	extends ContextMenuListAdapterActionCommand {
	
	@SuppressWarnings("unchecked")
	@Override
	protected boolean execute(MenuItem menuitem, IXbmcActivity activity, Object selectedItem) {
		return execute(activity, (T)selectedItem);
	}
	
	protected abstract boolean execute(IXbmcActivity activity, T selectedItem);
}