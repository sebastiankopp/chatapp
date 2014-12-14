package skoldvariant.model;

public class PWChangeSuccess {
	private boolean success;
	private String message;
	public PWChangeSuccess(boolean sc, String msg){
		success =sc;
		message = msg;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @return the success
	 */
	public boolean isSuccess() {
		return success;
	}
}
