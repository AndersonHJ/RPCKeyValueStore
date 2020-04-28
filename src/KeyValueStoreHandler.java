import java.util.HashMap;
import org.apache.thrift.TException;

import util.Logger;



/**
 * @author Shiqi Luo
 *
 */
public class KeyValueStoreHandler implements KeyValueStoreService.Iface {

	HashMap<String, String> data = null;
	String clientId = "";
	/**
	 * 
	 */
	public KeyValueStoreHandler() {
		this.data = new HashMap<>();
	}
	
	public String get(String key) throws TException {
		Logger.logServerInfo("query: [ get -> " + key + " ]");
		if(this.data.containsKey(key)){
			return this.data.get(key);
		}
		return null;
	}
	
	public boolean put(String key, String value) throws TException {
		if(value == null || key == null){
			return false;
		}
		
		Logger.logServerInfo("query: [ put -> " + key + " : " + value + " ]");
		
		this.data.put(key, value);
		
		return true;
	}
	
	@Override
	public boolean deleteKey(String key) throws TException {
		Logger.logServerInfo("query: [ delete -> " + key + " ]");
		if(this.data.remove(key) == null){
			return false;
		}
		return true;
	}

}
