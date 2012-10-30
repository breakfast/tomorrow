package com.breakfast.tomorrow.core.academic.repository;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.ReturnableEvaluator;
import org.neo4j.graphdb.StopEvaluator;
import org.neo4j.graphdb.Traverser;
import com.breakfast.tomorrow.core.academic.vo.Professor;
import com.breakfast.tomorrow.core.database.DataBase;
import com.breakfast.tomorrow.core.database.EntityRelashionship;
import com.breakfast.tomorrow.core.database.NodeRepositoryManager;

public class ProfessorRepository extends NodeRepositoryManager<Professor>{
	
	public Professor getProfessorPorId(long id){
		return getNodeEntityByIndex("id", id, Professor.class);		
	}
	
	@Override
	public void persistir(Professor nodeEntity) {
		super.persistir(nodeEntity);
		super.createEntityRelationship(nodeEntity, EntityRelashionship.PROFESSORES);
	}
	
	public Collection<Professor> getProfessores() {
		Iterator<Node> nodeIterator = DataBase.get()
					.getReferenceNode()
					.traverse(Traverser.Order.DEPTH_FIRST,
							StopEvaluator.DEPTH_ONE,
							ReturnableEvaluator.ALL_BUT_START_NODE,
							EntityRelashionship.PROFESSORES, Direction.OUTGOING)
					.iterator();
		Collection<Professor> lista = new ArrayList<Professor>();
		while(nodeIterator.hasNext()){
			lista.add(get(nodeIterator.next(), Professor.class));
		}
		return lista;
	}
	

}
