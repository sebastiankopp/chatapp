package skoldvariant.model;

import java.io.Serializable;

public class UserAddSuccess implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8448219786683446532L;
	private String msg;
	private boolean succ;
	public UserAddSuccess(String msg, boolean succ){
		this.succ = succ;
		this.msg = msg;
	}
	@Override
	public String toString(){
		return "Nutzer "+ ((succ)?"erfolgreich":"NICHT") + " hinzugefügt: " + msg;
	}
}
