package com.breakfast.tomorrow.core.academic.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import com.breakfast.tomorrow.core.database.IdNode;
import com.breakfast.tomorrow.core.database.IndexNode;

public class Instituicao implements Serializable {


	@IdNode private long id;
	@IndexNode private String nomeInstituicao;
	private Collection<Unidade> unidades = new ArrayList<Unidade>();
	
	public Instituicao(){}
	
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

	public Collection<Unidade> getUnidades() {
		return unidades;
	}

	public void setUnidades(Collection<Unidade> unidades) {
		this.unidades = unidades;
	}
	
	@Override
	public boolean equals(Object o) {
		if( !(o instanceof Instituicao)) return false;
		return ((Instituicao) o).getId() == this.getId();
	}
	
	@Override
	public int hashCode() {
		return getNomeInstituicao().length();
	}

	private static final long serialVersionUID = 1L;
	
}
