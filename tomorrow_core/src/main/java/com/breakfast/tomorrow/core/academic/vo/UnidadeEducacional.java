package com.breakfast.tomorrow.core.academic.vo;

import java.io.Serializable;
import java.util.Collection;

import com.breakfast.tomorrow.core.database.IdNode;
import com.breakfast.tomorrow.core.database.FieldNode;
import com.breakfast.tomorrow.core.database.IndexNode;

public class UnidadeEducacional implements Serializable{
	
	
	@IdNode private long id;
	@IndexNode private String nomeUnidade;
	@FieldNode private String local;
	private Collection<Curso> cursos;
	private InstituicaoEducacional instituicaoEducacional;
	
	public UnidadeEducacional() {}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNomeUnidade() {
		return nomeUnidade;
	}

	public void setNomeUnidade(String nomeUnidade) {
		this.nomeUnidade = nomeUnidade;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}
	
	public Collection<Curso> getCursos() {
		return cursos;
	}

	public void setCursos(Collection<Curso> cursos) {
		this.cursos = cursos;
	}
	
	public InstituicaoEducacional getInstituicaoEducacional() {
		return instituicaoEducacional;
	}

	public void setInstituicaoEducacional(InstituicaoEducacional instituicaoEducacional) {
		this.instituicaoEducacional = instituicaoEducacional;
	}



	private static final long serialVersionUID = 1L;
}
