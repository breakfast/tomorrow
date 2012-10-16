package com.breakfast.tomorrow.core.academic.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeSet;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.ReturnableEvaluator;
import org.neo4j.graphdb.StopEvaluator;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.Traverser;

import com.breakfast.tomorrow.core.academic.vo.Curso;
import com.breakfast.tomorrow.core.academic.vo.Instituicao;
import com.breakfast.tomorrow.core.academic.vo.Unidade;
import com.breakfast.tomorrow.core.database.DataBase;
import com.breakfast.tomorrow.core.database.EntityRelashionship;
import com.breakfast.tomorrow.core.database.NodeRepositoryManager;
import com.breakfast.tomorrow.core.database.RepositoryException;

public class UnidadeRepository extends NodeRepositoryManager<Unidade>{

	
	@Override
	public void persistir(Unidade nodeEntity) {
		super.persistir(nodeEntity);
		super.createEntityRelationship(nodeEntity, EntityRelashionship.UNIDADES);
	}
	
	public Unidade getUnidadePorId(long id){
		Unidade unidade = getNodeEntityByIndex("id", id, Unidade.class);
		if(unidade!=null){
			unidade.setInstituicao(getInstituicao(unidade));
			unidade.setCursos(getCursos(unidade));
		}
		return unidade;
	}
	
	public Collection<Unidade> getUnidades(){
		Iterator<Node> nodeIterator = DataBase.get().getReferenceNode().traverse(
				Traverser.Order.DEPTH_FIRST,
				StopEvaluator.DEPTH_ONE,
				ReturnableEvaluator.ALL_BUT_START_NODE,
				EntityRelashionship.UNIDADES,
				Direction.OUTGOING).iterator();
		Collection<Unidade> lista = new ArrayList<Unidade>();
		while(nodeIterator.hasNext()){
			lista.add(get(nodeIterator.next(), Unidade.class));
		}
		return lista;
	}
	
	
	public Collection<Curso> getCursos(Unidade unidade){
		Node unidade_ = getNode("id", unidade.getId(), Unidade.class);
		Iterator<Node> it = unidade_.traverse(
				Traverser.Order.DEPTH_FIRST,
				StopEvaluator.DEPTH_ONE,
				ReturnableEvaluator.ALL_BUT_START_NODE,
				Relacionamento.TEM,
				Direction.OUTGOING).iterator();
		Collection<Curso> colecao = new ArrayList<Curso>();
		while(it.hasNext()){
			colecao.add(get(it.next(), Curso.class));
		}
		return null;
	}
	
	public void setCursos(Unidade unidade, Collection<Curso> cursos){
		CursoRepository repositorioCurso = new CursoRepository();
		Collection<Curso> persistidas = getCursos(unidade);
		Collection<Curso> paraPersistir = new TreeSet<Curso>(cursos);
		Collection<Curso> paraRemover = new TreeSet<Curso>(persistidas);
		paraPersistir.removeAll(persistidas);
		paraRemover.removeAll(cursos);
		Transaction tx = DataBase.get().beginTx();
		Node unidade_ = getNode("id", unidade.getId(), Instituicao.class);
		try{
			for(Curso curso : paraPersistir){
				repositorioCurso.persistir(curso);
				Node curso_ = getNode("id", curso.getId(), Curso.class);
				unidade_.createRelationshipTo(curso_, Relacionamento.TEM);
			}
			for(Curso curso : paraRemover){
				Node curso_ = getNode("id", curso.getId(), Curso.class);
				curso_.delete();
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
	
	public Instituicao getInstituicao(Unidade unidade){
		Node unidade_ = getNode("id", unidade.getId(), Unidade.class);
		Iterator<Node> it = unidade_.traverse(
				Traverser.Order.DEPTH_FIRST,
				StopEvaluator.DEPTH_ONE,
				ReturnableEvaluator.ALL_BUT_START_NODE,
				Relacionamento.TEM,
				Direction.INCOMING).iterator();
		while(it.hasNext()){
			return get(it.next(), Instituicao.class);
		}
		return null;
	}
	
}
