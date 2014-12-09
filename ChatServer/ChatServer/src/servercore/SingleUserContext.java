package servercore;

import java.util.List;

import model.ChatMessage;
import model.Conversation;
import model.User;

public class SingleUserContext {
	private String token;
	private User user;
	
	public SingleUserContext(User u, String tk){	// direkt nach Login
		token = tk;
		user = u;
	}
	public Object changePW(String oldpw, String newpw){
		return null;
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
