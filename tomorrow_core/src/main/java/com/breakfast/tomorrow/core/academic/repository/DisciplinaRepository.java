package com.breakfast.tomorrow.core.academic.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.ReturnableEvaluator;
import org.neo4j.graphdb.StopEvaluator;
import org.neo4j.graphdb.Traverser;

import com.breakfast.tomorrow.core.academic.vo.Disciplina;
import com.breakfast.tomorrow.core.database.DataBase;
import com.breakfast.tomorrow.core.database.EntityRelashionship;
import com.breakfast.tomorrow.core.database.NodeRepositoryManager;

public class DisciplinaRepository extends NodeRepositoryManager<Disciplina> {
	
	public Collection<Disciplina> getDisciplinas() {
		Iterator<Node> nodeIterator = DataBase.get()
					.getReferenceNode()
					.traverse(Traverser.Order.DEPTH_FIRST,
							StopEvaluator.DEPTH_ONE,
							ReturnableEvaluator.ALL_BUT_START_NODE,
							EntityRelashionship.DISCIPLINAS, Direction.OUTGOING)
					.iterator();
		Collection<Disciplina> lista = new ArrayList<Disciplina>();
		while(nodeIterator.hasNext()){
			lista.add(get(nodeIterator.next(), Disciplina.class));
		}
		return lista;
	}
	
	public Disciplina getDisciplinaPorId(long id){
		return getNodeEntityByIndex("id", id, Disciplina.class);
	}

}
