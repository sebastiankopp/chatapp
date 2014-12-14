package skoldvariant.servercore;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

public class MainHttpServer {
	public static final String CONTEXT  = "/test";
	public final static int PORT = 8080;
	private HttpServer server;
	public MainHttpServer() throws IOException{
        server = HttpServer.create(new InetSocketAddress(PORT), 0);
        server.createContext(CONTEXT, new Acceptor());
        server.setExecutor(null); // creates a default executor
        server.start();
	}
	public String getUrl(){
		return String.format("http://[%s]:%d%s", server.getAddress().getAddress().getHostAddress(), server.getAddress().getPort(), CONTEXT);
	}
	public String getResourcePath(){
		return CONTEXT;
	}
	public void finish(){
		server.stop(PORT);
		System.err.println("Server stopped");
	}
	public int getPort(){
		return PORT;
	}
}
