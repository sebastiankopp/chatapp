package skoldvariant.test;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import skoldvariant.model.ChatMessage;
import skoldvariant.model.Conversation;
import skoldvariant.model.User;

import com.google.gson.Gson;

public class GsonTest {
	private Gson gspdp;
	@Before
	public void setUp(){
		gspdp = new Gson();
	}
	@Test 
	public void test1(){
		List<User> ux = Arrays.asList(
				new User(1, "hans", "Hans Wurst"),
				new User(2, "max", "Max Mustermann"),
				new User(3, "heinz", "Heinz Heinzelsen"),
				new User(4, "wurst", "Wurst Müller")
		);
		Conversation cnv1 = new Conversation(4711, ux);
		StringBuilder msgtext = new StringBuilder();
		long ctxx = 30 + Math.round(90*Math.random());
		for (int i = 0; i < ctxx; i++)
			msgtext.append(UUID.randomUUID().toString());
		ChatMessage msg = new ChatMessage(msgtext.toString(), ux.get(0), cnv1, "tokentoken");
		String before = msg.toString();
		System.out.println(before);
		String json = gspdp.toJson(msg, ChatMessage.class);
		System.out.println(json);
		int jby, tby;
		System.out.println("Json-Größe in B: " + (jby = json.getBytes().length));
		System.out.println("Text-Größe in B: " + (tby = msgtext.toString().getBytes().length));
		System.out.printf("Effizienz: %.1f %%\n", tby/(jby*0.01));
		String after = gspdp.fromJson(json, ChatMessage.class).toString();
		System.out.println(after);
		assertEquals(after, before);
	}
	
}
