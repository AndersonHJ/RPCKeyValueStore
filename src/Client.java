import java.io.IOException;
import java.net.UnknownHostException;

import org.apache.thrift.TApplicationException;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import Model.OperationType;
import Model.Request;
import util.ClientHelper;
import util.Logger;

/**
 * @author Shiqi Luo
 *
 */
public class Client {

	private TTransport transport;
	
	private String address = null;
	private int port = 0;
	
	/**
	 * Constructor of TCP client
	 * @param address
	 * @param port
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public Client(String address, int port) throws UnknownHostException, IOException{
		this.transport = new TSocket(address, port);
		this.address = ""+address;
		this.port = port;
	}
	
	/**
	 * Run method of SocketClient, listen to command line message, 
	 * send it to server and print response string
	 * @throws IOException
	 */
	public void run() throws IOException {
		String response = null;
		String query = "";
		
		try {
			transport.open();
		
			Request request = ClientHelper.getQuery();
			TProtocol protocol = new TBinaryProtocol(transport);
			KeyValueStoreService.Client client = new KeyValueStoreService.Client(protocol);
	
			while(request != null){
				if(request.getOperationType().equals(OperationType.get)){
					String value = null;
					try{
						value = client.get(request.getKey());
						response = "\"" + value + "\"";
					} catch (TApplicationException e) {
						if(e.getType() == org.apache.thrift.TApplicationException.MISSING_RESULT){
							response = "Don't have the value of key -> \"" + request.getKey() + "\"";
						}
					}
					
				}
				else if(request.getOperationType().equals(OperationType.put)){
					try{
						boolean result = client.put(request.getKey(), request.getValue());
						response = "put operation " + (result == true ? "successed" : "failed");
					} catch (TApplicationException e) {
						if(e.getType() == org.apache.thrift.TApplicationException.MISSING_RESULT){
							response = "put operation failed";
						}
					}
				}
				else if(request.getOperationType().equals(OperationType.delete)){
					try{
						boolean result = client.deleteKey(request.getKey());
						response = "delete operation " + (result == true ? "successed" : "failed(key is not existed)");
					} catch (TApplicationException e) {
						if(e.getType() == org.apache.thrift.TApplicationException.MISSING_RESULT){
							response = "delete operation failed";
						}
					}
				}
				Logger.printClientInfo(response);
				request = ClientHelper.getQuery();
			}
	
			transport.close();
		} catch (TException e) {
			Logger.printClientInfo(e.getLocalizedMessage());
		} catch (Exception e) {
			Logger.printClientInfo(e.getLocalizedMessage());
		}
	}

	/**
	 * Main function
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			if(args.length != 2){
				throw new IllegalArgumentException("Only accept 2 arguments: ip address and port!");
			}
			Client client = new Client(args[0], Integer.valueOf(args[1]));
			client.run();
		} catch (IOException | IllegalArgumentException e) {
			Logger.printClientInfo(e.getLocalizedMessage());
		}
	}

}
