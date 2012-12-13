package ch.morefx.xbmc;

public interface ConnectionDescriptor {

	public static final int DEFAULT_JSON_RPC_PORT = 9090;
	public static final int DEFAULT_PORT = 8080;
	public static final String DEFAULT_USERNAME = "xbmc";
	
	public int getPort();
	public void setPort(int port);
	
	public String getUsername();
	public void setUsername(String username);
	
	public String getPassword();
	public void setPassword(String password);
	
	public String getConnectionName();
	public void setConnectionName(String connectionName);
	
	public String getHost();
	public void setHost(String host);
	
	public String getMacAddress();
	public void setMacAddress(String macAddress);
	
	public long getId();
	public void setId(long id);	
	
	public String getConnectionUri();
}
