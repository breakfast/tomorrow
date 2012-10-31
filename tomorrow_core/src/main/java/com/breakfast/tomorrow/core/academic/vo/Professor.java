package com.breakfast.tomorrow.core.academic.vo;

import java.io.Serializable;
import java.util.Collection;


public class Professor extends Pessoa implements Serializable {
	
	public Professor(){}
	private Collection<Disciplina> disciplinas;
	
	private static final long serialVersionUID = 1L;
	
	public String getStringId(){
		return getId() != null ? getId().toString() : "";
	}
	
	public void setStringId(String id){
		this.setId(id != null ? Long.valueOf(id) : 0);
	}

	public Collection<Disciplina> getDisciplinas() {
		return disciplinas;
	}

	public void setDisciplinas(Collection<Disciplina> disciplinas) {
		this.disciplinas = disciplinas;
	}
	
	
	
}
