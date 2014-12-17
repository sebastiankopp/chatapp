package chatBase.srvAdmin;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.sound.midi.SysexMessage;

import chatBase.srv.Server;
import chatBase.srv.adm.AdminShellStub;
import de.root1.simon.Lookup;
import de.root1.simon.Simon;
import de.root1.simon.exceptions.EstablishConnectionFailed;
import de.root1.simon.exceptions.LookupFailedException;

public class AdminShell {
	public static final String COMMAND_DELUSER = "deluser";
	public static final String COMMAND_ADDUSER = "adduser";
	public static final String COMMAND_SHUTDOWN = "shutdown";
	public static final String COMMAND_SHOWUSERS = "showusers";
	private Scanner usr;
	private AdminShellStub astub;
	private Lookup nlu;
	public AdminShell(int port){
		usr = new Scanner(System.in);
		try {
			nlu = Simon.createNameLookup("127.0.0.1", port);
			astub = (AdminShellStub) nlu.lookup(AdminShellStub.DEFAULT_BINDING);
			
		} catch (UnknownHostException | LookupFailedException | EstablishConnectionFailed e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void eachTime() throws IOException{
		System.out.print("Admin # ");
		String line = usr.nextLine();
		String[] parts = line.split("\\s+");
		if (parts[0].equalsIgnoreCase(COMMAND_SHUTDOWN)){
			astub.shutdownServer();
			System.out.println("Server sollte beendet sein");
			nlu.release(astub);
			System.exit(0);
		} else if (parts[0].equalsIgnoreCase("exit")){
			System.out.println("Adminshell wird beendet");
			nlu.release(astub);
			System.exit(0);
		} else if (parts[0].equalsIgnoreCase(COMMAND_ADDUSER)){
			System.out.println(astub.addUser(parts[1], parts[2]));
		} else if (parts[0].equalsIgnoreCase(COMMAND_DELUSER)) {
			System.out.println(astub.deleteUser(parts[1]));
		} else if (parts[0].equalsIgnoreCase(COMMAND_SHOWUSERS)) {
			astub.seeUsers().forEach(System.out::println);
		} else {
			System.err.println("Falsche Eingabe. Bitte noch einmal");
			promptManual();
		}
		try {
			Thread.sleep(1800);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void promptManual(){
		System.out.println("Syntax:");
		System.out.println("----------------------------------------------------------------------------------");
		System.out.println("shutdown            Herunterfahren des Servers und Beenden der Adminshell");
		System.out.println("exit                Beenden der Adminshell");
		System.out.println("adduser <usr> <pw>  Nutzer mit Usernamen <usr> und Passwort <pw> hinzufügen");
		System.out.println("deluser <usr>       Nutzer <usr> entfernen");
		System.out.println("showusers           Nutzer anzeigen");
		System.out.println("----------------------------------------------------------------------------------");
	}
	
	public static void main(String[] args) {	// args[0]: Hauptport des Chatservers
		int port;
		try{
			port = Integer.parseInt(args[0]);
		} catch(Exception e){
			port = Server.DEFAULT_PORT;
			System.out.println("Warnung: Nutzung des Defaultports");
		}
		port += AdminShellStub.ADMIN_SHELL_PORT_OFFSET;
		AdminShell as = new AdminShell(port);
		as.promptManual();
		while (true){
			try {
				as.eachTime();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.exit(1);
			}
		}
	}
}
