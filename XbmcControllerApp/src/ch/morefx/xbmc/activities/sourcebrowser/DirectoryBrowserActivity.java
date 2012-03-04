package ch.morefx.xbmc.activities.sourcebrowser;

import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import ch.morefx.xbmc.activities.XbmcListActivity;
import ch.morefx.xbmc.model.FileSource;
import ch.morefx.xbmc.model.loaders.FileDirectoryLoader;
import ch.morefx.xbmc.model.loaders.PostExecuteHandler;
import ch.morefx.xbmc.util.Check;

public class DirectoryBrowserActivity 
	extends XbmcListActivity {
	
	public static final String EXTRA_FILESOURCE = "EXTRA_FILESOURCE";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		FileSource filesource = (FileSource)getIntent().getExtras().getSerializable(EXTRA_FILESOURCE);
		Check.notNull(filesource, "FileSource object missing from Intent extras");
		
		loadList(filesource);
	}
	
	private void loadList(FileSource filesource){
		new FileDirectoryLoader(this)
		 .setPostExecuteHandler(new PostExecuteHandler<List<FileSource>>() {
			public void onPostExecute(List<FileSource> result) {
				populateList(result);
			}})
		.execute(filesource);
	}
	
	private void populateList(List<FileSource> result){
		if (result != null){
			setListAdapter(new ArrayAdapter<FileSource>(getApplication(), android.R.layout.simple_list_item_1, result));
		}
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		FileSource filesource = (FileSource)getListAdapter().getItem(position);
		if (filesource.isDirectory()){
			loadList(filesource);	
		} else {
			Toast.makeText(l.getContext(), "PLAY " + filesource.toString(), Toast.LENGTH_LONG);
		}
		
	}
}
