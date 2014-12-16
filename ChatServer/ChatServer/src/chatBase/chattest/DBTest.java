package chatBase.chattest;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import chatBase.srv.DBAdapter;
public class DBTest {
	private DBAdapter dba;
	
	@Before
	public void zuerst(){
		dba = DBAdapter.getInstance();
	}
	@Test
	public void testAddAndVerify(){
		String uname = "hans";
		String passwd = "wurst";
		dba.addUser(uname, passwd);
		assertTrue(dba.verifyUsr(uname, passwd));
	}
	@Test
	public void testAddAndFalsify(){
		String uname = "hans";
		String passwd = "wurst";
		dba.addUser(uname, passwd);
		assertTrue(!dba.verifyUsr(uname, passwd + "x"));
	}
	@After
	public void tearDown(){
		
	}
}
