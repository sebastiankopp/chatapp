package skoldvariant.model;

public class NewConvRequest {
	private String[] users;
	private String token;
	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}
	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}
	/**
	 * @return the users
	 */
	public String[] getUsers() {
		return users;
	}
	/**
	 * @param users the users to set
	 */
	public void setUsers(String[] users) {
		this.users = users;
	}
}
