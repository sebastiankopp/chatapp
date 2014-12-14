package skoldvariant.model;

public class LoginResponse {
	private boolean success;
	private String token;
	private String errstr;
	private User udata;
	public LoginResponse(){}
	public LoginResponse(boolean success, String arg1, User data){
		this.success = success;
		if (success){
			token = arg1;
			this.setUdata(data);
			errstr = null;
		} else {
			errstr = arg1;
			token = null;
		}
	}
	public void setErrstr(String errstr){
		this.errstr = errstr;
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
	public void setToken(String token) {
		this.token = token;
	}
	/**
	 * @return the success
	 */
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success){
		this.success = success;
	}
	/**
	 * @return the udata
	 */
	public User getUdata() {
		return udata;
	}
	/**
	 * @param udata the udata to set
	 */
	public void setUdata(User udata) {
		this.udata = udata;
	}
}
