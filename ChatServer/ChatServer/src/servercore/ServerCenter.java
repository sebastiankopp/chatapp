package servercore;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;
import java.util.UUID;

import admincli.ServerSideCliThread;

import com.google.gson.Gson;

import security.PWHasher;
import db.RAdapter;
import model.ChatMessage;
import model.Conversation;
import model.LoginRequest;
import model.LoginResponse;
import model.PWChangeRequest;
import model.PWChangeSuccess;
import model.RequestWrapper;
import model.User;

public class ServerCenter {
	private static ServerCenter instance;
	private Gson gsc;
	private RAdapter rad;
	private TreeMap<String,SingleUserContext> actives; // eingeloggte Nutzer
	private PWHasher pwh;
	private ServerSideCliThread sclit;
	private ServerCenter (){
		 actives = new TreeMap<String,SingleUserContext>();
		 rad = new RAdapter();
		 gsc = new Gson();
		 sclit = new ServerSideCliThread();
		 try {
			pwh = new PWHasher();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		sclit.start();
	}
	public static ServerCenter getInstance() {
		if (instance == null)
			instance = new ServerCenter();
		return instance;
	}
	@SuppressWarnings({ "unchecked" })
	public String processJson(String reqBody, String param) {
		if (reqBody == null || reqBody.length() == 0){
			return "NIXNIXNIXNIX";
		} else {
			String tkn;
			RequestWrapper<Integer> rw;
			try{
				switch (Integer.parseInt(param)){
				// TODO Eigentliche Fälle hinschreiben
				case MessageTypes.CHNG_PW:
					PWChangeRequest pwc = gsc.fromJson(reqBody, PWChangeRequest.class);
					tkn = pwc.getToken();
					if(!validateToken(tkn)) return "";
					SingleUserContext suc = actives.get(tkn);
					PWChangeSuccess pws = suc.changePW(tkn, pwc.getOldPW(), pwc.getNewPW());
					return gsc.toJson(pws, PWChangeSuccess.class);
				case MessageTypes.CONV_ADDUSR:
					
					break;
				case MessageTypes.CONV_LIST:
					rw = gsc.fromJson(reqBody, RequestWrapper.class);	// Conversation-ID
					tkn = rw.getToken();
					if (!validateToken(tkn))return "";
					List<Conversation> convs = rad.getConvsByUsr(actives.get(tkn).getUser().getNickname());
					return gsc.toJson(convs);
				case MessageTypes.CREATE_CONV:
					
					break;
				case MessageTypes.LOGIN_REQ:
					System.err.println("Working on LoginRequest");
					System.err.println(reqBody);
					LoginRequest ask = gsc.fromJson(reqBody, LoginRequest.class);
					try {
						LoginResponse lresp = loginUser(ask);
						String returned = gsc.toJson(lresp, LoginResponse.class);
						System.err.println(returned);
						return returned;
					} catch (Exception e) {
						LoginResponse failx = new LoginResponse();
						failx.setErrstr("Login failed");
						failx.setSuccess(false);
						failx.setToken("no token");
						String failstring = gsc.toJson(failx, LoginResponse.class);
						System.err.println(failstring);
						return failstring;
					}
				case MessageTypes.LOGOUT_REQ:
					logoutUser(reqBody); // Logoutrequest besteht nur aus Token im Body
					break;
				case MessageTypes.PULL_REQ:
					rw = gsc.fromJson(reqBody, RequestWrapper.class);	// Conversation-ID
					tkn = rw.getToken();
					if (!actives.containsKey(tkn)){
						return "";
					}
					List<ChatMessage> l = rad.getMessages(rw.getRequest());
					return gsc.toJson(l);
				case MessageTypes.SUBMIT_MSG:
					ChatMessage input = gsc.fromJson(reqBody, ChatMessage.class);
					if (!actives.containsKey(input.getToken())) return "";
					suc = actives.get(input.getToken());
					List<ChatMessage> xx = suc.submitMsg(input);
					return gsc.toJson(xx);
				//	Typen:
				//  	Konversationen auflisten
				//		Konversation erstellen
				//		Konversation: Nutzer hinzufügen
				//		Logout-Anfrage
				//		Pull-Request
				//		PW ändern
				default: throw new Exception("Invalid parameter");
				}
			} catch (Exception e){
				if (e instanceof LoginException){
					
				}
				StringBuilder rc = new StringBuilder();
				Arrays.stream(e.getStackTrace()).map(el -> el.toString() + "\n").forEach(rc::append);
				return rc.toString();
			}
		}
		return "";
	}
	protected LoginResponse loginUser(LoginRequest liq) throws LoginException{
		// TODO
		boolean rcx = rad.validateUser(liq.getUsername(), liq.getPw());
		if (!rcx) {System.err.println(rcx);throw new LoginException();}
		else System.err.println("User " + liq.getUsername() + " erfolgreich validiert");
		StringBuilder veryRandom = new StringBuilder(UUID.randomUUID().toString());
		for (int i = 0; i < 20; i++)
			veryRandom.append(UUID.randomUUID().toString());
		String token = pwh.createHash(veryRandom.toString());
		User usr = rad.getUserByNickname(liq.getUsername());
		actives.put(token, new SingleUserContext(usr, token));
		LoginResponse rc = new LoginResponse();
		rc.setErrstr("no err");
		rc.setToken(token);
		rc.setSuccess(true);
		return rc;
	}
	protected void logoutUser(String token){
		actives.get(token).logout();
		actives.remove(token);
	}
	protected boolean validateToken(String tk){
		return actives.containsKey(tk);
	}
	

}
class LoginException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8475482426177984769L;

	public LoginException(){
		super("Login failed");
	}
	
}
