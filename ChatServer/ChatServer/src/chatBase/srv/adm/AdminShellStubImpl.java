package chatBase.srv.adm;

import java.util.List;

import chatBase.srv.DBAdapter;
import chatBase.srv.Server;
import de.root1.simon.annotation.SimonRemote;
@SimonRemote(value = {AdminShellStub.class})
public class AdminShellStubImpl implements AdminShellStub {
	private DBAdapter dba;
	private Server srv;
	public AdminShellStubImpl(Server srv){
		dba = DBAdapter.getInstance();
		this.srv = srv;
	}
	@Override
	public String deleteUser(String username) {
		boolean rc = dba.deleteUser(username);
		return "User " + username + (rc?" erfolgreich ":" nicht ") + "gelöscht";
	}

	@Override
	public String addUser(String username, String pw) {
		boolean rc = dba.addUser(username, pw);
		return "User " + username + (rc?" erfolgreich ":" nicht ") + "hinzugefügt";
	}

	@Override
	public List<String> seeUsers() {
		return dba.listUsers();
	}
	@Override
	public void shutdownServer() {
		// TODO Auto-generated method stub
		srv.stop();
	}

}
