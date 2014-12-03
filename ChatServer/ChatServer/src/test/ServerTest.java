package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import communication.MainHttpServer;
import static org.junit.Assert.*;
public class ServerTest {
	private MainHttpServer mhs = null;
	@Before
	public void pullUp() {
		try {
			mhs = new MainHttpServer();
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		}
	}
	@Test
	public void test(){
		try {
			String urlix = mhs.getUrl();
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
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		try {
			Thread.sleep(50000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@org.junit.After
	public void tearDown(){
		if (mhs != null)
			mhs.finish();
	}
}
