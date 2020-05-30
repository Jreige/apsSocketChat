package br.com.aps.unip.view;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import br.com.aps.unip.model.bean.Client;
import br.com.aps.unip.model.dao.ClientDao;

public class RegisterUser extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel jLEmail;
	private JLabel jLName;
	private JLabel jLPass;
	private JButton jBCancel;
	private JButton jBClear;
	private JButton jBAcces;
	private JTextField jTFEmail;
	private JTextField jTFName;
	private JPasswordField jPFPass;

	public RegisterUser() {
		super("CadastroUser");
		initComponents();
		configComponents();
		insertComponents();
		insertActions();
		start();
	}

	private void initComponents() {
		jLEmail		= new JLabel("Email:");
		jLName		= new JLabel("Nome:");
		jLPass		= new JLabel("Senha:");
		jBCancel	= new JButton("Cancelar");
		jBClear		= new JButton("Limpar");
		jBAcces		= new JButton("Cadastrar");
		jTFEmail	= new JTextField();
		jTFName		= new JTextField();
		jPFPass 	= new JPasswordField();

	}

	private void configComponents() {
		this.setLayout(null);
		this.setMinimumSize(new Dimension(500,280));
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setBackground(Color.WHITE);
		
		
		jBAcces.setBounds(10, 190, 150, 48);
		jBClear.setBounds(170, 190, 150, 48);
		jBCancel.setBounds(330, 190, 150, 48);
		
		jLEmail.setBounds(10, 10, 100, 48);
		jLEmail.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		jTFEmail.setBounds(120, 10, 360, 48);

		jLName.setBounds(10, 70, 100, 48);
		jLName.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		jTFName.setBounds(120, 70, 360, 48);

		jLPass.setBounds(10, 130, 100, 48);
		jLPass.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		jPFPass.setBounds(120, 130, 360, 48);
	}

	private void insertComponents() {
		this.add(jBAcces);
		this.add(jBClear);
		this.add(jBCancel);
		this.add(jLEmail);
		this.add(jLName);
		this.add(jLPass);
		this.add(jTFEmail);
		this.add(jTFName);
		this.add(jPFPass);
	}

	private void insertActions() {
		jBAcces.addActionListener(event -> {
			Client client = new Client();
			ClientDao dao = new ClientDao();
			client.setEmail(jTFEmail.getText());
			client.setNome(jTFName.getText());
			String password = String.valueOf(jPFPass.getPassword());
			client.setSenha(password);
			dao.save(client);
			
			jTFEmail.setText("");
			jTFName.setText("");
			jPFPass.setText("");
			
			new Login(); 
			this.dispose();
		});
		jBClear.addActionListener(event -> {
			jTFEmail.setText("");
			jTFName.setText("");
			jPFPass.setText("");
		});
		
		jBCancel.addActionListener(event -> {
			new Login(); 
			this.dispose();
		});
	}

	private void start() {
		this.pack();
		this.setVisible(true);
	}
	
	public static void main( String[] args) {
	   RegisterUser cad = new RegisterUser();
	}
}
