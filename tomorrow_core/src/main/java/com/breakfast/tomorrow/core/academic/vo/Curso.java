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
	private Unidade unidadeEducacional;
	private Map<Etapa, Collection<Disciplina>> configuracao;
	
	public void abrirTurma(Turma turma){
		if(configuracao == null) throw new IllegalArgumentException("Configuração NULL");
		if(configuracao.keySet() == null) throw new IllegalArgumentException("Nenhuma etapa encontrada");
		if(configuracao.keySet().size() == 0) throw new IllegalArgumentException("Nenhuma etapa encontrada");
		Collection<Etapa> etapas = configuracao.keySet();
		for(Etapa etapa : etapas){
			etapa.setDiciplinas(configuracao.get(etapa));
		}
		turma.setEtapas(etapas);
		turmas.add(turma);
	}
	

	public Curso(){}
	

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
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
	
	public Integer getMedia() {
		return media;
	}


	public void setMedia(Integer media) {
		this.media = media;
	}


	public Integer getQtdeDependenciaReprovacao() {
		return qtdeDependenciaReprovacao;
	}


	public void setQtdeDependenciaReprovacao(Integer qtdeDependenciaReprovacao) {
		this.qtdeDependenciaReprovacao = qtdeDependenciaReprovacao;
	}

	public Collection<Turma> getTurmas() {
		return turmas;
	}

	public void setTurmas(Collection<Turma> turmas) {
		this.turmas = turmas;
	}

	public Unidade getUnidadeEducacional() {
		return unidadeEducacional;
	}

	public void setUnidadeEducacional(Unidade unidadeEducacional) {
		this.unidadeEducacional = unidadeEducacional;
	}
   
	public Map<Etapa, Collection<Disciplina>> getConfiguracao() {
		return configuracao;
	}


	public void setConfiguracao(Map<Etapa, Collection<Disciplina>> configuracao) {
		this.configuracao = configuracao;
	}
	
	public String getStringId(){
		return getId() != null ? getId().toString() : "";
	}
	
	public void setStringId(String id){
		this.setMedia(id != null ? Integer.valueOf(id) : 0);
	}
	
	public String getStringMedia(){
		return getMedia() != null ? getMedia().toString() : "";
	}
	
	public void setStringQtdeDependenciaReprovacao(String qtde){
		this.setQtdeDependenciaReprovacao(qtde!=null?Integer.valueOf(qtde) : 0);
	}
	
	public String getStringQtdeDependenciaReprovacao(){
		return getQtdeDependenciaReprovacao() !=null ? getQtdeDependenciaReprovacao().toString() : "";
	}
	
	public void setStringMedia(String media){
		this.setMedia(media != null ? Integer.valueOf(media) : 0);
	}


	@Override
	public boolean equals(Object o) {
		if( !(o instanceof Curso)) return false;
		return ((Curso) o).getId() == this.getId();
	}
	
	@Override
	public int hashCode() {
		return getNomeCurso().length();
	}
	
	@Override
	public String toString() {
		return "[ " + this.getId()+ "]" + this.getNomeCurso();
	}
	
	private static final long serialVersionUID = 1L;
}
