package service;

import controller.UsuarioFiltro;

public class UsuarioBean {

	public boolean pesquisarAntes(UsuarioFiltro filtro) {
		boolean retorno = true;
		if(filtro.getNome() == null) {
			retorno = false;
		}
		return retorno;
	}
}
