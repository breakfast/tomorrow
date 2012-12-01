package com.breakfast.tomorrow.core.academic.repository;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.ReturnableEvaluator;
import org.neo4j.graphdb.StopEvaluator;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.Traverser;
import org.neo4j.graphdb.Traverser.Order;

import com.breakfast.tomorrow.core.academic.vo.Aula;
import com.breakfast.tomorrow.core.academic.vo.Diario;
import com.breakfast.tomorrow.core.academic.vo.Disciplina;
import com.breakfast.tomorrow.core.database.DataBase;
import com.breakfast.tomorrow.core.database.EntityRelashionship;
import com.breakfast.tomorrow.core.database.NodeRepositoryManager;

public class DiarioRepository extends NodeRepositoryManager<Diario>{
	
	public Diario getDiarioPorId(long id){
		Node node = getNode("id", id, Diario.class);
		return carregar(node);
	}
	
	public Diario carregar(Node node){
		Diario diario = get(node,Diario.class);
		return diario;
	}
	
	@Override
	public void persistir(Diario nodeEntity) {
		super.persistir(nodeEntity);
		super.createEntityRelationship(nodeEntity, EntityRelashionship.DIARIOS);
	}
	
	public Collection<Diario> getDiarioes() {
		Iterator<Node> nodeIterator = DataBase.get()
					.getReferenceNode()
					.traverse(Traverser.Order.DEPTH_FIRST,
							StopEvaluator.DEPTH_ONE,
							ReturnableEvaluator.ALL_BUT_START_NODE,
							EntityRelashionship.PROFESSORES, Direction.OUTGOING)
					.iterator();
		Collection<Diario> lista = new ArrayList<Diario>();
		while(nodeIterator.hasNext()){
			Diario diario = carregar(nodeIterator.next());
			lista.add(diario);
		}
		return lista;
	}
	
	public Collection<Aula> getAulas(Diario diario){
		AulaRepository repo = new AulaRepository();
		Collection<Aula> colecao = new ArrayList<Aula>();
		Node node = getNode("id", diario.getId(), Disciplina.class);
		if(node==null) return colecao;
		Iterator<Node> it = node.traverse(Order.DEPTH_FIRST, 
				  StopEvaluator.DEPTH_ONE, 
				  ReturnableEvaluator.ALL_BUT_START_NODE, 
				  Relacionamento.DISCIPLINA,
				  Direction.OUTGOING).iterator();
		while(it.hasNext()){
			colecao.add(repo.carregar(it.next()));
		}
		return colecao;
	}
	
	public void setAulas(Diario diario){
		if(diario.getAulas() == null) return;
		AulaRepository aulaRepo = new AulaRepository();
		Collection<Aula> persistidas = getAulas(diario);
		Collection<Aula> paraPersistir = new HashSet<Aula>(diario.getAulas());
		Collection<Aula> paraRemover = new HashSet<Aula>(persistidas);
		paraPersistir.removeAll(persistidas);
		paraRemover.removeAll(diario.getAulas());
		Transaction tx = DataBase.get().beginTx();
		Node diario_ = getNode("id", diario.getId(), Diario.class);
		try{
			for(Aula aula : diario.getAulas()){
				aulaRepo.persistir(aula);
			}
			for(Aula aula : paraPersistir){
				Node aula_ = getNode("id", aula.getId(), Aula.class);
				if(!diario_.hasRelationship(Direction.INCOMING,Relacionamento.DISCIPLINA)){
					diario_.createRelationshipTo(aula_, Relacionamento.DISCIPLINA);
				}
			}
			for(Aula aula : paraRemover){
				aulaRepo.delete(aula);
			}
			tx.success();
		}
		catch(Exception e){
			tx.failure();
		}
		finally{
			tx.finish();
		}
	}
	

}
