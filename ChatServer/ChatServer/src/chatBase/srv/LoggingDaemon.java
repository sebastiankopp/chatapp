package chatBase.srv;

import java.io.PrintStream;
/**
 * Klasse zum Logging für den Server
 * @author Sebastian Kopp
 *
 */
public class LoggingDaemon {
	private PrintStream pw;
	public LoggingDaemon(PrintStream ps){
		this.setPw(ps);
	}
	/**
	 * @return the pw
	 */
	public PrintStream getPw() {
		return pw;
	}
	/**
	 * @param pw the pw to set
	 */
	public void setPw(PrintStream pw) {
		this.pw = pw;
	}
	public void finish(){
		pw.close();
	}
}
