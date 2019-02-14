package view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controller.UsuarioFiltro;
import controller.UsuarioModel;

public class UsuarioPesquisa extends JFrame {

	private JPanel contentPane;
	private JTextField txtNomePesquisa;
	private UsuarioModel model = new UsuarioModel();
	private JTable tableUsuarios = new JTable();
	private UsuarioFiltro filtro = new UsuarioFiltro();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UsuarioPesquisa frame = new UsuarioPesquisa();
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
	public UsuarioPesquisa() {
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 832, 489);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTelaPesquisarUsuario = new JLabel("Pesquisar Usuário");
		lblTelaPesquisarUsuario.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblTelaPesquisarUsuario.setBounds(5, 5, 424, 22);
		contentPane.add(lblTelaPesquisarUsuario);
		

			JButton btnNovoUsuario = new JButton("");
			btnNovoUsuario.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					CadastroUsuario cadastroUsuarioTela = new CadastroUsuario();
					cadastroUsuarioTela.setVisible(true);  
					cadastroUsuarioTela.setLocation(300,300);  
					cadastroUsuarioTela.setResizable(false);
				}
			});
			btnNovoUsuario.setIcon(new ImageIcon(UsuarioPesquisa.class.getResource("/images/novo_usuario_mini.png")));
			btnNovoUsuario.setBounds(164, 140, 48, 48);
			contentPane.add(btnNovoUsuario);
			
			JLabel lblNovoUsuario = new JLabel("Novo Usuário");
			lblNovoUsuario.setFont(new Font("Tahoma", Font.PLAIN, 11));
			lblNovoUsuario.setBounds(155, 188, 68, 14);
			contentPane.add(lblNovoUsuario);
			
			JButton btnPesquisar = new JButton("");
			btnPesquisar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
				}
			});
			
			btnPesquisar.setIcon(new ImageIcon(UsuarioPesquisa.class.getResource("/images/pesquisar.png")));
			btnPesquisar.setBounds(33, 140, 48, 48);
			contentPane.add(btnPesquisar);
			
			JLabel lblPesquisar = new JLabel("Pesquisar");
			lblPesquisar.setBounds(35, 188, 46, 14);
			contentPane.add(lblPesquisar);
			
			JButton btnLimpar = new JButton("");
			btnLimpar.setIcon(new ImageIcon(UsuarioPesquisa.class.getResource("/images/Limpar.png")));
			btnLimpar.setBounds(100, 140, 48, 48);
			contentPane.add(btnLimpar);
			
			JLabel lblLimpar = new JLabel("Limpar");
			lblLimpar.setBounds(110, 188, 38, 14);
			contentPane.add(lblLimpar);
			
			txtNomePesquisa = new JTextField();
			txtNomePesquisa.setBounds(25, 71, 312, 20);
			contentPane.add(txtNomePesquisa);
			txtNomePesquisa.setColumns(10);
			
			JLabel lblNomePesquisa = new JLabel(" Nome ou Parte do Nome:");
			lblNomePesquisa.setBounds(25, 48, 160, 14);
			contentPane.add(lblNomePesquisa);
			
			tableUsuarios.setBounds(54, 229, 725, 190);		
			tableUsuarios.setModel(model);
			JScrollPane pane = new JScrollPane(tableUsuarios);
			pane.setBounds(45,213,718,218);
			contentPane.add(pane);
			
	}
}
