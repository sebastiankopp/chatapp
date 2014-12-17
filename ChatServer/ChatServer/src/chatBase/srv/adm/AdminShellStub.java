package chatBase.srv.adm;

import java.util.List;
/**
 * Dient der Kommunikation zwischen Chatserver und lokaler Adminkommandozeile
 * @author Sebastian
 *
 */
public interface AdminShellStub {
	public static final String DEFAULT_BINDING = "adsrvx";
	public static final int ADMIN_SHELL_PORT_OFFSET = 3;
	public String deleteUser(String username);
	public String addUser(String username, String pw);
	public List<String> seeUsers();
	public void shutdownServer();
}
