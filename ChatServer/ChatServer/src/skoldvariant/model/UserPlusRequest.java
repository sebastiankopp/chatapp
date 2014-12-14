package skoldvariant.model;

public class UserPlusRequest {
	private String token, nickname;
	private int conv;

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
	 * @return the conv
	 */
	public int getConv() {
		return conv;
	}

	/**
	 * @param conv the conv to set
	 */
	public void setConv(int conv) {
		this.conv = conv;
	}
}
