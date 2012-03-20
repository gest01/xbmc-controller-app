package ch.morefx.xbmc.model;

public class PlayerProperties {
	
	private int position;
	
	public int getPosition(){
		return this.position;
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
