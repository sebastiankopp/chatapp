package communication;

import model.ChatMessage;
import model.UserAddSuccess;

import com.google.gson.Gson;

public class GsonPackAndDepack {
	
	private Gson gs;
	public GsonPackAndDepack(){
		gs = new Gson();
	}
	@Deprecated
	public <T> String toJson(T obj){
		return gs.toJson(obj, obj.getClass());
	}
	public <T> String toJson(T obj, Class<T> _class){
		return gs.toJson(obj, _class);
	}
	public <T> T fromJson(String json, Class<T> _class){
		return gs.fromJson(json, _class);
	}
}

