package com.breakfast.tomorrow.core.database;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.NotFoundException;

/**
 * Classe responsavel pela propiedades das entidades
 * ex: count, sum, ids
 * @author Kleber Ilario
 *
 */
public abstract class EntityProperties {
	
	public static String COUNT = "count";
	
	public static long getID(Class<?> model){
		Node refNode = DataBase.get().getReferenceNode();
		Long id = 1l;
		try{
			id = (Long) refNode.getProperty(COUNT + model.getSimpleName());
			id++;
			refNode.setProperty(COUNT + model.getSimpleName(), id);
		}
		catch(NotFoundException e){
			refNode.setProperty(COUNT + model.getSimpleName(), 1l);
		}
		return id;
	}

}
