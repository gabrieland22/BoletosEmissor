package view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controller.UsuarioController;
import model.Usuario;
import service.CriptografiaUtil;
import service.LoginBean;
import java.awt.Window.Type;
import java.awt.Toolkit;

public class Login extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtLogin;
	private JPasswordField txtSenha;
	private JLabel logo;
	private JButton btnEntrar;
	private Usuario usuarioLogado;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		final LoginBean loginService = new LoginBean();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					loginService.iniciar();
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/images/logo_mini.png")));
		setTitle("Envio de Boletos");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 200, 726, 516);;
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		
		JLabel lblLogin = new JLabel("Login");
		lblLogin.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblLogin.setBounds(174, 205, 69, 20);
		contentPane.add(lblLogin);
		
		txtLogin = new JTextField();
		txtLogin.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtLogin.setBounds(174, 234, 356, 38);
		contentPane.add(txtLogin);
		txtLogin.setColumns(10);
		
		JLabel lblSenha = new JLabel("Senha");
		lblSenha.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblSenha.setBounds(174, 283, 69, 20);
		contentPane.add(lblSenha);
		
		txtSenha = new JPasswordField();
		txtSenha.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtSenha.setBounds(174, 314, 356, 38);
		contentPane.add(txtSenha);
		
		logo = new JLabel("");
		logo.setIcon(new ImageIcon(Login.class.getResource("/images/logo.png")));
		logo.setBounds(249, 43, 209, 135);
		contentPane.add(logo);
		
		btnEntrar = new JButton("Entrar");
		btnEntrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(checkLogin(txtLogin.getText(), new String(txtSenha.getPassword()))) {
					JOptionPane.showMessageDialog(null, "Bem Vindo ao Sistema!", "Login", JOptionPane.INFORMATION_MESSAGE);
					
			         TelaPrincipal telaPrincipal = new TelaPrincipal(usuarioLogado);  
			         telaPrincipal.setVisible(true);  
			         telaPrincipal.setLocation(300,300);  
			         telaPrincipal.setResizable(false);
			         dispose(); 
				
				}else {
					JOptionPane.showMessageDialog(null, "Dados Inválidos!", "Informação" ,JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		btnEntrar.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnEntrar.setBounds(297, 389, 115, 29);
		contentPane.add(btnEntrar);
	
	}
	
	public boolean checkLogin(String login, String senha) {
		UsuarioController usuController = new UsuarioController();
		 if(usuController.verificaLogin(login, CriptografiaUtil.criptografar(senha))) {
			 usuarioLogado = usuController.recuperaUsuarioLogado(login, CriptografiaUtil.criptografar(senha));
			 return true;
		 }else {
			 return false;
		 }
	}
}
