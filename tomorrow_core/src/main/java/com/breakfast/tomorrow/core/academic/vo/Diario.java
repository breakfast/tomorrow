package com.breakfast.tomorrow.core.academic.vo;

import java.io.Serializable;
import java.util.Collection;

import com.breakfast.tomorrow.core.database.FieldNode;
import com.breakfast.tomorrow.core.database.IdNode;

public class Diario implements Serializable{


	public Diario(){}

	@IdNode private long id;
	@FieldNode private int aulasPrevistas;
	@FieldNode private int aulasDadas;
	Collection<Aula> aulas;
	Collection<Avaliacao> avaliacoes;
	private Disciplina disciplina;
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public int getAulasPrevistas() {
		return aulasPrevistas;
	}

	public void setAulasPrevistas(int aulasPrevistas) {
		this.aulasPrevistas = aulasPrevistas;
	}

	public int getAulasDadas() {
		return aulasDadas;
	}

	public void setAulasDadas(int aulasDadas) {
		this.aulasDadas = aulasDadas;
	}

	public Collection<Aula> getAulas() {
		return aulas;
	}

	public void setAulas(Collection<Aula> aulas) {
		this.aulas = aulas;
	}

	public Collection<Avaliacao> getAvaliacoes() {
		return avaliacoes;
	}

	public void setAvaliacoes(Collection<Avaliacao> avaliacoes) {
		this.avaliacoes = avaliacoes;
	}

	public Disciplina getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}

	private static final long serialVersionUID = 1L;
}
