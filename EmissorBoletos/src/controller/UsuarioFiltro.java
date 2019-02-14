package controller;

public class UsuarioFiltro {
	
	private String nome;
	private String senha;
	
	public void clear(){
		nome = "";
		senha = "";
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
