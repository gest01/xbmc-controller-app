package ch.morefx.xbmc.command;

import java.util.Arrays;
import java.util.List;

import ch.morefx.xbmc.model.FileSource;
import ch.morefx.xbmc.util.Check;

/**
 * Implements a JSON-RPC command that queries all directories within a specified path by using the Files.GetDirectory command.
 */
public class GetFileDirectoryCommand
	extends JsonCommand
		implements CommandResponseHandler {
	
	private FileSource source;
	
	public GetFileDirectoryCommand(FileSource source) {
		super("Files.GetDirectory");
		Check.argumentNotNull(source, "source");
		
		this.source = source;
	}
	
	@Override
	void prepareCommand(CommandBuilder builder) {
		builder.addParams("properties", FileSource.getFields());
		builder.addParams("directory", this.source.getFile());
		builder.addParams("media", this.source.getMediaType());
		builder.setSortMethodAscending("file");
	}
	
	public void handleResponse(CommandResponse response) {
		FileSource[] sourceArray = response.asArrayResultWithCreator("files", FileSource[].class, FileSource.class, new FileSourceInstanceCreator(this.source.getMediaType(), this.source));
		this.directories = Arrays.asList(sourceArray);
	}
	
	private List<FileSource> directories;
	
	/**
	 * 
	 * @return
	 */
	public List<FileSource> getDirectories(){
		return this.directories;
	}	
}
