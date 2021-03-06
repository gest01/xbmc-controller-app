package ch.morefx.xbmc.activities;

import android.app.Dialog;
import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;
import ch.morefx.xbmc.R;
import ch.morefx.xbmc.model.Artist;
import ch.morefx.xbmc.util.ThumbnailLoader;


public class ContextMenuShowArtistInfoActionCommand
	extends ContextMenuArrayAdapterActionCommand<Artist> {
	
	@Override
	protected boolean execute(IXbmcActivity activity, Artist selectedItem) {
		
		Context mContext = activity.getContext();
		Dialog dialog = new Dialog(mContext);

		dialog.setContentView(R.layout.artist_info_dialog);
		dialog.setTitle(selectedItem.getLabel());

		TextView text = (TextView) dialog.findViewById(R.id.text);
		text.setText(selectedItem.getDescription());
		ImageView image = (ImageView) dialog.findViewById(R.id.image);
		
		ThumbnailLoader loader = new ThumbnailLoader(selectedItem, mContext);
		loader.loadIntoView(image);
		
		dialog.show();
		
		return true;
	}
}