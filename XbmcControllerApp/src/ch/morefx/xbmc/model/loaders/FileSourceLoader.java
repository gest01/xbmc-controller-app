package ch.morefx.xbmc.model.loaders;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import ch.morefx.xbmc.command.CommandExecutorAdapter;
import ch.morefx.xbmc.command.GetFileSourcesCommand;
import ch.morefx.xbmc.command.JsonCommandExecutor;
import ch.morefx.xbmc.model.FileSource;

public class FileSourceLoader extends AsyncTaskLoader<Void, Void, List<FileSource>> {

	private CommandExecutorAdapter executor;
	
	public FileSourceLoader(Context context) {
		super(context);
		
		this.executor = new CommandExecutorAdapter(new JsonCommandExecutor(getConnection()));
	}
	
	@Override
	protected List<FileSource> doInBackground(Void... params) {
		
		List<FileSource> resultList = new ArrayList<FileSource>();

		executeAndMergeResult(new GetFileSourcesCommand(GetFileSourcesCommand.MEDIA_MUSIC), resultList);
		executeAndMergeResult(new GetFileSourcesCommand(GetFileSourcesCommand.MEDIA_PICTURES), resultList);
		executeAndMergeResult(new GetFileSourcesCommand(GetFileSourcesCommand.MEDIA_VIDEO), resultList);
		
		return resultList;
	}
	
	
	private void executeAndMergeResult(GetFileSourcesCommand command, List<FileSource> result) {
		executor.execute(command);
		for(FileSource fs : command.getFileSources()){
			if (!fs.isAddonSource()){
				result.add(fs);	
			}
		}
	}
}
