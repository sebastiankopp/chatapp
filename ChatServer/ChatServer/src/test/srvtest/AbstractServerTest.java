package test.srvtest;

import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import communication.MainHttpServer;

public abstract class AbstractServerTest {
	protected MainHttpServer mhs = null;
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
	public abstract void test();
	
	@org.junit.After
	public void tearDown(){
		if (mhs != null)
			mhs.finish();
	}
}
