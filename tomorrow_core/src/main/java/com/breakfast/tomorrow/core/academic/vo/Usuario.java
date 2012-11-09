package com.breakfast.tomorrow.core.academic.vo;

import java.io.Serializable;

import com.breakfast.tomorrow.core.database.FieldNode;
import com.breakfast.tomorrow.core.database.IdNode;
import com.breakfast.tomorrow.core.database.IndexNode;



/**
 * Classe Represenativa de um usuario no sistema pra controle de acessos 
 * @author Maicon
 *
 */
public class Usuario implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Usuario() {
	}
  
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getApelido() {
		return apelido;
	}

	public void setApelido(String apelido) {
		this.apelido = apelido;
	}

	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStringId(){
		return getId() != null ? getId().toString() : "";
	}
	
	public void setStringId(String id){
		this.setId(id != null ? Long.valueOf(id) : 0);
	}

	
	@IdNode    private Long id ;
	@FieldNode private String nome;
	@IndexNode private String email;
	@IndexNode private String apelido;
	@FieldNode private String perfil;
	@FieldNode private String senha;
	
		
	
}
