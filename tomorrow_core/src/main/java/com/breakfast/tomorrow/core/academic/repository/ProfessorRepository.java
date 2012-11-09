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
import com.breakfast.tomorrow.core.academic.vo.Professor;
import com.breakfast.tomorrow.core.database.DataBase;
import com.breakfast.tomorrow.core.database.EntityRelashionship;
import com.breakfast.tomorrow.core.database.NodeRepositoryManager;

public class ProfessorRepository extends NodeRepositoryManager<Professor>{
	
	public Professor getProfessorPorId(long id){
		Node node = getNode("id", id, Professor.class);
		return carregar(node);
	}
	
	public Professor carregar(Node node){
		Professor professor = get(node,Professor.class);
		return professor;
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
	
	public Collection<Disciplina> getDisciplinas(Professor professor){
		DisciplinaRepository repo = new DisciplinaRepository();
		Node node = getNode("id", professor.getId(), Professor.class);
		Iterator<Node> it = node.traverse(Traverser.Order.DEPTH_FIRST,
						StopEvaluator.DEPTH_ONE,
						ReturnableEvaluator.ALL_BUT_START_NODE,
						Relacionamento.TEM, Direction.INCOMING)
				.iterator();
		Collection<Disciplina> colecao = new ArrayList<Disciplina>();
		while(it.hasNext()){
			Disciplina disciplina = repo.carregar(it.next());
			disciplina.setProfessor(professor);
			colecao.add(disciplina);
		}
		return colecao;
	}
	

}
