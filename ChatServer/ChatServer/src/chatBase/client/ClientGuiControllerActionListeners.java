package chatBase.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import chatBase.model.ChatMessage;
import chatBase.model.ChatMessageMessage;

public class ClientGuiControllerActionListeners {
	
	private ClientGuiViewSwing gui;
	public ActionListener actionListenerButtonLogout;
	public ActionListener actionListenerButtonLogin;
	public ActionListener actionListenerButtonSenden;
	
	public ClientGuiControllerActionListeners(ClientGuiViewSwing clientGuiView) {
		gui = clientGuiView;
	

	// Control Actionlisterners
	
	actionListenerButtonLogin = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			String nickname = gui.editClientNickname.getText().trim();
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
			gui.client = new ClientController(serverIP, intServerPort, nickname, gui);
			gui.adCli.startAdv(serverIP, (intServerPort+2));

			if(gui.client.start()) {
				
			gui.memoChat.setText("");
			gui.istVerbunden = true;
			
			// zeug nach login deaktivieren, bzw aktivieren
			gui.buttonLogin.setEnabled(false);
			gui.buttonLogout.setEnabled(true);

			gui.editServerIP.setEditable(false);
			gui.editServerPort.setEditable(false);
			gui.editClientNickname.setEditable(false);}
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
			
			gui.buttonLogin.setEnabled(true);
			gui.buttonLogout.setEnabled(false);

			gui.editServerIP.setEditable(true);
			gui.editServerPort.setEditable(true);
			gui.editClientNickname.setEditable(true);
			
			gui.adCli.stopAdv();
		}
	}; 
	
}
}
