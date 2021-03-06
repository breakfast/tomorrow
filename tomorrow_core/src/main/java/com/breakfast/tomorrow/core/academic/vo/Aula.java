package com.breakfast.tomorrow.core.academic.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.breakfast.tomorrow.core.database.FieldNode;
import com.breakfast.tomorrow.core.database.IdNode;
import com.breakfast.tomorrow.core.database.IndexNode;

public class Aula implements Serializable{
	

	
	/**
	 * fields of class
	 */
	@IdNode private long id;
	@FieldNode private long data;
	@IndexNode private String descricao;
	@FieldNode private short qtdeAulas = 1;
	private Collection<Aluno> faltantes = new ArrayList<Aluno>();

	/**
	 * Default Constructor for Aula
	 */
	public Aula() {}
	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getData() {
		Date date = new Date();
		date.setTime(this.data == 0 ? new Date().getTime() : this.data);
		return date;
	}

	public void setData(Date data) {
		this.data = data.getTime();

	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public short getQtdeAulas() {
		return qtdeAulas;
	}

	public void setQtdeAulas(short qtdeAulas) {
		this.qtdeAulas = qtdeAulas;
	}

	public Collection<Aluno> getFaltantes() {
		return faltantes;
	}


	public void setFaltantes(Collection<Aluno> faltantes) {
		this.faltantes = faltantes;
	}


	@Override
	public String toString() {
		return "[" + this.getId() + "]" + this.getDescricao();
	}
	
	private static final long serialVersionUID = 1L;

}
