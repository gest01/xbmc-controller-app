//package ch.morefx.xbmc.activities.musiclibrary;
//
//import java.util.List;
//
//import android.app.Activity;
//import android.app.ListFragment;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.ArrayAdapter;
//import android.widget.ListView;
//import ch.morefx.xbmc.model.Artist;
//import ch.morefx.xbmc.model.loaders.ArtistLoader;
//import ch.morefx.xbmc.model.loaders.PostExecuteHandler;
//
//public class ArtistFragment extends ListFragment {
//	
//	private ArrayAdapter<Artist> listAdapter;
//	private OnArtistSelectedListener artistListener;
//
//	@Override
//	public void onAttach(Activity activity) {
//		super.onAttach(activity);
//        try {
//        	this.artistListener = (OnArtistSelectedListener) activity;
//        } catch (ClassCastException e) { }
//	}
//	
//	@Override
//	public void onActivityCreated(Bundle savedInstanceState) {
//		super.onActivityCreated(savedInstanceState);
//		
//		new ArtistLoader(getActivity())
//			.setPostExecuteHandler(new PostExecuteHandler<List<Artist>>() {
//				public void onPostExecute(List<Artist> result) {
//					ArtistFragment.this.listAdapter.addAll(result);
//				}})
//			.execute();
//	}
//	
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//				
//		this.listAdapter = new ArrayAdapter<Artist>(getActivity(), android.R.layout.simple_list_item_1);
//		setListAdapter(this.listAdapter);
//	}
//	
//	@Override
//	public void onListItemClick(ListView l, View v, int position, long id) {
//		Artist artist = this.listAdapter.getItem(position);
//		fireOnArtistSelected(artist);
//	}
//	
//	/**
//	 * Fires the onArtistSelected event when a listener interface was attached to it.
//	 * @param artist The artist selected by the user.
//	 */
//	private void fireOnArtistSelected(Artist artist){
//		if (this.artistListener != null){
//			this.artistListener.onArtistSelected(artist);
//		}
//	}
//
//	public interface OnArtistSelectedListener {
//        public void onArtistSelected(Artist artist);
//    }
//}
//
