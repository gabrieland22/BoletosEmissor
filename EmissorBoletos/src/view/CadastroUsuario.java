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
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controller.UsuarioController;
import model.Usuario;
import service.CriptografiaUtil;

public class CadastroUsuario extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNomeUsuario;
	private JTextField txtSenhaUsuario;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CadastroUsuario frame = new CadastroUsuario(null);
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
	public CadastroUsuario(final Usuario usuario) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(CadastroUsuario.class.getResource("/images/novo_usuario_mini.png")));
		lblNewLabel.setBounds(10, 11, 48, 48);
		contentPane.add(lblNewLabel);
		
		JLabel lblNovoUsuarioTela = new JLabel("Novo Usuário");
		lblNovoUsuarioTela.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNovoUsuarioTela.setBounds(68, 26, 106, 20);
		contentPane.add(lblNovoUsuarioTela);
		
		JLabel lblNomeUsuario = new JLabel("Nome Usuário*");
		lblNomeUsuario.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNomeUsuario.setBounds(10, 95, 89, 14);
		contentPane.add(lblNomeUsuario);
		
		txtNomeUsuario = new JTextField();
		txtNomeUsuario.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtNomeUsuario.setBounds(109, 92, 300, 22);
		if(usuario != null) {
			txtNomeUsuario.setText(usuario.getNome());
		}
		contentPane.add(txtNomeUsuario);
		txtNomeUsuario.setColumns(10);
		
		JLabel lblSenhaUsuario = new JLabel("Senha*");
		lblSenhaUsuario.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblSenhaUsuario.setBounds(53, 137, 46, 14);
		contentPane.add(lblSenhaUsuario);
		
		txtSenhaUsuario = new JPasswordField();
		txtSenhaUsuario.setBounds(110, 135, 164, 22);
		contentPane.add(txtSenhaUsuario);
		txtSenhaUsuario.setColumns(10);
		
		JButton btnSalvarUsuario = new JButton("Salvar");
		btnSalvarUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(salvarAntes()){
					if(usuario == null) {
						Usuario usr = new Usuario();
						usr.setNome(txtNomeUsuario.getText());
						usr.setSenha(CriptografiaUtil.criptografar(txtSenhaUsuario.getText()));
						usr.setDataCriacao(new Date());
						UsuarioController usuCon = new UsuarioController();
						usuCon.salvar(usr);
						JOptionPane.showMessageDialog(null, "Usuário Salvo com Sucesso!", "", JOptionPane.INFORMATION_MESSAGE);
						dispose();
					}else {
						UsuarioController usuCon = new UsuarioController();
						usuario.setNome(txtNomeUsuario.getText());
						usuario.setSenha(CriptografiaUtil.criptografar(txtSenhaUsuario.getText()));
						usuCon.salvar(usuario);
						JOptionPane.showMessageDialog(null, "Usuário Salvo com Sucesso!", "", JOptionPane.INFORMATION_MESSAGE);
						dispose();
					}
				}
			}
		});
		btnSalvarUsuario.setBounds(34, 179, 89, 23);
		contentPane.add(btnSalvarUsuario);
		
		JButton btnLimpar = new JButton("Limpar");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtNomeUsuario.setText("");
				txtSenhaUsuario.setText("");
			}
		});
		btnLimpar.setBounds(177, 179, 89, 23);
		contentPane.add(btnLimpar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancelar.setBounds(315, 179, 89, 23);
		contentPane.add(btnCancelar);
		
		JLabel lblCamposObrigatorios = new JLabel("Campos marcos com (*) são obrigatórios.");
		lblCamposObrigatorios.setForeground(Color.RED);
		lblCamposObrigatorios.setBounds(10, 237, 252, 14);
		contentPane.add(lblCamposObrigatorios);
	}
	
	public boolean salvarAntes(){
		Boolean salvar = true;
			if(txtNomeUsuario.getText() == null || txtNomeUsuario.getText().trim().equals("")){
				salvar = false;
				JOptionPane.showMessageDialog(null, "Nome do Usuário é obrigatório!", "" ,JOptionPane.ERROR_MESSAGE);
			}
			
			if(txtSenhaUsuario.getText() == null || txtSenhaUsuario.getText().trim().equals("")){
				salvar = false;
				JOptionPane.showMessageDialog(null, "Senha é obrigatória!", "" ,JOptionPane.ERROR_MESSAGE);
			}
		return salvar;
	}

	public JTextField getTxtNomeUsuario() {
		return txtNomeUsuario;
	}

	public void setTxtNomeUsuario(JTextField txtNomeUsuario) {
		this.txtNomeUsuario = txtNomeUsuario;
	}

}
