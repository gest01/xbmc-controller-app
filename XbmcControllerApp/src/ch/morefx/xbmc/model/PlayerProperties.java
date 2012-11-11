package ch.morefx.xbmc.model;

public class PlayerProperties {
	
	private int position;
	private TimeStamp current, total;
	
	public int getPosition(){
		return this.position;
	}
	
	public TimeStamp getCurrentTime(){
		return this.current;
	}
	
	public void setCurrentTime(TimeStamp timestamp){
		this.current = timestamp;
	}
	
	public TimeStamp getTotalTime(){
		return this.total;
	}
	
	public void setTotalTime(TimeStamp timestamp){
		this.total = timestamp;
	}
	
	
	public static class TimeStamp
	{
		private int hours, milliseconds, minutes, seconds;
		
		public int getHours() {
			return hours;
		}
		
		public int getSeconds() {
			return seconds;
		}
		
		public int getMilliseconds() {
			return milliseconds;
		}
		
		public int getMinutes() {
			return minutes;
		}
		
		public int getTotalSeconds(){
			return (hours * 60 * 60) + (minutes * 60) + (seconds);
		}
		
		@Override
		public String toString() {
			return String.format("%02d:%02d:%02d", hours, minutes, seconds);
		}
	}
	
	public static String[] getFields(){
		return new String[] {
				"canrotate"
				,"canrepeat"
				,"speed"
				,"canshuffle"
				,"shuffled"
				,"canmove"
				,"subtitleenabled"
				,"percentage"
				,"type"
				,"repeat"
				,"canseek"
				,"currentsubtitle"
				,"subtitles"
				,"totaltime"
				,"canzoom"
				,"currentaudiostream"
				,"playlistid"
				,"audiostreams"
				,"partymode"
				,"time"
				,"position"
				,"canchangespeed"
		};
	}
}
