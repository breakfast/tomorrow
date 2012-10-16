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

import com.breakfast.tomorrow.core.academic.vo.Aluno;
import com.breakfast.tomorrow.core.database.DataBase;
import com.breakfast.tomorrow.core.database.EntityRelashionship;
import com.breakfast.tomorrow.core.database.NodeRepositoryManager;


public class AlunoRepository extends NodeRepositoryManager<Aluno>{
	
	@Override
	public void persistir(Aluno nodeEntity) {
		super.persistir(nodeEntity);
		super.createEntityRelationship(nodeEntity, EntityRelashionship.ALUNOS);
	}
	
	public Aluno getAlunoPorId(long id){
		Aluno aluno = getNodeEntityByIndex("id", id, Aluno.class);
		return aluno;
	}
	
	public Collection<Aluno> getAlunos(){
		Iterator<Node> nodeIterator = DataBase.get().getReferenceNode().traverse(
				Traverser.Order.DEPTH_FIRST,
				StopEvaluator.DEPTH_ONE,
				ReturnableEvaluator.ALL_BUT_START_NODE,
				EntityRelashionship.AVALIACOES,
				Direction.OUTGOING).iterator();
		List<Aluno> lista = new ArrayList<Aluno>();
		while(nodeIterator.hasNext()){
			lista.add(this.get(nodeIterator.next(), Aluno.class));
		}
		return lista;
	}
	
}
