package ch.morefx.xbmc.util;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

public class DrawableManager {
    private final Map<String, Drawable> drawableMap;
    private static final String TAG = "DrawableManager";
    
    private final DefaultHttpClient httpClient;
    
    public DrawableManager(DefaultHttpClient httpClient) {
        drawableMap = new HashMap<String, Drawable>();
        this.httpClient = httpClient;
    }

    /**
     * Fetches a picture from an given Url and creates a Drawable instance of that picture. 
     * If the urlString is not valid or doesn't return a valid image, the fallback will be returned and used for further requests. 
     * @param urlString The url from where to fetch the image
     * @param fallback The fallback drawable
     * @return Drawable
     */
    public Drawable fetchDrawableWithFallback(String urlString, Drawable fallback) {
    	Drawable drawable = fetchDrawable(urlString);
    	if (drawable == null){
    		 drawableMap.put(urlString, fallback);
    		 drawable = fallback;
    	}
    	return drawable;
    }
    
    public Drawable fetchDrawable(String urlString) {
        if (drawableMap.containsKey(urlString)) {
            return drawableMap.get(urlString);
        }

        Log.d(TAG, "image url:" + urlString);
        try {
        	
            InputStream is = fetch(urlString);
            Drawable drawable = Drawable.createFromStream(is, "src");
            if (drawable != null) {
                drawableMap.put(urlString, drawable);
            } else {
              Log.w(TAG, "could not get thumbnail");
            }

            return drawable;
        } catch (MalformedURLException e) {
            Log.e(TAG, "fetchDrawable failed", e);
            return null;
        } catch (IOException e) {
            Log.e(TAG, "fetchDrawable failed", e);
            return null;
        }
    }

    public void fetchDrawableOnThread(final String urlString, final ImageView imageView) {
        if (drawableMap.containsKey(urlString)) {
            imageView.setImageDrawable(drawableMap.get(urlString));
        }

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message message) {
                imageView.setImageDrawable((Drawable) message.obj);
            }
        };

        Thread thread = new Thread() {
            @Override
            public void run() {
                //TODO : set imageView to a "pending" image
                Drawable drawable = fetchDrawable(urlString);
                Message message = handler.obtainMessage(1, drawable);
                handler.sendMessage(message);
            }
        };
        thread.start();
    }

    private InputStream fetch(String urlString) throws MalformedURLException, IOException {
        DefaultHttpClient httpClient = this.httpClient;
        HttpGet request = new HttpGet(urlString);
        HttpResponse response = httpClient.execute(request);
        return response.getEntity().getContent();
    }

}
