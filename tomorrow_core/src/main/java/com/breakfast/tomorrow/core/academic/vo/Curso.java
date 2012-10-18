package com.breakfast.tomorrow.core.academic.vo;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

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
	@FieldNode private String stringConfig;
	private Collection<Turma> turmas;
	private Unidade unidadeEducacional;
	
	
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

	public Unidade getUnidadeEducacional() {
		return unidadeEducacional;
	}


	public void setUnidadeEducacional(Unidade unidadeEducacional) {
		this.unidadeEducacional = unidadeEducacional;
	}
	
	public Map<Etapa, Collection<Disciplina>> getConfiguracao() {
		Map<Etapa, Collection<Disciplina>> map = new HashMap<Etapa, Collection<Disciplina>>();
		if(stringConfig==null)return map;
		String[] objects = this.stringConfig.split("|");
		for(String o : objects){
			Etapa etapa = new Etapa();
			etapa.setIndice(Integer.parseInt(o.substring(1, 2)));
		}
		return map;
	}
	
	@Test
	public void teste(){
		String s = "1{kleber,gomes,ilario};2{vander,ferreira,ilario};";
		String[] objects = s.split(";");
		for(String o : objects){
			String[] x = o.split(",");
			for(String y : x){
				System.out.println(y);
			}
		}
	}

	public void setConfiguracao(Map<Etapa, Collection<Disciplina>> configuracao) {
		if(configuracao==null) return;
		String stringConfig = "";
		int indexEtapa = 0;
		for(Etapa etapa : configuracao.keySet()){
			indexEtapa++;
			stringConfig+= indexEtapa;
			Collection<Disciplina> listaDisciplina = configuracao.get(etapa);
			for(Disciplina disciplina : listaDisciplina){
				stringConfig+= "{" + disciplina.getNomeDisciplina() + ",}";
			}
			stringConfig+="|";
		}
		this.stringConfig = stringConfig;
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
