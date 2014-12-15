package chatBase.srv;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import chatBase.model.ChatMessage;
import chatBase.model.ChatMessageMessage;
import chatBase.model.ChatMessageWhoisin;

/** One instance of this thread will run for each client */
class ClientThread extends Thread {
	/**
	 * 
	 */
	private Server server;
	private Socket socket;// the socket where to listen/talk
	private ObjectInputStream sInput;
	private ObjectOutputStream sOutput;
	private long id; // my unique id (easier for disconnection)
	private String username;
	private ChatMessage cm; // received msg
	private String date; // Verbindungsdatum

	// Constructore
	public ClientThread(Server server, Socket socket, long id) {
		this.server = server;
		// a unique id
		this.id = id;
		this.setSocket(socket);
		/* Creating both Data Stream */
		server.logMessage("Thread trying to create Object Input/Output Streams");
		try {
			// create output first
			setsOutput(new ObjectOutputStream(socket.getOutputStream()));
			setsInput(new ObjectInputStream(socket.getInputStream()));
			// read the username
			setUsername((String) getsInput().readObject());
			server.logMessage( getUsername() + " just connected.");
		}
		catch (IOException e) {
			server.logStackTrace(e);
			return;
		}
		// have to catch ClassNotFoundException
		// but I read a String, I am sure it will work
		catch (ClassNotFoundException e) {}
        date = LocalDateTime.now().toString() + "\n";
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
				server.logMessage(getUsername() + " Exception reading Streams: " + e);
				server.logStackTrace(e);
				break;				
			}
			// the messaage part of the ChatMessage
			String message = "";
			// Switch on the type of message receive
			switch(cm.getType()) {
			case ChatMessage.MESSAGE:
				ChatMessageMessage cmm = (ChatMessageMessage) cm; //cast is OK cause type is message
				message = cmm.getMessage();
				this.server.broadcast(getUsername() + ": " + message);
				break;
			case ChatMessage.LOGOUT:
				server.logMessage(getUsername() + " disconnected with a LOGOUT message.");
				keepGoing = false;
				break;
			
				
			}
		}
		// remove myself from the arrayList containing the list of the
		// connected Clients
		this.server.remove(id);
		close();
	}
	void writeWhoIsIn(){
		List<String> wholist = new LinkedList<String>();
		server.map.forEach((Long ii, ClientThread dd) -> 
			wholist.add((ii+1) + ") " + dd.getUsername() + " since " + dd.date));
		ChatMessageWhoisin wiimsg = new ChatMessageWhoisin(ChatMessage.WHOISIN, wholist);
		writeMsg(wiimsg);
	}
	// try to close everything
	private void close() {
		// try to close the connection
		try {
			if(getsOutput() != null) getsOutput().close();
		}
		catch(Exception e) {}
		try {
			if(getsInput() != null) getsInput().close();
		}
		catch(Exception e) {};
		try {
			if(getSocket() != null) getSocket().close();
		}
		catch (Exception e) {}
	}

	/*
	 * Write a String to the Client output stream
	 */
	
	//TODO Der String muss durch ChatMessage ersetzt werden und der Client muss ebenso prüfen welche Art von Message es ist, damit MESSAGE, WHOISIN und ADVERT unterschieden werden können
	//Eigentlich muss das, was jetzt der Client macht der Server auch machen und umgekehrt
	
	boolean writeMsg(ChatMessage msg) {
		// if Client is still connected send the message to it
		if(!getSocket().isConnected()) {
			close();
			return false;
		}
		// write the message to the stream	// 
		try {
			getsOutput().writeObject(msg);
		} catch(IOException e) {
			server.logMessage("Error sending message to " + getUsername());
			server.logStackTrace(e);
		}
		return true;
	}
//	boolean writeMsgWhoIsIn(List<String> users){
//		if (!getSocket().isConnected()){
//			close();
//			return false;
//		}
//		try{
//			
//		} catch (Exception e){
//	}
	/**
	 * @return the sInput
	 */
	public ObjectInputStream getsInput() {
		return sInput;
	}

	/**
	 * @param sInput the sInput to set
	 */
	public void setsInput(ObjectInputStream sInput) {
		this.sInput = sInput;
	}

	/**
	 * @return the sOutput
	 */
	public ObjectOutputStream getsOutput() {
		return sOutput;
	}

	/**
	 * @param sOutput the sOutput to set
	 */
	public void setsOutput(ObjectOutputStream sOutput) {
		this.sOutput = sOutput;
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