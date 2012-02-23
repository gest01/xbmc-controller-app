package ch.morefx.xbmc;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import ch.morefx.xbmc.preferences.ApplicationPreferenceActivity;
import ch.morefx.xbmc.screens.home.HomeScreenActivity;
import ch.morefx.xbmc.util.DialogUtility;

public class XbmcControllerMainActivity extends Activity {
	
	private XbmcRemoteControlApplication application;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        this.application = (XbmcRemoteControlApplication)getApplication();
    }
    
    @Override
    protected void onStart() {
    	super.onStart();
    	ensureXbmcConnections();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_options_menu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
      switch (item.getItemId()) {
	      case R.id.mnu_connections:
        	   Intent intent = new Intent(XbmcControllerMainActivity.this, ApplicationPreferenceActivity.class);
        	   startActivity(intent);
	        return true;
	      default:
	        return super.onOptionsItemSelected(item);
       }
    }
    
    private void ensureXbmcConnections(){

    	XbmcConnection defaultConnection = this.application.getConnectionManager().getDefaultConnection();
    	if (defaultConnection != null){
    		
    		// set the connection;
    		this.application.setCurrentConnection(defaultConnection);
    		
    		// a default connection is specified...Go to home screen
      	   Intent intent = new Intent(XbmcControllerMainActivity.this, HomeScreenActivity.class);
      	   startActivity(intent);
      	   
    	} else {
    		
    		List<XbmcConnection> connections = this.application.getConnectionManager().getConnections();
        	if (connections.size() > 0){
        		showConnectionSelectionDialog();
        	} else {
        		
        		DialogUtility.showYesNo(this, 
        				R.string.no_connection_create_new, 
        				R.string.no_connection, 
        				new OnClickListener() {
    						public void onClick(DialogInterface dialog, int which) {
    						   dialog.dismiss();
    						   if (which == DialogInterface.BUTTON_POSITIVE){
    				         	   Intent intent = new Intent(XbmcControllerMainActivity.this, XbmcConnectionEdit.class);
    				         	   startActivity(intent);
    						   }
    						}
    			});
        	}
    	}
    }
    
    private void showConnectionSelectionDialog(){
    	
    	List<XbmcConnection> connections = this.application.getConnectionManager().getConnections();
    	
    	final ArrayAdapter<XbmcConnection> items = new ArrayAdapter<XbmcConnection>(this, R.layout.list_item, connections);
    	
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setIcon(android.R.drawable.ic_menu_info_details);
    	builder.setTitle("Select a connection");
    	builder.setNegativeButton(android.R.string.cancel, null);
    	builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
    	    public void onClick(DialogInterface dialog, int item) {
    	        Toast.makeText(getApplicationContext(), items.getItem(item).toString(), Toast.LENGTH_SHORT).show();
    	    }
    	});
    	AlertDialog alert = builder.create();
    	alert.show();
    }
}