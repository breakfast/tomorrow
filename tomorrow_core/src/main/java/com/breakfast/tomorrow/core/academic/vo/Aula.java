package com.breakfast.tomorrow.core.academic.vo;

import java.util.Date;

import com.breakfast.tomorrow.core.database.FieldNode;
import com.breakfast.tomorrow.core.database.IdNode;
import com.breakfast.tomorrow.core.database.IndexNode;

public class Aula{
	
	/**
	 * fields of class
	 */
	@IdNode private long id;
	@FieldNode private long data;
	@IndexNode private String descricao;
	@FieldNode private short qtdeAulas;

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
		date.setTime(this.data);
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

	@Override
	public String toString() {
		return "[" + this.getId() + "]" + this.getDescricao();
	}

}
