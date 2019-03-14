package controller;

public class ClienteFiltro {
	private String nome ;
	private String codigo ;
	private String cpf ;
	private Boolean gridSelecionarCliente;
	
	public void clear() {
		nome = "";
		codigo = "";
		cpf = "";
		gridSelecionarCliente = null;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Boolean getGridSelecionarCliente() {
		return gridSelecionarCliente;
	}

	public void setGridSelecionarCliente(Boolean gridSelecionarCliente) {
		this.gridSelecionarCliente = gridSelecionarCliente;
	}
	
	
		
}
