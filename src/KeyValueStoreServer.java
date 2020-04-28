
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.server.TThreadPoolServer.Args;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TSocket;

import util.Logger;



/**
 * @author Shiqi Luo
 *
 */
public class KeyValueStoreServer {
	
	public static KeyValueStoreHandler handler;

	public static KeyValueStoreService.Processor<KeyValueStoreService.Iface> keyValueProcessor;
	
	private int port = 0;
	
	private class KeyValueStoreProcessor extends KeyValueStoreService.Processor<KeyValueStoreService.Iface> {

		/* (non-Javadoc)
		 * @see org.apache.thrift.TBaseProcessor#process(org.apache.thrift.protocol.TProtocol, org.apache.thrift.protocol.TProtocol)
		 */
		@Override
		public void process(TProtocol arg0, TProtocol arg1) throws TException {
			TSocket socket = (TSocket) arg0.getTransport();
			Logger.logClientInfo("ip: " + socket.getSocket().getInetAddress() + ", port: " + socket.getSocket().getPort());
			
			super.process(arg0, arg1);
			
		}

		public KeyValueStoreProcessor(KeyValueStoreService.Iface iface) {
			super(iface);
		}
		
	}
	
	public KeyValueStoreServer(int port){
		this.port = port;
	}
	
	public void run(){
		try{
			this.handler = new KeyValueStoreHandler();
			this.keyValueProcessor = new KeyValueStoreProcessor(this.handler);
			
			TServerTransport serverTransport = new TServerSocket(this.port);

			Runnable connectionThread = new Runnable() {
				@Override
				public void run() {
					try{
						Args args = new Args(serverTransport);
						args.processor(keyValueProcessor);
						
						TServer server = new TThreadPoolServer(args);
						
						server.serve();
					} catch (Exception e) {
						Logger.logServerError(e.getMessage());
					}
					
				}
			};
			
			new Thread(connectionThread).start();

		} catch (Exception e) {
			Logger.logServerError(e.getMessage());
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if(args.length != 1){
			throw new IllegalArgumentException("Only accept 1 argument: port");
		}

		KeyValueStoreServer server = new KeyValueStoreServer(Integer.parseInt(args[0]));
		server.run();
	}

}
