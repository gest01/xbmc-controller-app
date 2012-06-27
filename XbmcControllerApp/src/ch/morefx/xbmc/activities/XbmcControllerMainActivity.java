package ch.morefx.xbmc.activities;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import ch.morefx.xbmc.R;
import ch.morefx.xbmc.XbmcConnection;
import ch.morefx.xbmc.XbmcRemoteControlApplication;
import ch.morefx.xbmc.activities.home.HomeScreenActivity;
import ch.morefx.xbmc.util.DialogUtility;
import ch.morefx.xbmc.util.ConnectionTester;

public class XbmcControllerMainActivity extends Activity {
	
	private XbmcRemoteControlApplication application;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_layout);
        
        this.application = (XbmcRemoteControlApplication)getApplication();
    }
      
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_options_menu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
      switch (item.getItemId()) {
	      case R.id.mnu_select_connection:
	    	  showConnectionSelectionDialog();
	        return true;
	        
	      case R.id.mnu_new_connection:
        	   Intent i = new Intent(XbmcControllerMainActivity.this, XbmcConnectionEditActivity.class);
        	   startActivity(i);
	        return true;
	        
	      default:
	        return super.onOptionsItemSelected(item);
       }
    }
    
    private void showConnectionSelectionDialog(){
    	
    	List<XbmcConnection> connections = this.application.getConnectionManager().getConnections();
    	
    	final ArrayAdapter<XbmcConnection> items = new ArrayAdapter<XbmcConnection>(this, R.layout.list_item, connections);
    	
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setTitle("Select connection");
    	builder.setCancelable(false);
    	builder.setNegativeButton(android.R.string.cancel, null);
    	builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
    	    public void onClick(DialogInterface dialog, int item) {
    	    	dialog.dismiss();
    	    	tryConnect(items.getItem(item));
    	    }
    	});
    	AlertDialog alert = builder.create();
    	alert.show();
    }
    
    private void tryConnect(final XbmcConnection connection){
		final ProgressDialog pd = new ProgressDialog(XbmcControllerMainActivity.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage("Connecting " + connection.getConnectionName() + "...");
        pd.setIndeterminate(true);
        pd.setCancelable(false);
        pd.show();

        Handler handler = new Handler(){
        	@Override
        	public void handleMessage(Message msg) {
                pd.dismiss();
                if (msg.what == ConnectionTester.CONNECTION_OK){
                	application.setCurrentConnection(connection);
             	   	Intent i = new Intent(XbmcControllerMainActivity.this, HomeScreenActivity.class);
             	   	startActivity(i);
                } else {
                	DialogUtility.showError(XbmcControllerMainActivity.this, "unable to connect " + connection.getConnectionName(), "Connection failed");
                }
        	}
        };
        
        ConnectionTester tester = new ConnectionTester();
        tester.canConnect(connection.getConnector(), handler);
    }
}