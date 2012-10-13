package com.breakfast.tomorrow.core.academic.vo;




import com.breakfast.tomorrow.core.database.IdNode;



public class Diario {
	
	/**
	 * Default Constructor for Curso
	 */
	public Diario(){}

	@IdNode private long id;
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}	

}
