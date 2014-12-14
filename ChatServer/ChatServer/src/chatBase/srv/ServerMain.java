package chatBase.srv;

import javax.swing.JOptionPane;

public class ServerMain {
	public static void main(String[] args){
		int portNumber = Server.DEFAULT_PORT;
		portNumber = Integer.parseInt(JOptionPane.showInputDialog("Please enter a port number"));
		if (args.length > 0){
			try {
				portNumber =Integer.parseInt(args[1]);
				
			} catch (Exception e) {
				System.err.println("Port number might be invalid");
				System.err.println("Usage: java -cp chatapp.jar chatBase.ServerMain [portnumber]");
			}
		}
		Server srv = Server.getInstance(portNumber, System.err);
		srv.performInfiniteLoop();
		System.out.println("kjk");
	}
}
