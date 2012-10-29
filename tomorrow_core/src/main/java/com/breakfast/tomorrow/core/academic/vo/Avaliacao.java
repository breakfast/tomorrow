package com.breakfast.tomorrow.core.academic.vo;

import java.io.Serializable;

import com.breakfast.tomorrow.core.database.FieldNode;
import com.breakfast.tomorrow.core.database.IdNode;

public class Avaliacao implements Serializable{

	
	/**
	 * fields of class
	 */ 
	@IdNode private long id;
	@FieldNode private int peso;
	@FieldNode private String descricao;

	
	/**
	 * Default Constructor for 
	 */
	public Avaliacao(){}

	public long getId(){
		return id;
	}
	
	public void setId(long id){
		this.id = id;
	}
	
	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@Override
	public String toString() {
		return "[" + this.getId() + "]" + this.getDescricao();
	}

	private static final long serialVersionUID = 1L;
}


