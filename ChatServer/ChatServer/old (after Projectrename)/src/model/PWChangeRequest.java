package model;

public class PWChangeRequest {
	private String token;
	private String oldPW;
	private String newPW;
	public PWChangeRequest(){
		
	}
	public PWChangeRequest(String token, String old, String _new){
		this.token = token;
		this.oldPW = old;
		this.newPW = _new;
	}
	/**
	 * @return the newPW
	 */
	public String getNewPW() {
		return newPW;
	}
	/**
	 * @param newPW the newPW to set
	 */
	public void setNewPW(String newPW) {
		this.newPW = newPW;
	}
	/**
	 * @return the oldPW
	 */
	public String getOldPW() {
		return oldPW;
	}
	/**
	 * @param oldPW the oldPW to set
	 */
	public void setOldPW(String oldPW) {
		this.oldPW = oldPW;
	}
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
}
