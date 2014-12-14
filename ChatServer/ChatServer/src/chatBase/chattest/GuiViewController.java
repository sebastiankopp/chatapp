package chatBase.chattest;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
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
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


class GuiViewController {

	// Frame Main
	JFrame frameMain;
	
	// Chat
	JTextArea memoVerlauf = new JTextArea();
	JTextArea memoChat = new JTextArea();
	JButton buttonSenden = new JButton("Senden");


	// MenuBar
	 JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu menuEinstellungen = new JMenu("Einstellungen");
		menuBar.add(menuEinstellungen);
		JMenuItem menuItemEinstellungenSichern = new JMenuItem("Sichern");
		menuEinstellungen.add(menuItemEinstellungenSichern);
		return menuBar;
	}
	 
	 void setFixSizeOfComponent(JComponent comp, int width, int height){
		 Dimension d = new Dimension(width, height);
		 comp.setSize(d);
		 comp.setMinimumSize(d);
		 comp.setMaximumSize(d);
		 comp.setPreferredSize(d);
	 }
	 
	 JPanel createChatPanel() {
		 JPanel panel = new JPanel();
		 panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		 setFixSizeOfComponent(panel, 500, 400);
		 
		 JPanel wrapPanel4Chat = new JPanel();
		 wrapPanel4Chat.setLayout(new BoxLayout(wrapPanel4Chat, BoxLayout.X_AXIS));
		 wrapPanel4Chat.add(memoChat);
		 setFixSizeOfComponent(memoChat, 400, 100);
		 wrapPanel4Chat.add(buttonSenden);
		 setFixSizeOfComponent(buttonSenden,100,100);
		 
		 panel.add(memoVerlauf);
		 setFixSizeOfComponent(memoVerlauf, 500, 300);
		 panel.add(new JSeparator(SwingConstants.HORIZONTAL));
		 panel.add(wrapPanel4Chat);
		 setFixSizeOfComponent(wrapPanel4Chat,500,100);
		 
		 return panel;
	 }
	 
	 JPanel createConfigPanel(){
		 JPanel panelOuter = new JPanel();
		 panelOuter.setLayout(new BoxLayout(panelOuter, BoxLayout.X_AXIS));
		 setFixSizeOfComponent(panelOuter, 200, 400);
		 
		 JPanel panelInner = new JPanel();
		 panelInner.setLayout(new BoxLayout(panelInner, BoxLayout.Y_AXIS));
		 setFixSizeOfComponent(panelInner, 198, 398);
		 
		 JTextField editServerIP = new JTextField();
		 JTextField editServerPort = new JTextField();
		 JTextField editClientNickname = new JTextField();
		 JButton buttonLogin = new JButton("Anmeldung am Server");
		 JList<String> listKontakte = new JList<String>();
		 
		 JPanel panelEinstellungen = new JPanel();
		 panelEinstellungen.setLayout(new BoxLayout(panelEinstellungen, BoxLayout.Y_AXIS));
		 setFixSizeOfComponent(panelEinstellungen, 196, 100);
		 
		 panelEinstellungen.add(new JLabel("Server IP:"));
		 panelEinstellungen.add(editServerIP);
		 panelEinstellungen.add(new JLabel("Server Port:"));
		 panelEinstellungen.add(editServerPort);
		 panelEinstellungen.add(new JLabel("Client Nickname:"));
		 panelEinstellungen.add(editClientNickname);
		 
		 JPanel panelKontakte = new JPanel();
		 panelKontakte.setLayout(new BoxLayout(panelKontakte, BoxLayout.Y_AXIS));
		 setFixSizeOfComponent(panelKontakte, 196, 256);
		 panelKontakte.add(new JLabel("gerade online:"));
		 panelKontakte.add(listKontakte);
		 
		 panelInner.add(panelEinstellungen);
		 panelInner.add(Box.createRigidArea(new Dimension(0,40))); //bisl Abstand
		 panelInner.add(panelKontakte);
		 
		 panelOuter.add(panelInner);
		 panelOuter.add(new JSeparator(SwingConstants.VERTICAL));
		 return panelOuter;
	 }

	// Frame Main
	 void createFrame() {
		frameMain = new JFrame("Chat-App pre-alpha");
		frameMain.setLayout(new BorderLayout());
		frameMain.setResizable(false);
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
		
		memoVerlauf.setEditable(false);

		frameMain.pack();
	}
	 
	public GuiViewController() {
		super();
		createFrame();
	}

	// GUI-methodes
	public void setVisible(boolean visible) {
		frameMain.setVisible(visible);
	}

	public void dispose() {
		frameMain.dispose();
	}
	
	// Control Actionlisterners
	ActionListener actionListenerButtonSenden = new ActionListener() {
		public void actionPerformed(ActionEvent e) {	
		}
	}; 

}
