package skoldvariant.test.srvtest;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

import skoldvariant.model.ChatMessage;
import skoldvariant.model.Conversation;
import skoldvariant.model.User;

import com.google.gson.Gson;

public class SimpleMessagingTest extends AbstractServerTest{

	@Override
	public void test() {
		// TODO Auto-generated method stub
		try{
			Gson gsc = new Gson();
			String urlix = "http://127.0.0.1:" + mhs.getPort() + mhs.getResourcePath();
			URL obj = new URL(urlix);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			User u1 = new User(1, "kA", "Irgendwer"), u2 = new User(2, "ano", "Ano Nymus");
			ChatMessage cmessg = new ChatMessage("Servus", u1, new Conversation(815, Arrays.asList(u1, u2)), "ksjdf");
			
			con.setDoOutput(true);
			con.setRequestMethod("POST"); 
			DataOutputStream dosx = new DataOutputStream(con.getOutputStream());
			String bodyx = gsc.toJson(cmessg, ChatMessage.class);
			dosx.writeBytes(bodyx);
			dosx.flush();
			dosx.close();

			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'POST' request to URL : " + urlix);
			System.out.println("Response Code : " + responseCode);
			
//			con.setDoOutput(true);
						
			BufferedReader in = new BufferedReader( new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
		
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			//print result
			System.out.println(response.toString());
			boolean rc = response.toString().endsWith(bodyx);
			System.out.println(rc?"YEAH":"NOOOOOOO");
			assertTrue(rc);

		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

}
