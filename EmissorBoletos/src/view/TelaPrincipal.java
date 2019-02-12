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

import service.TelaPrincipalService;

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
					TelaPrincipal frame = new TelaPrincipal();
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
	public TelaPrincipal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 880, 540);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNovoUsuario = new JButton("");
		btnNovoUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CadastroUsuario cadastroUsuarioTela = new CadastroUsuario();
				cadastroUsuarioTela.setVisible(true);  
				cadastroUsuarioTela.setLocation(300,300);  
				cadastroUsuarioTela.setResizable(false);
			}
		});
		btnNovoUsuario.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/images/novo_usuario.png")));
		btnNovoUsuario.setBounds(103, 149, 175, 200);
		contentPane.add(btnNovoUsuario);
		
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
						TelaPrincipalService telaPrincipalService = new TelaPrincipalService();
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
		btnNovoArquivo.setBounds(349, 149, 175, 200);
		contentPane.add(btnNovoArquivo);
		
		JButton btnEnviarEmail = new JButton("");
		btnEnviarEmail.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/images/enviar_emails.png")));
		btnEnviarEmail.setBounds(589, 149, 175, 200);
		contentPane.add(btnEnviarEmail);
		
		JLabel lblNovoUsuario = new JLabel("Novo Usuário");
		lblNovoUsuario.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNovoUsuario.setBounds(140, 361, 102, 23);
		contentPane.add(lblNovoUsuario);
		
		JLabel lblNovoArquivo = new JLabel("Novo Arquivo");
		lblNovoArquivo.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNovoArquivo.setBounds(395, 360, 102, 23);
		contentPane.add(lblNovoArquivo);
		
		JLabel lblEnviarEmail = new JLabel("Enviar Email");
		lblEnviarEmail.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblEnviarEmail.setBounds(632, 360, 102, 23);
		contentPane.add(lblEnviarEmail);
		
		JLabel lblUsuarioLogado = new JLabel("Usuário Logado Aqui");
		lblUsuarioLogado.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblUsuarioLogado.setBounds(34, 11, 267, 31);
		contentPane.add(lblUsuarioLogado);
	}
}
