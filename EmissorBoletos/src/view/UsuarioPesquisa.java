package view;

import java.awt.Color;
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
import java.awt.Toolkit;

public class UsuarioPesquisa extends JFrame {

	private JPanel contentPane;
	private JTextField txtNomePesquisa;
	private UsuarioModel model = new UsuarioModel();
	private JTable tableUsuarios = new JTable();
	private UsuarioFiltro filtro = new UsuarioFiltro();
	private Usuario usuarioLogado;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UsuarioPesquisa frame = new UsuarioPesquisa(null);
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
	public UsuarioPesquisa(Usuario usuLog) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(UsuarioPesquisa.class.getResource("/images/logo_mini.png")));
		setTitle("Envio de Boletos");
		
		usuarioLogado = usuLog;
		
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
			btnNovoUsuario.setBounds(168, 140, 48, 48);
			contentPane.add(btnNovoUsuario);
			
			JLabel lblNovoUsuario = new JLabel("Novo Usuário");
			lblNovoUsuario.setFont(new Font("Tahoma", Font.PLAIN, 11));
			lblNovoUsuario.setBounds(159, 188, 68, 14);
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
			
			JButton btnLimpar = new JButton("");	
			btnLimpar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					limpar();
				}
			});
			btnLimpar.setIcon(new ImageIcon(UsuarioPesquisa.class.getResource("/images/Limpar.png")));
			btnLimpar.setBounds(100, 140, 48, 48);
			contentPane.add(btnLimpar);
			
			txtNomePesquisa = new JTextField();
			txtNomePesquisa.setBounds(25, 71, 312, 20);
			contentPane.add(txtNomePesquisa);
			txtNomePesquisa.setColumns(10);
			
			
			JLabel lblNomePesquisa = new JLabel(" Nome ou Parte do Nome:");
			lblNomePesquisa.setBounds(25, 48, 160, 14);
			contentPane.add(lblNomePesquisa);
			
			tableUsuarios.setBackground(new Color(255, 255, 255));
			tableUsuarios.setBounds(54, 229, 725, 190);		
			tableUsuarios.setModel(model);
			JScrollPane pane = new JScrollPane(tableUsuarios);
			pane.setBounds(46, 213, 718, 218);
			contentPane.add(pane);
			
			JButton btnEditarUsuario = new JButton("");
			btnEditarUsuario.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(tableUsuarios.getSelectedRow() != -1) {
						if(tableUsuarios.getSelectedRowCount() == 1) {
							editar();
						}else {
							JOptionPane.showMessageDialog(null, "Para Editar favor selecionar apenas 1 item.", "Selecione", JOptionPane.INFORMATION_MESSAGE);
						}
					}else {
						JOptionPane.showMessageDialog(null, "Favor Selecionar um item.", "Selecione", JOptionPane.INFORMATION_MESSAGE);
					}
					
				}
			});
			btnEditarUsuario.setIcon(new ImageIcon(UsuarioPesquisa.class.getResource("/images/editarRegistro.png")));
			btnEditarUsuario.setBounds(237, 140, 48, 48);
			contentPane.add(btnEditarUsuario);
			
			JLabel lblEditarUsuario = new JLabel("Editar");
			lblEditarUsuario.setFont(new Font("Tahoma", Font.PLAIN, 11));
			lblEditarUsuario.setBounds(247, 188, 48, 14);
			contentPane.add(lblEditarUsuario);
			
			JButton btnExcluirUsuario = new JButton("");
			btnExcluirUsuario.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(tableUsuarios.getSelectedRow() != -1) {
						if(tableUsuarios.getSelectedRowCount() == 1) {
							remover();
						}else {
						JOptionPane.showMessageDialog(null, "Para Excluir favor selecionar apenas 1 item.", "Selecione", JOptionPane.INFORMATION_MESSAGE);
						}
					}else {
						JOptionPane.showMessageDialog(null, "Favor Selecionar um item.", "Selecione", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			});
			btnExcluirUsuario.setIcon(new ImageIcon(UsuarioPesquisa.class.getResource("/images/deletarRegistro.png")));
			btnExcluirUsuario.setBounds(304, 140, 48, 48);
			contentPane.add(btnExcluirUsuario);
			
			JLabel lblExcluirUsuario = new JLabel("Excluir");
			lblExcluirUsuario.setFont(new Font("Tahoma", Font.PLAIN, 11));
			lblExcluirUsuario.setBounds(314, 188, 38, 14);
			contentPane.add(lblExcluirUsuario);
			
			JLabel lblPequisar = new JLabel("Pesquisar");
			lblPequisar.setFont(new Font("Tahoma", Font.PLAIN, 11));
			lblPequisar.setBounds(36, 188, 57, 14);
			contentPane.add(lblPequisar);
			
			JLabel lblLimpar = new JLabel("Limpar");
			lblLimpar.setFont(new Font("Tahoma", Font.PLAIN, 11));
			lblLimpar.setBounds(110, 188, 38, 14);
			contentPane.add(lblLimpar);
			
			JButton btnVoltar = new JButton("");
			btnVoltar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			btnVoltar.setIcon(new ImageIcon(UsuarioPesquisa.class.getResource("/images/voltar.png")));
			btnVoltar.setBounds(706, 140, 48, 48);
			contentPane.add(btnVoltar);
			
			JLabel lblVoltar = new JLabel("Voltar");
			lblVoltar.setFont(new Font("Tahoma", Font.PLAIN, 11));
			lblVoltar.setBounds(716, 188, 38, 14);
			contentPane.add(lblVoltar);
			
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
		filtro.clear();
		model.limparGrid();
	}
	
	public void pesquisar() {
		model.preencheGrid(filtro);
	}
	
	public void remover() {
		if(usuarioLogado.getId() != 1 && tableUsuarios.getSelectedRow() == 0) {
			JOptionPane.showMessageDialog(null, "Não é possível Excluir o usuário Administrador.", "Alerta", JOptionPane.INFORMATION_MESSAGE);
		}else {
			model.removeItemGrid(tableUsuarios.getSelectedRow());
		}
	}
	
	public void editar() {
		Usuario usuario = new Usuario();
		usuario = model.recueraUsuarioSelecionado(tableUsuarios.getSelectedRow());
		if(usuarioLogado.getId() != 1 && usuario.getId() == 1) {
			JOptionPane.showMessageDialog(null, "Não é possível Editar o usuário Administrador.", "Alerta", JOptionPane.INFORMATION_MESSAGE);
		}else {
			CadastroUsuario cadastroUsuarioTela = new CadastroUsuario(usuario, usuarioLogado);
			limpar();
			cadastroUsuarioTela.setVisible(true);  
			cadastroUsuarioTela.setLocation(300,300);  
			cadastroUsuarioTela.setResizable(false);
		}
	}
	
	public void novoUsuario() {
		CadastroUsuario cadastroUsuarioTela = new CadastroUsuario(null,usuarioLogado);
		limpar();
		cadastroUsuarioTela.setVisible(true);  
		cadastroUsuarioTela.setLocation(300,300);  
		cadastroUsuarioTela.setResizable(false);
	}
	
}
