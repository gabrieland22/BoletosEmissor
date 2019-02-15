package service;

import java.util.Date;

import controller.UsuarioController;
import model.Usuario;

public class LoginBean {

	public void iniciar() {
		UsuarioController usuCon = new UsuarioController();
		if (usuCon.listarUsuarios().size() == 0 ) {
			Usuario usr = new Usuario();
			usr.setNome("Administrador");
			usr.setDataCriacao(new Date());
			usr.setSenha(CriptografiaUtil.criptografar("admboletos"));
			usuCon.salvar(usr);
		}
	}
	
}
