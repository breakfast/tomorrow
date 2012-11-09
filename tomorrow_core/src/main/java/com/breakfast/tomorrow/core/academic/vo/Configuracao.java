package com.breakfast.tomorrow.core.academic.vo;

import java.io.Serializable;

import com.breakfast.tomorrow.core.database.FieldNode;
import com.breakfast.tomorrow.core.database.IdNode;

public class Configuracao implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Configuracao(){}
	
	@IdNode private Long id;
	@FieldNode private String email;
	@FieldNode private String senha;
	@FieldNode private String localBanco;
	@FieldNode private String localBackup;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getLocalBanco() {
		return localBanco;
	}
	public void setLocalBanco(String localBanco) {
		this.localBanco = localBanco;
	}
	public String getLocalBackup() {
		return localBackup;
	}
	public void setLocalBackup(String localBackup) {
		this.localBackup = localBackup;
	}
	
	public String getStringId(){
		return getId() != null ? getId().toString() : "";
	}
	
	public void setStringId(String id){
		this.setId(id != null ? Long.valueOf(id) : 0);
	}
	

}
