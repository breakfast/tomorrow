package com.breakfast.tomorrow.core.database;

import java.util.Iterator;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.DynamicRelationshipType;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.NotFoundException;
import org.neo4j.graphdb.ReturnableEvaluator;
import org.neo4j.graphdb.StopEvaluator;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.Traverser.Order;

/**
 * Classe responsavel pela propiedades das entidades
 * ex: count, sum, ids
 * @author Kleber Ilario
 *
 */

public abstract class EntityProperties {
	
	private static String PROPERTY_RELATIONSHIP = "PROPERTY";
	public static String COUNT = "count";
	
	public synchronized static long getID(Class<?> model){ 
		
		String propertyName = COUNT + model.getSimpleName();
		Node nodeRef = DataBase.get().getReferenceNode();
		Node node = null;
		Iterator<Node> it = nodeRef.traverse(Order.DEPTH_FIRST, 
									 StopEvaluator.DEPTH_ONE, 
									 ReturnableEvaluator.ALL_BUT_START_NODE, 
									 DynamicRelationshipType.withName(PROPERTY_RELATIONSHIP), 
									 Direction.OUTGOING).iterator();
		while(it.hasNext()){
			node = it.next();
			break;
		}
		
		if(node==null){
			node = DataBase.get().createNode();
			nodeRef.createRelationshipTo(node, DynamicRelationshipType.withName(PROPERTY_RELATIONSHIP));
		}
		Long id = 1l;
		Transaction tx = DataBase.get().beginTx();
		try{
			id = (Long) node.getProperty(propertyName);
			id++;
			node.setProperty(propertyName, id);
			tx.success();
		}
		catch(NotFoundException e){
			node.setProperty(propertyName, 1l);
			tx.success();
		}
		finally{
			tx.finish();
		}
		return id;
	}

}
