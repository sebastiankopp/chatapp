package userclient;

import com.google.gson.Gson;

import servercore.MainHttpServer;

public class UserClientCore {
	private static final String PROTOCOL_PREFIX = "http://";
	private String srvhost;
	private int srvport;
	private static final String DEFAULT_HOST = "localhost";
	private static final String PRE_CONTEXT = (MainHttpServer.CONTEXT + "/");
	private String token;
	private Gson gs;
	private String mainpartOfConStr;
	public UserClientCore(){
		
	}
	public UserClientCore(String serverhost, int serverport){
		this.srvport = serverport;
		this.srvhost = serverhost;
		this.mainpartOfConStr = PROTOCOL_PREFIX + this.srvhost + ":" + this.srvport + PRE_CONTEXT;
	}
	public String login(){
		return "";
	}
	public String sendMessage(){
		return "";
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
}