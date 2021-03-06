package ch.morefx.xbmc.activities.preferences;

import android.content.Intent;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceFragment;
import ch.morefx.xbmc.ConnectionDescriptor;
import ch.morefx.xbmc.R;
import ch.morefx.xbmc.XbmcConnectionDescriptor;
import ch.morefx.xbmc.XbmcConnectionManager;
import ch.morefx.xbmc.XbmcRemoteControlApplication;
import ch.morefx.xbmc.activities.XbmcConnectionEditActivity;

public class ApplicationPreferenceFragment extends PreferenceFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Load the preferences from an XML resource
		addPreferencesFromResource(R.xml.application_preferences);

	}
	

	@Override
	public void onStart() {
		super.onStart();
	    
		loadConnections();
		
        final XbmcConnectionManager manager = getConnectionManager();
        
        ListPreference lpDelete = (ListPreference)getPreferenceManager().findPreference("deleteConnection");
        lpDelete.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
			public boolean onPreferenceChange(Preference preference, Object newValue) {
				long connectionId = Long.parseLong(newValue.toString());
				manager.deleteConnection(connectionId);
				manager.persist();
				loadConnections();
				return true;
			}
		});
        
        Preference pref = getPreferenceManager().findPreference("customPref");
        pref.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			public boolean onPreferenceClick(Preference preference) {
				Intent intent = new Intent(getActivity(), XbmcConnectionEditActivity.class);
	         	startActivity(intent);
				return false;
			}
		});
	}
	
	private void loadConnections(){
		
		final XbmcConnectionManager manager = getConnectionManager();
		ConnectionDescriptor[] connections = (ConnectionDescriptor[])manager.getConnections().toArray(new XbmcConnectionDescriptor[0]);
        String[] entries = new String[connections.length];
        String[] entriesKeys = new String[connections.length];
        for(int i = 0 ; i < connections.length; i++){
        	entries[i] = connections[i].getConnectionName();
        	entriesKeys[i] = Long.toString(connections[i].getId());
        }
        
        ListPreference lpDelete = (ListPreference)getPreferenceManager().findPreference("deleteConnection");
        lpDelete.setEnabled(connections.length > 0);
        lpDelete.setEntries(entries);
        lpDelete.setEntryValues(entriesKeys);

	}
	
	private XbmcConnectionManager getConnectionManager(){
		 final XbmcRemoteControlApplication application = (XbmcRemoteControlApplication)getActivity().getApplication();
	     return application.getConnectionManager();
	}
}
