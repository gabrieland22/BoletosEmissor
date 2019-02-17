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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ClientePesquisa extends JFrame {

	private JPanel contentPane;
	private JTextField txtNomeClientePesquisa;
	private JTextField txtNumeroCPFPesquisa;
	private JTextField txtCodigoClientePesquisa;

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
		btnPesquisarCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnPesquisarCliente.setIcon(new ImageIcon(ClientePesquisa.class.getResource("/images/pesquisar.png")));
		btnPesquisarCliente.setBounds(45, 145, 48, 48);
		contentPane.add(btnPesquisarCliente);
		
		JButton btnLimparCliente = new JButton("");
		btnLimparCliente.setIcon(new ImageIcon(ClientePesquisa.class.getResource("/images/Limpar.png")));
		btnLimparCliente.setBounds(114, 145, 48, 48);
		contentPane.add(btnLimparCliente);
		
		JButton btnNovoCliente = new JButton("");
		btnNovoCliente.setIcon(new ImageIcon(ClientePesquisa.class.getResource("/images/novo_usuario_mini.png")));
		btnNovoCliente.setBounds(184, 145, 48, 48);
		contentPane.add(btnNovoCliente);
		
		JLabel label = new JLabel("Pesquisar");
		label.setBounds(47, 193, 46, 14);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("Limpar");
		label_1.setBounds(124, 193, 38, 14);
		contentPane.add(label_1);
		
		JLabel lblNovoCliente = new JLabel("Novo Cliente");
		lblNovoCliente.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNovoCliente.setBounds(178, 193, 68, 14);
		contentPane.add(lblNovoCliente);
		
		JScrollPane scrollPaneCliente = new JScrollPane((Component) null);
		scrollPaneCliente.setBounds(22, 218, 791, 218);
		contentPane.add(scrollPaneCliente);
		
		txtNomeClientePesquisa = new JTextField();
		txtNomeClientePesquisa.setColumns(10);
		txtNomeClientePesquisa.setBounds(33, 59, 312, 20);
		contentPane.add(txtNomeClientePesquisa);
		
		JLabel lblNomeClientePesquisa = new JLabel(" Nome ou Parte do Nome:");
		lblNomeClientePesquisa.setBounds(33, 41, 160, 14);
		contentPane.add(lblNomeClientePesquisa);
		
		txtNumeroCPFPesquisa = new JTextField();
		txtNumeroCPFPesquisa.setColumns(10);
		txtNumeroCPFPesquisa.setBounds(33, 101, 160, 20);
		contentPane.add(txtNumeroCPFPesquisa);
		
		JLabel lblCPFClientePesquisa = new JLabel("Numero CPF:");
		lblCPFClientePesquisa.setBounds(33, 83, 160, 14);
		contentPane.add(lblCPFClientePesquisa);
		
		JButton btnEditarCliente = new JButton("");
		btnEditarCliente.setIcon(new ImageIcon(ClientePesquisa.class.getResource("/images/editarRegistro.png")));
		btnEditarCliente.setBounds(251, 145, 48, 48);
		contentPane.add(btnEditarCliente);
		
		JButton btnExcluirCliente = new JButton("");
		btnExcluirCliente.setIcon(new ImageIcon(ClientePesquisa.class.getResource("/images/deletarRegistro.png")));
		btnExcluirCliente.setBounds(318, 145, 48, 48);
		contentPane.add(btnExcluirCliente);
		
		JLabel lblEditarCliente = new JLabel("Editar");
		lblEditarCliente.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblEditarCliente.setBounds(261, 193, 48, 14);
		contentPane.add(lblEditarCliente);
		
		JLabel lblExcluirCliente = new JLabel("Excluir");
		lblExcluirCliente.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblExcluirCliente.setBounds(328, 193, 38, 14);
		contentPane.add(lblExcluirCliente);
		
		txtCodigoClientePesquisa = new JTextField();
		txtCodigoClientePesquisa.setColumns(10);
		txtCodigoClientePesquisa.setBounds(203, 101, 160, 20);
		contentPane.add(txtCodigoClientePesquisa);
		
		JLabel lblCodigoClientePesquisa = new JLabel("Código");
		lblCodigoClientePesquisa.setBounds(203, 83, 160, 14);
		contentPane.add(lblCodigoClientePesquisa);
		
		JButton btnNovoArquivo = new JButton("");
		btnNovoArquivo.setIcon(new ImageIcon(ClientePesquisa.class.getResource("/images/novo_arquivo.png")));
		btnNovoArquivo.setBounds(386, 145, 48, 48);
		contentPane.add(btnNovoArquivo);
		
		JLabel lblNovoArquivo = new JLabel("Importar Arquivo");
		lblNovoArquivo.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNovoArquivo.setBounds(374, 193, 88, 14);
		contentPane.add(lblNovoArquivo);
	}
}
