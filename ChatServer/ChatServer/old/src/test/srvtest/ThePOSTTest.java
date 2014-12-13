package test.srvtest;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

public class ThePOSTTest extends AbstractServerTest {

	@Override 
	public void test() {
		// TODO Auto-generated method stub
		try{
			String urlix = "http://127.0.0.1:" + mhs.getPort() + mhs.getResourcePath();
			URL obj = new URL(urlix);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setDoOutput(true);
			con.setRequestMethod("POST"); 
			DataOutputStream dosx = new DataOutputStream(con.getOutputStream());
			String bodyx = UUID.randomUUID().toString();
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
			assertTrue(response.toString().length() > 0 );

		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

}
