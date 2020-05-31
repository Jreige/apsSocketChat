package br.unip.aps.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;


@Entity
@Table(name="CLIENTE")
public class Cliente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(name = "NOME", unique=true)
	private String name;
	@Column(name = "SENHA")
	private String pass;
	@Column(name = "EMAIL", unique=true)
	private String email;
	
	@OneToMany(mappedBy = "remetente",fetch = FetchType.EAGER)
	private List<Mensagem> enviados;
	
	@OneToMany(mappedBy = "destinatario")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Mensagem> recebidos;

	public Cliente() {
	}
	
	public void add(Mensagem mensagem) {
		if(enviados == null) {
			enviados = new ArrayList<>();
		}
		if(recebidos == null) {
			recebidos = new ArrayList<>();
		}
		
		if(mensagem.getRemetente() == this) {
			this.enviados.add(mensagem);
		} else {
			this.recebidos.add(mensagem);
		}
	}
	
	//toString
	
	//Getters & Setters
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setNome(String nome) {
		this.name = nome;
	}
	public String getSenha() {
		return pass;
	}
	public void setSenha(String senha) {
		this.pass = senha;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List<Mensagem> getEnviados() {
		return enviados;
	}
	public void setEnviados(List<Mensagem> enviados) {
		this.enviados = enviados;
	}
	public List<Mensagem> getRecebidos() {
		return recebidos;
	}
	public void setRecebidos(List<Mensagem> recebidos) {
		this.recebidos = recebidos;
	}
	
	@Override
	public String toString() {
		return "Client [id=" + id + ", name=" + name + "]";
	}
}
