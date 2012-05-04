package ch.morefx.xbmc.activities.home;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import ch.morefx.xbmc.R;
import ch.morefx.xbmc.activities.XbmcActivity;
import ch.morefx.xbmc.model.remotecontrol.RemoteController;

public class RemoteControlActivity extends XbmcActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.remotecontrol_activity_layout);
	   
		GridView gridview = (GridView) findViewById(R.id.gridview);
	    gridview.setAdapter(new ImageAdapter(this, getRemoteController()));
	}
	
	private static class ImageAdapter extends BaseAdapter {
	    private Context mContext;
	    private RemoteController remotecontroller; 
	    
	    public ImageAdapter(Context c, RemoteController remotecontroller) {
	        mContext = c;
	        this.remotecontroller = remotecontroller;
	    }

	    public int getCount() {
	        return mThumbIds.length;
	    }

	    public Object getItem(int position) {
	        return null;
	    }

	    public long getItemId(int position) {
	        return 0;
	    }

	    // create a new ImageView for each item referenced by the Adapter
	    public View getView(int position, View convertView, ViewGroup parent) {
	        
	        Button imageView;
	        
	        if (convertView == null) {  // if it's not recycled, initialize some attributes
	            imageView = new Button(mContext);
	            imageView.setText("My Button " + position);
	            imageView.setTag(mThumbIds[position]);
	            imageView.setBackgroundDrawable(mContext.getResources().getDrawable(mThumbIds[position]));
		        imageView.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						
						if (v.getTag().equals(R.drawable.navigation_up))
							remotecontroller.up();
						if (v.getTag().equals(R.drawable.navigation_down))
							remotecontroller.down();
						if (v.getTag().equals(R.drawable.navigation_left))
							remotecontroller.left();
						if (v.getTag().equals(R.drawable.navigation_right))
							remotecontroller.right();
						if (v.getTag().equals(R.drawable.navigation_select))
							remotecontroller.select();
						if (v.getTag().equals(R.drawable.navigation_shutdown))
							remotecontroller.shutdown();
						if (v.getTag().equals(R.drawable.navigation_reboot))
							remotecontroller.reboot();
						
						if (v.getTag().equals(R.drawable.audiolibraryfolder))
							remotecontroller.back();
						
						if (v.getTag().equals(R.drawable.audioibrary))
							remotecontroller.home();
					}
				});
	            
	        } else {
	            imageView = (Button) convertView;
	        }

	        

	        return imageView;
	    }

	    // references to our images
	    private Integer[] mThumbIds = {
	    		
	    		
	    		// http://www.softicons.com/free-icons/toolbar-icons/soft-scraps-icons-by-deleket
	    		R.drawable.audiolibraryfolder,
	    		R.drawable.navigation_up,
	    		R.drawable.audioibrary,
	    		
	    		R.drawable.navigation_left,
	    		R.drawable.navigation_select,
	    		R.drawable.navigation_right,
	    		
	    		R.drawable.navigation_reboot,
	    		R.drawable.navigation_down, 
	    		R.drawable.navigation_shutdown
	    };
	}
}
