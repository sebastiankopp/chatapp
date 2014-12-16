package chatBase.srv;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import chatBase.model.ChatMessage;
import chatBase.model.ChatMessageMessage;
import chatBase.model.ChatMessageWhoisin;
import chatBase.model.PWChangeRequest;
import chatBase.model.PWChangeResponse;

/** One instance of this thread will run for each client */
class ClientThread extends Thread {
	/**
	 * 
	 */
	private Server srv;
	private Socket socket;// the socket where to listen/talk
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private long id; 
	private String username;
	private DBAdapter dba;
	private ChatMessage cm; // received msg
	public ClientThread(Server server, Socket socket, long id) {
		this.srv = server;
		this.dba = DBAdapter.getInstance();
		this.id = id;
		this.setSocket(socket);
		server.logMessage("Thread trying to create Object Input/Output Streams");
		try {
			// zuerst Output-Socket
			setsOutput(new ObjectOutputStream(socket.getOutputStream()));
			setsInput(new ObjectInputStream(socket.getInputStream()));
			setUsername((String) getsInput().readObject());		// TODO statt Usernamen hier künftig Login-Message empfangen
			server.logMessage( getUsername() + " just connected.");
		}
		catch (IOException e) {
			server.logStackTrace(e);
			return;
		}
		catch (ClassNotFoundException e) {}
	}

	// what will run forever
	public void run() {
		// to loop until LOGOUT
		boolean keepGoing = true;
		while(keepGoing) {
			// read a String (which is an object)
			try {
				cm = (ChatMessage) getsInput().readObject();
			} catch (IOException | ClassNotFoundException e) {
				srv.logMessage(getUsername() + " Exception reading Streams: " + e);
				srv.logStackTrace(e);
				break;				
			}
			// Unterscheidung nach Messagetyp
			switch(cm.getType()) {
			case ChatMessage.MESSAGE:
				ChatMessageMessage cmm = (ChatMessageMessage) cm; //cast is OK cause type is message
				String message = cmm.getMessage();
				this.srv.sendToAll(getUsername() + ": " + message);
				break;
			case ChatMessage.LOGOUT:
				srv.logMessage(getUsername() + " hat sich ausgeloggt.");
				keepGoing = false;
				break;
			case ChatMessage.CHANGE_PW:
				PWChangeRequest pwcr = (PWChangeRequest) cm;
				if (dba.verifyUsr(username, pwcr.getOldpw())){
					boolean rcxx = dba.changePW(username, pwcr.getNewpw());
					writeMsg(new PWChangeResponse(rcxx));
					srv.logMessage(username + " hat sein PW gewechselt");
				} else {
					writeMsg(new PWChangeResponse(false));
					srv.logMessage(username + " konnte sein PW nicht wechseln");
				}
			}
		}
		// nach Ende der Schleife aus der Liste des Servers entfernen
		this.srv.remove(id);
		close();
	}
	void writeWhoIsIn(){
		List<String> wholist = new LinkedList<String>();
		srv.map.forEach((Long ii, ClientThread dd) ->  wholist.add(dd.getUsername()));
		ChatMessageWhoisin wiimsg = new ChatMessageWhoisin(ChatMessage.WHOISIN, wholist);
		writeMsg(wiimsg);
	}
	
	private void close() {
		try {
			if(getsOutput() != null) getsOutput().close();
		} catch(Exception e) {}
		
		try {
			if(getsInput() != null) getsInput().close();
		} catch(Exception e) {};
			
		try {
			if(getSocket() != null) getSocket().close();
		} catch (Exception e) {}
	}

	
	//done Der String muss durch ChatMessage ersetzt werden und der Client muss ebenso prüfen welche Art von Message es ist, damit MESSAGE, WHOISIN und ADVERT unterschieden werden können
	//Eigentlich muss das, was jetzt der Client macht der Server auch machen und umgekehrt
	/**
	 * Nachricht senden
	 * @param msg
	 * @return ob das Senden erfolgreich war
	 */
	boolean writeMsg(ChatMessage msg) {
		if(!getSocket().isConnected()) {
			close();
			return false;
		}
		try {
			getsOutput().writeObject(msg);
		} catch(IOException e) {
			srv.logMessage("Error sending message to " + getUsername());
			srv.logStackTrace(e);
		}
		return true;
	}
	/**
	 * @return the sInput
	 */
	public ObjectInputStream getsInput() {
		return ois;
	}

	/**
	 * @param sInput the sInput to set
	 */
	public void setsInput(ObjectInputStream sInput) {
		this.ois = sInput;
	}

	/**
	 * @return the sOutput
	 */
	public ObjectOutputStream getsOutput() {
		return oos;
	}

	/**
	 * @param sOutput the sOutput to set
	 */
	public void setsOutput(ObjectOutputStream sOutput) {
		this.oos = sOutput;
	}

	/**
	 * @return the socket
	 */
	public Socket getSocket() {
		return socket;
	}

	/**
	 * @param socket the socket to set
	 */
	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
}