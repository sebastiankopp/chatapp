package chatBase.client;

import java.net.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.io.*;

import chatBase.model.ChatMessage;
import chatBase.model.ChatMessageMessage;
import chatBase.model.ChatMessageWhoisin;


public class ClientController  {

	// for I/O
	private ObjectInputStream sInput;		// to read from the socket
	private ObjectOutputStream sOutput;		// to write on the socket
	private Socket socket;

	private ClientGuiViewSwing cgv;
	private String server, username;
	private int port;


	ClientController(String server, int port, String username, ClientGuiViewSwing cg) {
		this.server = server;
		this.port = port;
		this.username = username;
		this.cgv = cg;
	}
	
	public boolean start() {
		// verbindungsversuch zu server
		try {
			socket = new Socket(server, port);
		} 
		// error:
		catch(Exception ec) {
			display("Error connectiong to server:" + ec);
			cgv.verbindungsfehler();
			return false;
		}
		
		String msg = "Verbindung OK " + socket.getInetAddress() + ":" + socket.getPort();
		display(msg);
	
		/* Creating both Data Stream */
		try
		{
			sInput  = new ObjectInputStream(socket.getInputStream());
			sOutput = new ObjectOutputStream(socket.getOutputStream());
		}
		catch (IOException eIO) {
			display("Exception creating new Input/output Streams: " + eIO);
			cgv.verbindungsfehler();
			return false;
		}

		// erzeuge ListenThread
		new ListenFromServer().start();
		//TODO Login in ChatMessage integrieren
		// sende username zu server. ist ein String, danach wird ChatMessage verwendet
		try
		{
			sOutput.writeObject(username);
		}
		catch (IOException eIO) {
			display("Exception doing login : " + eIO);
			disconnect();
			cgv.verbindungsfehler();
			return false;
		}
		// success we inform the caller that it worked
		return true;
	}

	/*
	 * To send to GUI
	 */
	private void display(String msg) {
			cgv.appendMemoVerlauf(msg + "\n");		// append zu ClientGUIView JTextArea
	}
	
	// sende message zu server
	protected void sendMessage(ChatMessage msg) {
		try {
			sOutput.writeObject(msg);
		}
		catch(IOException e) {
			display("Fehler: Kann Nachricht nicht zu Server senden: " + e);
		}
	}

	// läuft irwas schief, alle I/O-Streams schließen und Verbindung trennen; catch muss halt da sein
	private void disconnect() {
		try { 
			if(sInput != null) sInput.close();
		}
		catch(Exception e) {} // nix machen
		try {
			if(sOutput != null) sOutput.close();
		}
		catch(Exception e) {} // nix machen
        try{
			if(socket != null) socket.close();
		}
		catch(Exception e) {} // nix machen
		
		// info für GUI
		cgv.verbindungsfehler();
			
	}
	
	  static List<String> readSmallTextFile(String aFileName) throws IOException {
		    Path path = Paths.get(aFileName);
		    //System.out.println("readtf");
		    return Files.readAllLines(path, StandardCharsets.UTF_8);
	  }
		  
	  static void writeSmallTextFile(List<String> aLines, String aFileName) throws IOException {
	    Path path = Paths.get(aFileName);
	    Files.write(path, aLines, StandardCharsets.UTF_8);
	    //System.out.println("writetf");
	  }

	// Listen-Thread-Klasse, die auf Messages vom Server wartet und an Gui weitergibt
	class ListenFromServer extends Thread {

		public void run() {
			while(true) {
				try {
					ChatMessage cmMsg = (ChatMessage) sInput.readObject();
					//message ausgeben
						switch(cmMsg.getType()) {
						case ChatMessage.MESSAGE:
							ChatMessageMessage cmm = (ChatMessageMessage) cmMsg; //cast is OK cause type is message
							cgv.appendMemoVerlauf(cmm.getMessage());
							break;
						case ChatMessage.WHOISIN:
							ChatMessageWhoisin cmw = (ChatMessageWhoisin) cmMsg; //cast is OK cause type is message
							String[] aryList = cmw.getKontaktListe().toArray(new String[cmw.getKontaktListe().size()]);
							cgv.listKontakte.setListData(aryList);
							break;
						}
				}
				catch(IOException e) {
					display("Server hat Verbindung geschlossen: " + e);
						cgv.verbindungsfehler();
					break;
				}
				// mal wieder nen senseless catch, der nix macht
				catch(ClassNotFoundException e2) {
				}
			}
		}
	}
}
