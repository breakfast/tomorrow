package com.breakfast.tomorrow.core.academic.vo;

import java.io.Serializable;

import com.breakfast.tomorrow.core.database.IdNode;
import com.breakfast.tomorrow.core.database.FieldNode;
import com.breakfast.tomorrow.core.database.IndexNode;

public class Disciplina implements Serializable {
	
	public Disciplina() {}
	
	public Disciplina(String nome){
		this.nomeDisciplina = nome;
	}
	
	public Disciplina(String nome, int indice){
		this.nomeDisciplina = nome;
		this.indice = indice;
	}

	@IdNode private long id;
	@IndexNode private String nomeDisciplina;
	@FieldNode private int indice;
	private Diario diario;
	private Professor professor;
	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNomeDisciplina() {
		return this.nomeDisciplina;
	}
	
	public int getIndice() {
		return this.indice;
	}


	public void setIndice(int indice) {
		this.indice = indice;
	}

	public void setNomeDisciplina(String nomeDisciplina) {
		this.nomeDisciplina = nomeDisciplina;
	}
	
	public Diario getDiario() {
		return diario;
	}

	public void setDiario(Diario diario) {
		this.diario = diario;
	}
	
	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	@Override
	public boolean equals(Object o) {
		if( !(o instanceof Disciplina)) return false;
		return ((Disciplina) o).getId() == this.getId();
	}
	
	@Override
	public int hashCode() {
		return getNomeDisciplina().length();
	}
	
	@Override
	public String toString() {
		return "[" + this.getId()+ "]" + this.getNomeDisciplina();
	}
	
	private static final long serialVersionUID = 1L;

}
