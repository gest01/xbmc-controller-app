package ch.morefx.xbmc.model;


public class ApplicationProperties {

	private boolean muted;
	private int volume;
	private Version version;
	
	public ApplicationProperties(boolean muted, int volume, Version version) {
		this.muted = muted;
		this.volume = volume;
		this.version = version;
	}
	
	public Version getVersion() {
		return version;
	}
	
	public boolean isMuted() {
		return muted;
	}
	
	public int getVolume() {
		return volume;
	}
	
	public String getVersionString(){
		return this.version.toString();
	}
	
	public static class Version {
		int major, minor;
		String tag;
		
		@Override
		public String toString() {
			return String.format("%s.%s-%s", major, minor, tag);
		}
	}
}
