package skoldvariant.admincli;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import skoldvariant.db.CUDAdapter;

public class ServerSideCliThread extends Thread {
	public static final String FILE_FROM_SRV = "foo.txt";
	public static final String FILE_TO_SRV = "bar.txt";
	public static final int CLI_SLEEP = 1800;
	private File in, out;
//	private RAdapter rad;
	private CUDAdapter cud;
	public ServerSideCliThread() {
		in = new File(FILE_TO_SRV);
		out = new File(FILE_TO_SRV);
//		rad = new RAdapter();
		cud = new CUDAdapter();
	}
	@Override
	public void run(){
		while (true){
			try {
				PrintWriter pw = new PrintWriter(out);
				BufferedReader br = new BufferedReader(new FileReader(in));
				List<String> lines = new LinkedList<String>();
				String line;
				while ((line = br.readLine()) != null){
					lines.add(line);
				}
				br.close();
				FileWriter fr2 = new FileWriter(in);
				fr2.write(' ');
				fr2.close();
				for (String line2: lines){
					String ret = processStatement(line2);
					pw.println(ret);
				}
				pw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				Thread.sleep(CLI_SLEEP);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			continue;
		}
	}
	public String processStatement(String stmt){
		boolean rc;
		String[] parts = stmt.split("\\s+");
		switch(parts[0].toLowerCase()){
		case AdminShell.COMMAND_ADDUSER: 
			rc = cud.addUser(parts[1], parts[2]);
			if (rc) return "User added";
			else return "Useradd failed";
		case AdminShell.COMMAND_DELUSER:
			rc = cud.deleteUserByNickName(parts[1]);
			if (rc) return "User deleted";
			else return "Userdelete failed";
		case AdminShell.COMMAND_SHUTDOWN:
			return "Not yet implemented";
		default: return "Command not accepted";
		}
	}
}
