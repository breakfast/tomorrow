package com.breakfast.tomorrow.core.academic.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.ReturnableEvaluator;
import org.neo4j.graphdb.StopEvaluator;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.Traverser;
import org.neo4j.graphdb.Traverser.Order;

import com.breakfast.tomorrow.core.academic.vo.Aluno;
import com.breakfast.tomorrow.core.academic.vo.Disciplina;
import com.breakfast.tomorrow.core.academic.vo.Etapa;
import com.breakfast.tomorrow.core.database.DataBase;
import com.breakfast.tomorrow.core.database.EntityRelashionship;
import com.breakfast.tomorrow.core.database.NodeRepositoryManager;
import com.breakfast.tomorrow.core.database.RepositoryException;


public class AlunoRepository extends NodeRepositoryManager<Aluno>{
	
	@Override
	public void persistir(Aluno nodeEntity) {
		super.persistir(nodeEntity);
		super.createEntityRelationship(nodeEntity, EntityRelashionship.ALUNOS);
	}
	
	public Aluno getAlunoPorId(long id){
		Node node = getNode("id",id,Aluno.class);
		Aluno aluno = carregar(node);
		return aluno;
	}
	
	public Aluno getAlunoPorIndex(String index, String valor){
		Node node = getNode(index,valor,Aluno.class);
		return null; 
	}
	
	public Collection<Aluno> getAlunos(){
		Iterator<Node> nodeIterator = DataBase.get().getReferenceNode().traverse(
				Traverser.Order.DEPTH_FIRST,
				StopEvaluator.DEPTH_ONE,
				ReturnableEvaluator.ALL_BUT_START_NODE,
				EntityRelashionship.ALUNOS,
				Direction.OUTGOING).iterator();
		List<Aluno> lista = new ArrayList<Aluno>();
		while(nodeIterator.hasNext()){
			lista.add(this.get(nodeIterator.next(), Aluno.class));
		}
		return lista;
	}
	
	public Aluno carregar(Node node){
		Aluno aluno = get(node, Aluno.class);
		return aluno;
	}
	
	public void setEtapaCursando(Aluno aluno){
		if(aluno==null) return;
		if(aluno.getEtapaCursando() == null)return;
		EtapaRepository etapaRepo = new EtapaRepository();
		Transaction tx = DataBase.get().beginTx();
		try{
			Node aluno_ = getNode("id", aluno.getId(), Aluno.class);
			Node etapa_ = getNode("id", aluno.getId(), Etapa.class);
			if(aluno_ != null && (etapa_ != null)){
				aluno_.createRelationshipTo(etapa_, Relacionamento.MATRICULADO);
				Etapa etapa = etapaRepo.carregar(etapa_);
				for(Disciplina disciplina : etapa.getDiciplinas()){
					Node disciplina_ = getNode("id", disciplina.getId(), Disciplina.class);
					Relationship relationship = aluno_.createRelationshipTo(disciplina_, Relacionamento.DISCIPLINA); 
					relationship.setProperty("status", "Cursando");
					relationship.setProperty("media", "0");
				}
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
	
	public Collection<Etapa> getEtapasCursando(Aluno aluno){
		if(aluno == null) return new ArrayList<Etapa>();
		Node aluno_ = getNode("id", aluno.getId(), Aluno.class);
		Iterator<Node> it = aluno_.traverse(Order.DEPTH_FIRST, 
				StopEvaluator.DEPTH_ONE, 
				ReturnableEvaluator.ALL_BUT_START_NODE, 
				Relacionamento.MATRICULADO, 
				Direction.OUTGOING).iterator();
		Collection<Etapa> etapas = new ArrayList<Etapa>();
		while(it.hasNext()){
			Etapa etapa = get(it.next(), Etapa.class);
			etapas.add(etapa);
		}
		return etapas;
	}
	
}
