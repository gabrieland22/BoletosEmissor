package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controller.ClienteController;
import controller.UsuarioController;
import model.Cliente;
import model.Usuario;
import service.CriptografiaUtil;
import java.awt.Toolkit;

public class ClienteCadastro extends JFrame {

	private JPanel contentPane;
	private JTextField txtNomeClienteCadastro;
	private JTextField txtCPFCadastro;
	private JTextField txtCodigoCadastro;
	private JTextField txtEnderecoCadastro;
	private JTextField txtBairroCadastro;
	private JTextField txtCidadeCadastro;
	private JTextField txtEstadoCadastro;
	private JTextField txtSexoCadastro;
	private JTextField txtTelefoneCadastro;
	private JTextField txtEmailCadastro;
	private JTextField txtCepCadastroCliente;
	private Usuario usuarioLogado;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClienteCadastro frame = new ClienteCadastro(null,null);
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
	public ClienteCadastro(final Cliente cliente,final Usuario usuLog) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(ClienteCadastro.class.getResource("/images/logo_mini.png")));
		setTitle("Envio de Boletos");
		usuarioLogado = usuLog;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 885, 304);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCamposMarcadosCom = new JLabel("Campos marcados com (*) são obrigatórios.");
		lblCamposMarcadosCom.setForeground(Color.RED);
		lblCamposMarcadosCom.setBounds(10, 237, 252, 14);
		contentPane.add(lblCamposMarcadosCom);
		
		JButton btnSalvarCliente = new JButton("");
		btnSalvarCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(salvarAntes()){
					if(cliente == null) {
						Cliente cli = new Cliente();
						preencheObjetoCliente(cli);
						ClienteController cliCon = new ClienteController();
						if(!cliCon.existeCliente(cli)) {
							cliCon.salvar(cli);
							JOptionPane.showMessageDialog(null, "Cliente Salvo com Sucesso!", "", JOptionPane.INFORMATION_MESSAGE);	
						}else {
							JOptionPane.showMessageDialog(null, "Já existe cliente com com o mesmo código ou CPF informado!", "", JOptionPane.WARNING_MESSAGE);
						}
						
						dispose();
					}else {
						preencheObjetoCliente(cliente);
						ClienteController cliCon = new ClienteController();
						cliCon.salvar(cliente);
						JOptionPane.showMessageDialog(null, "Cliente Salvo com Sucesso!", "", JOptionPane.INFORMATION_MESSAGE);
						dispose();
					}
				}
			}
		});
		btnSalvarCliente.setIcon(new ImageIcon(ClienteCadastro.class.getResource("/images/salvar.png")));
		btnSalvarCliente.setBounds(51, 160, 48, 48);
		contentPane.add(btnSalvarCliente);
		
		JButton blnLimpar = new JButton("");
		blnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpar();
			}
		});
		blnLimpar.setIcon(new ImageIcon(ClienteCadastro.class.getResource("/images/Limpar.png")));
		blnLimpar.setBounds(126, 159, 48, 48);
		contentPane.add(blnLimpar);
		
		JButton btnCancelarCadastroCliente = new JButton("");
		btnCancelarCadastroCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancelarCadastroCliente.setIcon(new ImageIcon(ClienteCadastro.class.getResource("/images/cancelar.png")));
		btnCancelarCadastroCliente.setBounds(196, 160, 48, 48);
		contentPane.add(btnCancelarCadastroCliente);
		
		JLabel lblNomeCadastroCliente = new JLabel("Nome:*");
		lblNomeCadastroCliente.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNomeCadastroCliente.setBounds(10, 73, 48, 14);
		contentPane.add(lblNomeCadastroCliente);
		
		txtNomeClienteCadastro = new JTextField();
		txtNomeClienteCadastro.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtNomeClienteCadastro.setColumns(10);
		txtNomeClienteCadastro.setBounds(57, 71, 300, 22);
		if(cliente != null) {
			txtNomeClienteCadastro.setText(cliente.getNome());
		}
		contentPane.add(txtNomeClienteCadastro);
		
		JLabel logoCadastroCliente = new JLabel("");
		logoCadastroCliente.setIcon(new ImageIcon(ClienteCadastro.class.getResource("/images/novo_usuario_mini.png")));
		logoCadastroCliente.setBounds(10, 11, 48, 48);
		contentPane.add(logoCadastroCliente);
		
		JLabel lblNovoCliente = new JLabel("Novo Cliente");
		lblNovoCliente.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNovoCliente.setBounds(68, 26, 106, 20);
		contentPane.add(lblNovoCliente);
		
		txtCPFCadastro = new JTextField();
		txtCPFCadastro.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtCPFCadastro.setColumns(10);
		txtCPFCadastro.setBounds(405, 71, 147, 22);
		if(cliente != null) {
			txtCPFCadastro.setText(cliente.getCpf());
		}
		contentPane.add(txtCPFCadastro);
		
		JLabel lblCPFCadastro = new JLabel("CPF:*");
		lblCPFCadastro.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblCPFCadastro.setBounds(367, 74, 43, 14);
		contentPane.add(lblCPFCadastro);
		
		JLabel lblCodigoCadastro = new JLabel("Código:*");
		lblCodigoCadastro.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblCodigoCadastro.setBounds(562, 73, 70, 20);
		contentPane.add(lblCodigoCadastro);
		
		txtCodigoCadastro = new JTextField();
		txtCodigoCadastro.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtCodigoCadastro.setColumns(10);
		txtCodigoCadastro.setBounds(621, 71, 105, 22);
		if(cliente != null) {
			txtCodigoCadastro.setText(cliente.getCodigo());
		}
		contentPane.add(txtCodigoCadastro);
		
		txtEnderecoCadastro = new JTextField();
		txtEnderecoCadastro.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtEnderecoCadastro.setColumns(10);
		txtEnderecoCadastro.setBounds(74, 99, 214, 22);
		if(cliente != null) {
			txtEnderecoCadastro.setText(cliente.getRua());
		}
		contentPane.add(txtEnderecoCadastro);
		
		JLabel lblEndereco = new JLabel("Endereço:");
		lblEndereco.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblEndereco.setBounds(10, 100, 58, 20);
		contentPane.add(lblEndereco);
		
		JLabel lblBairro = new JLabel("Bairro:");
		lblBairro.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblBairro.setBounds(296, 102, 48, 14);
		contentPane.add(lblBairro);
		
		txtBairroCadastro = new JTextField();
		txtBairroCadastro.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtBairroCadastro.setColumns(10);
		txtBairroCadastro.setBounds(337, 99, 158, 22);
		if(cliente != null) {
			txtBairroCadastro.setText(cliente.getBairro());
		}
		contentPane.add(txtBairroCadastro);
		
		JLabel lblCidade = new JLabel("Cidade:");
		lblCidade.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblCidade.setBounds(502, 102, 48, 14);
		contentPane.add(lblCidade);
		
		txtCidadeCadastro = new JTextField();
		txtCidadeCadastro.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtCidadeCadastro.setColumns(10);
		txtCidadeCadastro.setBounds(548, 99, 165, 22);
		if(cliente != null) {
			txtCidadeCadastro.setText(cliente.getCidade());
		}
		contentPane.add(txtCidadeCadastro);
		
		JLabel lblEstado = new JLabel("Estado:");
		lblEstado.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblEstado.setBounds(722, 102, 48, 14);
		contentPane.add(lblEstado);
		
		txtEstadoCadastro = new JTextField();
		txtEstadoCadastro.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtEstadoCadastro.setColumns(10);
		txtEstadoCadastro.setBounds(768, 99, 96, 22);
		if(cliente != null) {
			txtEstadoCadastro.setText(cliente.getEstado());
		}
		contentPane.add(txtEstadoCadastro);
		
		txtSexoCadastro = new JTextField();
		txtSexoCadastro.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtSexoCadastro.setColumns(10);
		txtSexoCadastro.setBounds(778, 70, 48, 22);
		if(cliente != null) {
			txtSexoCadastro.setText(cliente.getSexo());
		}
		contentPane.add(txtSexoCadastro);
		
		JLabel lblSexo = new JLabel("Sexo:");
		lblSexo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblSexo.setBounds(739, 71, 34, 20);
		contentPane.add(lblSexo);
		
		JLabel lblTelefone = new JLabel("Telefone:");
		lblTelefone.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblTelefone.setBounds(194, 130, 58, 14);
		contentPane.add(lblTelefone);
		
		txtTelefoneCadastro = new JTextField();
		txtTelefoneCadastro.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtTelefoneCadastro.setColumns(10);
		txtTelefoneCadastro.setBounds(253, 127, 165, 22);
		if(cliente != null) {
			txtTelefoneCadastro.setText(cliente.getCelular());
		}
		contentPane.add(txtTelefoneCadastro);
		
		JLabel lblEmail = new JLabel("Email:*");
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblEmail.setBounds(427, 129, 48, 14);
		contentPane.add(lblEmail);
		
		txtEmailCadastro = new JTextField();
		txtEmailCadastro.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtEmailCadastro.setColumns(10);
		txtEmailCadastro.setBounds(474, 127, 352, 22);
		if(cliente != null) {
			txtEmailCadastro.setText(cliente.getEmail());
		}
		contentPane.add(txtEmailCadastro);
		
		JLabel lblSalvarCliente = new JLabel("Salvar");
		lblSalvarCliente.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblSalvarCliente.setBounds(54, 210, 48, 14);
		contentPane.add(lblSalvarCliente);
		
		JLabel lblLimparCadastroCliente = new JLabel("Limpar");
		lblLimparCadastroCliente.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblLimparCadastroCliente.setBounds(131, 209, 48, 14);
		contentPane.add(lblLimparCadastroCliente);
		
		JLabel lblCancelarCadastroCliente = new JLabel("Cancelar");
		lblCancelarCadastroCliente.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblCancelarCadastroCliente.setBounds(197, 209, 58, 14);
		contentPane.add(lblCancelarCadastroCliente);
		
		JLabel lblCEPCadastroCliente = new JLabel("CEP:");
		lblCEPCadastroCliente.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblCEPCadastroCliente.setBounds(10, 130, 58, 14);
		contentPane.add(lblCEPCadastroCliente);
		
		txtCepCadastroCliente = new JTextField();
		txtCepCadastroCliente.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtCepCadastroCliente.setColumns(10);
		txtCepCadastroCliente.setBounds(42, 127, 147, 22);
		if(cliente != null) {
			txtCepCadastroCliente.setText(cliente.getCep());
		}
		contentPane.add(txtCepCadastroCliente);
	}
	
	public boolean salvarAntes(){
		Boolean salvar = true;
			if(txtNomeClienteCadastro.getText() == null || txtNomeClienteCadastro.getText().trim().equals("")){
				salvar = false;
				JOptionPane.showMessageDialog(null, "Nome do cliente é obrigatório!", "" ,JOptionPane.ERROR_MESSAGE);
			}
			if(txtCPFCadastro.getText() == null || txtCPFCadastro.getText().trim().equals("")){
				salvar = false;
				JOptionPane.showMessageDialog(null, "CPF é obrigatório!", "" ,JOptionPane.ERROR_MESSAGE);
			}
			if(getTxtCodigoCadastro().getText() == null || getTxtCodigoCadastro().getText().trim().equals("")){
				salvar = false;
				JOptionPane.showMessageDialog(null, "Código é obrigatório!", "" ,JOptionPane.ERROR_MESSAGE);
			}
			if(getTxtEmailCadastro().getText() == null || getTxtEmailCadastro().getText().trim().equals("")){
				salvar = false;
				JOptionPane.showMessageDialog(null, "Email é obrigatório!", "" ,JOptionPane.ERROR_MESSAGE);
			}
			
			
		return salvar;
	}
	
	public void preencheObjetoCliente(Cliente obj) {
		obj.setNome(txtNomeClienteCadastro.getText());
		obj.setCpf(txtCPFCadastro.getText());
		obj.setCodigo(txtCodigoCadastro.getText());
		obj.setSexo(txtSexoCadastro.getText());
		obj.setRua(txtEnderecoCadastro.getText());
		obj.setBairro(txtBairroCadastro.getText());
		obj.setCidade(txtCidadeCadastro.getText());
		obj.setEstado(txtEstadoCadastro.getText());
		obj.setCep(txtCepCadastroCliente.getText());
		obj.setCelular(txtTelefoneCadastro.getText());
		obj.setEmail(txtEmailCadastro.getText());
		
		if(obj.getId() == 0 ) {
			obj.setDataCriacao(new Date());
			obj.setUsuarioCriacao(usuarioLogado.getNome());
			obj.setUsuarioAlteracao(usuarioLogado.getNome());
			obj.setDataAlteracao(new Date());
		}else {
			obj.setUsuarioAlteracao(usuarioLogado.getNome());
			obj.setDataAlteracao(new Date());
		}
	}
	
	public void limpar() {
		txtNomeClienteCadastro.setText("");
		txtCPFCadastro.setText("");
		txtCodigoCadastro.setText("");
		txtSexoCadastro.setText("");
		txtEnderecoCadastro.setText("");
		txtBairroCadastro.setText("");
		txtCidadeCadastro.setText("");
		txtEstadoCadastro.setText("");
		txtCepCadastroCliente.setText("");
		txtTelefoneCadastro.setText("");
		txtEmailCadastro.setText("");
		
	}

	public JTextField getTxtNomeClienteCadastro() {
		return txtNomeClienteCadastro;
	}

	public void setTxtNomeClienteCadastro(JTextField txtNomeClienteCadastro) {
		this.txtNomeClienteCadastro = txtNomeClienteCadastro;
	}

	public JTextField getTxtCPFCadastro() {
		return txtCPFCadastro;
	}

	public void setTxtCPFCadastro(JTextField txtCPFCadastro) {
		this.txtCPFCadastro = txtCPFCadastro;
	}

	public JTextField getTxtCodigoCadastro() {
		return txtCodigoCadastro;
	}

	public void setTxtCodigoCadastro(JTextField txtCodigoCadastro) {
		this.txtCodigoCadastro = txtCodigoCadastro;
	}

	public JTextField getTxtEnderecoCadastro() {
		return txtEnderecoCadastro;
	}

	public void setTxtEnderecoCadastro(JTextField txtEnderecoCadastro) {
		this.txtEnderecoCadastro = txtEnderecoCadastro;
	}

	public JTextField getTxtBairroCadastro() {
		return txtBairroCadastro;
	}

	public void setTxtBairroCadastro(JTextField txtBairroCadastro) {
		this.txtBairroCadastro = txtBairroCadastro;
	}

	public JTextField getTxtCidadeCadastro() {
		return txtCidadeCadastro;
	}

	public void setTxtCidadeCadastro(JTextField txtCidadeCadastro) {
		this.txtCidadeCadastro = txtCidadeCadastro;
	}

	public JTextField getTxtEstadoCadastro() {
		return txtEstadoCadastro;
	}

	public void setTxtEstadoCadastro(JTextField txtEstadoCadastro) {
		this.txtEstadoCadastro = txtEstadoCadastro;
	}

	public JTextField getTxtSexoCadastro() {
		return txtSexoCadastro;
	}

	public void setTxtSexoCadastro(JTextField txtSexoCadastro) {
		this.txtSexoCadastro = txtSexoCadastro;
	}

	public JTextField getTxtTelefoneCadastro() {
		return txtTelefoneCadastro;
	}

	public void setTxtTelefoneCadastro(JTextField txtTelefoneCadastro) {
		this.txtTelefoneCadastro = txtTelefoneCadastro;
	}

	public JTextField getTxtEmailCadastro() {
		return txtEmailCadastro;
	}

	public void setTxtEmailCadastro(JTextField txtEmailCadastro) {
		this.txtEmailCadastro = txtEmailCadastro;
	}
	
	
}
