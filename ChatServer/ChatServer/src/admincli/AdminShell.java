package admincli;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class AdminShell {
	private String f_fromsrv, f_tosrv;
	private BufferedReader fromsrv;
	private PrintWriter tosrv;
	private Scanner usr;
	public AdminShell(String infrSrv, String outtoSrv){
		usr = new Scanner(System.in);
		f_fromsrv = infrSrv;
		f_tosrv = outtoSrv;
		try {
			tosrv = new PrintWriter(new BufferedWriter(new FileWriter(f_tosrv, false)));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	public void eachTime() throws IOException{
		System.out.print("Admin # ");
		String[] line = usr.nextLine().split("\\s+");
		if (line[0].equalsIgnoreCase("shutdown")){
			// Server-Shutdown
		} else if (line[0].equalsIgnoreCase("exit")){
			System.out.println("Adminshell wird beendet");
			System.exit(0);
		} else if (line[0].equalsIgnoreCase("adduser")){
			
		} else if (line[0].equalsIgnoreCase("deluser")) {
			
		} else {
			System.err.println("Falsche Eingabe. Bitte noch einmal");
			promptManual();
		}
		tosrv.close();
		fromsrv = new BufferedReader(new FileReader(f_fromsrv));
		try {
			Thread.sleep(1800);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String line2;
		while ((line2 =  fromsrv.readLine()) != null)
			System.out.println(line2);
		tosrv = new PrintWriter(new BufferedWriter(new FileWriter(f_tosrv, false)));
		tosrv.write("");
	}
	private void promptManual(){
		System.out.println("Syntax:");
		System.out.println("----------------------------------------------------------------------------------");
		System.out.println("shutdown            Herunterfahren des Servers und Beenden der Adminshell");
		System.out.println("exit                Beenden der Adminshell");
		System.out.println("adduser <usr> <pw>  Nutzer mit Usernamen <usr> und Passwort <pw> hinzufügen");
		System.out.println("deluser <usr>       Nutzer <usr> entfernen");
		System.out.println("----------------------------------------------------------------------------------");
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AdminShell as = new AdminShell("foo.txt", "bar.txt");
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
