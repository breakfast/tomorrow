package com.breakfast.tomorrow.core.database;

import java.lang.reflect.Field;

import org.neo4j.graphdb.Node;

public abstract class NodeEntity{
	
	public NodeEntity(){}

	public NodeEntity(Node node){
		this.node = node;
	}
	
	@IndexNode protected long id;
	protected Node node;
	
	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}

	public long getId() {
		this.id = (Long) getProperty("id");
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	protected Object getProperty(Object attribute){
		return node != null ? node.getProperty("") : attribute;  
		//this.idCurso = node != null ? ((Long) node.getProperty(ID_CURSO)).longValue() : this.idCurso;
		
	}
	
	
	protected Object getProperty(String attributeName){
		Object attribute = null;
		try{
			Field field = this.getClass().getField(attributeName);
			attribute = field.get(this);
		}
		catch(Exception e){
			
		}
		return node != null ? node.getProperty(attributeName) : attribute;  
		//this.idCurso = node != null ? ((Long) node.getProperty(ID_CURSO)).longValue() : this.idCurso;
		
	}
	
	@Override
	public String toString() {
		return Utils.toStringObject(this, this.getClass());
	}
	
	
}