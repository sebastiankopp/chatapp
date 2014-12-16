package chatBase.model;

public class PWChangeResponse extends ChatMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7268164201076380448L;
	private boolean success;
	public PWChangeResponse(){super(PW_CH_RESP);}
	public PWChangeResponse(boolean success){
		super(PW_CH_RESP);
		this.setSuccess(success);
	}
	/**
	 * @return the success
	 */
	public boolean isSuccess() {
		return success;
	}
	/**
	 * @param success the success to set
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}
}
