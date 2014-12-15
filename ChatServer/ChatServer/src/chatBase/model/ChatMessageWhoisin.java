package chatBase.model;

import java.io.*;

import javax.swing.JList;
/*
 * This class defines the different type of messages that will be exchanged between the
 * Clients and the Server. 
 * When talking from a Java Client to a Java Server a lot easier to pass Java objects, no 
 * need to count bytes or to wait for a line feed at the end of the frame
 */
public class ChatMessageWhoisin extends ChatMessage {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2505953641872795451L;
	
	/**
	 * 
	 */
	//protected static final long serialVersionUID = 1112122200L;

	
	// The different types of message sent by the Client
	// WHOISIN to receive the list of the users connected
	// MESSAGE an ordinary message
	// LOGOUT to disconnect from the Server
	//public static final int WHOISIN = 0, MESSAGE = 1, LOGOUT = 2, ADVERTISEMENT = 3;
	//private int type;
	private JList<String> kontaktliste;
	
	// constructor
	public ChatMessageWhoisin(int type, JList<String> kontaktliste) {
		super(type);
		this.kontaktliste = kontaktliste;
	}
	
	// getters
	public JList<String> getKontaktListe() {
		return kontaktliste;
	}
}
