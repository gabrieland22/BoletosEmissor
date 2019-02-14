package controller;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import model.Usuario;

public class UsuarioModel extends AbstractTableModel{


	private static final long serialVersionUID = 1L;
	private List<Usuario> listUsu = new ArrayList<>();
	private String[] colunas = {"Ações","Nome","Data de Cadastro"};
	
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
//		if(coluna == 0) {
//			return listUsu.get(linha);
//		}
//		return null;
		
		switch (coluna) {
		case 0:
			return listUsu.get(linha).getNome();
		case 1:
			return listUsu.get(linha).getId();
		}
		return null;
	}

}
