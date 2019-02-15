package controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import model.Usuario;

public class UsuarioModel extends AbstractTableModel{


	private static final long serialVersionUID = 1L;
	private List<Usuario> listUsu = new ArrayList<>();
	private String[] colunas = {"Nome","Data de Cadastro"};
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	String dataCriacao = "";
	
	
	
	@Override
	public String getColumnName(int column) {
		return colunas[column];
	}
	
	@Override
	public int getRowCount() {
		return listUsu.size();
	}

	@Override
	public int getColumnCount() {
		return colunas.length;
	}

	@Override
	public Object getValueAt(int linha, int coluna) {

		
		switch (coluna) {
		case 0:
			return listUsu.get(linha).getNome();
		case 1:
			dataCriacao = sdf.format(listUsu.get(linha).getDataCriacao());
			return  dataCriacao;
		}
		return null;
	}
	
	public void preencheGrid(UsuarioFiltro filtro) {
		UsuarioController usuCon = new UsuarioController();
		listUsu = usuCon.buscaPadrao(filtro);
		this.fireTableDataChanged();
	}
	
	public void removeItemGrid(int linha) {
		UsuarioController usuCon = new UsuarioController();
		usuCon.remover(listUsu.get(linha).getId());
		this.listUsu.remove(linha);
		this.fireTableDataChanged();
	}
	
	public void limparGrid() {
		listUsu.clear();
		this.fireTableDataChanged();
	}

}
