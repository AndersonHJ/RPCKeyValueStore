package util;

/**
 * Logger class, is used to print/generate log on server/client side
 * @author Shiqi Luo
 */
public class Logger {

	/**
	 * Log information logs on server side
	 * @param message
	 * @param clientAddr
	 */
	public static void logServerInfo(String message){
		System.out.println(System.currentTimeMillis() + "  Info: " + message + "\n");
	}
	
	/**
	 * Log errors on server side
	 * @param error
	 * @param clientAddr
	 */
	public static void logServerError(String error){
		System.out.println(System.currentTimeMillis() + "  Error: " + error + "\n");
	}
	
	/**
	 * Log information logs on server side
	 * @param message
	 * @param clientAddr
	 */
	public static void logClientInfo(String message){
		System.out.println(System.currentTimeMillis() + "\t\t--Client: " + message + "\n");
	}
	
	/**
	 * Print information on client side
	 * @param info
	 */
	public static void printClientInfo(String info){
		System.out.println(System.currentTimeMillis() + "  Message: " + info + "\n\n");
	}

}
