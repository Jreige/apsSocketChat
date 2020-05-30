package br.com.aps.unip.model.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Client {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(name = "NOME", unique=true)
	private String name;
	@Column(name = "SENHA")
	private String pass;
	@Column(name = "EMAIL", unique=true)
	private String email;

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
}
