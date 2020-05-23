package client;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.io.IOException;
import java.net.Socket;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import common.Utils;
import server.Server;

public class Login extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton jBLogin;
	private JLabel jLUser, jLPort, jLTitle;
	private JTextField jTPort, jTUSER;

	public Login() {
		super("Login");
		initComponents();
		configComponents();
		insertComponents();
		insertActions();
		start();
	}

	private void initComponents() {
		jBLogin = new JButton("Entrar");
		jLUser = new JLabel("Apelido:", SwingConstants.CENTER);
		jLPort = new JLabel("Porta:", SwingConstants.CENTER);
		jLTitle = new JLabel();
		jTPort = new JTextField();
		jTUSER = new JTextField();
	}

	private void configComponents() {
		this.setLayout(null);
		this.setMinimumSize(new Dimension(400, 300));
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setBackground(Color.WHITE);

		jLTitle.setBounds(10, 10, 375, 100);
		final ImageIcon icon = new ImageIcon("./images/icone.svg");
		jLTitle.setIcon(new ImageIcon(icon.getImage().getScaledInstance(375, 100, Image.SCALE_SMOOTH)));

		jBLogin.setBounds(10, 220, 375, 48);

		jLUser.setBounds(10, 120, 100, 48);
		jLUser.setBorder(BorderFactory.createLineBorder(Color.GRAY));

		jLPort.setBounds(10, 170, 100, 48);
		jLPort.setBorder(BorderFactory.createLineBorder(Color.GRAY));

		jTUSER.setBounds(120, 120, 265, 40);
		jTPort.setBounds(120, 170, 265, 40);
	}

	private void insertComponents() {
		this.add(jBLogin);
		this.add(jLUser);
		this.add(jLPort);
		this.add(jLTitle);
		this.add(jTPort);
		this.add(jTUSER);
	}

	private void insertActions() {
		jBLogin.addActionListener(event -> {
			try {

			String nickname = jTUSER.getText();
			int port = Integer.parseInt(jTPort.getText());
			
			jTUSER.setText("");
			jTPort.setText("");
			
			Socket connection = new Socket(Server.HOST, Server.PORT);
			String connectionInfo = (nickname + ":" + connection.getLocalAddress().getHostAddress() + ":" + port);	
			
			Utils.sendMessage(connection, connectionInfo);
			
			if(Utils.receiveMessage(connection).equals("SUCESSO")) {
				new Home(connection, connectionInfo);
				this.dispose();
			}else {
				JOptionPane.showMessageDialog(null, "Alguem usuario já possui este apelido, ou esta conectado nesta porta!");
			}
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null,"Erro ao conectar, verifique se o servidor esta em execucao!");
			}
			});
	}

	private void start() {
		this.pack();
		this.setVisible(true);
	}

	public static void main(final String[] args) {
		final Login login = new Login();
	}
}