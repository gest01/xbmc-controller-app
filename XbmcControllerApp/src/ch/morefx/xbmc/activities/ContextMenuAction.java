package ch.morefx.xbmc.activities;

import android.view.MenuItem;

public interface ContextMenuAction {

	boolean execute(MenuItem menuitem, IXbmcActivity activity);
}
