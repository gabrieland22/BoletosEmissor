package view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import model.Usuario;
import service.ImportarArquivoBean;

public class TelaPrincipal extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
	
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaPrincipal frame = new TelaPrincipal(null);
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
	public TelaPrincipal(final Usuario usuarioLogado) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 880, 540);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JButton btnEnviarEmail = new JButton("");
		btnEnviarEmail.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/images/enviar_emails.png")));
		btnEnviarEmail.setBounds(589, 149, 175, 200);
		contentPane.add(btnEnviarEmail);
		
		JLabel lblUsuario = new JLabel("Usuários");
		lblUsuario.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblUsuario.setBounds(168, 360, 75, 23);
		contentPane.add(lblUsuario);
		
		JLabel lblClientes = new JLabel("Clientes");
		lblClientes.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblClientes.setBounds(416, 360, 60, 23);
		contentPane.add(lblClientes);
		
		JLabel lblEnviarEmail = new JLabel("Email");
		lblEnviarEmail.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblEnviarEmail.setBounds(668, 360, 66, 23);
		contentPane.add(lblEnviarEmail);
		
		JLabel lblUsuarioLogado = new JLabel("Bem vindo(a) " + usuarioLogado.getNome());
		lblUsuarioLogado.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblUsuarioLogado.setBounds(34, 11, 820, 31);
		contentPane.add(lblUsuarioLogado);
		
		JButton btnClientes = new JButton("");
		btnClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ClientePesquisa telaClientePesquisa = new ClientePesquisa(usuarioLogado);
				telaClientePesquisa.setVisible(true);
			}
		});
		btnClientes.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/images/clientes.png")));
		btnClientes.setBounds(352, 149, 175, 200);
		contentPane.add(btnClientes);
		
		JButton btnUsuarios = new JButton("");
		btnUsuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UsuarioPesquisa telaUsuarioPesquisa = new UsuarioPesquisa(usuarioLogado);
				telaUsuarioPesquisa.setVisible(true);
			}
		});
		btnUsuarios.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/images/usuario.png")));
		btnUsuarios.setBounds(116, 149, 175, 200);
		contentPane.add(btnUsuarios);
	}
}
