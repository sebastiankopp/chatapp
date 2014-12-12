package servercore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import com.sun.net.httpserver.*;
public class Acceptor implements HttpHandler{
	private ServerCenter sc;
	public Acceptor(){
		sc = ServerCenter.getInstance();
	}
	
	@Override
	public void handle(HttpExchange httx) throws IOException {
		System.err.println("Handling started");
		BufferedReader brx = new BufferedReader(new InputStreamReader(httx.getRequestBody()));
		String line;
		String reqBody = "";
		while ((line = brx.readLine()) != null){
			reqBody += line;
		}
		
		Headers headsx = httx.getResponseHeaders();	
		OutputStream os = httx.getResponseBody();
		String [] parts = httx.getRequestURI().toString().split("\\/");
		String paramx = parts[parts.length-1];
		String response;
		try {
			response = sc.processJson(reqBody, paramx);
			httx.sendResponseHeaders(200, response.length());
			headsx.add("Content-Type", "application/json; charset=utf-8");
		} catch (Exception e) {
			response = "<html><head><title>Internal Server Error. HTTP 500!</title><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />"+
					"</head><body><h1>Internal Server Error. HTTP 500!</h1><p>" + e.getMessage() + "</p></body></html>";	// oder "400. Bad Request." o. ä.
			headsx.add("Content-Type", "text/html; charset=utf-8");
		}
		
		os.write(response.getBytes(security.PWHasher.STD_ENCODING));
		brx.close();
		os.close();
	}

}
