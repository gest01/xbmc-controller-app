package ch.morefx.xbmc.command;

import java.util.Arrays;
import java.util.List;

import ch.morefx.xbmc.model.FileSource;

/**
 * Implements a JSON-RPC command that queries all file sources by using the Files.GetSources command.
 */
public class GetFileSourcesCommand extends JsonCommand
	implements CommandResponseHandler {
	
	public static final String MEDIA_MUSIC = "music";
	public static final String MEDIA_VIDEO = "video";
	public static final String MEDIA_PICTURES = "pictures";

	private String media;
	
	public GetFileSourcesCommand(String media) {
		super("Files.GetSources");
		this.media = media;
	}
	
	@Override
	void prepareCommand(CommandBuilder builder) {
		builder.addParams("media", this.media);
	}
	
	public void handleResponse(CommandResponse response) {
		FileSource[] sourceArray = response.asArrayResultWithCreator("sources", FileSource[].class, FileSource.class, new FileSourceInstanceCreator(this.media));
		this.filesources = Arrays.asList(sourceArray);
	}


	private List<FileSource> filesources;
	
	/**
	 * 
	 * @return
	 */
	public List<FileSource> getFileSources(){
		return this.filesources;
	}
	
}
