package com.breakfast.tomorrow.core.academic.vo;

import java.io.Serializable;
import java.util.Collection;


public class Aluno extends Pessoa implements Serializable {
	
	private Etapa etapaCursando;
	private Collection<Disciplina> dependencias;
	private Collection<Responsavel> responsaveis;
	
	public Aluno(){}

	
	public void matricular(Etapa etapa){
		this.etapaCursando = etapa;
	}

	public Etapa getEtapaCursando() {
		return etapaCursando;
	}
	
	public Collection<Disciplina> getDependencias() {
		return dependencias;
	}


	public void setDependencias(Collection<Disciplina> dependencias) {
		this.dependencias = dependencias;
	}

	public Collection<Responsavel> getResponsaveis() {
		return responsaveis;
	}

	public void setResponsaveis(Collection<Responsavel> responsaveis) {
		this.responsaveis = responsaveis;
	}
	
	public String getStringId(){
		return getId() != null ? getId().toString() : "";
	}
	
	public void setStringId(String id){
		this.setId(id != null ? Long.valueOf(id) : 0);
	}
	
	private static final long serialVersionUID = 1L;

}
