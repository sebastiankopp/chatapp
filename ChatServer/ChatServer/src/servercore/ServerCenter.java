package servercore;

import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;
import java.util.UUID;

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
	private ServerCenter (){
		 actives = new TreeMap<String,SingleUserContext>();
		 rad = new RAdapter();
		 gsc = new Gson();
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
					LoginRequest ask = gsc.fromJson(reqBody, LoginRequest.class);
					SingleUserContext usctx = loginUser(ask);
					actives.put(usctx.getToken(), usctx);
					return gsc.toJson(new LoginResponse(true, usctx.getToken()), LoginResponse.class);
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
					break;
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
					return gsc.toJson(new LoginResponse(false, e.getMessage()), LoginResponse.class);
				}
				StringBuilder rc = new StringBuilder();
				Arrays.stream(e.getStackTrace()).map(el -> el.toString() + "\n").forEach(rc::append);
				return rc.toString();
			}
		}
		return "";
	}
	protected SingleUserContext loginUser(LoginRequest liq) throws LoginException{
		// TODO
		boolean rcx = rad.validateUser(liq.getUsername(), liq.getPw());
		if (!rcx) throw new LoginException();
		StringBuilder veryRandom = new StringBuilder(UUID.randomUUID().toString());
		for (int i = 0; i < 20; i++)
			veryRandom.append(UUID.randomUUID().toString());
		String token = pwh.createHash(veryRandom.toString());
		User usr = rad.getUserByNickname(liq.getUsername());
		actives.put(token, new SingleUserContext(usr, token));
		return null;
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
