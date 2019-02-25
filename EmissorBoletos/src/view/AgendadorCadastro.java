package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import controller.AgendadorController;
import controller.AgendadorModel;
import model.Agendador;
import model.Usuario;
import service.AgendamentoEmail;

import java.awt.Toolkit;

public class AgendadorCadastro extends JFrame {

	private JPanel contentPane;
	private AgendadorModel model = new AgendadorModel();
	private String[] listHoras = {"00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23"};
	private String[] listMinutos = {"00","01","02","03","04","05","06","07","08","09","10","11","12"
	,"13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28"
	,"29","30","31","32","33","34","35","36","37","38","39","40"
	,"39","40","41","42","43","44","45","46","47","48","49","50"
	,"51","52","53","54","55","56","57","58","59"};
	private JTable tableAgendamento = new JTable();
	private Usuario usuarioLogado;
	private AgendamentoEmail agendamentoEmail;
	private JComboBox comboBoxHora = new JComboBox(listHoras);
	private JComboBox comboBoxMinuto = new JComboBox(listMinutos);
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AgendadorCadastro frame = new AgendadorCadastro(null,null);
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
	public AgendadorCadastro(Usuario usuLog, AgendamentoEmail agendEmail) {
		setTitle("Envio de Boletos");
		setIconImage(Toolkit.getDefaultToolkit().getImage(AgendadorCadastro.class.getResource("/images/logo_mini.png")));
		
		usuarioLogado = usuLog;
		agendamentoEmail = agendEmail;
		model.preencheGrid();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 832, 334);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCadastrarAgendamento = new JLabel("Cadastrar Agendamento de Envio");
		lblCadastrarAgendamento.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblCadastrarAgendamento.setBounds(5, 5, 424, 22);
		contentPane.add(lblCadastrarAgendamento);
			
			JButton btnSalvar = new JButton("");
			btnSalvar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
						salvar();
				}
			});
			
			btnSalvar.setIcon(new ImageIcon(AgendadorCadastro.class.getResource("/images/salvar.png")));
			btnSalvar.setBounds(33, 140, 48, 48);
			contentPane.add(btnSalvar);
			
			JButton btnLimpar = new JButton("");	
			btnLimpar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					limpar();
				}
			});
			btnLimpar.setIcon(new ImageIcon(AgendadorCadastro.class.getResource("/images/Limpar.png")));
			btnLimpar.setBounds(100, 140, 48, 48);
			contentPane.add(btnLimpar);
			
			
			JLabel lblHora = new JLabel("Hora:");
			lblHora.setBounds(25, 48, 32, 14);
			contentPane.add(lblHora);
			
			tableAgendamento.setBackground(new Color(255, 255, 255));
			tableAgendamento.setBounds(54, 229, 725, 190);
			tableAgendamento.setModel(model);
			JScrollPane scrollPaneAgendamento = new JScrollPane(tableAgendamento);
			scrollPaneAgendamento.setBounds(46, 213, 718, 78);
			contentPane.add(scrollPaneAgendamento);
			
			
			JButton btnEditarAgendamento = new JButton("");
			btnEditarAgendamento.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(tableAgendamento.getSelectedRow() != -1) {
						if(tableAgendamento.getSelectedRowCount() == 1) {
							editar();
						}else {
							JOptionPane.showMessageDialog(null, "Para Editar favor selecionar apenas 1 item.", "Selecione", JOptionPane.INFORMATION_MESSAGE);
						}
					}else {
						JOptionPane.showMessageDialog(null, "Favor Selecionar um item.", "Selecione", JOptionPane.INFORMATION_MESSAGE);
					}
					
				}
			});
			btnEditarAgendamento.setIcon(new ImageIcon(AgendadorCadastro.class.getResource("/images/editarRegistro.png")));
			btnEditarAgendamento.setBounds(165, 140, 48, 48);
			contentPane.add(btnEditarAgendamento);
			
			JLabel lblEditarAgendamento = new JLabel("Editar");
			lblEditarAgendamento.setFont(new Font("Tahoma", Font.PLAIN, 11));
			lblEditarAgendamento.setBounds(175, 188, 48, 14);
			contentPane.add(lblEditarAgendamento);
			
			JButton btnRemoverAgendamento = new JButton("");
			btnRemoverAgendamento.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(tableAgendamento.getSelectedRow() != -1) {
							remover();
					}else {
						JOptionPane.showMessageDialog(null, "Favor Selecionar um item.", "Selecione", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			});
			btnRemoverAgendamento.setIcon(new ImageIcon(AgendadorCadastro.class.getResource("/images/deletarRegistro.png")));
			btnRemoverAgendamento.setBounds(232, 140, 48, 48);
			contentPane.add(btnRemoverAgendamento);
			
			JLabel lblRemoverAgendamento = new JLabel("Remover");
			lblRemoverAgendamento.setFont(new Font("Tahoma", Font.PLAIN, 11));
			lblRemoverAgendamento.setBounds(238, 188, 48, 14);
			contentPane.add(lblRemoverAgendamento);
			
			JLabel lblSalvar = new JLabel("Salvar");
			lblSalvar.setFont(new Font("Tahoma", Font.PLAIN, 11));
			lblSalvar.setBounds(36, 188, 57, 14);
			contentPane.add(lblSalvar);
			
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
			btnVoltar.setIcon(new ImageIcon(AgendadorCadastro.class.getResource("/images/voltar.png")));
			btnVoltar.setBounds(706, 140, 48, 48);
			contentPane.add(btnVoltar);
			
			JLabel lblVoltar = new JLabel("Voltar");
			lblVoltar.setFont(new Font("Tahoma", Font.PLAIN, 11));
			lblVoltar.setBounds(716, 188, 38, 14);
			contentPane.add(lblVoltar);
			
			comboBoxHora.setBounds(56, 45, 53, 20);
			comboBoxHora.setSelectedIndex(0);
			comboBoxHora.addItem(comboBoxHora);
			contentPane.add(comboBoxHora);
			
			
			JLabel lblMinuto = new JLabel("Minuto:");
			lblMinuto.setBounds(122, 48, 41, 14);
			contentPane.add(lblMinuto);
			
			comboBoxMinuto.setBounds(164, 45, 58, 20);
			comboBoxMinuto.setSelectedIndex(0);
			comboBoxMinuto.addItem(comboBoxMinuto);
			contentPane.add(comboBoxMinuto);
			
			
			
	}
	
	
	public void limpar() {
		comboBoxMinuto.setSelectedIndex(0);
		comboBoxHora.setSelectedIndex(0);
	}
	
	
	public void remover() {
			model.removeAgendamento(tableAgendamento.getSelectedRow());
			model.preencheGrid();
			if(agendamentoEmail != null) {
				agendamentoEmail.cancelarAgendamento();
			}
	}
	
	public void editar() {
		Agendador agendador = new Agendador();
		agendador = model.recueraAgendamentoSelecionado(tableAgendamento.getSelectedRow());
		comboBoxMinuto.setSelectedItem(agendador.getMinuto());
		comboBoxHora.setSelectedItem(agendador.getHora());
	}
	
	public void salvar() {
		AgendadorController agendCon = new AgendadorController();
		Agendador agendador = agendCon.recuperaAgendamento();
		agendador.setHora(comboBoxHora.getSelectedItem().toString());
		agendador.setMinuto(comboBoxMinuto.getSelectedItem().toString());
		agendador.setEnviarBoleto(true);
		agendador.setDataCriacao(new Date());
		agendador.setUsuarioCriacao(usuarioLogado.getNome());
		agendCon.salvar(agendador);
		model.preencheGrid();
		
		if(agendamentoEmail == null) {
			agendamentoEmail =  new AgendamentoEmail(Integer.parseInt(agendador.getHora()), Integer.parseInt(agendador.getMinuto()));
		} else {
			agendamentoEmail.cancelarAgendamento();
			agendamentoEmail =  new AgendamentoEmail(Integer.parseInt(agendador.getHora()), Integer.parseInt(agendador.getMinuto()));
		}
		
		JOptionPane.showMessageDialog(null, "Agendamento Salvo com Sucesso!", "", JOptionPane.INFORMATION_MESSAGE);
	}
	
	
}
