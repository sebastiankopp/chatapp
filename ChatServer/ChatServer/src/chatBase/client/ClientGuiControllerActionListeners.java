package chatBase.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import chatBase.model.ChatMessage;
import chatBase.model.ChatMessageMessage;

public class ClientGuiControllerActionListeners {
	
	private ClientGuiViewSwing gui;
	public ActionListener actionListenerButtonLogout;
	public ActionListener actionListenerButtonLogin;
	public ActionListener actionListenerButtonSenden;
	public ActionListener actionListenerEinstellungenSichern;
	public ActionListener actionListenerEinstellungenLaden;
	
	public ClientGuiControllerActionListeners(ClientGuiViewSwing clientGuiView) {
		gui = clientGuiView;
	

	// Control Actionlisterners
		
	actionListenerEinstellungenSichern = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			File file =new File("config.cfg");
			//System.out.println("newFile");
			 
    	    //if file doesnt exists, then create it
    	    if(!file.exists()){
    	    	try {
					file.createNewFile();
					//System.out.println("try");
				} catch (IOException e) {
					// do nothing
					e.printStackTrace();
					//System.out.println("catch");
				}
    	    }
    	    //System.out.println("afterfirsttry");
    	    
    	    
    	    List<String> aLines = new ArrayList<String>();
    	    try {
        	    aLines.add(gui.editClientNickname.getText().trim());
        	    aLines.add(gui.editServerIP.getText().trim());
        	    aLines.add(gui.editServerPort.getText().trim());
				ClientController.writeSmallTextFile(aLines, "config.cfg");
				//System.out.println("try2");
			} catch (Exception e) {
				// do nothing
				e.printStackTrace();
				//System.out.println("catch2");
			};
			//System.out.println("aftersectry");
			
		}
	};
	
	actionListenerEinstellungenLaden = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			File file =new File("config.cfg");
			//System.out.println("newFile");
			 
    	    //if file doesnt exists, then create it
    	    if(!file.exists()){
    	    	try {
					file.createNewFile();
					//System.out.println("try");
				} catch (IOException e) {
					// do nothing
					e.printStackTrace();
					//System.out.println("catch");
				}
    	    }
    	    //System.out.println("afterfirsttry");
    	    
    	    List<String> aLines = new ArrayList<String>();
    	    
    	    try {
				aLines = (List<String>) ClientController.readSmallTextFile("config.cfg");
				gui.editClientNickname.setText(aLines.get(0));
				gui.editServerIP.setText(aLines.get(1));
				gui.editServerPort.setText(aLines.get(2));
				//System.out.println("try2");
			} catch (Exception e) {
				// do nothing
				e.printStackTrace();
				//System.out.println("catch2");
			};
			//System.out.println("aftersectry");
			
		}
	};
	
	actionListenerButtonLogin = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			String nickname = gui.editClientNickname.getText().trim();
			String password = new String (gui.passwordEditClientPassword.getPassword());
			String serverIP = gui.editServerIP.getText().trim();
			String serverPort = gui.editServerPort.getText().trim();
			int intServerPort = 1500; //standard
			try {
				intServerPort = Integer.parseInt(serverPort);
			}
			catch(Exception en) {
				//nix  
			}

			//genug Infos hierfür:
			gui.client = new ClientController(serverIP, intServerPort, nickname, password, gui);
			gui.adCli.startAdv(serverIP, (intServerPort+2));

			if(gui.client.start()) {
				
			gui.memoChat.setText("");
			gui.istVerbunden = true;
			
			// zeug nach login deaktivieren, bzw aktivieren
			gui.buttonLogin.setEnabled(false);
			gui.buttonLogout.setEnabled(true);

			gui.editServerIP.setEditable(false);
			gui.editServerPort.setEditable(false);
			gui.editClientNickname.setEditable(false);
			gui.passwordEditClientPassword.setEditable(false);}
		}
	}; 
	
	actionListenerButtonSenden = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if (gui.istVerbunden){
	gui.client.sendMessage(new ChatMessageMessage(ChatMessage.MESSAGE, gui.memoChat.getText()));				
	gui.memoChat.setText("");
	gui.memoChat.requestFocusInWindow();}
			//else nix machen
		}
	}; 
	
	actionListenerButtonLogout = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {	
			gui.client.sendMessage(new ChatMessage(ChatMessage.LOGOUT));
			
			gui.deleteObjects();
			
			gui.resetGuiOptions();
			
			gui.createObjects();
		}
	}; 
	
}
}
