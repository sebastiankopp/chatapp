package skoldvariant.userclient;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import skoldvariant.model.ChatMessage;
import skoldvariant.model.Conversation;
import skoldvariant.model.LoginRequest;
import skoldvariant.model.LoginResponse;
import skoldvariant.model.User;
import skoldvariant.servercore.MainHttpServer;
import skoldvariant.servercore.MessageTypes;

public class UserClientCore {
	private static final String PROTOCOL_PREFIX = "http://";
	private String srvhost;
	private User user;
	private int srvport;
	private static final String DEFAULT_HOST = "localhost";
	private static final String HTMETHOD = "POST";
	private static final String TEXTPLAIN = "text/plain";
	private static final String APPLICATION_JSON = "application/json";
	private static final String PRE_CONTEXT = (MainHttpServer.CONTEXT + "/");
	private String token;
	private HashMap<Integer, Conversation> convs;
	private Gson gs;
	private String mainpartOfConStr;
	private HttpURLConnection con;
	public UserClientCore(){
		
	}
	public UserClientCore(String serverhost, int serverport){
		this.srvport = serverport;
		this.srvhost = serverhost;
		this.mainpartOfConStr = PROTOCOL_PREFIX + this.srvhost + ":" + this.srvport + PRE_CONTEXT;
	}
	public String login(String usr, String pw){
		LoginRequest lrq = new LoginRequest();
		lrq.setHost("localhost");
		lrq.setPw(pw);
		lrq.setUsername(usr);
		lrq.setPort(4711);		// actually not needed
		String json = gs.toJson(lrq, LoginRequest.class);
		String rc;
		try {
			rc = processRequest(new URL(mainpartOfConStr + MessageTypes.LOGIN_REQ), json, APPLICATION_JSON);
			LoginResponse lrsp = gs.fromJson(rc, LoginResponse.class);
			if (lrsp.isSuccess()){
				this.token = lrsp.getToken();
				this.user = lrsp.getUdata();
				return "Herzlich Willkommen";
			} else return lrsp.getErrstr();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Fehler aufgetreten. Mehr s. o.";
		}	
	}
	public String sendMessage(String msg, int convid){
		ChatMessage cm = new ChatMessage(msg, user, convs.get(convid), token);
		try {
			String rc = processRequest(new URL(mainpartOfConStr + MessageTypes.SUBMIT_MSG), msg, APPLICATION_JSON);
			List<ChatMessage> msgs = gs.fromJson(rc, new TypeToken<List<ChatMessage>>() {}.getType());
			StringBuilder rcs = new StringBuilder();
			msgs.forEach(mm -> rcs.append(String.format("%s: \"%s\"\n", mm.getFrom().getNickname(), mm.getMessage())));
			return rcs.toString();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Fehler aufgetreten. Mehr s. o.";
		}
	}
	public String pull(){
		return "";
	}
	public String createConv(){
		return "";
	}
	public String addUsrToConv(){
		return "";
	}
	public String seeConvs(){
		return "";
	}
	private String processRequest(URL url, String content, String contentType){
	    con = null; 
	    String rc;
	    try {
	    	con = (HttpURLConnection)url.openConnection();
	    	con.setRequestMethod(HTMETHOD);
	    	con.setRequestProperty("Content-Type", contentType); // although it is usually 
//	    	connection.setRequestProperty("Content-Length", "" +Integer.toString(urlParameters.getBytes().length));
//	      connection.setRequestProperty("Content-Language", "en-US");  
	    	con.setUseCaches (false);
	    	con.setDoInput(true);
	    	con.setDoOutput(true);
	    	DataOutputStream dos = new DataOutputStream (con.getOutputStream ());
	    	dos.writeBytes (content);
	    	dos.flush ();
	    	dos.close ();
	    	InputStream instr = con.getInputStream();
	    	BufferedReader rd = new BufferedReader(new InputStreamReader(instr));
	    	String line;
	    	StringBuilder response = new StringBuilder(); 
	    	while((line = rd.readLine()) != null) {
	    		response.append(line);
	    		response.append('\n');
	    	}
	    	rd.close();
	    	rc = response.toString();
	    } catch (Exception e) {
	    	e.printStackTrace();
	    	rc = null;
	    } 
	    if(con != null) con.disconnect(); 
	    return rc;
	}
}