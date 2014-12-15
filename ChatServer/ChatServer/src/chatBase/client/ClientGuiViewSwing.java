package chatBase.client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


class ClientGuiViewSwing {

	// Frame Main
	private JFrame frameMain;
	private ClientGuiControllerActionListeners clientguiAL;
	
	// Chat
	protected JTextArea memoVerlauf = new JTextArea();
	protected JTextArea memoChat = new JTextArea();
	protected JButton buttonSenden = new JButton("Senden");
	protected ClientController client;
	
	// Config
	protected JTextField editServerIP = new JTextField();
	protected JTextField editServerPort = new JTextField();
	protected JTextField editClientNickname = new JTextField();
	
	// Login
	protected JButton buttonLogin = new JButton("Anmeldung am Server");
	protected JButton buttonLogout = new JButton("Abmeldung vom Server");
	protected boolean istVerbunden = false;
	
	// Kontakte
	protected JList<String> listKontakte = new JList<String>();
	
	// Werbung (bisl Geld verdienen :-) )
	private JTextArea memoWerbung = new JTextArea();
	protected AdClientController adCli;
	
	// MenuBar
	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu menuEinstellungen = new JMenu("Einstellungen");
		menuBar.add(menuEinstellungen);
		JMenuItem menuItemEinstellungenSichern = new JMenuItem("Sichern");
		menuEinstellungen.add(menuItemEinstellungenSichern);
		JMenuItem menuItemEinstellungenLaden = new JMenuItem("Laden");
		menuEinstellungen.add(menuItemEinstellungenLaden);
		return menuBar;
	}
	 
	private void setFixSizeOfComponent(JComponent comp, int width, int height){
		 Dimension d = new Dimension(width, height);
		 comp.setSize(d);
		 comp.setMinimumSize(d);
		 comp.setMaximumSize(d);
		 comp.setPreferredSize(d);
	 }
	 
	private JPanel createChatPanel() {
		 JPanel panel = new JPanel();
		 panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		 setFixSizeOfComponent(panel, 500, 400);
		 
		 JPanel wrapPanel4Chat = new JPanel();
		 wrapPanel4Chat.setLayout(new BoxLayout(wrapPanel4Chat, BoxLayout.X_AXIS));
		 
		 JPanel panelMemoChatWrap = new JPanel();
		 panelMemoChatWrap.setLayout(new GridLayout(1,1));
		 setFixSizeOfComponent(panelMemoChatWrap, 400, 100);
		 //setFixSizeOfComponent(memoChat, 400, 100);
		 
		 panelMemoChatWrap.add(new JScrollPane(memoChat));
		 
		 wrapPanel4Chat.add(panelMemoChatWrap);
		 wrapPanel4Chat.add(buttonSenden);
		 setFixSizeOfComponent(buttonSenden,100,100);
		 buttonSenden.addActionListener(clientguiAL.actionListenerButtonSenden);
		 
		 JPanel panelMemoVerlaufWrap = new JPanel();
		 panelMemoVerlaufWrap.setLayout(new GridLayout(1,1));
		 memoVerlauf.setEditable(false);
		 //setFixSizeOfComponent(memoVerlauf, 500, 300);
		 setFixSizeOfComponent(panelMemoVerlaufWrap, 500, 300);
		 panelMemoVerlaufWrap.add(new JScrollPane(memoVerlauf));
		 
		 panel.add(panelMemoVerlaufWrap);
		 panel.add(new JSeparator(SwingConstants.HORIZONTAL));
		 panel.add(wrapPanel4Chat);
		 setFixSizeOfComponent(wrapPanel4Chat,500,100);
		 
		 return panel;
	 }
	 
	private JPanel createConfigPanel(){
		 JPanel panelOuter = new JPanel();
		 panelOuter.setLayout(new BoxLayout(panelOuter, BoxLayout.X_AXIS));
		 setFixSizeOfComponent(panelOuter, 200, 400);
		 
		 JPanel panelInner = new JPanel();
		 panelInner.setLayout(new BoxLayout(panelInner, BoxLayout.Y_AXIS));
		 setFixSizeOfComponent(panelInner, 198, 398);
		 
		 JPanel panelEinstellungen = new JPanel();
		 panelEinstellungen.setLayout(new BoxLayout(panelEinstellungen, BoxLayout.Y_AXIS));
		 setFixSizeOfComponent(panelEinstellungen, 196, 160);
		 
		 
		 panelEinstellungen.add(new JLabel("Server IP:"));
		 panelEinstellungen.add(editServerIP);
		 panelEinstellungen.add(new JLabel("Server Port:"));
		 panelEinstellungen.add(editServerPort);
		 panelEinstellungen.add(new JLabel("Client Nickname:"));
		 panelEinstellungen.add(editClientNickname);
		 panelEinstellungen.add(Box.createRigidArea(new Dimension(0,2))); //bisl Abstand
		 panelEinstellungen.add(buttonLogin);
		 buttonLogin.addActionListener(clientguiAL.actionListenerButtonLogin);
		 setFixSizeOfComponent(buttonLogin, 194, 26);
		 panelEinstellungen.add(Box.createRigidArea(new Dimension(0,2))); //bisl Abstand
		 panelEinstellungen.add(buttonLogout);
		 buttonLogout.addActionListener(clientguiAL.actionListenerButtonLogout);
		 setFixSizeOfComponent(buttonLogout, 194, 26);
		 buttonLogout.setEnabled(false);
		 
		 
		 JPanel panelKontakte = new JPanel();
		 panelKontakte.setLayout(new BoxLayout(panelKontakte, BoxLayout.Y_AXIS));
		 setFixSizeOfComponent(panelKontakte, 196, 196);
		 panelKontakte.add(new JLabel("gerade online:"));
		 panelKontakte.add(new JScrollPane(listKontakte));
		 //setFixSizeOfComponent(listKontakte, 194, 194);
		 
		 panelInner.add(panelEinstellungen);
		 panelInner.add(Box.createRigidArea(new Dimension(0,40))); //bisl Abstand
		 panelInner.add(panelKontakte);
		 
		 panelOuter.add(panelInner);
		 panelOuter.add(new JSeparator(SwingConstants.VERTICAL));
		 return panelOuter;
	 }

	private JPanel createAdvertPanel(){
		 JPanel panel = new JPanel();
		 panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		 setFixSizeOfComponent(panel, 700, 100);
		 panel.add(memoWerbung);	 
		 memoWerbung.setEditable(false);
		 return panel;
	 }
	 
	// Frame Main
	private  void createFrame() {
		frameMain = new JFrame("Chat-App pre-alpha");
		frameMain.setLayout(new BorderLayout());
		
		
		frameMain.setResizable(false);
		
		setStandard();
		
		
		// try {
			//später: frameMain.setIconImage(ImageIO.read(getClass().getResource("./resources/nestIcon.png")));
		// } catch (IOException e) {
			// shouldn't happen.
			// If this happens: No icon, which isn't a real problem
		//	e.printStackTrace();
		// }
		//frameMain.setSize(800, 600);
		//frameMain.setMaximumSize(new Dimension(800, 600));
		//frameMain.setMinimumSize(new Dimension(800, 600));
		// frame.setResizable(false);
		frameMain.setLocationRelativeTo(null);
		frameMain.setLocation(100, 50);
		// SwingUtilities.updateComponentTreeUI(frame);
		frameMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frameMain.setJMenuBar(createMenuBar());
		frameMain.add(createConfigPanel(), BorderLayout.WEST);
		//frameMain.add(new JSeparator(SwingConstants.VERTICAL), BorderLayout.CENTER);
		frameMain.add(createChatPanel(), BorderLayout.CENTER);
		frameMain.add(createAdvertPanel(), BorderLayout.SOUTH);

		frameMain.pack();
	}
	 
	public ClientGuiViewSwing() {
		super();
		clientguiAL = new ClientGuiControllerActionListeners(this);
		adCli = new AdClientController(memoWerbung);
		createFrame();
	}

	// GUI-methodes
	public void setVisible(boolean visible) {
		frameMain.setVisible(visible);
	}

	public void dispose() {
		frameMain.dispose();
	}
	
	protected void appendMemoVerlauf(String str) {
		memoVerlauf.append(str);
		memoVerlauf.setCaretPosition(memoVerlauf.getText().length() - 1);
	}
	
	protected void verbindungsfehler() {
		buttonLogin.setEnabled(true);
		buttonLogout.setEnabled(false);
		setStandard();
		editServerIP.setEditable(true); //sab
		editServerPort.setEditable(true);	//sab
		
		//for (ActionListener al : buttonSenden.getActionListeners()){
		//	buttonSenden.removeActionListener(al);
		//}  //sab nicht nötig, über istverbunden geregelt
		
		istVerbunden = false;
	}
	
	private void setStandard(){
		// später durch autoload von einstellungen ersetzen (Feature 3)
		editServerIP.setText("127.0.0.1");
		editServerPort.setText("1500");
		editClientNickname.setText("Unbekannt");
	}

}
