package servercore;

import java.io.IOException;

public class Main {

	
	public static void main(String[] args) throws IOException {
		new MainHttpServer();
	}

}
/*
 *  To run as a console application just open a console window and: 
 * > java Server
 * > java Server portNumber
 * If the port number is not specified 1500 is used
 */