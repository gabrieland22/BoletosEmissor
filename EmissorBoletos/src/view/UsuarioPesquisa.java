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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controller.UsuarioFiltro;
import controller.UsuarioModel;
import model.Usuario;

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
					novoUsuario();
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
					filtro.setNome(txtNomePesquisa.getText());
					if(pesquisarAntes(filtro)) {
						pesquisar();
					}else {
						JOptionPane.showMessageDialog(null, "Favor informar o nome.", "Campo Obrigatório", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			});
			
			btnPesquisar.setIcon(new ImageIcon(UsuarioPesquisa.class.getResource("/images/pesquisar.png")));
			btnPesquisar.setBounds(33, 140, 48, 48);
			contentPane.add(btnPesquisar);
			
			JLabel lblPesquisar = new JLabel("Pesquisar");
			lblPesquisar.setBounds(35, 188, 46, 14);
			contentPane.add(lblPesquisar);
			
			JButton btnLimpar = new JButton("");	
			btnLimpar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					limpar();
				}
			});
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
			
			JButton btnEditarUsuario = new JButton("");
			btnEditarUsuario.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(tableUsuarios.getSelectedRow() != -1) {
						editar();
					}else {
						JOptionPane.showMessageDialog(null, "Favor Selecionar um item.", "Selecione", JOptionPane.INFORMATION_MESSAGE);
					}
					
				}
			});
			btnEditarUsuario.setIcon(new ImageIcon(UsuarioPesquisa.class.getResource("/images/editarRegistro.png")));
			btnEditarUsuario.setBounds(231, 140, 48, 48);
			contentPane.add(btnEditarUsuario);
			
			JLabel lblEditarUsuario = new JLabel("Editar");
			lblEditarUsuario.setFont(new Font("Tahoma", Font.PLAIN, 11));
			lblEditarUsuario.setBounds(241, 188, 48, 14);
			contentPane.add(lblEditarUsuario);
			
			JButton btnExcluirUsuario = new JButton("");
			btnExcluirUsuario.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(tableUsuarios.getSelectedRow() != -1) {
						if(tableUsuarios.getSelectedRowCount() == 1) {
							remover();
						}else {
						JOptionPane.showMessageDialog(null, "Para excluir favor selecionar apenas 1 item.", "Selecione", JOptionPane.INFORMATION_MESSAGE);
						}
					}else {
						JOptionPane.showMessageDialog(null, "Favor Selecionar um item.", "Selecione", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			});
			btnExcluirUsuario.setIcon(new ImageIcon(UsuarioPesquisa.class.getResource("/images/deletarRegistro.png")));
			btnExcluirUsuario.setBounds(298, 140, 48, 48);
			contentPane.add(btnExcluirUsuario);
			
			JLabel lblExcluirUsuario = new JLabel("Excluir");
			lblExcluirUsuario.setFont(new Font("Tahoma", Font.PLAIN, 11));
			lblExcluirUsuario.setBounds(308, 188, 38, 14);
			contentPane.add(lblExcluirUsuario);
			
	}
	
	public boolean pesquisarAntes(UsuarioFiltro filtro) {
		boolean retorno = true;
		if(filtro.getNome() == null || filtro.getNome().trim().equals("")) {
			retorno = false;
		}
		return retorno;
	}
	
	public void limpar() {
		txtNomePesquisa.setText("");
		model.limparGrid();
	}
	
	public void pesquisar() {
		model.preencheGrid(filtro);
	}
	
	public void remover() {
		model.removeItemGrid(tableUsuarios.getSelectedRow());
	}
	
	public void editar() {
		Usuario usuario = new Usuario();
		usuario = model.recueraUsuarioSelecionado(tableUsuarios.getSelectedRow());
		CadastroUsuario cadastroUsuarioTela = new CadastroUsuario(usuario);
		limpar();
		cadastroUsuarioTela.setVisible(true);  
		cadastroUsuarioTela.setLocation(300,300);  
		cadastroUsuarioTela.setResizable(false);
		
	}
	
	public void novoUsuario() {
		CadastroUsuario cadastroUsuarioTela = new CadastroUsuario(null);
		limpar();
		cadastroUsuarioTela.setVisible(true);  
		cadastroUsuarioTela.setLocation(300,300);  
		cadastroUsuarioTela.setResizable(false);
	}
	
}
