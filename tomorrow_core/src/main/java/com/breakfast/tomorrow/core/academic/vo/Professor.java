package com.breakfast.tomorrow.core.academic.vo;

import java.io.Serializable;

public class Professor extends Pessoa implements Serializable {
	
	public Professor(){}
	
	private static final long serialVersionUID = 1L;
	
	public String getStringId(){
		return getId() != null ? getId().toString() : "";
	}
	
	public void setStringId(String id){
		this.setId(id != null ? Long.valueOf(id) : 0);
	}
}
