package chatBase.srv;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.Map.Entry;
import java.util.TreeMap;

/*
 * The server that can be run both as a console application or a GUI
 */
public class Server {
	public static final int DEFAULT_PORT = 1500;
	private int maxId;
	TreeMap<Integer, ClientThread> map; // an ArrayList to keep the list of the Client
	private int port;// the port number to listen for connection
	private boolean keepGoing;// the boolean that will be turned of to stop the server
	private LoggingDaemon ld;
	private static Server instance;
	public static Server getInstance(int port, PrintStream ps){
		if (Server.instance == null){
			Server.instance = new Server(port, ps);
		}
		return Server.instance;
	}
	private Server(int port, PrintStream ps) {
		this.port = port;
		map = new TreeMap<Integer,ClientThread>();
		this.ld = new LoggingDaemon(ps);
		maxId = 0;
	}	
	public void performInfiniteLoop() {
		keepGoing = true;
		int clntThrId = maxId;
		maxId++;
		/* create socket server and wait for connection requests */
		try{
			ServerSocket serverSocket = new ServerSocket(port);
			while(keepGoing) {
				logMessage("Server waiting for Clients on port " + port + ".");
				Socket socket = serverSocket.accept();  	// accept connection
				if (!keepGoing) break;
				ClientThread ct = new ClientThread(this, socket, clntThrId);  // make a thread of it
				map.put(clntThrId, ct);									// save it in the ArrayList
				ct.start();
			}
			serverSocket.close();
			for (Entry<Integer, ClientThread> dd: map.entrySet()){
				try{
					dd.getValue().getsInput().close();
					dd.getValue().getsOutput().close();
					dd.getValue().getSocket().close();
				} catch(Exception exc) {
					logStackTrace(exc);
				}
			}
		}
		catch (IOException e) {
            logStackTrace(e);
		}
	}		
    /*
     * For the GUI to stop the server
     */
	public void logStackTrace(Throwable thr){
		ld.getPw().println(LocalDateTime.now() + ":" + thr.getMessage());
		thr.printStackTrace(ld.getPw());
	}
	public void logMessage(String msg){
		ld.getPw().println(LocalDateTime.now().toString() + ": " + msg);
	}
	public void stop() {
		keepGoing = false;
		// connect to myself as Client to exit statement 
		// Socket socket = serverSocket.accept();
		try {
			new Socket("localhost", port);
		}
		catch(Exception e) {
			logStackTrace(e);
		}
	}
	/*
	 *  to broadcast a message to all Clients
	 */
	synchronized void broadcast(String message) {
		String messageWithDT;
		ld.getPw().println(messageWithDT = "\n"+LocalDateTime.now().toString() + " " + message);// log message for server
		// we loop in reverse order in case we would have to remove a Client
		// because it has disconnected
		map.forEach((Integer ii, ClientThread dd) ->{
			boolean rc= dd.writeMsg(messageWithDT);
			if (!rc){
				map.remove(ii);
				logMessage("Client " + dd.getUsername() + " was disconnected and removed from the list.");
			}
		});
	}
	// for a client who logoff using the LOGOUT message
	synchronized void remove(int id) {
		// scan the array list until we found the Id
		map.remove(id);
	}
	public LoggingDaemon getLd() {
		// TODO Auto-generated method stub
		return this.ld;
	}
}