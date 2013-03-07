package ch.morefx.xbmc.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;

import android.graphics.drawable.Drawable;
import ch.morefx.xbmc.ConnectionDescriptor;
import ch.morefx.xbmc.util.Check;

/**
 * Implements a XbmcConnector that sends and receives all commands through a Http Connection.
 */
class XbmcHttpConnector implements XbmcConnector {

	private ConnectionDescriptor descriptor;
	
	/**
	 * Defines the connection timeout in miliseconds
	 */
	private static final int CONNECTION_TIMEOUT = 10000; // 10s
	
	public XbmcHttpConnector(ConnectionDescriptor descriptor) {
		Check.argumentNotNull(descriptor, "descriptor");
		this.descriptor = descriptor;
	}
	
	public String send(String jsonCommand) throws Exception {
		
		HttpPost post = new HttpPost(this.descriptor.getConnectionUri());
		StringEntity entity = new StringEntity(jsonCommand);
		entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
		post.setEntity(entity);
		
		HttpResponse response = createHttpClient().execute(post);
		InputStream in = response.getEntity().getContent();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		return reader.readLine();
	}
	
	public Drawable downloadImage(String url) throws IOException {
		
        DefaultHttpClient httpClient = createHttpClient();
        HttpGet request = new HttpGet(url);
        HttpResponse response = httpClient.execute(request);
        InputStream is = response.getEntity().getContent();
        Drawable drawable = Drawable.createFromStream(is, "src");
        is.close();
        return drawable;
	}
	
	private DefaultHttpClient createHttpClient(){
		DefaultHttpClient client = new DefaultHttpClient();
		client.setHttpRequestRetryHandler(new HttpRequestRetryHandlerImpl());
		client.getCredentialsProvider().setCredentials(
				new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT),
				new UsernamePasswordCredentials(this.descriptor.getUsername(), this.descriptor.getPassword()));
		
		HttpConnectionParams.setConnectionTimeout(client.getParams(), CONNECTION_TIMEOUT);
		return client;
	}
}
