package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class MenuPrincipal {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuPrincipal window = new MenuPrincipal();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MenuPrincipal() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1240, 695);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNovoUsurio = new JLabel("Novo Usu\u00E1rio");
		lblNovoUsurio.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNovoUsurio.setBounds(182, 385, 125, 38);
		frame.getContentPane().add(lblNovoUsurio);
		
		JLabel lblNovoArquivo = new JLabel("Novo Arquivo");
		lblNovoArquivo.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNovoArquivo.setBounds(560, 386, 113, 28);
		frame.getContentPane().add(lblNovoArquivo);
		
		JLabel lblNewLabel = new JLabel("Enviar Emails");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setBounds(963, 391, 113, 28);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(MenuPrincipal.class.getResource("/images/novo_usuario.png")));
		label.setBounds(109, 114, 256, 256);
		frame.getContentPane().add(label);
		
		JLabel label_1 = new JLabel("");
		label_1.setIcon(new ImageIcon(MenuPrincipal.class.getResource("/images/nova_planilha.png")));
		label_1.setBounds(478, 114, 256, 256);
		frame.getContentPane().add(label_1);
		
		JLabel label_2 = new JLabel("");
		label_2.setIcon(new ImageIcon(MenuPrincipal.class.getResource("/images/enviar_emails.png")));
		label_2.setBounds(880, 131, 256, 256);
		frame.getContentPane().add(label_2);
	}
}
