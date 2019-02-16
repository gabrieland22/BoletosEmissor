package controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import model.Cliente;
import model.Usuario;

public class ClienteModel extends AbstractTableModel{
	private static final long serialVersionUID = 1L;
	private List<Cliente> listCli = new ArrayList<>();
	private String[] colunas = {"Nome","Código","Endereço","CPF","Celular","Email","Usuário Cadastro / Alteração","Data de Cadastro / Alteração"};
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	String dataCriacao = "";
	
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
			return "Rua/Av "+listCli.get(linha).getRua()+" - "+listCli.get(linha).getBairro()+" - "+listCli.get(linha).getCidade()
					+" - "+listCli.get(linha).getEstado();
		case 3:
			return listCli.get(linha).getCpf();
		case 4:
			return listCli.get(linha).getCelular();
		case 5:
			return listCli.get(linha).getEmail();
		case 6:
			return listCli.get(linha).getUsuarioCriacao()+" / "+listCli.get(linha).getUsuarioAlteracao();
		case 7:
			dataCriacao = sdf.format(listCli.get(linha).getDataCriacao()) +" / "+ sdf.format(listCli.get(linha).getDataAlteracao());
			return  dataCriacao;
		}
		return null;
	}
	
	public void preencheGrid(ClienteFiltro filtro) {
		ClienteController cliCon = new ClienteController();
		listCli = cliCon.buscaPadrao(filtro);
		this.fireTableDataChanged();
	}
	
	public void removeItemGrid(int linha) {
		
		ClienteController cliCon = new ClienteController();
		usuCon.remover(listCli.get(linha).getId());
		this.listCli.remove(linha);
		this.fireTableDataChanged();
	}
	
	public void limparGrid() {
		listCli.clear();
		this.fireTableDataChanged();
	}
	
	public Cliente recueraClienteSelecionado (int linha) {
		Cliente cliente = new Cliente();
		ClienteController cliCon = new ClienteController();
		cliente = cliCon.recuperaUsuarioPorID(listCli.get(linha).getId());
		return cliente;
	}

}
