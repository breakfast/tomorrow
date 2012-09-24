package com.breakfast.tomorrow.core.academic;

import java.util.Iterator;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.ReturnableEvaluator;
import org.neo4j.graphdb.StopEvaluator;
import org.neo4j.graphdb.Traverser;

import com.breakfast.tomorrow.core.database.DataBase;
import com.breakfast.tomorrow.core.database.DataBaseException;
import com.breakfast.tomorrow.core.database.EntityRelashionship;
import com.breakfast.tomorrow.core.database.NodeEntity;
import com.breakfast.tomorrow.core.database.NodeEntityManager;

public class Diario extends NodeEntity{

	private static NodeEntityManager<Diario> manager = new NodeEntityManager<Diario>();
	
	/**
	 * Default Constructor for Curso
	 */
	public Diario(){}

	
	public Diario (Node node){
		super(node);
	}

		
	public static void persist(Diario diario) throws DataBaseException {
		manager.persistir(diario); 
		manager.createEntityRelationship(diario, EntityRelashionship.DIARIOS);
	}
	
	
	public static void delete(Diario diario) throws DataBaseException{
		manager.delete(diario);
	}
	

	public static Diario getDiarioPorId(long id){
		return manager.getNodeEntityById(id, Diario.class);
	}
	
    public static Iterator<Diario> getDiarios(){
		
		Iterator<Diario> iterator = new Iterator<Diario>() {
		
		public final Iterator<Node> nodeIterator = DataBase.get().getReferenceNode().traverse(Traverser.Order.DEPTH_FIRST,
				  StopEvaluator.DEPTH_ONE,
				  ReturnableEvaluator.ALL_BUT_START_NODE,
				  EntityRelashionship.DIARIOS,
				  Direction.OUTGOING).iterator();

		@Override
		public boolean hasNext() {
			return nodeIterator.hasNext();
		}

		@Override
		public Diario next() {
			Node nextNode = nodeIterator.next();
			return new Diario(nextNode);
		}

		@Override
		public void remove() { 
			nodeIterator.remove();
		}

		
		};
		return iterator;
	}

}
