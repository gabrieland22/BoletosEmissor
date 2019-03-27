package controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import model.Cliente;
import model.Usuario;

public class SelecionarClienteModel extends AbstractTableModel{
	private static final long serialVersionUID = 1L;
	private List<Cliente> listCli = new ArrayList<>();
	private String[] colunas = {"Nome","Código","CPF","Celular","Email","Enviar"};
	
	@Override
	public String getColumnName(int column) {
		return colunas[column];
	}
	
	@Override
	public int getRowCount() {
		return listCli.size();
	}

	@Override
	public int getColumnCount() {
		return colunas.length;
	}
	
	@Override
	public Object getValueAt(int linha, int coluna) {
		
		switch (coluna) {
		case 0:
			return listCli.get(linha).getNome();
		case 1:
			return listCli.get(linha).getCodigo();
		case 2:
			return listCli.get(linha).getCpf();
		case 3:
			return listCli.get(linha).getCelular();
		case 4:
			return listCli.get(linha).getEmail();
		case 5:
			return listCli.get(linha).getEnviarEmail().equals(true) ? "Sim" : "Não";
		}
		return null;
	}
	
	public void preencheGrid(ClienteFiltro filtro) {
		ClienteController cliCon = new ClienteController();
		listCli = cliCon.buscaPadrao(filtro);
		if(listCli != null && listCli.size() > 0) {
			this.fireTableDataChanged();
		}else {
			JOptionPane.showMessageDialog(null, "Nenhum resultado encontrado!", "", JOptionPane.INFORMATION_MESSAGE);
		}
		
	}
	
	public void removeSelecaoCliente(int linha) {
		
			ClienteController cliCon = new ClienteController();
			cliCon.removerClienteSelecionado(listCli.get(linha).getId());

	}
	
	public void selecionarCliente(int linha) {
		
		ClienteController cliCon = new ClienteController();
		cliCon.salvarClienteSelecionado(listCli.get(linha).getId());

	}
	
	public void limparGrid() {
		listCli.clear();
		this.fireTableDataChanged();
	}
	
	public Cliente recuperaClienteSelecionado (int linha) {
		Cliente cliente = new Cliente();
		ClienteController cliCon = new ClienteController();
		cliente = cliCon.recuperaClientePorID(listCli.get(linha).getId());
		return cliente;
	}
	
	

}
