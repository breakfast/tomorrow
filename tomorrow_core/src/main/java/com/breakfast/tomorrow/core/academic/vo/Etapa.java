package com.breakfast.tomorrow.core.academic.vo;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

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
	private Collection<Disciplina> diciplinas;
	private Turma turma;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public Collection<Disciplina> getDiciplinas() {
		return diciplinas;
	}

	public void setDiciplinas(Collection<Disciplina> diciplinas) {
		this.diciplinas = diciplinas;
	}
	
	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}
	
	@Override
	public boolean equals(Object o) {
		if( !(o instanceof Etapa)) return false;
		return getId() != 0 ? ((Etapa) o).getId().equals(this.getId()) : super.equals(o);
	}
	
	@Override
	public int hashCode() {
		return id != 0 ? getId().intValue() : super.hashCode();
	}
	
	@Override
	public String toString() {
		return "[" + this.getId()+ "]" + this.getNomeEtapa();
	}
	
	private static final long serialVersionUID = 1L;
	

}
