package test;

import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import javax.swing.JOptionPane;

import org.junit.Test;

import security.PWHasher;
public class SecuTest {
	
	@Test
	public void test1(){
		try {
			PWHasher pwh = new PWHasher();
			String in = JOptionPane.showInputDialog("Bitte was eingeben");
			String anotherOne = JOptionPane.showInputDialog("Bitte das gleiche nochmal eingeben");
			String hash1 = pwh.createHash(in);
			String hash2 = pwh.createHash(anotherOne);
			if (pwh.createHash(in).length() != 64) fail("Falsche Hashlänge");
			assertEquals(hash1, hash2);
			System.out.println("Hash1: " + hash1 + "\nHash2: " + hash2);
		} catch (NoSuchAlgorithmException e) {
			fail(e.getMessage());
		}
	}
	@Test
	public void test2(){
		try {
			PWHasher pwh = new PWHasher();
			String in = JOptionPane.showInputDialog("Bitte was eingeben");
			String anotherOne = JOptionPane.showInputDialog("Bitte was anderes eingeben");
			String hash1 = pwh.createHash(in);
			String hash2 = pwh.createHash(anotherOne);
			if (pwh.createHash(in).length() != 64) fail("Falsche Hashlänge");
			assertNotEquals(hash1, hash2);
			System.out.println("Hash1: " + hash1 + "\nHash2: " + hash2);
		} catch (NoSuchAlgorithmException e) {
			fail(e.getMessage());
		}
	}
}
