package view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

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

import service.TelaPrincipalBean;

public class TelaPrincipal extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	FileInputStream fis;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
	
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaPrincipal frame = new TelaPrincipal("");
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
	public TelaPrincipal(String nomeUsuario) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 880, 540);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		

		
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
						TelaPrincipalBean telaPrincipalService = new TelaPrincipalBean();
							try {
								fis = new FileInputStream(fileChooser.getSelectedFile().getAbsoluteFile());
							} catch (FileNotFoundException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							try {
								telaPrincipalService.importarArquivo(fis);
							} catch (InvalidFormatException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						
					}else if(resposta == 1){
						JOptionPane.showMessageDialog(null, "NÃO");
					}
				}
				
			}
		});
		btnNovoArquivo.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/images/nova_planilha.png")));
		btnNovoArquivo.setBounds(40, 401, 102, 90);
		contentPane.add(btnNovoArquivo);
		
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
		
		JLabel lblUsuarioLogado = new JLabel("Bem vindo(a) " + nomeUsuario);
		lblUsuarioLogado.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblUsuarioLogado.setBounds(34, 11, 267, 31);
		contentPane.add(lblUsuarioLogado);
		
		JButton btnClientes = new JButton("");
		btnClientes.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/images/clientes.png")));
		btnClientes.setBounds(352, 149, 175, 200);
		contentPane.add(btnClientes);
		
		JButton btnUsuarios = new JButton("");
		btnUsuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UsuarioPesquisa telaUsuarioPesquisa = new UsuarioPesquisa();
				telaUsuarioPesquisa.setVisible(true);
			}
		});
		btnUsuarios.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/images/usuario.png")));
		btnUsuarios.setBounds(116, 149, 175, 200);
		contentPane.add(btnUsuarios);
	}
}
