import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Server.java
 * The server class for the game/chat
 * Usage: java Server <hostname> <port>
 * @author jbschmi3
 *
 */
public class Server {
	static String hostname = "localhost";
	static int port = 0;
	
	public static void main(String args[]) {		
		if(args.length != 2) {
			System.out.println("Usage: java Server <hostname> <port>");
		}
		
		try {
			hostname = args[0];
			port = Integer.parseInt(args[1]);
						
			Game game = new Game();
			
			Registry reg = LocateRegistry.createRegistry(port);
			reg.rebind(hostname, game);
			
			System.out.println("Server is running...");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
