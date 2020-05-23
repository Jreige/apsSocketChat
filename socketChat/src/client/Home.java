package client;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import common.Utils;

public class Home extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ArrayList<String> openedChats;
	private Map<String, CClientListener> connectedListeners;
	private ArrayList<String> connectedUsers;
	private String connectionInfo;
	private Socket connection;
	private ServerSocket server;
	private boolean running;
	
	private JLabel jLTitle;
	private JButton jBGetConnected, jBStartTalk;
	private JList jList;
	private JScrollPane jScroll;

	public Home(Socket connection, String connectionInfo) {
		super("Chat - Home");
		this.connectionInfo = connectionInfo;
		this.connection = connection;
		initComponents();
		configComponents();
		insertComponents();
		insertActions();
		start();
	}

	private void initComponents() {
		jLTitle = new JLabel("< usuario: " + connectionInfo.split(":")[0] + " >", SwingConstants.CENTER);
		jBGetConnected = new JButton("Atualizar Contatos");
		jBStartTalk = new JButton("Iniciar Conversa");
		jList = new JList();
		jScroll = new JScrollPane(jList);
		connectedUsers = new ArrayList<String>();
		openedChats = new ArrayList<String>();
		connectedListeners = new HashMap<String, CClientListener>();
		server = null;
		running = false;
	}

	private void configComponents() {

		this.setLayout(null);
		this.setMinimumSize(new Dimension(600,480));
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setBackground(Color.WHITE);
		
		jLTitle.setBounds(10, 10, 370, 40);
		jLTitle.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		
		jBGetConnected.setBounds(400, 10, 180, 40);
		jBGetConnected.setFocusable(false);
		
		jBStartTalk.setBounds(10, 400, 575, 40);
		jBStartTalk.setFocusable(false);
		
		jList.setBorder(BorderFactory.createTitledBorder("Usuarios Online"));
		jList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		jScroll.setBounds(10, 60, 575, 335);
		jScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		jScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jScroll.setBorder(null);
	}

	private void insertComponents() {
		this.add(jLTitle);
		this.add(jBGetConnected);
		this.add(jBStartTalk);
		this.add(jScroll);
	}

	private void insertActions() {
		this.addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {
			}
			
			@Override
			public void windowIconified(WindowEvent e) {
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent e) {
				
			}
			
			@Override
			public void windowDeactivated(WindowEvent e) {
			}
			
			@Override
			public void windowClosing(WindowEvent e) {
				running = false;
				Utils.sendMessage(connection, "QUIT");
				System.out.println("> Conexão encerrada.");
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				
			}
			
			@Override
			public void windowActivated(WindowEvent e) {
				
			}
		});
		
		jBGetConnected.addActionListener(event -> getConnectedUsers());
		jBStartTalk.addActionListener(event -> openChat());
	}

	private void start() {
		this.pack();
		this.setVisible(true);
		startServer(this, Integer.parseInt(connectionInfo.split(":")[2]));
	}
	
	private void getConnectedUsers() {
		Utils.sendMessage(connection, "GET_CONNECTED_USERS");
		String response = Utils.receiveMessage(connection);
		jList.removeAll();
		connectedUsers.clear();
		for(String info : response.split(";")) {
			if(!info.equals(connectionInfo)) {
				connectedUsers.add(info);
			}
		}
		jList.setListData(connectedUsers.toArray());
	}

	public ArrayList<String> getOpenedChats() {
		return openedChats;
	}

	public Map<String, CClientListener> getConnectedListeners() {
		return connectedListeners;
	}

	public String getConnectionInfo() {
		return connectionInfo;
	}

	private void openChat() {
		int index = jList.getSelectedIndex();
		
		if(index != -1) {
			String value = jList.getSelectedValue().toString();
			String[] splited = connectionInfo.split(":");
			if(!openedChats.contains(connectionInfo)) {
				try {
					Socket connection = new Socket(splited[1], Integer.parseInt(splited[2]));
					Utils.sendMessage(connection, "OPEN_CHAT;" + this.connectionInfo);//Abre chat com connectInfo deste home
					
					CClientListener cClientListener = new CClientListener(this, connection);
					cClientListener.setChat(new Chat(this, connection, connectionInfo, this.connectionInfo.split(":")[0]));
					cClientListener.setChatOpen(true);
					connectedListeners.put(connectionInfo, cClientListener);
					openedChats.add(connectionInfo);
				} catch (IOException e) {
					System.out.println("[Home:openChat] -> " + e.getMessage());
				}
			}
		}
	}

	private void startServer(Home home, int port) {
		new Thread() {
			@Override
			public void run() {
				running = true;
				try {
					server = new ServerSocket(port);
					System.out.println("Servidor cliente iniciado na porta: " + port);
					while(running) {
						Socket connection = server.accept();
						CClientListener cClientListener = new CClientListener(home, connection);
						new Thread(cClientListener).start();
					}
				}catch(IOException e) {
					System.out.println("[Home:startServer] -> " + e.getMessage());
				}
			}
		}.start();
	}
}
