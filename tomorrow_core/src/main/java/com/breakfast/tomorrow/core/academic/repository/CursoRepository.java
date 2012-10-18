package com.breakfast.tomorrow.core.academic.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.ReturnableEvaluator;
import org.neo4j.graphdb.StopEvaluator;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.Traverser;

import com.breakfast.tomorrow.core.academic.vo.Curso;
import com.breakfast.tomorrow.core.academic.vo.Turma;
import com.breakfast.tomorrow.core.academic.vo.Unidade;
import com.breakfast.tomorrow.core.database.DataBase;
import com.breakfast.tomorrow.core.database.EntityRelashionship;
import com.breakfast.tomorrow.core.database.NodeRepositoryManager;
import com.breakfast.tomorrow.core.database.RepositoryException;

public class CursoRepository extends NodeRepositoryManager<Curso>{
	
	
	@Override
	public void persistir(Curso nodeEntity){
		super.persistir(nodeEntity);
		super.createEntityRelationship(nodeEntity, EntityRelashionship.CURSOS);
	}
	
	public Curso getCursoPorId(long id){
		Curso curso = getNodeEntityByIndex("id", id, Curso.class);
		if(curso!=null){
			curso.setUnidadeEducacional(getUnidade(curso));
			curso.setTurmas(getTurmas(curso));
		}
		return curso;
	}

	public Collection<Curso> getCursos(){
		Iterator<Node> nodeIterator = DataBase.get().getReferenceNode().traverse(Traverser.Order.DEPTH_FIRST,
				  StopEvaluator.DEPTH_ONE,
				  ReturnableEvaluator.ALL_BUT_START_NODE,
				  EntityRelashionship.CURSOS,
				  Direction.OUTGOING).iterator();
		List<Curso> lista = new ArrayList<Curso>();
		while(nodeIterator.hasNext()){
			get(nodeIterator.next(), Curso.class);
		}
		return lista;
	}
	
	public Collection<Turma> getTurmas(Curso curso){
		Node curso_ = getNode("id", curso.getId(), Curso.class);
		Iterator<Node> it = curso_.traverse(
				Traverser.Order.DEPTH_FIRST,
				StopEvaluator.DEPTH_ONE,
				ReturnableEvaluator.ALL_BUT_START_NODE,
				Relacionamento.TEM,
				Direction.OUTGOING).iterator();
		Collection<Turma> colecao = new ArrayList<Turma>();
		while(it.hasNext()){
			colecao.add(get(it.next(), Turma.class));
		}
		return null;
		
	}
	
	
	
	public void setTurmas(Curso curso, Collection<Turma> turmas){
		TurmaRepository repositorioTurma = new TurmaRepository();
		Collection<Turma> persistidas = getTurmas(curso);
		Collection<Turma> paraPersistir = new TreeSet<Turma>(turmas);
		Collection<Turma> paraRemover = new TreeSet<Turma>(persistidas);
		paraPersistir.removeAll(persistidas);
		paraRemover.removeAll(turmas);
		Transaction tx = DataBase.get().beginTx();
		Node curso_ = getNode("id", curso.getId(), Curso.class);
		try{
			for(Turma turma : paraPersistir){
				repositorioTurma.persistir(turma);
				Node turma_ = getNode("id", turma.getId(), Turma.class);
				curso_.createRelationshipTo(turma_, Relacionamento.TEM);
			}
			for(Turma turma : paraRemover){
				Node turma_ = getNode("id", turma.getId(), Turma.class);
				turma_.delete();
			}
			tx.success();
		} 
		catch(Exception e){
			tx.failure();
			new RepositoryException(e);
		}
		finally{
			tx.finish();
		}
		
	}
	
	
	public Unidade getUnidade(Curso curso){
		Node curso_ = getNode("id", curso.getId(), Curso.class);
		Iterator<Node> it = curso_.traverse(
				Traverser.Order.DEPTH_FIRST,
				StopEvaluator.DEPTH_ONE,
				ReturnableEvaluator.ALL_BUT_START_NODE,
				Relacionamento.TEM,
				Direction.INCOMING).iterator();
		while(it.hasNext()){
			return get(it.next(), Unidade.class);
		}
		return null;
	}

}
