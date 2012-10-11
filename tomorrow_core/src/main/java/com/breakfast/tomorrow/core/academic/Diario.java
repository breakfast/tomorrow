package com.breakfast.tomorrow.core.academic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.ReturnableEvaluator;
import org.neo4j.graphdb.StopEvaluator;
import org.neo4j.graphdb.Traverser;

import com.breakfast.tomorrow.core.database.DataBase;
import com.breakfast.tomorrow.core.database.IdNode;
import com.breakfast.tomorrow.core.database.NodeRepository;
import com.breakfast.tomorrow.core.database.NodeRepositoryManager;
import com.breakfast.tomorrow.core.database.RepositoryException;
import com.breakfast.tomorrow.core.database.EntityRelashionship;

public class Diario implements NodeRepository{

	private static NodeRepositoryManager<Diario> manager = new NodeRepositoryManager<Diario>();
	
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


	public static void persist(Diario diario) throws RepositoryException {
		manager.persistir(diario); 
		manager.createEntityRelationship(diario, EntityRelashionship.DIARIOS);
	}
	
	
	public static void delete(Diario diario) throws RepositoryException{
		manager.delete(diario);
	}
	

	public static Diario getDiarioPorId(long id){
		return manager.getNodeEntityById(id, Diario.class);
	}
	
    public static Collection<Diario> getDiarios(){
		Iterator<Node> nodeIterator = DataBase.get().getReferenceNode().traverse(Traverser.Order.DEPTH_FIRST,
				  StopEvaluator.DEPTH_ONE,
				  ReturnableEvaluator.ALL_BUT_START_NODE,
				  EntityRelashionship.DIARIOS,
				  Direction.OUTGOING).iterator();
		List<Diario> lista = new ArrayList<Diario>();
		while(nodeIterator.hasNext()){
			lista.add(manager.get(nodeIterator.next(), Diario.class));
		}
		return lista;
	}

}
