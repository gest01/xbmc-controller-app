package ch.morefx.xbmc.activities.sourcebrowser;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import ch.morefx.xbmc.activities.XbmcListActivity;
import ch.morefx.xbmc.model.FileSource;
import ch.morefx.xbmc.model.loaders.FileSourceLoader;
import ch.morefx.xbmc.model.loaders.PostExecuteHandler;

public class SourceBrowserActivity 
	extends XbmcListActivity {

	private FileSourceArrayAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getListView().setTextFilterEnabled(true);
		
		this.adapter = new FileSourceArrayAdapter(this, android.R.layout.simple_list_item_1);
		setListAdapter(adapter);
		
		new FileSourceLoader(this)
		 .setPostExecuteHandler(new PostExecuteHandler<List<FileSource>>() {
			public void onPostExecute(List<FileSource> result) {
				populateList(result);
			}})
		.execute();
	}
	
	
	private void populateList(List<FileSource> result){
		this.adapter.clear();
		if (result != null){
			this.adapter.addAll(result);
		}
	}
	
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		FileSource filesource = (FileSource)getListAdapter().getItem(position);
		Intent intent = new Intent(this, DirectoryBrowserActivity.class);
		intent.putExtra(DirectoryBrowserActivity.EXTRA_FILESOURCE, filesource);
		startActivity(intent);
	}
}
