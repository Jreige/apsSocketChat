package client;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import common.Utils;

public class Chat extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Home home;
	private Socket connection;
	private String connectionInfo;
	private ArrayList<String> messageList;

	private JLabel jLTitle;
	private JEditorPane jEPMessages;
	private JTextField jTFMessages;
	private JButton jBMessages;
	private JPanel jPanel;
	private JScrollPane jScroll;

	public Chat(Home home, Socket connection, String connectionInfo, String title) {
		super("Chat " + title);
		this.home = home;
		this.connection = connection;
		this.connectionInfo = connectionInfo;
		initComponents();
		configComponents();
		insertComponents();
		insertActions();
		start();
	}

	private void initComponents() {
		messageList = new ArrayList<String>();
		jLTitle = new JLabel(connectionInfo.split(":")[0], SwingConstants.CENTER);
		jEPMessages = new JEditorPane();
		jScroll = new JScrollPane(jEPMessages);
		jTFMessages = new JTextField();
		jBMessages = new JButton("Enviar");
		jPanel = new JPanel(new BorderLayout());

	}

	private void configComponents() {
		this.setMinimumSize(new Dimension(480, 720));
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		jEPMessages.setContentType("text/html");
		jEPMessages.setEditable(false);
		jScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		jScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		jBMessages.setSize(100, 40);
	}

	private void insertComponents() {
		this.add(jLTitle, BorderLayout.NORTH);
		this.add(jScroll, BorderLayout.CENTER);
		this.add(jPanel, BorderLayout.SOUTH);
		jPanel.add(jTFMessages, BorderLayout.CENTER);
		jPanel.add(jBMessages, BorderLayout.EAST);
	}

	public void appenedMessage(String received) {
		messageList.add(received);
		String message = "";
		for (String str : messageList)
			message += str;

		jEPMessages.setText(message);
	}

	private void send() {
		if (jTFMessages.getText().length() > 0) {
			DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
			String message = "<b>[ " + dateFormat.format(new Date()) + "]" + this.jLTitle.getText() + ": </b><i>" + jTFMessages.getText() + "</i><br>";
			Utils.sendMessage(connection, "MESSAGE;" + message);
			appenedMessage("<b>["+ dateFormat.format(new Date()) + "] Eu: </b><i>" + jTFMessages.getText() + "</i><br>");
			jTFMessages.setText("");
		}
	}

	private void start() {
		this.pack();
		this.setVisible(true);
	}
	
	private void insertActions() {
		jBMessages.addActionListener(event -> send());
		jTFMessages.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyChar() == KeyEvent.VK_ENTER)
					send();
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}
		});
	}
	
//	public static void main(String[] args) {
//		Chat chat = new Chat("Victor:127.0.0.1:8584", "alguem");
//	}
}
