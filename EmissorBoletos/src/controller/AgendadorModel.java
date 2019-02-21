package controller;

import java.text.SimpleDateFormat;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import model.Agendador;

public class AgendadorModel extends AbstractTableModel{


	private static final long serialVersionUID = 1L;
	private Agendador agend = new Agendador();
	private String[] colunas = {"Hora","Minuto","Enviar","Usuário Cadastro" , "Data de Cadastro"};
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	String dataCriacao = "";
	
	@Override
	public String getColumnName(int column) {
		return colunas[column];
	}
	
	@Override
	public int getRowCount() {
		return 1;
	}

	@Override
	public int getColumnCount() {
		return colunas.length;
	}

	@Override
	public Object getValueAt(int linha, int coluna) {

		
		switch (coluna) {
		case 0:
			return agend.getHora();
		case 1:
			return agend.getMinuto();
		case 2:
			return agend.isEnviarBoleto() == true ? "Sim" : "Não";
		case 3:
			return agend.getUsuarioCriacao();
		case 4:
			dataCriacao = sdf.format(agend.getDataCriacao());
			return  dataCriacao;
		}
		return null;
	}
	
	public void preencheGrid() {
		AgendadorController agendCon = new AgendadorController();
		agend = agendCon.recuperaAgendamento();
		if(agend != null) {
			this.fireTableDataChanged();
		}
	}
	
	
	public void removeAgendamento(int linha) {
		
		AgendadorController agendCon = new AgendadorController();
		Object[] botoes = { "Sim", "Não" };
		int resposta = JOptionPane.showOptionDialog(null,
				"Deseja remover o agendamento?",
				"Confirmação", 
				JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
				botoes, botoes[0]);
		if(resposta == 0) {
			agendCon.remover();
			this.fireTableDataChanged();
		}
	}
	
	
	public Agendador recueraAgendamentoSelecionado (int linha) {
		Agendador agendador = new Agendador();
		AgendadorController agendCon = new AgendadorController();
		agendador = agendCon.recuperaAgendamento();
		return agendador;
	}

}
