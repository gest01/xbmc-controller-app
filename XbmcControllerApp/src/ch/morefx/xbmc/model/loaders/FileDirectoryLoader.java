package ch.morefx.xbmc.model.loaders;

import java.util.List;

import android.content.Context;
import ch.morefx.xbmc.command.CommandExecutorAdapter;
import ch.morefx.xbmc.command.GetFileDirectoryCommand;
import ch.morefx.xbmc.command.JsonCommandExecutor;
import ch.morefx.xbmc.model.FileSource;
import ch.morefx.xbmc.util.Check;

public class FileDirectoryLoader extends AsyncTaskLoader<FileSource, Void, List<FileSource>> {
	
	public FileDirectoryLoader(Context context) {
		super(context);
	}
	
	@Override
	protected List<FileSource> doInBackground(FileSource... params) {
		Check.assertion(params.length == 1, "a root filesource is mandatory!");
		
		FileSource fs = params[0];
		
		GetFileDirectoryCommand command = new GetFileDirectoryCommand(fs);
		CommandExecutorAdapter executor = new CommandExecutorAdapter(new JsonCommandExecutor(getConnection()));
		executor.execute(command);
		
		return command.getDirectories();
	}
}
