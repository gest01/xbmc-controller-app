package ch.morefx.xbmc;

import java.io.Serializable;

public class XbmcConnectionDescriptor 
	implements ConnectionDescriptor, Serializable {

	private static final long serialVersionUID = 1L;
	
	private String username, password, host, connectionName, macAddress;
	private int port;
	private long id;
	
	private static final String JSONRPC_URL = "http://%s:%s/jsonrpc";
	
	public XbmcConnectionDescriptor() {
		this.username = DEFAULT_USERNAME;
		this.port = DEFAULT_PORT;
	}
	
	public String getConnectionUri() {
		return String.format(JSONRPC_URL, getHost(), getPort());
	}
	

	public int getPort() {
		return this.port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConnectionName() {
		return this.connectionName;
	}

	public void setConnectionName(String connectionName) {
		this.connectionName = connectionName;
	}

	public String getHost() {
		return this.host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getMacAddress() {
		return this.macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return getConnectionName();
	}

}
