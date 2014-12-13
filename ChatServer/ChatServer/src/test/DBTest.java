package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import db.CUDAdapter;
import db.RAdapter;

public class DBTest {
	private CUDAdapter cad;
	private RAdapter rad;
	private String uname = "xyzilios";
	@Before
	public void setUp(){
		cad = new CUDAdapter();
		rad = new RAdapter();
	}
	@Test
	public void test() {
		String pw ="lksjfsadfhjsafhsafsadsaf";
		cad.addUser(uname, pw);
		assertTrue(rad.validateUser(uname, pw));
	}
	@After
	public void pullDown(){
//		cad.deleteUser(u)
		boolean rc = cad.deleteUserByNickName(uname);
		System.out.println(rc);
	}

}
