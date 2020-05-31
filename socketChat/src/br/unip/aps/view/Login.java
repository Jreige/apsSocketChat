package br.unip.aps.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.Socket;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import br.unip.aps.dao.ClientDao;
import br.unip.aps.model.Cliente;
import br.unip.aps.server.Server;
import br.unip.aps.server.common.Utils;

public class Login extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton jBLogin;
	private JButton jBAcess;
	private JLabel jLUser;
	private JLabel jLPort;
	private JLabel jLPass;
	private JLabel jLTitle;
	private JLabel jLForgotPass;
	private JTextField jTUSER;
	private JTextField jTPort;
	private JPasswordField jPFPass;

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
		jBAcess = new JButton("Cadastrar");
		jLUser  = new JLabel("Apelido:", SwingConstants.CENTER);
		jLPort  = new JLabel("Porta:", SwingConstants.CENTER);
		jLPass  = new JLabel("Senha:", SwingConstants.CENTER);
		jLForgotPass = new JLabel("<html> <font color=#0050FF><u><b>Forgot password?</b></u></font</html>");
		jLTitle = new JLabel();
		jTUSER  = new JTextField();
		jTPort  = new JTextField();
		jPFPass  = new JPasswordField();
	}

	private void configComponents() {
		this.setLayout(null);
		this.setMinimumSize(new Dimension(400, 370));
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setBackground(Color.WHITE);

		jLTitle.setBounds(10, 10, 375, 100);
		final ImageIcon icon = new ImageIcon("./images/icone.svg");
		jLTitle.setIcon(new ImageIcon(icon.getImage().getScaledInstance(375, 100, Image.SCALE_SMOOTH)));
		
		jLForgotPass.setBounds(280, 260, 105, 15);
		
		jBLogin.setBounds(10, 280, 182, 48);
		jBAcess.setBounds(200, 280, 182, 48);

		jLUser.setBounds(10, 120, 100, 48);
		jLUser.setBorder(BorderFactory.createLineBorder(Color.GRAY));

		jLPort.setBounds(10, 170, 100, 48);
		jLPort.setBorder(BorderFactory.createLineBorder(Color.GRAY));

		jLPass.setBounds(10, 220, 100, 48);
		jLPass.setBorder(BorderFactory.createLineBorder(Color.GRAY));

		jTUSER.setBounds(120, 120, 265, 40);
		jTPort.setBounds(120, 170, 265, 40);
		jPFPass.setBounds(120, 220, 265, 40);
	}

	private void insertComponents() {
		this.add(jBLogin);
		this.add(jBAcess);
		this.add(jLUser);
		this.add(jLPort);
		this.add(jLPass);
		this.add(jLTitle);
		this.add(jLForgotPass);
		this.add(jTPort);
		this.add(jTUSER);
		this.add(jPFPass);
	}

	private void insertActions() {
		MouseListener mlactions = new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent e) {
				new UpdatePass();
			
			}
			@Override
			public void mousePressed(MouseEvent e) {
			}
			@Override
			public void mouseReleased(MouseEvent e) {
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				
			}
			@Override
			public void mouseExited(MouseEvent e) {
			}	
			}; 
			
			jLForgotPass.addMouseListener(mlactions);
		
			jBLogin.addActionListener(event -> {
			try {

				String nickname = jTUSER.getText();
				int port = Integer.parseInt(jTPort.getText());
				String password = String.valueOf(jPFPass.getPassword());
				
				Cliente client = new Cliente();
				ClientDao dao = new ClientDao();

				jTUSER.setText("");
				jTPort.setText("");

				Socket connection = new Socket(Server.HOST, Server.PORT);
				String connectionInfo = (nickname + ":" + connection.getLocalAddress().getHostAddress() + ":" + port);

				Utils.sendMessage(connection, connectionInfo);
				
				client = dao.findByName(nickname);

				if (!client.getSenha().equals(password)) {
					JOptionPane.showMessageDialog(null, "Senha incorreta!");
				}else{
					if (Utils.receiveMessage(connection).equals("SUCESSO")) {
						new Home(connection, connectionInfo);
						this.dispose();
					} else {
						JOptionPane.showMessageDialog(null,
								"Alguem usuario já possui este apelido, ou esta conectado nesta porta!");
					}
				}
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Erro ao conectar, verifique se o servidor esta em execucao!");
			}

		});

		jBAcess.addActionListener(event -> {
			new RegisterUser();
			this.dispose();
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