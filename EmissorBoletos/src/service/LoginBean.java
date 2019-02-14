package service;

import controller.UsuarioController;
import model.Usuario;

public class LoginBean {

	public void iniciar() {
		UsuarioController usuCon = new UsuarioController();
		if (usuCon.listarUsuarios().size() == 0 ) {
			Usuario usr = new Usuario();
			usr.setNome("Administrador");
			usr.setSenha(CriptografiaUtil.criptografar("admboletos"));
			usuCon.salvar(usr);
		}
	}
	
}
