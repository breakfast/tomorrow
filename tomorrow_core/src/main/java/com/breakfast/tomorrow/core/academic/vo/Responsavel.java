package com.breakfast.tomorrow.core.academic.vo;

import java.io.Serializable;

public class Responsavel extends Pessoa implements Serializable{
	
	private Aluno aluno;
	
	public Responsavel(){}
	
	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		
		this.aluno = aluno;
	}
	
	public String getStringId(){
		return getId() != null ? getId().toString() : "";
	}
	
	public void setStringId(String id){
		this.setId(id != null ? Long.valueOf(id) : 0);
	}

	private static final long serialVersionUID = 1L;
	


}
