package communication;

import model.UserAddSuccess;

import com.google.gson.Gson;

public class GsonPackAndDepack {
	
	private Gson gs;
	public GsonPackAndDepack(){
		gs = new Gson();
	}
	
	// TODO typesafe methods
	public UserAddSuccess makeUserAddSuccFromJson(String json){
		return gs.fromJson(json, UserAddSuccess.class);
	}
	public String jsonFromUserAddSuccess(UserAddSuccess uas){
		return gs.toJson(uas, UserAddSuccess.class);
	}
	
	
}

