//package ch.morefx.xbmc.activities.musiclibrary.deprecated;
//
//import android.app.SearchManager;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.ContextMenu;
//import android.view.ContextMenu.ContextMenuInfo;
//import android.view.Menu;
//import android.view.MenuInflater;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ListView;
//import android.widget.SearchView;
//import ch.morefx.xbmc.R;
//import ch.morefx.xbmc.activities.ContextMenuAdapter;
//import ch.morefx.xbmc.activities.ContextMenuAddToPlaylistActionCommand;
//import ch.morefx.xbmc.activities.ContextMenuPlayItemActionCommand;
//import ch.morefx.xbmc.activities.ContextMenuShowArtistInfoActionCommand;
//import ch.morefx.xbmc.activities.XbmcListActivity;
//import ch.morefx.xbmc.model.Artist;
//import ch.morefx.xbmc.model.loaders.ArtistLoader;
//
//public class ArtistActivity 
//	extends XbmcListActivity 
//		implements SearchView.OnQueryTextListener {
//
//	private ArtistArrayAdapter adapter;
//	
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//
//		this.adapter = new ArtistArrayAdapter(this, android.R.layout.simple_list_item_1);
//		setListAdapter(adapter);
//		
//		registerForContextMenu();
//		
//		onPlayerUpdate();
//	}
//	
//	// http://developer.android.com/training/search/setup.html
//
//	public boolean onQueryTextSubmit(String query) {
//		adapter.getFilter().filter(query);
//		return false;
//	}
//
//	public boolean onQueryTextChange(String newText) {
//		adapter.getFilter().filter(newText);
//		return false;
//	}
//	
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		
//		MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.searchview_in_menu, menu);
//        
//	    // Get the SearchView and set the searchable configuration
//	    SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//	    SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
//	    searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//	    searchView.setIconifiedByDefault(true); // Do not iconify the widget; expand it by default
//	    searchView.setSubmitButtonEnabled(true);
//        searchView.setOnQueryTextListener(this);
//	    return true;
//		
//	}
//	
//	@Override
//	public void onPlayerUpdate() {
//		new ArtistLoader(adapter).execute();
//	}
//	
//	@Override
//	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
//		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
//		Artist artist = (Artist)super.getListAdapter().getItem(info.position);
//		
//		ContextMenuAdapter cmadapter = new ContextMenuAdapter(menu, this);
//		cmadapter.setTitle(artist.toString());
//		cmadapter.add("Show artist info", new ContextMenuShowArtistInfoActionCommand());
//		cmadapter.add("Play all titles", new ContextMenuPlayItemActionCommand());
//		cmadapter.add("Add to playlist", new ContextMenuAddToPlaylistActionCommand());
//	}
//	
//
//	@Override
//	public void onListItemClick(ListView l, View v, int position, long id) {
//		Artist artist = (Artist)super.getListAdapter().getItem(position);
//		Intent intent = new Intent(this, AlbumActivity.class);
//		intent.putExtra(AlbumActivity.EXTRA_ARTIST, artist);
//		startActivity(intent);
//	}
//
//}
