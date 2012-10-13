package com.breakfast.tomorrow.core.academic.vo;

import java.io.Serializable;
import java.util.Collection;

import com.breakfast.tomorrow.core.database.IdNode;
import com.breakfast.tomorrow.core.database.IndexNode;

public class InstituicaoEducacional implements Serializable {


	@IdNode private long id;
	@IndexNode private String nomeInstituicao;
	private Collection<UnidadeEducacional> unidades;
	
	public InstituicaoEducacional(){}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNomeInstituicao() {
		return nomeInstituicao;
	}

	public void setNomeInstituicao(String nomeInstituicao){
		this.nomeInstituicao = nomeInstituicao;
	}

	public Collection<UnidadeEducacional> getUnidades() {
		return unidades;
	}

	public void setUnidades(Collection<UnidadeEducacional> unidades) {
		this.unidades = unidades;
	}

	private static final long serialVersionUID = 1L;
	
}
