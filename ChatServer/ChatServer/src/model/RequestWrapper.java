package model;

public class RequestWrapper<T> {
	private String token;
	private T request;
	public RequestWrapper(T req, String token){
		this.request = req;
		this.token = token;
	}
	
	/**
	 * @return the request
	 */
	public T getRequest() {
		return request;
	}
	/**
	 * @param request the request to set
	 */
	public void setRequest(T request) {
		this.request = request;
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
