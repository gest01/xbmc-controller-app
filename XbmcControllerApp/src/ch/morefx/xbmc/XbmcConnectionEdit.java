package ch.morefx.xbmc;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import ch.morefx.xbmc.util.DialogUtility;

public class XbmcConnectionEdit extends Activity {

	public static final String PARAM_EXTRA_CONENCTION = "CONNECTION";
	
	private XbmcConnection connection;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xbmc_connection_detail);

		
		this.connection = (XbmcConnection)getIntent().getSerializableExtra(PARAM_EXTRA_CONENCTION);
		if (this.connection == null){
			this.connection = new XbmcConnection();
			setTitle("Todo new connection");
		}
		
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
	}

	private void bindConnection(){
		getConnectionNameTextView().setText(this.connection.getConnectionName());
		getUsernameTextView().setText(this.connection.getUsername());
		getPasswordTextView().setText(this.connection.getPassword());
		getHostTextView().setText(this.connection.getHost());
		getPortTextView().setText(Integer.toString(this.connection.getPort()));
		getDefaultConnectionCheckBox().setChecked(this.connection.isDefault());
	}
	
	private void updateConnection(){
		this.connection.setConnectionName(getConnectionNameTextView().getText().toString());
		this.connection.setHost(getHostTextView().getText().toString());
		this.connection.setUsername(getUsernameTextView().getText().toString());
		this.connection.setPassword(getPasswordTextView().getText().toString());
		this.connection.setPort(Integer.parseInt(getPortTextView().getText().toString()));
		this.connection.setDefault(getDefaultConnectionCheckBox().isChecked());
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
	
	private CheckBox getDefaultConnectionCheckBox(){
		return (CheckBox)findViewById(R.id.chkisdefault);
	}
}
