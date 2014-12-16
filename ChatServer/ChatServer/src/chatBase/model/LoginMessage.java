package chatBase.model;

public class LoginMessage implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3588637256642374790L;
	private String username, password;
	public LoginMessage(){}
	public LoginMessage(String uname, String pw){
		this.username = uname;
		this.password = pw;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
}
