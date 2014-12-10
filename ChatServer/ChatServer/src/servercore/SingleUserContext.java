package servercore;

import java.util.List;

import db.CUDAdapter;
import db.RAdapter;
import model.ChatMessage;
import model.Conversation;
import model.PWChangeSuccess;
import model.User;

public class SingleUserContext {
	private String token;
	private User user;
	private CUDAdapter cad;
	private RAdapter rad;
	public SingleUserContext(User u, String tk){	// direkt nach Login
		token = tk;
		user = u;
		cad = new CUDAdapter();
		rad = new RAdapter();
	}
	public PWChangeSuccess changePW(String token, String oldpw, String newpw){
		if (!token.equals(this.token))
			return null;
		boolean rc = rad.validateUser(user.getNickname(), oldpw);
		if (!rc ){
			return new PWChangeSuccess(false, "Old PW wrong");
		}
		cad.changePW(user, newpw);
		return rc?(new PWChangeSuccess(true, "Change successful")):(new PWChangeSuccess(false, "DB Error. Sorry"));
	}
	public Object submitMsg(ChatMessage msg){
		
		
		return msg;
	}
	public Object addUserToConv(User ux, Conversation conv){
		return null;
		
	}
	public List<Conversation> getConvs(){
		return null;
	}
	public List<Conversation> getAllConvs(User participant){
		return null;
	}
	public List<ChatMessage> getMsgs(Conversation conv){
		return null;
	}
}
