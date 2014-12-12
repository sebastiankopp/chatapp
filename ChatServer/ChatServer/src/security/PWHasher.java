package security;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Klasse zum Erzeugen von Passworthashes nach dem SHA-256-Algorithmus
 * @author Sebastian
 *
 */
public class PWHasher {
	private MessageDigest md;
	private static final String ALGORITHM = "SHA-256";
	public static final String STD_ENCODING = "UTF-8";
	public PWHasher() throws NoSuchAlgorithmException{
		md = MessageDigest.getInstance(ALGORITHM);
	}
	public String createHash(String text) {
		try {
			md.update(text.getBytes(STD_ENCODING));
		} catch (UnsupportedEncodingException e) {
			md.update(text.getBytes());
		}
		byte[] hashval = md.digest();
		StringBuilder sb = new StringBuilder();
		for (Byte b: hashval){
			sb.append(String.format("%02x", b));
		}
		return sb.toString();
	}
}