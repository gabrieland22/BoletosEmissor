package service;

import java.util.Date;

import controller.AgendadorController;
import controller.UsuarioController;
import model.Agendador;
import model.Usuario;

public class LoginBean {

	public void iniciar() {
		UsuarioController usuCon = new UsuarioController();
		AgendadorController agendCon = new AgendadorController();
		if (usuCon.listarUsuarios().size() == 0 ) {
			Usuario usr = new Usuario();
			usr.setNome("Administrador");
			usr.setDataCriacao(new Date());
			usr.setSenha(CriptografiaUtil.criptografar("admboletos"));
			usr.setUsuarioCriacao("Administrador");
			usr.setUsuarioAlteracao("Administrador");
			usr.setDataAlteracao(new Date());
			usuCon.salvar(usr);
			
			Agendador agend = new Agendador();
			agend.setHora("20");
			agend.setMinuto("30");
			agend.setEnviarBoleto(false);
			agend.setUsuarioCriacao("Administrador");
			agend.setDataCriacao(new Date());
			agendCon.salvar(agend);
			
		}
	}
	
}
