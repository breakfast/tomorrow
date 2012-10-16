package com.breakfast.tomorrow.core.academic.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import com.breakfast.tomorrow.core.database.IdNode;
import com.breakfast.tomorrow.core.database.FieldNode;
import com.breakfast.tomorrow.core.database.IndexNode;

public class Unidade implements Serializable{
	
	
	@IdNode private long id;
	@IndexNode private String nomeUnidade;
	@FieldNode private String local;
	private Instituicao instituicao;
	private Collection<Curso> cursos = new ArrayList<Curso>();
	
	public Unidade() {}

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
	
	public Instituicao getInstituicao() {
		return instituicao;
	}

	public void setInstituicao(Instituicao instituicao) {
		this.instituicao = instituicao;
	}
	
	@Override
	public boolean equals(Object o) {
		if( !(o instanceof Unidade)) return false;
		return ((Unidade) o).getId() == this.getId();
	}
	
	@Override
	public int hashCode() {
		return getNomeUnidade().length();
	}

	private static final long serialVersionUID = 1L;
}
