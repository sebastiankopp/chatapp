package chatBase.srv;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.Map.Entry;
import java.util.TreeMap;

import chatBase.model.ChatMessage;
import chatBase.model.ChatMessageMessage;
import chatBase.srvAdmin.ServerSideCliThread;

/**
 * Zentraler Chatserver, welcher die Einzelthreads und das Llgging verwaltet
 * @author Sebastian
 *
 */
public class Server {
	public static final int DEFAULT_PORT = 1500;
//	private int maxId;
	TreeMap<Long, ClientThread> map; // an ArrayList to keep the list of the Client
	private int port;// the port number to listen for connection
	private boolean goOn;// the boolean that will be turned of to stop the server
	private LoggingDaemon ld;
	private ServerSideCliThread clithr;
	private static Server instance;
	public static Server getInstance(int port, PrintStream ps){
		if (Server.instance == null){
			Server.instance = new Server(port, ps);
		}
		return Server.instance;
	}
	public static Server getInstance(){
		return Server.instance;
	}
	private Server(int port, PrintStream ps) {
		this.port = port;
		map = new TreeMap<Long,ClientThread>();
		this.ld = new LoggingDaemon(ps);
		this.clithr = new ServerSideCliThread();
		this.clithr.start();
//		maxId = 0;
	}
	/**
	 * Zentraler Durchlauf: je neuem Client wird ein neuer Thread eröffnet
	 */
	public void doRun() {
		goOn = true;
		/* create socket server and wait for connection requests */
		try{
			ServerSocket serverSocket = new ServerSocket(port);
			while(goOn) {
				logMessage("Server waiting for Clients on port " + port + ".");
				Socket socket = serverSocket.accept();  	// accept connection
				long clntThrId = System.currentTimeMillis();
				if (!goOn) break;
				ClientThread ct = new ClientThread(this, socket, clntThrId); 
				map.put(clntThrId, ct);	
				map.forEach((Long l, ClientThread cthr) -> cthr.writeWhoIsIn()); // An alle beim login sagen, wer drin ist.
				ct.start();
			}
			serverSocket.close();
			for (Entry<Long, ClientThread> dd: map.entrySet()){
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
    /**
     * Loggt den Stacktrace eines Throwable-Objektes mit Zeitstempel in den im LoggingDaemon referenzierten
     * PrintStream
     * @param thr Zu protokollierendes Throwable-Objekt
     */
	public void logStackTrace(Throwable thr){
		ld.getPw().println(LocalDateTime.now() + ":" + thr.getMessage());
		thr.printStackTrace(ld.getPw());
	}
	/**
	 * Loggt eine allg. Lognachricht mit Zeitstempel in den im LoggingDaemon referenzierten
     * PrintStream
	 * @param msg Zu protokollierende Meldung (ohne Zeitstempel (der wird generiert))
	 */
	public void logMessage(String msg){
		ld.getPw().println(LocalDateTime.now().toString() + ": " + msg);
	}
	/**
	 * Stoppt den Server
	 */
	public void stop() {
		goOn = false;
		try {
			new Socket("localhost", port);
		}
		catch(Exception e) {
			logStackTrace(e);
		}
	}
	/**
	 * Eine als String-Objekt vorhandene Nachricht an alle verbundenen Clients als normale
	 * Chat-Nachricht versenden
	 * Sollte das Senden bei einem Client nicht funktionieren, wird seine Verbindung getrennt
	 * @param message 
	 */
	synchronized void sendToAll(String message) {
		String messageWithDT;
		ld.getPw().println(messageWithDT = "\n"+LocalDateTime.now().toString() + " " + message);
		map.forEach((Long ii, ClientThread dd) ->{
			ChatMessageMessage msgx = new ChatMessageMessage(ChatMessage.MESSAGE, messageWithDT);
			boolean rc= dd.writeMsg(msgx);
			if (!rc){
				map.remove(ii);
				logMessage("Client " + dd.getUsername() + " was disconnected and removed from the list.");
				map.forEach((Long l, ClientThread cthr) -> cthr.writeWhoIsIn());
			}
		});
	}
	/**
	 * Einen Client entfernen und die aktualisierte Liste aller verbundenen User an ihre Clients senden
	 * @param id Session-Key in der Map
	 */
	synchronized void remove(long id) {
		// scan the array list until we found the Id
		map.remove(id);
		map.forEach((Long l, ClientThread cthr) -> cthr.writeWhoIsIn()); // Beim Logout allen mitteilen, wer drin ist
	}
	public LoggingDaemon getLd() {
		// TODO Auto-generated method stub
		return this.ld;
	}
}