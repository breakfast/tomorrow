package com.breakfast.tomorrow.core.academic.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.breakfast.tomorrow.core.database.IdNode;
import com.breakfast.tomorrow.core.database.FieldNode;
import com.breakfast.tomorrow.core.database.IndexNode;

public class Etapa implements Serializable{

	public Etapa() {}

	/**
	 * fields of class
	 */
	@IdNode private long id;
	@IndexNode private String nomeEtapa;
	@FieldNode private Date inicioEtapa;
	@FieldNode private Date fimEtapa;
	@FieldNode private int indice;
	private List<Disciplina> diciplinas;
	private Turma turma;
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNomeEtapa(){
		return this.nomeEtapa ;
	}
	public void setNomeEtapa(String nomeEtapa){
		this.nomeEtapa = nomeEtapa ;
	}
	
	public Date getInicioEtapa(){
		return this.inicioEtapa ;
	}
	
	public void setInicioEtapa(Date inicioEtapa){
		this.inicioEtapa = inicioEtapa;
				
	}
	
	public Date getFimEtapa(){
		return this.fimEtapa;
	}
	
	public void setFimEtapa(Date fimEtapa){
		this.fimEtapa = fimEtapa;
				
	}
	
	public int getIndice() {
		return this.indice;
	}


	public void setIndice(int indice) {
		this.indice = indice;
	}

	public List<Disciplina> getDiciplinas() {
		return diciplinas;
	}

	public void setDiciplinas(List<Disciplina> diciplinas) {
		this.diciplinas = diciplinas;
	}
	
	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}
	/*
	@Override
	protected Object clone(){
		Etapa etapa = new Etapa();
		etapa.setIndice(this.getIndice());
		etapa.setInicioEtapa(this.getInicioEtapa());
		etapa.setFimEtapa(this.getFimEtapa());
		etapa.setNomeEtapa(this.getNomeEtapa());
		return etapa;
	}
	*/
	private static final long serialVersionUID = 1L;

}
