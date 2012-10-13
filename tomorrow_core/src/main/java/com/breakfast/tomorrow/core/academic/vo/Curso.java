package com.breakfast.tomorrow.core.academic.vo;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import com.breakfast.tomorrow.core.database.IdNode;
import com.breakfast.tomorrow.core.database.FieldNode;
import com.breakfast.tomorrow.core.database.IndexNode;

public class Curso implements Serializable{
	
	/**
	 * fields of class
	*/ 
	@IdNode private long id;
	@IndexNode private String nomeCurso; 
	@FieldNode private String descricao;
	@FieldNode private String duracao;
	@FieldNode private int media;
	@FieldNode private int qtdeDependenciaReprovacao;
	private Collection<Turma> turmas;
	private UnidadeEducacional unidadeEducacional; 
	private Map<Etapa, Collection<Disciplina>> configuracao;
	
	
	public void abrirTurma(Turma turma){
		turmas.add(turma);
	}
	

	public Curso(){}
	

	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}
	
	public String getNomeCurso(){
		return this.nomeCurso ;
	}
	
	public void setNomeCurso(String nomeCurso){
		this.nomeCurso = nomeCurso;
	}
	
	public String getDescricao(){
		return this.descricao ; 
	}
	
	public void setDescricao(String descricao){
		this.descricao = descricao;
	}
	
	public String getDuracao (){
		return this.duracao;
	}
	
	public void setDuracao(String duracao){
		this.duracao = duracao;
	}
	
	public int getMedia() {
		return media;
	}


	public void setMedia(int media) {
		this.media = media;
	}


	public int getQtdeDependenciaReprovacao() {
		return qtdeDependenciaReprovacao;
	}


	public void setQtdeDependenciaReprovacao(int qtdeDependenciaReprovacao) {
		this.qtdeDependenciaReprovacao = qtdeDependenciaReprovacao;
	}

	public Collection<Turma> getTurmas() {
		return turmas;
	}

	public void setTurmas(Collection<Turma> turmas) {
		this.turmas = turmas;
	}

	public UnidadeEducacional getUnidadeEducacional() {
		return unidadeEducacional;
	}


	public void setUnidadeEducacional(UnidadeEducacional unidadeEducacional) {
		this.unidadeEducacional = unidadeEducacional;
	}
	
	public Map<Etapa, Collection<Disciplina>> getConfiguracao() {
		return this.configuracao;
	}


	public void setConfiguracao(Map<Etapa, Collection<Disciplina>> configuracao) {
		this.configuracao = configuracao;
	}



	private static final long serialVersionUID = 1L;
}
