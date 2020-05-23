import java.awt.*;

import javax.swing.*;

class Login extends JFrame {

	private JButton JBLogin;
    private JLabel JLUser, JLPort, JLTitle;
    private JTextField JTPort, JTUSER;

   public Login(){
        super("Login");
        initComponents();
        insertComponents();
        insertActions();
        start();
    }

   private void initComponents(){
        JBLogin = new JButton("Entrar");
        JLUser = new JLabel("Apelido:", SwingConstants.CENTER);
        JLPort = new JLabel("Porta:", SwingConstants.CENTER);
        JLTitle = new JLabel();
        JTPort = new JTextField();
        JTUSER = new JTextField();
   }

   private void configComponents(){
        this.setLayout(null);
        this.setMinimumSize(new Dimension(400, 300));
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(Color.WHITE);   

        JLTitle.setBounds(10, 10, 375, 100);
        ImageIcon icon = new ImageIcon("Logo.png");
        JLTitle.setIcon(new ImageIcon(icon.getImage().getScaledInstance(375,100,Image.SCALE_SMOOTH)));
   
        JBLogin.setBounds(10, 220, 375, 48);

        JLUser.setBounds(10, 120, 100, 48);
        JLUser.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        JLPort.setBounds(10, 170, 100, 48);
        JLPort.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        JTUSER.setBounds(120, 120, 265, 40);
        JTPort.setBounds(120, 170, 265, 40);
    }   

    private void insertComponents(){
        this.add(JBLogin);
        this.add(JLUser);
        this.add(JLPort);
        this.add(JLTitle);
        this.add(JTPort);
        this.add(JTUSER);
    }

    private void insertActions(){

    }

    private void start(){
        this.pack();
        this.setVisible(true);
    }

    public static void main(String[] args) {
        Login login = new Login();
    }
}