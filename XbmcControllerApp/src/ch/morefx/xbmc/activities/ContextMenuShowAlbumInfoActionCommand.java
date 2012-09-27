package ch.morefx.xbmc.activities;

import android.app.Dialog;
import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;
import ch.morefx.xbmc.R;
import ch.morefx.xbmc.model.Album;
import ch.morefx.xbmc.util.ThumbnailLoader;

public class ContextMenuShowAlbumInfoActionCommand 	
	extends ContextMenuArrayAdapterActionCommand<Album> {
	
	@Override
	protected boolean execute(IXbmcActivity activity, Album selectedItem) {
		
		Context mContext = activity.getContext();
		Dialog dialog = new Dialog(mContext);

		dialog.setContentView(R.layout.album_info_dialog);
		dialog.setTitle(selectedItem.getLabel());

		TextView text = (TextView) dialog.findViewById(R.id.text);
		text.setText("TODO");
		ImageView image = (ImageView) dialog.findViewById(R.id.image);
		
		ThumbnailLoader loader = new ThumbnailLoader(selectedItem, mContext);
		loader.loadIntoView(image);
		
		dialog.show();
		
		return true;
	}
}