package com.breakfast.tomorrow.core.academic.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.ReturnableEvaluator;
import org.neo4j.graphdb.StopEvaluator;
import org.neo4j.graphdb.Traverser;

import com.breakfast.tomorrow.core.academic.vo.Diario;

import com.breakfast.tomorrow.core.database.DataBase;
import com.breakfast.tomorrow.core.database.EntityRelashionship;
import com.breakfast.tomorrow.core.database.NodeRepositoryManager;
import com.breakfast.tomorrow.core.database.RepositoryException;

public class DiarioRepository  extends NodeRepositoryManager<Diario>{
	
	
	public Diario getDiarioPorId(long id){
		return getNodeEntityById(id, Diario.class);
	}
	
    public Collection<Diario> getDiarios(){
			Iterator<Node> nodeIterator = DataBase.get().getReferenceNode().traverse(Traverser.Order.DEPTH_FIRST,
					  StopEvaluator.DEPTH_ONE,
					  ReturnableEvaluator.ALL_BUT_START_NODE,
					  EntityRelashionship.DIARIOS,
					  Direction.OUTGOING).iterator();
			List<Diario> lista = new ArrayList<Diario>();
			while(nodeIterator.hasNext()){
				lista.add(get(nodeIterator.next(), Diario.class));
			}
			return lista;
    }
    
    public void persist(Diario diario) throws RepositoryException {
		super.persistir(diario); 
		createEntityRelationship(diario, EntityRelashionship.DIARIOS);
	}
	
	
	
    
    

}
 