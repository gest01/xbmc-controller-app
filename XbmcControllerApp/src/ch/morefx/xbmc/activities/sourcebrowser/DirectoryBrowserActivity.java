package ch.morefx.xbmc.activities.sourcebrowser;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import ch.morefx.xbmc.activities.XbmcListActivity;
import ch.morefx.xbmc.model.FileSource;
import ch.morefx.xbmc.model.loaders.FileDirectoryLoader;
import ch.morefx.xbmc.model.loaders.PostExecuteHandler;
import ch.morefx.xbmc.util.Check;

public class DirectoryBrowserActivity 
	extends XbmcListActivity {
	
	public static final String EXTRA_FILESOURCE = "EXTRA_FILESOURCE";
	
	private FileSourceArrayAdapter adapter;
	private FileSource current;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		FileSource filesource = (FileSource)getIntent().getExtras().getSerializable(EXTRA_FILESOURCE);
		Check.notNull(filesource, "FileSource object missing from Intent extras");
		
		this.adapter = new FileSourceArrayAdapter(this, android.R.layout.simple_list_item_1);
		setListAdapter(this.adapter);
		
		loadList(filesource);
	}
	
	private void loadList(FileSource filesource){
		this.current = filesource;
		new FileDirectoryLoader(this)
		 .setPostExecuteHandler(new PostExecuteHandler<List<FileSource>>() {
			public void onPostExecute(List<FileSource> result) {
				populateList(result);
			}})
		.execute(filesource);
	}
	
	private void populateList(List<FileSource> result){
		this.adapter.clear();
		if (result != null){
			this.adapter.addAll(result);
		}
	}
	
	@Override
	public void onBackPressed() {
		if (this.current != null && this.current.hasParent()){
			FileSource parent = this.current.getParent();
			loadList(parent);
		} else {
			Intent intent = new Intent(this, SourceBrowserActivity.class);
			startActivity(intent);
		}
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		FileSource filesource = (FileSource)getListAdapter().getItem(position);
		if (filesource.isDirectory()){
			loadList(filesource);	
		} else {
			getAudioPlayer().playSong(filesource);
		}
	}
}
