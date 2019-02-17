package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.Usuario;

import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.ImageIcon;

public class ClienteCadastro extends JFrame {

	private JPanel contentPane;
	private JPasswordField passwordField;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClienteCadastro frame = new ClienteCadastro(null);
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
	public ClienteCadastro(final Usuario usuarioLogado) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 885, 489);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("Campos marcos com (*) são obrigatórios.");
		label.setForeground(Color.RED);
		label.setBounds(10, 237, 252, 14);
		contentPane.add(label);
		
		JButton button = new JButton("Salvar");
		button.setBounds(34, 179, 89, 23);
		contentPane.add(button);
		
		JButton button_1 = new JButton("Limpar");
		button_1.setBounds(177, 179, 89, 23);
		contentPane.add(button_1);
		
		JButton button_2 = new JButton("Cancelar");
		button_2.setBounds(315, 179, 89, 23);
		contentPane.add(button_2);
		
		passwordField = new JPasswordField();
		passwordField.setColumns(10);
		passwordField.setBounds(110, 135, 164, 22);
		contentPane.add(passwordField);
		
		JLabel label_1 = new JLabel("Senha*");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label_1.setBounds(53, 137, 46, 14);
		contentPane.add(label_1);
		
		JLabel label_2 = new JLabel("Nome Usuário*");
		label_2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label_2.setBounds(10, 95, 89, 14);
		contentPane.add(label_2);
		
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 13));
		textField.setColumns(10);
		textField.setBounds(109, 92, 300, 22);
		contentPane.add(textField);
		
		JLabel logoCadastroCliente = new JLabel("");
		logoCadastroCliente.setIcon(new ImageIcon(ClienteCadastro.class.getResource("/images/novo_usuario_mini.png")));
		logoCadastroCliente.setBounds(10, 11, 48, 48);
		contentPane.add(logoCadastroCliente);
		
		JLabel lblNovoCliente = new JLabel("Novo Cliente");
		lblNovoCliente.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNovoCliente.setBounds(68, 26, 106, 20);
		contentPane.add(lblNovoCliente);
	}
}
