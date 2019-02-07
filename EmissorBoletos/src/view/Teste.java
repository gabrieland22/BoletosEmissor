package view;

import controller.UsuarioController;
import model.Usuario;
import service.CriptografiaUtil;

public class Teste {

	public static void main(String[] args) {
			
		
		Usuario usr = new Usuario();
		usr.setNome("gabriel");
		usr.setSenha(CriptografiaUtil.criptografar("novasenha"));
	//	CooperadoController coopCon = new CooperadoController();		
		UsuarioController usuCon = new UsuarioController();
		usuCon.salvar(usr);
	}
}
