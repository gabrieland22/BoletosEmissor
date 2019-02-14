package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.Component;
import javax.swing.JTextField;
import javax.swing.ImageIcon;

public class ClientePesquisa extends JFrame {

	private JPanel contentPane;
	private JTextField txtNomeCliente;
	private JTextField txtNumeroCPF;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientePesquisa frame = new ClientePesquisa();
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
	public ClientePesquisa() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 871, 485);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblPesquisarCliente = new JLabel("Pesquisar Cliente");
		lblPesquisarCliente.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblPesquisarCliente.setBounds(10, 11, 424, 22);
		contentPane.add(lblPesquisarCliente);
		
		JButton btnPesquisarCliente = new JButton("");
		btnPesquisarCliente.setIcon(new ImageIcon(ClientePesquisa.class.getResource("/images/pesquisar.png")));
		btnPesquisarCliente.setBounds(53, 145, 48, 48);
		contentPane.add(btnPesquisarCliente);
		
		JButton btnLimparCliente = new JButton("");
		btnLimparCliente.setIcon(new ImageIcon(ClientePesquisa.class.getResource("/images/Limpar.png")));
		btnLimparCliente.setBounds(118, 145, 48, 48);
		contentPane.add(btnLimparCliente);
		
		JButton btnNovoCliente = new JButton("");
		btnNovoCliente.setIcon(new ImageIcon(ClientePesquisa.class.getResource("/images/novo_usuario_mini.png")));
		btnNovoCliente.setBounds(184, 145, 48, 48);
		contentPane.add(btnNovoCliente);
		
		JLabel label = new JLabel("Pesquisar");
		label.setBounds(55, 193, 46, 14);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("Limpar");
		label_1.setBounds(128, 193, 38, 14);
		contentPane.add(label_1);
		
		JLabel lblNovoCliente = new JLabel("Novo Cliente");
		lblNovoCliente.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNovoCliente.setBounds(178, 193, 68, 14);
		contentPane.add(lblNovoCliente);
		
		JScrollPane scrollPaneCliente = new JScrollPane((Component) null);
		scrollPaneCliente.setBounds(65, 218, 718, 218);
		contentPane.add(scrollPaneCliente);
		
		txtNomeCliente = new JTextField();
		txtNomeCliente.setColumns(10);
		txtNomeCliente.setBounds(33, 64, 312, 20);
		contentPane.add(txtNomeCliente);
		
		JLabel label_3 = new JLabel(" Nome ou Parte do Nome:");
		label_3.setBounds(33, 41, 160, 14);
		contentPane.add(label_3);
		
		txtNumeroCPF = new JTextField();
		txtNumeroCPF.setColumns(10);
		txtNumeroCPF.setBounds(34, 112, 160, 20);
		contentPane.add(txtNumeroCPF);
		
		JLabel lblNumeroCpf = new JLabel("Numero CPF:");
		lblNumeroCpf.setBounds(34, 89, 160, 14);
		contentPane.add(lblNumeroCpf);
	}
}
