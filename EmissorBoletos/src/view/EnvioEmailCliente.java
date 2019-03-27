package view;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

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

import controller.ClienteController;
import controller.ClienteFiltro;
import controller.ClienteModel;
import controller.SelecionarClienteModel;
import model.Cliente;

public class EnvioEmailCliente extends JFrame {

	private JPanel contentPane;
	private JTextField txtNome;
	private JTextField txtCpf;
	private JTextField txtCodigo;
	private JTable tableSelecionarCliente = new JTable();
	private ClienteFiltro filtro = new ClienteFiltro();
	private SelecionarClienteModel model = new SelecionarClienteModel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EnvioEmailCliente frame = new EnvioEmailCliente();
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
	public EnvioEmailCliente() {
		setTitle("Envio de Boletos");
		setIconImage(Toolkit.getDefaultToolkit().getImage(EnvioEmailCliente.class.getResource("/images/logo_mini.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 869, 497);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblSelecionarCliente = new JLabel("Selecionar Cliente");
		lblSelecionarCliente.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblSelecionarCliente.setBounds(16, 13, 424, 22);
		contentPane.add(lblSelecionarCliente);
		
		JLabel lblNome = new JLabel(" Nome ou Parte do Nome:");
		lblNome.setBounds(38, 43, 160, 14);
		contentPane.add(lblNome);
		
		txtNome = new JTextField();
		txtNome.setColumns(10);
		txtNome.setBounds(39, 61, 312, 20);
		contentPane.add(txtNome);
		
		JLabel lblCpf = new JLabel("Numero CPF:");
		lblCpf.setBounds(39, 85, 160, 14);
		contentPane.add(lblCpf);
		
		txtCpf = new JTextField();
		txtCpf.setColumns(10);
		txtCpf.setBounds(39, 103, 160, 20);
		contentPane.add(txtCpf);
		
		txtCodigo = new JTextField();
		txtCodigo.setColumns(10);
		txtCodigo.setBounds(209, 103, 160, 20);
		contentPane.add(txtCodigo);
		
		JLabel lblCodigo = new JLabel("Código");
		lblCodigo.setBounds(209, 85, 160, 14);
		contentPane.add(lblCodigo);
		
		JButton btnPesquisar = new JButton("");
		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				filtro.setNome(txtNome.getText());
				filtro.setCodigo(txtCodigo.getText());
				filtro.setCpf(txtCpf.getText());
				if(pesquisarAntes(filtro)) {
					pesquisar();
				}else {
					JOptionPane.showMessageDialog(null, "Favor preencher ao menos 1 filtro de pesquisa", "Campo Obrigatório", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		btnPesquisar.setIcon(new ImageIcon(EnvioEmailCliente.class.getResource("/images/pesquisar.png")));
		btnPesquisar.setSelectedIcon(null);
		btnPesquisar.setBounds(51, 147, 48, 48);
		contentPane.add(btnPesquisar);
		
		JButton btnLimpar = new JButton("");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpar();
			}
		});
		btnLimpar.setIcon(new ImageIcon(EnvioEmailCliente.class.getResource("/images/Limpar.png")));
		btnLimpar.setSelectedIcon(null);
		btnLimpar.setBounds(120, 147, 48, 48);
		contentPane.add(btnLimpar);
		
		JButton btnSalvar = new JButton("");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(tableSelecionarCliente.getSelectedRow() != -1) {
						salvar();
				}else {
					JOptionPane.showMessageDialog(null, "Favor Selecionar ao menos um item.", "Selecione", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		btnSalvar.setIcon(new ImageIcon(EnvioEmailCliente.class.getResource("/images/salvar.png")));
		btnSalvar.setBounds(190, 147, 48, 48);
		contentPane.add(btnSalvar);
		
		JButton btnRemover = new JButton("");
		btnRemover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(tableSelecionarCliente.getSelectedRow() != -1) {
						remover();
				}else{
					JOptionPane.showMessageDialog(null, "Favor Selecionar ao menos um item.", "Selecione", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		btnRemover.setIcon(new ImageIcon(EnvioEmailCliente.class.getResource("/images/deletarRegistro.png")));
		btnRemover.setBounds(256, 148, 48, 48);
		contentPane.add(btnRemover);
		
		tableSelecionarCliente.setBounds(54, 229, 725, 190);		
		tableSelecionarCliente.setModel(model);
		JScrollPane scrollPaneSelecionarCliente = new JScrollPane(tableSelecionarCliente);
		scrollPaneSelecionarCliente.setBounds(27, 213, 801, 226);
		contentPane.add(scrollPaneSelecionarCliente);
		
		JLabel lblPesquisar = new JLabel("Pesquisar");
		lblPesquisar.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblPesquisar.setBounds(52, 194, 48, 14);
		contentPane.add(lblPesquisar);
		
		JLabel lblLimpar = new JLabel("Limpar");
		lblLimpar.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblLimpar.setBounds(129, 194, 50, 14);
		contentPane.add(lblLimpar);
		
		JLabel lblSalvar = new JLabel("Salvar");
		lblSalvar.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblSalvar.setBounds(200, 194, 38, 14);
		contentPane.add(lblSalvar);
		
		JLabel lblRemover = new JLabel("Remover");
		lblRemover.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblRemover.setBounds(259, 195, 47, 14);
		contentPane.add(lblRemover);
		
		JButton btnVoltar = new JButton("");
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnVoltar.setIcon(new ImageIcon(EnvioEmailCliente.class.getResource("/images/voltar.png")));
		btnVoltar.setBounds(758, 149, 48, 48);
		contentPane.add(btnVoltar);
		
		JLabel lblVoltar = new JLabel("Voltar");
		lblVoltar.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblVoltar.setBounds(768, 197, 38, 14);
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
	
	public void remover(){
		Object[] botoes = { "Sim", "Não" };
		int resposta = JOptionPane.showOptionDialog(null,
				"Deseja salvar a remoção?",
				"Confirmação", 
				JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
				botoes, botoes[0]);
		if(resposta == 0) {
			int[] listaIdiceSelecionados;
			listaIdiceSelecionados = tableSelecionarCliente.getSelectedRows();
			
			for(int i = 0 ; i<listaIdiceSelecionados.length; i++){
				model.removeSelecaoCliente(listaIdiceSelecionados[i]);
			}
			
			JOptionPane.showMessageDialog(null, "Cliente(s) desmarcado(s) com sucesso!", "", JOptionPane.INFORMATION_MESSAGE);
		}
		pesquisar();
	}
	
	public void salvar(){
		Object[] botoes = { "Sim", "Não" };
		int resposta = JOptionPane.showOptionDialog(null,
				"Deseja salvar a seleção?",
				"Confirmação", 
				JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
				botoes, botoes[0]);
		if(resposta == 0) {
			int[] listaIdiceSelecionados;
			listaIdiceSelecionados = tableSelecionarCliente.getSelectedRows();
			
			for(int i = 0 ; i<listaIdiceSelecionados.length; i++){
				model.selecionarCliente(listaIdiceSelecionados[i]);
			}
			
			JOptionPane.showMessageDialog(null, "Cliente(s) marcado(s) com sucesso!", "", JOptionPane.INFORMATION_MESSAGE);
		}
		pesquisar();
	}
	
	public void limpar() {
		filtro.clear();
		txtNome.setText("");
		txtCodigo.setText("");
		txtCpf.setText("");
		model.limparGrid();
	}
}
