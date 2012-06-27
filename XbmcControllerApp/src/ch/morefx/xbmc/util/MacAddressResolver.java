package ch.morefx.xbmc.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.InetAddress;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class MacAddressResolver implements Runnable{
	
	private String mHost = null;
	private String mMac = null;
	private Handler mHandler;
	
	public static final String MESSAGE_MAC_ADDRESS = "MAC_ADDRESS";
	
	public MacAddressResolver(String ipString, Handler handler){
		mHost = ipString;
		mHandler = handler;
	}
	
	public void run(){
		
		new Thread(new Runnable(){
			
			public void run() {
				mMac = arpResolve(mHost);
				Bundle bundle = new Bundle();
				bundle.putString(MESSAGE_MAC_ADDRESS, mMac);
				Message message = new Message();
				message.setData(bundle);
				mHandler.sendMessage(message);
			}
			
		}).start();
		

	}
	
	private String arpResolve(String host){
		System.out.println("ARPRESOLVE HOST: " + host);
		try{
			//Parse it as a proper InetAddress - it might be a hostname. We don't know yet.
			InetAddress inet = InetAddress.getByName(host);
			
			//initiate some sort of traffic to ensure we get an arp entry (if we're on same subnet, that is...)
			inet.isReachable(500); //timeout of 500ms. just to trigger the arp resolution process
			
			//Get the official string representation of the resolved ip address
			String ipString = inet.getHostAddress();
			BufferedReader br = new BufferedReader(new FileReader("/proc/net/arp"));
			String line = "";
			while(true){
				line = br.readLine();
				if (line == null)
					break;
				if(line.startsWith(ipString)){
					br.close();
					System.out.println("ARPRESOLVE MAC:\n" + line);
					return line.split("\\s+")[3]; // 4th word, separated by "whitespace"
				}
			}
			br.close();
			return "";
	
		}catch(Exception e){
			System.out.println(e);
			return "";
		}

	}

}