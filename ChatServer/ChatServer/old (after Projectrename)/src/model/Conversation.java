package model;

import java.util.List;

public class Conversation {
	private List<User> users;
	private int convId;
	public Conversation(int convId, List<User>ux){
		this.users = ux;
		this.convId = convId;
	}
	public void addUser(User usr){
		users.add(usr);
	}
	/**
	 * @return the users
	 */
	public List<User> getUsers() {
		return users;
	}
	/**
	 * @param users the users to set
	 */
	public void setUsers(List<User> users) {
		this.users = users;
	}
	/**
	 * @return the convId
	 */
	public int getConvId() {
		return convId;
	}
	/**
	 * @param convId the convId to set
	 */
	public void setConvId(int convId) {
		this.convId = convId;
	}
	public String toString(){
		StringBuilder rc = new StringBuilder("<");
		users.stream().map(u -> "{"+ u.toString() + "}").forEach(rc::append);
		return rc.toString() + ">";
	}
}
