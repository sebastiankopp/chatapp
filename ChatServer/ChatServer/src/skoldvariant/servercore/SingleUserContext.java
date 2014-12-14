package skoldvariant.servercore;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import skoldvariant.db.CUDAdapter;
import skoldvariant.db.RAdapter;
import skoldvariant.model.ChatMessage;
import skoldvariant.model.Conversation;
import skoldvariant.model.PWChangeSuccess;
import skoldvariant.model.User;

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
	public User getUser(){
		return this.user;
	}
	public String getToken(){
		return this.token;
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
	public List<ChatMessage> submitMsg(ChatMessage msg){
		cad.addMessage(msg);
		List<ChatMessage> msgs = rad.getMessages(msg.getConv().getConvId());
		msgs.sort((ChatMessage a, ChatMessage b) -> a.getTstamp().compareTo(b.getTstamp()));
		return msgs;
	}
	public boolean addUserToConv(User ux, Conversation conv){
		List<Integer> ids = conv.getUsers().stream().map(uu -> uu.getUserid()).collect(Collectors.toList());
		if (ids.contains(ux.getUserid()) || !ids.contains(user.getUserid())) return false;
		boolean rrc = cad.userToConv(ux, conv);
		return rrc;
	}
	public Conversation createConv(String[] _usernames){
		List<String> unames = Arrays.asList(_usernames);
		List<User> users;
		if (!unames.contains(user.getNickname()))unames.add(user.getNickname());
		users = unames.stream().map(str -> rad.getUserByNickname(str)).collect(Collectors.toList());
		return cad.addConv(users);
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
	public void logout(){
		token = "";
	}
}
