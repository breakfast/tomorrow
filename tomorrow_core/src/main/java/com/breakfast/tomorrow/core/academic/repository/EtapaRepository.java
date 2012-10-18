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
import org.neo4j.graphdb.Traverser.Order;


import com.breakfast.tomorrow.core.academic.vo.Disciplina;
import com.breakfast.tomorrow.core.academic.vo.Etapa;
import com.breakfast.tomorrow.core.academic.vo.Turma;

import com.breakfast.tomorrow.core.database.DataBase;
import com.breakfast.tomorrow.core.database.EntityRelashionship;
import com.breakfast.tomorrow.core.database.NodeRepositoryManager;
import com.breakfast.tomorrow.core.database.RepositoryException;

public class EtapaRepository extends NodeRepositoryManager<Etapa> {
	
	
	
	public void persistir(Etapa nodeEntity){
		super.persistir(nodeEntity);
		super.createEntityRelationship(nodeEntity, EntityRelashionship.ETAPAS);
	}
	
	
	public Etapa getEtapaPorId(long id){
		Etapa etapa = getNodeEntityByIndex("id", id, Etapa.class);
		if(etapa!=null){
			etapa.setTurma(getTurma(etapa));
			etapa.setDiciplinas(getDisciplinas(etapa));
		}
		return etapa;
	}
	
	
	public Collection<Etapa> getEtapas(){
		Iterator<Node> nodeIterator = DataBase.get().getReferenceNode().traverse(Traverser.Order.DEPTH_FIRST,
				  StopEvaluator.DEPTH_ONE,
				  ReturnableEvaluator.ALL_BUT_START_NODE,
				  EntityRelashionship.CURSOS,
				  Direction.OUTGOING).iterator();
		List<Etapa> lista = new ArrayList<Etapa>();
		while(nodeIterator.hasNext()){
			get(nodeIterator.next(), Etapa.class);
		}
		return lista;
	}
	
	public List<Disciplina> getDisciplinas(Etapa etapa){
		Node node = getNode("id", etapa.getId(), Etapa.class);
		if(node==null) return null;
		List<Disciplina> lista = new ArrayList<Disciplina>();
		Iterator<Node> it = node.traverse(Order.DEPTH_FIRST, 
										  StopEvaluator.DEPTH_ONE, 
										  ReturnableEvaluator.ALL_BUT_START_NODE, 
										  Relacionamento.TEM,
										  Direction.OUTGOING).iterator();
		while(it.hasNext()){
			lista.add(new NodeRepositoryManager<Disciplina>().get(it.next(), Disciplina.class));
		}
		return lista;
	}
	
	public void adiconarDisciplinas(Etapa etapa){
		Node nodeEtapa = getNode("id", etapa.getId(), Etapa.class);
		Transaction tx = DataBase.get().beginTx();
		try{
			for(Disciplina disciplina : etapa.getDiciplinas()){
				Node nodeDisciplina = getNode("id",disciplina.getId(),Disciplina.class);
				if(!nodeDisciplina.hasRelationship(Relacionamento.TEM)){
					nodeEtapa.createRelationshipTo(nodeDisciplina, Relacionamento.TEM);
				}
			}
			tx.success();
		}
		catch(Exception e){
			tx.failure();
			throw new RepositoryException(e);
		}
		finally{
			tx.finish();
		}
	}
	
	
	public void setDisciplina(Etapa etapa, Collection<Disciplina> disciplinas){
		DisciplinaRepository repositorioDisciplina = new DisciplinaRepository();
		Collection<Disciplina> persistidas = getDisciplinas(etapa);
		Collection<Disciplina> paraPersistir = new TreeSet<Disciplina>(disciplinas);
		Collection<Disciplina> paraRemover = new TreeSet<Disciplina>(persistidas);
		paraPersistir.removeAll(persistidas);
		paraRemover.removeAll(disciplinas);
		Transaction tx = DataBase.get().beginTx();
		Node etapa_ = getNode("id", etapa.getId(), Etapa.class);
		try{
			for(Disciplina disciplina : paraPersistir){
				repositorioDisciplina.persistir(disciplina);
				Node disciplina_= getNode("id", disciplina.getId(), Disciplina.class);
				etapa_.createRelationshipTo(disciplina_, Relacionamento.TEM);
			}
			for(Disciplina disciplina : paraRemover){
				Node disciplina_ = getNode("id", disciplina.getId(), Disciplina.class);
				disciplina_.delete();
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
	
	public Turma getTurma(Etapa etapa){
		Node etapa_ = getNode("id", etapa.getId(), Etapa.class);
		Iterator<Node> it = etapa_.traverse(
				Traverser.Order.DEPTH_FIRST,
				StopEvaluator.DEPTH_ONE,
				ReturnableEvaluator.ALL_BUT_START_NODE,
				Relacionamento.TEM,
				Direction.INCOMING).iterator();
		while(it.hasNext()){
			return get(it.next(), Turma.class);
		}
		return null;
	}
	
}
