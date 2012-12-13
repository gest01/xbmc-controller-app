package ch.morefx.xbmc.net;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import android.graphics.drawable.Drawable;
import android.util.Log;
import ch.morefx.xbmc.ConnectionDescriptor;
import ch.morefx.xbmc.util.Check;

// http://wiki.xbmc.org/index.php?title=JSON-RPC_API


class XbmcSocketConnector implements XbmcConnector {
	private ConnectionDescriptor descriptor;
	
	public XbmcSocketConnector(ConnectionDescriptor descriptor) {
		Check.argumentNotNull(descriptor, "descriptor");
		
		this.descriptor = descriptor;
	}
	
	/* (non-Javadoc)
	 * @see ch.morefx.xbmc.net.XbmcConnector#downloadImage(java.lang.String)
	 */
	public Drawable downloadImage(String url) throws IOException {
		return null;
	}
	
	/* (non-Javadoc)
	 * @see ch.morefx.xbmc.net.XbmcConnector#send(java.lang.String)
	 */
	public String send(String jsonCommand) throws Exception {

		Log.d("XbmcSocketConnector", "SEND : " + jsonCommand);

		createSocket();
		
		out.write(jsonCommand);
		out.flush();

		char[] buffer = new char[1024];

		int read = 0, totalread = 0;

		StringBuffer sb = new StringBuffer();	
		while ((read = in.read(buffer)) > -1) {
			sb.append(buffer, 0, read);
			totalread += read;
			if (read < buffer.length)
				break;
		}

		String tmp = sb.toString();

		Log.d("XbmcSocketConnector", "RECV(" + totalread + ") : " + tmp);

		return tmp;
	}
	
	private InputStreamReader in;
	private OutputStreamWriter out;
	
	private void createSocket() throws Exception {
		if (in == null || out == null) {
			Socket socket = new Socket(this.descriptor.getHost(), ConnectionDescriptor.DEFAULT_JSON_RPC_PORT);
			in = new InputStreamReader(socket.getInputStream());
			out = new OutputStreamWriter(socket.getOutputStream());
		}
	}
}
