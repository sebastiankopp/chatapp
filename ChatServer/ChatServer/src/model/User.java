package model;
/**
 * 
 * @author Sebastian Kopp
 *
 */
public class User {
	
	private int userid;
	private String nickname;
	private String realname; // opt
	public User(){}
	public User(int id, String nickn, String rn){
		this.userid = id;
		this.nickname = nickn;
		this.realname = rn;
	}
	/**
	 * @return the userid
	 */
	public int getUserid() {
		return userid;
	}
	/**
	 * @param userid the userid to set
	 */
	public void setUserid(int userid) {
		this.userid = userid;
	}
	/**
	 * @return the nickname
	 */
	public String getNickname() {
		return nickname;
	}
	/**
	 * @param nickname the nickname to set
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	/**
	 * @return the realname
	 */
	public String getRealname() {
		return realname;
	}
	/**
	 * @param realname the realname to set
	 */
	public void setRealname(String realname) {
		this.realname = realname;
	}
	
}
