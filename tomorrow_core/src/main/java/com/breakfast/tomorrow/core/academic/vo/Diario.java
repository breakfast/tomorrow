package com.breakfast.tomorrow.core.academic.vo;

import java.io.Serializable;
import java.util.Collection;
import com.breakfast.tomorrow.core.database.IdNode;

public class Diario implements Serializable{


	public Diario(){}

	@IdNode private long id;
	Collection<Aula> aulas;
	Collection<Avaliacao> avaliacoes;
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}	
	
	private static final long serialVersionUID = 1L;
}
