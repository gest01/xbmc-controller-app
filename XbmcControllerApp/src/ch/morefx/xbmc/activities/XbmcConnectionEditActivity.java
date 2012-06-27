package ch.morefx.xbmc.activities;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import ch.morefx.xbmc.R;
import ch.morefx.xbmc.XbmcConnection;
import ch.morefx.xbmc.XbmcConnectionManager;
import ch.morefx.xbmc.XbmcRemoteControlApplication;
import ch.morefx.xbmc.util.DialogUtility;
import ch.morefx.xbmc.util.MacAddressResolver;

public class XbmcConnectionEditActivity extends XbmcActivity {

	public static final String PARAM_EXTRA_CONNECTION = "CONNECTION";
	
	private XbmcConnection connection;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xbmc_connection_detail);

		
		this.connection = (XbmcConnection)getIntent().getSerializableExtra(PARAM_EXTRA_CONNECTION);
		if (this.connection == null){
			this.connection = new XbmcConnection();
			setTitle("Todo new connection");
		}
		
		getHostTextView().setOnFocusChangeListener(new OnFocusChangeListener() {
			Handler handler = new Handler(){
				public void handleMessage(android.os.Message message){
					if(message.getData().containsKey(MacAddressResolver.MESSAGE_MAC_ADDRESS)){
						String mac = message.getData().getString(MacAddressResolver.MESSAGE_MAC_ADDRESS);
						System.out.println("FUCK " + mac);
						
						if(!mac.equals("")){
							getMacTextView().setText(mac);
							Toast toast = Toast.makeText(XbmcConnectionEditActivity.this, "Updated MAC for host: " + getHostTextView().getText().toString() + "\nto: " + mac, Toast.LENGTH_SHORT);
							toast.show();
						}
					}
				}
			};
			
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) return;
				if(getMacTextView().getText().toString().equals(""))
					handler.post(new MacAddressResolver(getHostTextView().getText().toString(), handler));
			}
		});
		
		bindConnection();

		((Button)findViewById(R.id.btnsave)).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (validateInput()){
					updateConnection();
					
					XbmcRemoteControlApplication application = (XbmcRemoteControlApplication)getApplication();
					XbmcConnectionManager manager = application.getConnectionManager();
					if (connection.isNew()){
						manager.add(connection);
					}
					
					manager.persist();
					finish();
				} 
			}
		});
		
		((Button)findViewById(R.id.btncancel)).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				XbmcConnectionEditActivity.this.finish();
			}
		});
	}

	private void bindConnection(){
		getConnectionNameTextView().setText(this.connection.getConnectionName());
		getUsernameTextView().setText(this.connection.getUsername());
		getPasswordTextView().setText(this.connection.getPassword());
		getHostTextView().setText(this.connection.getHost());
		getPortTextView().setText(Integer.toString(this.connection.getPort()));
		getMacTextView().setText(this.connection.getMacAddress());
	}
	
	private void updateConnection(){
		this.connection.setConnectionName(getConnectionNameTextView().getText().toString());
		this.connection.setHost(getHostTextView().getText().toString());
		this.connection.setUsername(getUsernameTextView().getText().toString());
		this.connection.setPassword(getPasswordTextView().getText().toString());
		this.connection.setPort(Integer.parseInt(getPortTextView().getText().toString()));
		this.connection.setMacAddress(getMacTextView().getText().toString());
	}
	
	private boolean validateInput(){
		if (TextUtils.isEmpty(getConnectionNameTextView().getText())){
			getConnectionNameTextView().requestFocus();
			return showErrorAndReturn(R.string.connection_name_is_required);
		}
		
		if (TextUtils.isEmpty(getHostTextView().getText())){
			getHostTextView().requestFocus();
			return showErrorAndReturn(R.string.connection_host_is_required);
		}
		
		if (TextUtils.isEmpty(getPortTextView().getText())){
			getPortTextView().requestFocus();
			return showErrorAndReturn(R.string.connection_port_is_required);
		}

		if (TextUtils.isEmpty(getUsernameTextView().getText())){
			getUsernameTextView().requestFocus();
			return showErrorAndReturn(R.string.connection_username_is_required);
		}
		
		if (TextUtils.isEmpty(getPasswordTextView().getText())){
			getPasswordTextView().requestFocus();
			return showErrorAndReturn(R.string.connection_password_is_required);
		}

		return true;
	}

	private boolean showErrorAndReturn(int id){
		DialogUtility.showError(this, id);
		return false;
	}
	
	
	private TextView getUsernameTextView(){
		return (TextView)findViewById(R.id.txtusername);
	}
	
	private TextView getPasswordTextView(){
		return (TextView)findViewById(R.id.txtpassword);
	}
	
	private TextView getPortTextView(){
		return (TextView)findViewById(R.id.txtport);
	}
	
	private TextView getConnectionNameTextView(){
		return (TextView)findViewById(R.id.txtconnectioname);
	}
	
	private TextView getHostTextView(){
		return (TextView)findViewById(R.id.txthost);
	}
	
	private TextView getMacTextView(){
		return (TextView)findViewById(R.id.txtmac);
	}
}
