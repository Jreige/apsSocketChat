package br.unip.aps.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="MENSAGEM")
public class Mensagem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;
	
	@Column(name="TEXTO_MENSAGEM")
	private String textoMensagem;
	
	@Column(name = "DATA_HORA_MENSAGEM")
	private LocalDateTime dataHoraMensagem;
	
	@ManyToOne
	@Column(name="REMETENTE")
	private Cliente remetente;
	
	@ManyToOne
	@Column(name="DESTINATARIO")
	private Cliente destinatario;

	public Mensagem() {
	}

	// Getters & Setters
	
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTextoMensagem() {
		return textoMensagem;
	}

	public void setTextoMensagem(String textoMensagem) {
		this.textoMensagem = textoMensagem;
	}

	public LocalDateTime getDataHoraMensagem() {
		return dataHoraMensagem;
	}

	public void setDataHoraMensagem(LocalDateTime dataHoraMensagem) {
		this.dataHoraMensagem = dataHoraMensagem;
	}

	public Cliente getRemetente() {
		return remetente;
	}

	public void setRemetente(Cliente remetente) {
		this.remetente = remetente;
	}

	public Cliente getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(Cliente destinatario) {
		this.destinatario = destinatario;
	}

	@Override
	public String toString() {
		return "Mensagem [id=" + id + ", textoMensagem=" + textoMensagem + ", dataHoraMensagem=" + dataHoraMensagem
				+ ", remetente=" + remetente + ", destinatario=" + destinatario + "]";
	}
	
	
}
