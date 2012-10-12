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

import com.breakfast.tomorrow.core.academic.vo.Aula;
import com.breakfast.tomorrow.core.database.DataBase;
import com.breakfast.tomorrow.core.database.EntityRelashionship;
import com.breakfast.tomorrow.core.database.NodeRepositoryManager;

public class AulaRepository extends NodeRepositoryManager<Aula>{
	
	public Collection<Aula> getAulas() {
		Iterator<Node> nodeIterator = DataBase.get().getReferenceNode().traverse(Traverser.Order.DEPTH_FIRST,
			StopEvaluator.DEPTH_ONE,
			ReturnableEvaluator.ALL_BUT_START_NODE,
			EntityRelashionship.AULAS, Direction.OUTGOING).iterator();
		List<Aula> lista = new ArrayList<Aula>();
		while(nodeIterator.hasNext()){
			lista.add(get(nodeIterator.next(), Aula.class));
		}
		
		return lista;
}
	
	public Aula getAulaPorId(long id) {
		return getNodeEntityById(id, Aula.class);
	}

}
