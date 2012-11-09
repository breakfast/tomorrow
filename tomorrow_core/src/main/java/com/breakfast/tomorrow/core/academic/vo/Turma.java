package com.breakfast.tomorrow.core.academic.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.breakfast.tomorrow.core.database.IdNode;
import com.breakfast.tomorrow.core.database.FieldNode;
import com.breakfast.tomorrow.core.database.IndexNode;

public class Turma implements Serializable{
	
	@IdNode private long id;
	@IndexNode private String nomeTurma;	
	@FieldNode private String observacao;
	@FieldNode private long inicio;
	@FieldNode private String turno;
	private Curso curso ;
	private Collection<Etapa> etapas = new ArrayList<Etapa>();


	/*
	@Override
	protected Object clone(){
		Turma clone = new Turma();
		clone.setNomeTurma(this.nomeTurma);
		clone.setObservacao(this.observacao);
		clone.setTurno(this.turno);
		return clone;
	}
	*/
	public Turma() {
	}
	
	public Long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}


	public String getNomeTurma(){
		return this.nomeTurma ;
	}
	
	public void setNomeTurma(String nomeTurma){
		this.nomeTurma = nomeTurma;
	}
	
	public String getObservacao(){
		return this.observacao; 
	}
	
	public void setObservacao(String observacao){
		this.observacao = observacao;
	}
	
	public Date getInicio() {
		Date date = new Date();
		date.setTime(this.inicio == 0 ? new Date().getTime() : this.inicio);
		return date;
	}

	public void setInicio(Date inicio) {
		if(inicio!=null) this.inicio = inicio.getTime();
	}

	public String getTurno() {
		return turno;
	}
	
	public void setTurno(String turno) {
		this.turno = turno;
	}

	
	public Collection<Etapa> getEtapas() {
		return etapas;
	}

	public void setEtapas(Collection<Etapa> etapas) {
		this.etapas = etapas;
	}

	public void setInicio(long inicio) {
		this.inicio = inicio;
	}
	
	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	@Override
	public String toString() {
		return "[" + this.id + "] " + this.nomeTurma;
	}
	
	public String getStringId(){
		return getId() != null ? getId().toString() : "";
	}
	
	public void setStringId(String id){
		this.setId(id != null ? Long.valueOf(id) : 0);
	}
	
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Turma)) return false;
		return id != 0 ? ((Turma)o).getId().equals(this.getId()) : super.equals(o);
	}
	
	@Override
	public int hashCode() {
		return id != 0 ? getId().intValue() : super.hashCode();
	}
	
	private static final long serialVersionUID = 1L;
}
