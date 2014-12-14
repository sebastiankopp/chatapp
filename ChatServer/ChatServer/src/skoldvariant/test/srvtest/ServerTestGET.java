package skoldvariant.test.srvtest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import static org.junit.Assert.*;
public class ServerTestGET extends AbstractServerTest {
	

	public void test(){
		try {
			String urlix = "http://127.0.0.1:" + mhs.getPort() + mhs.getResourcePath() + "/foo";
			URL obj = new URL(urlix);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET"); 
			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'GET' request to URL : " + urlix);
			System.out.println("Response Code : " + responseCode);
 
			BufferedReader in = new BufferedReader( new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
 
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
 
			//print result
			System.out.println(response.toString());
			assertTrue(response.toString().length() > 0);

		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
//		try {
//			Thread.sleep(50);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	
}
