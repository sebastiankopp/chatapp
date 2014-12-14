package chatBase.chattest;
/**
 * @author Tobias Köhler
 * Real GUI 4 View-Programmers
 **/

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


class CopyOldOfGuiViewController {

	// Frame Main
	JFrame frameMain;
	
	// Contacts
	JList listKontakte = new JList();
	
	// Chat
	JTextArea memoVerlauf = new JTextArea();
	JTextArea memoChat = new JTextArea();
	JButton buttonSenden = new JButton("Senden");

	// MenuBar
	 JMenu menuDatei;

	// MenuBar Implementation:
	 JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		menuDatei = new JMenu("Einstellungen");
		menuBar.add(menuDatei);
		//menuItemImport = new JMenuItem2("import file...");
		//menuImport.add(menuItemImport);
		return menuBar;
	}
	 
	 JPanel createChatPanel() {
		 JPanel panel = new JPanel();
		 panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		 
		 JPanel wrapPanel4Verlauf = new JPanel();
		 wrapPanel4Verlauf.setLayout(new BoxLayout(wrapPanel4Verlauf, BoxLayout.X_AXIS));
		 wrapPanel4Verlauf.add(Box.createRigidArea(new Dimension(0,300)));
		 wrapPanel4Verlauf.add(memoVerlauf);
		 
		 JPanel wrapPanel4Chat = new JPanel();
		 wrapPanel4Chat.setLayout(new BoxLayout(wrapPanel4Chat, BoxLayout.X_AXIS));
		 wrapPanel4Chat.add(memoChat);
		 wrapPanel4Chat.add(buttonSenden);
		 
		 panel.add(wrapPanel4Verlauf);
		 panel.add(new JSeparator(SwingConstants.HORIZONTAL));
		 panel.add(wrapPanel4Chat);
		 
		 return panel;
	 }
	 
	 JPanel createKontaktPanel(){
		 JPanel panel = new JPanel();
		 panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		 
		 JPanel wrapPanel = new JPanel();
		 wrapPanel.setLayout(new BoxLayout(wrapPanel, BoxLayout.Y_AXIS));
		 
		 JLabel lkontakte=new JLabel("Kontakte");
		 
		 wrapPanel.add(lkontakte);
		 wrapPanel.add(Box.createVerticalGlue());
		 wrapPanel.add(Box.createRigidArea(new Dimension (200,0)));
		 wrapPanel.add(listKontakte);
		 
		 panel.add(wrapPanel);
		 panel.add(new JSeparator(SwingConstants.VERTICAL));
		 panel.add(new JSeparator(SwingConstants.VERTICAL));
		 return panel;
	 }

	// Frame Main
	 void createFrame() {
		frameMain = new JFrame("Chat-App pre-alpha");
		frameMain.setLayout(new BorderLayout());
		// try {
			//später: frameMain.setIconImage(ImageIO.read(getClass().getResource("./resources/nestIcon.png")));
		// } catch (IOException e) {
			// shouldn't happen.
			// If this happens: No icon, which isn't a real problem
		//	e.printStackTrace();
		// }
		frameMain.setSize(800, 600);
		frameMain.setMaximumSize(new Dimension(800, 600));
		frameMain.setMinimumSize(new Dimension(800, 600));
		// frame.setResizable(false);
		frameMain.setLocationRelativeTo(null);
		frameMain.setLocation(100, 50);
		// SwingUtilities.updateComponentTreeUI(frame);
		frameMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frameMain.setJMenuBar(createMenuBar());
		frameMain.add(createKontaktPanel(), BorderLayout.WEST);
		//frameMain.add(new JSeparator(SwingConstants.VERTICAL), BorderLayout.CENTER);
		frameMain.add(createChatPanel(), BorderLayout.CENTER);

		frameMain.pack();
	}

	public CopyOldOfGuiViewController() {
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

}
