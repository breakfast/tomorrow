package com.breakfast.tomorrow.core.academic.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.ReturnableEvaluator;
import org.neo4j.graphdb.StopEvaluator;
import org.neo4j.graphdb.Traverser;

import com.breakfast.tomorrow.core.academic.vo.Turma;
import com.breakfast.tomorrow.core.database.DataBase;
import com.breakfast.tomorrow.core.database.EntityRelashionship;
import com.breakfast.tomorrow.core.database.NodeRepositoryManager;

public class TurmaRepository extends NodeRepositoryManager<Turma> {
	
	public Collection<Turma> getTurmas(){
		Iterator<Node> nodeIterator = DataBase.get().getReferenceNode().traverse(Traverser.Order.DEPTH_FIRST,
						  StopEvaluator.DEPTH_ONE,
						  ReturnableEvaluator.ALL_BUT_START_NODE,
						  EntityRelashionship.TURMA,
						  Direction.OUTGOING).iterator();
		Collection<Turma> lista = new ArrayList<Turma>();
		while(nodeIterator.hasNext()){
			lista.add(get(nodeIterator.next(), Turma.class));
		}
		return lista;
	}
	
	public Turma getTurmaPorId(long id){
		return getNodeEntityByIndex("id", id, Turma.class);
	}

}
