package model;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tb_agendador")
public class Agendador {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Column
	private String hora;
	@Column
	private String minuto;
	@Column (name = "enviar_boleto")
	private boolean enviarBoleto;
	@Column (name = "usuario_criacao")
	private String usuarioCriacao;
	@Column (name = "data_criacao")
	private Date dataCriacao;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getHora() {
		return hora;
	}
	public void setHora(String hora) {
		this.hora = hora;
	}
	public String getMinuto() {
		return minuto;
	}
	public void setMinuto(String minuto) {
		this.minuto = minuto;
	}
	public boolean isEnviarBoleto() {
		return enviarBoleto;
	}
	public void setEnviarBoleto(boolean enviarBoleto) {
		this.enviarBoleto = enviarBoleto;
	}
	public String getUsuarioCriacao() {
		return usuarioCriacao;
	}
	public void setUsuarioCriacao(String usuarioCriacao) {
		this.usuarioCriacao = usuarioCriacao;
	}
	public Date getDataCriacao() {
		return dataCriacao;
	}
	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	
	
	
}
