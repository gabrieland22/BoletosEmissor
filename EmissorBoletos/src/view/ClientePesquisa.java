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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import controller.ClienteFiltro;
import controller.ClienteModel;
import model.Cliente;
import model.Usuario;
import service.ImportarArquivoBean;
import java.awt.Toolkit;

public class ClientePesquisa extends JFrame {

	private JPanel contentPane;
	private JTextField txtNomeClientePesquisa;
	private JTextField txtNumeroCPFPesquisa;
	private JTextField txtCodigoClientePesquisa;
	private ClienteFiltro filtro = new ClienteFiltro();
	private ClienteModel model = new ClienteModel();
	private JTable tableClientes = new JTable();
	private Usuario usuarioLogado;
	FileInputStream fis;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientePesquisa frame = new ClientePesquisa(null);
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
	public ClientePesquisa(Usuario usuLog) {
		setTitle("Envio de Boletos");
		setIconImage(Toolkit.getDefaultToolkit().getImage(ClientePesquisa.class.getResource("/images/logo_mini.png")));
		usuarioLogado = usuLog;
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
				filtro.setNome(txtNomeClientePesquisa.getText());
				filtro.setCodigo(txtCodigoClientePesquisa.getText());
				filtro.setCpf(txtNumeroCPFPesquisa.getText());
				if(pesquisarAntes(filtro)) {
					pesquisar();
				}else {
					JOptionPane.showMessageDialog(null, "Favor preencher ao menos 1 filtro de pesquisa", "Campo Obrigatório", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		btnPesquisarCliente.setIcon(new ImageIcon(ClientePesquisa.class.getResource("/images/pesquisar.png")));
		btnPesquisarCliente.setBounds(45, 145, 48, 48);
		contentPane.add(btnPesquisarCliente);
		
		JButton btnLimparCliente = new JButton("");
		btnLimparCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpar();
			}
		});
		btnLimparCliente.setIcon(new ImageIcon(ClientePesquisa.class.getResource("/images/Limpar.png")));
		btnLimparCliente.setBounds(114, 145, 48, 48);
		contentPane.add(btnLimparCliente);
		
		JButton btnNovoCliente = new JButton("");
		btnNovoCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				novoCliente();
			}
		});
		btnNovoCliente.setIcon(new ImageIcon(ClientePesquisa.class.getResource("/images/novo_usuario_mini.png")));
		btnNovoCliente.setBounds(184, 145, 48, 48);
		contentPane.add(btnNovoCliente);
		
		JLabel lblNovoCliente = new JLabel("Novo Cliente");
		lblNovoCliente.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNovoCliente.setBounds(178, 193, 68, 14);
		contentPane.add(lblNovoCliente);
		
		
		
		tableClientes.setBounds(54, 229, 725, 190);		
		tableClientes.setModel(model);
		JScrollPane scrollPaneCliente = new JScrollPane(tableClientes);
		scrollPaneCliente.setBounds(28,213,801,218);
		contentPane.add(scrollPaneCliente);
		
		txtNomeClientePesquisa = new JTextField();
		txtNomeClientePesquisa.setColumns(10);
		txtNomeClientePesquisa.setBounds(33, 59, 312, 20);
		contentPane.add(txtNomeClientePesquisa);
		
		JLabel lblNomeClientePesquisa = new JLabel(" Nome ou Parte do Nome:");
		lblNomeClientePesquisa.setBounds(32, 41, 160, 14);
		contentPane.add(lblNomeClientePesquisa);
		
		txtNumeroCPFPesquisa = new JTextField();
		txtNumeroCPFPesquisa.setColumns(10);
		txtNumeroCPFPesquisa.setBounds(33, 101, 160, 20);
		contentPane.add(txtNumeroCPFPesquisa);
		
		JLabel lblCPFClientePesquisa = new JLabel("Numero CPF:");
		lblCPFClientePesquisa.setBounds(33, 83, 160, 14);
		contentPane.add(lblCPFClientePesquisa);
		
		JButton btnEditarCliente = new JButton("");
		btnEditarCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(tableClientes.getSelectedRow() != -1) {
					if(tableClientes.getSelectedRowCount() == 1) {
						editar();
					}else {
						JOptionPane.showMessageDialog(null, "Para Editar favor selecionar apenas 1 item.", "Selecione", JOptionPane.INFORMATION_MESSAGE);
					}
				}else {
					JOptionPane.showMessageDialog(null, "Favor Selecionar um item.", "Selecione", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		btnEditarCliente.setIcon(new ImageIcon(ClientePesquisa.class.getResource("/images/editarRegistro.png")));
		btnEditarCliente.setBounds(251, 145, 48, 48);
		contentPane.add(btnEditarCliente);
		
		JButton btnExcluirCliente = new JButton("");
		btnExcluirCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(tableClientes.getSelectedRow() != -1) {
					if(tableClientes.getSelectedRowCount() == 1) {
						remover();
					}else {
					JOptionPane.showMessageDialog(null, "Para Excluir favor selecionar apenas 1 item.", "Selecione", JOptionPane.INFORMATION_MESSAGE);
					}
				}else {
					JOptionPane.showMessageDialog(null, "Favor Selecionar um item.", "Selecione", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
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
		btnNovoArquivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Procurar Arquivo");
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				
				// Somente Planilhas
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Planilhas", "xls", "xlsx");
				fileChooser.setFileFilter(filter);
				
				int retorno = fileChooser.showOpenDialog(fileChooser);
				
				if(retorno == 0) {
					Object[] botoes = { "Sim", "Não" };
					int resposta = JOptionPane.showOptionDialog(null,
							"Deseja enviar o Arquivo?",
							"Confirmação", 
							JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
							botoes, botoes[0]);
					
					if(resposta == 0){
						ImportarArquivoBean importarArquivoBean = new ImportarArquivoBean();
							try {
								fis = new FileInputStream(fileChooser.getSelectedFile().getAbsoluteFile());
							} catch (FileNotFoundException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							try {
								importarArquivoBean.importarArquivo(fis,usuarioLogado);
							} catch (InvalidFormatException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						
					}
				}
				
			}
		});
		btnNovoArquivo.setIcon(new ImageIcon(ClientePesquisa.class.getResource("/images/novo_arquivo.png")));
		btnNovoArquivo.setBounds(386, 145, 48, 48);
		contentPane.add(btnNovoArquivo);
		
		JLabel lblNovoArquivo = new JLabel("Importar Arquivo");
		lblNovoArquivo.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNovoArquivo.setBounds(374, 193, 88, 14);
		contentPane.add(lblNovoArquivo);
		
		JLabel lblPesquisar = new JLabel("Pesquisar");
		lblPesquisar.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblPesquisar.setBounds(47, 193, 48, 14);
		contentPane.add(lblPesquisar);
		
		JLabel lblLimpar = new JLabel("Limpar");
		lblLimpar.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblLimpar.setBounds(124, 193, 50, 14);
		contentPane.add(lblLimpar);
		
		JButton btnVoltar = new JButton("");
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnVoltar.setIcon(new ImageIcon(ClientePesquisa.class.getResource("/images/voltar.png")));
		btnVoltar.setBounds(769, 145, 48, 48);
		contentPane.add(btnVoltar);
		
		JLabel lblVoltar = new JLabel("Voltar");
		lblVoltar.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblVoltar.setBounds(779, 193, 38, 14);
		contentPane.add(lblVoltar);
	}
	public boolean pesquisarAntes(ClienteFiltro filtro) {
		boolean retorno = true;
		if((filtro.getNome() == null || filtro.getNome().trim().equals("")) 
				&&  (filtro.getCodigo() == null || filtro.getCodigo().trim().equals(""))
				&& (filtro.getCpf() == null || filtro.getCpf().trim().equals(""))) {
			retorno = false;
		}
		return retorno;
	}
	
	public void pesquisar() {
		model.preencheGrid(filtro);
	}
	
	public void limpar() {
		filtro.clear();
		txtNomeClientePesquisa.setText("");
		txtCodigoClientePesquisa.setText("");
		txtNumeroCPFPesquisa.setText("");
		model.limparGrid();
	}
	
	public void novoCliente() {
		ClienteCadastro clienteCadastroTela = new ClienteCadastro(null,usuarioLogado);
		limpar();
		clienteCadastroTela.setVisible(true);  
		clienteCadastroTela.setLocation(300,300);  
		clienteCadastroTela.setResizable(false);
	}
	
	public void editar() {
		Cliente cliente = new Cliente();
		cliente = model.recuperaClienteSelecionado(tableClientes.getSelectedRow());
			ClienteCadastro clienteCadastroTela = new ClienteCadastro(cliente,usuarioLogado);
			limpar();
			clienteCadastroTela.setVisible(true);  
			clienteCadastroTela.setLocation(300,300);  
			clienteCadastroTela.setResizable(false);
	}
	
	public void remover() {
			model.removeItemGrid(tableClientes.getSelectedRow());
	}
}
