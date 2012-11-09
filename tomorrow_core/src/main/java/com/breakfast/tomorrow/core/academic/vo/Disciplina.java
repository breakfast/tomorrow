package com.breakfast.tomorrow.core.academic.vo;

import java.io.Serializable;

import com.breakfast.tomorrow.core.database.IdNode;
import com.breakfast.tomorrow.core.database.IndexNode;

public class Disciplina implements Serializable {
	
	public Disciplina() {}
	
	public Disciplina(String nome){
		diario = new Diario();
		diario.setDisciplina(this);
		this.nomeDisciplina = nome;
	}

	@IdNode private long id;
	@IndexNode private String nomeDisciplina;
	private Diario diario;
	private Professor professor;
	private Etapa etapa;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeDisciplina() {
		return this.nomeDisciplina;
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

	public Etapa getEtapa() {
		return etapa;
	}

	public void setEtapa(Etapa etapa) {
		this.etapa = etapa;
	}

	@Override
	public boolean equals(Object o) {
		if( !(o instanceof Disciplina)) return false;
		return id != 0 ? ((Disciplina) o).getId().equals(this.getId()) : super.equals(o);
	}
	
	@Override
	public int hashCode() {
		return id != 0 ? getId().intValue() : super.hashCode();
	}
	
	@Override
	public String toString() {
		return "[" + this.getId()+ "]" + this.getNomeDisciplina();
	}
	
	private static final long serialVersionUID = 1L;

}
