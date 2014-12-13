package model;

public class LoginResponse {
	private boolean success;
	private String token;
	private String errstr;
	public LoginResponse(boolean success, String arg1){
		this.success = success;
		if (success){
			token = arg1;
			errstr = null;
		} else {
			errstr = arg1;
			token = null;
		}
	}
	/**
	 * @return the errstr
	 */
	public String getErrstr() {
		return errstr;
	}
	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}
	/**
	 * @return the success
	 */
	public boolean isSuccess() {
		return success;
	}
}
