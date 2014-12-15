package chatBase.chattest;
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import chatBase.model.Advertisement;
import chatBase.srv.SimonWerbeServerInterface;
import chatBase.srv.WerbeSender;
import de.root1.simon.Lookup;
import de.root1.simon.NameLookup;
import de.root1.simon.Simon;
import de.root1.simon.exceptions.EstablishConnectionFailed;
import de.root1.simon.exceptions.LookupFailedException;
import de.root1.simon.exceptions.NameBindingException;
public class SimonAdTest {
	private static final int AD_TEST_PORT = 22222;
	private WerbeSender ws;
	private Lookup nlu;
	private SimonWerbeServerInterface swsint;
	@Before
	public void pullUp(){
		try {
			ws = new WerbeSender(AD_TEST_PORT);
			nlu = Simon.createNameLookup("127.0.0.1", AD_TEST_PORT);
			swsint = (SimonWerbeServerInterface) nlu.lookup(WerbeSender.AD_BINDING);
		} catch (IOException | NameBindingException | LookupFailedException | EstablishConnectionFailed e) {
			fail(e.getMessage());
		}
	}
	@Test
	public void test(){
		Arrays.asList(
				new Advertisement("neues Waschmittel"), new Advertisement("Das schnellste Auto der Welt!!")
		).forEach(ws::addAdvertisement);
		for (int i = 0; i < 20; i++){
			System.out.println(swsint.createAd());
		}
		assertTrue(true);
	}
	@After
	public void after(){
		nlu.release(swsint);
	}
}
