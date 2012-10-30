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
import com.breakfast.tomorrow.core.academic.vo.Etapa;
import com.breakfast.tomorrow.core.academic.vo.Turma;
import com.breakfast.tomorrow.core.database.DataBase;
import com.breakfast.tomorrow.core.database.EntityRelashionship;
import com.breakfast.tomorrow.core.database.NodeRepositoryManager;
import com.breakfast.tomorrow.core.database.RepositoryException;

public class TurmaRepository extends NodeRepositoryManager<Turma> {
	
	@Override
	public void persistir(Turma nodeEntity){
		super.persistir(nodeEntity);
		super.createEntityRelationship(nodeEntity, EntityRelashionship.TURMA);
	}
	
	public Turma getTurmaPorId(long id){
		Turma turma = getNodeEntityByIndex("id", id, Turma.class);
		if(turma!=null){
			turma.setCurso(getCurso(turma));
			turma.setEtapas(getEtapas(turma));		
		}
		return turma;
	}
		
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
	
	public Collection<Etapa> getEtapas(Turma turma){
		Node turma_ = getNode("id", turma.getId(), Turma.class);
		Iterator<Node> it = turma_.traverse(
				Traverser.Order.DEPTH_FIRST,
				StopEvaluator.DEPTH_ONE,
				ReturnableEvaluator.ALL_BUT_START_NODE,
				Relacionamento.TEM,
				Direction.OUTGOING).iterator();
		Collection<Etapa> colecao = new ArrayList<Etapa>();
		while(it.hasNext()){
			colecao.add(get(it.next(), Etapa.class));
		}
		return colecao;
		
	}
	
	public void setEtapas(Turma turma, Collection<Etapa> etapas){
		EtapaRepository repositorioEtapa = new EtapaRepository();
		Collection<Etapa> persistidas = getEtapas(turma);
		Collection<Etapa> paraPersistir = new TreeSet<Etapa>(etapas);
		Collection<Etapa> paraRemover = new TreeSet<Etapa>(persistidas);
		paraPersistir.removeAll(persistidas);
		paraRemover.removeAll(etapas);
		Transaction tx = DataBase.get().beginTx();
		Node turma_ = getNode("id", turma.getId(), Turma.class);
		try{
			for(Etapa etapa : paraPersistir){
				repositorioEtapa.persistir(etapa);
				Node etapa_ = getNode("id", etapa.getId(), Etapa.class);
				turma_.createRelationshipTo(etapa_, Relacionamento.TEM);
			}
			for(Etapa etapa : paraRemover){
				Node etapa_ = getNode("id", etapa.getId(), Etapa.class);
				etapa_.delete();
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
	
	public Curso getCurso(Turma turma){
		Node turma_ = getNode("id", turma.getId(), Turma.class);
		Iterator<Node> it = turma_.traverse(
				Traverser.Order.DEPTH_FIRST,
				StopEvaluator.DEPTH_ONE,
				ReturnableEvaluator.ALL_BUT_START_NODE,
				Relacionamento.TEM,
				Direction.INCOMING).iterator();
		while(it.hasNext()){
			return get(it.next(), Curso.class);
		}
		return null;
	}
}
