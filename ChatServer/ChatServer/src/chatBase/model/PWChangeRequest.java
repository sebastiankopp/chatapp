package chatBase.model;

public class PWChangeRequest extends ChatMessage{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6910825574580793899L;
	public PWChangeRequest(){super(CHANGE_PW);}
	private String user, oldpw, newpw;
	public PWChangeRequest(String user, String oldpw, String newpw){
		super(CHANGE_PW);
		this.newpw = newpw;
		this.oldpw = oldpw;
		this.user = user;
	}
	/**
	 * @return the newpw
	 */
	public String getNewpw() {
		return newpw;
	}
	/**
	 * @param newpw the newpw to set
	 */
	public void setNewpw(String newpw) {
		this.newpw = newpw;
	}
	/**
	 * @return the oldpw
	 */
	public String getOldpw() {
		return oldpw;
	}
	/**
	 * @param oldpw the oldpw to set
	 */
	public void setOldpw(String oldpw) {
		this.oldpw = oldpw;
	}
	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}
}
