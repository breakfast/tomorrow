package com.breakfast.tomorrow.core.academic.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.ReturnableEvaluator;
import org.neo4j.graphdb.StopEvaluator;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.Traverser;
import org.neo4j.graphdb.Traverser.Order;


import com.breakfast.tomorrow.core.academic.vo.Diario;
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
		setDisciplinas(nodeEntity, nodeEntity.getDiciplinas());
	}
	
	
	public Etapa getEtapaPorId(long id){
		Node node = getNode("id", id, Etapa.class);
		return carregar(node);
	}
	
	public Etapa carregar(Node node){
		Etapa etapa = get(node, Etapa.class);
		if(etapa!=null){
			//etapa.setTurma(getTurma(etapa));
			etapa.setDiciplinas(getDisciplinas(etapa));
		}
		return etapa; 
	}
	
	
	public Collection<Etapa> getEtapas(){
		Iterator<Node> nodeIterator = DataBase.get().getReferenceNode().traverse(Traverser.Order.DEPTH_FIRST,
				  StopEvaluator.DEPTH_ONE,
				  ReturnableEvaluator.ALL_BUT_START_NODE,
				  EntityRelashionship.ETAPAS,
				  Direction.OUTGOING).iterator();
		Collection<Etapa> lista = new ArrayList<Etapa>();
		while(nodeIterator.hasNext()){
			lista.add(carregar(nodeIterator.next()));
		}
		return lista;
	}
	
	public Collection<Disciplina> getDisciplinas(Etapa etapa){
		return getDisciplinas(etapa, Relacionamento.TEM);
	}
	
	public Collection<Disciplina> getDisciplinas(Etapa etapa, RelationshipType relacionamento){
		DisciplinaRepository repo = new DisciplinaRepository();
		List<Disciplina> lista = new ArrayList<Disciplina>();
		Node node = getNode("id", etapa.getId(), Etapa.class);
		if(node==null) return lista;
		Iterator<Node> it = node.traverse(Order.DEPTH_FIRST, 
										  StopEvaluator.DEPTH_ONE, 
										  ReturnableEvaluator.ALL_BUT_START_NODE, 
										  relacionamento,
										  Direction.OUTGOING).iterator();
		while(it.hasNext()){
			Disciplina disciplina = repo.carregar(it.next());
			disciplina.setEtapa(etapa);
			lista.add(disciplina);
		}
		return lista;
	}
	
	
	public void setDisciplinas(Etapa etapa, Collection<Disciplina> disciplinas){
		setDisciplinas(etapa, disciplinas, Relacionamento.TEM);
	}
	
	public void setDisciplinas(Etapa etapa, Collection<Disciplina> disciplinas, RelationshipType relacionamento){
		if(disciplinas==null)return;
		DisciplinaRepository repositorioDisciplina = new DisciplinaRepository();
		Collection<Disciplina> persistidas = getDisciplinas(etapa,relacionamento);
		Collection<Disciplina> paraPersistir = new HashSet<Disciplina>(disciplinas); 
		Collection<Disciplina> paraRemover = new HashSet<Disciplina>(persistidas);
		paraPersistir.removeAll(persistidas);
		paraRemover.removeAll(disciplinas);
		Transaction tx = DataBase.get().beginTx();
		Node etapa_ = getNode("id", etapa.getId(), Etapa.class);
		try{
			for(Disciplina disciplina : disciplinas){
				repositorioDisciplina.persistir(disciplina);
			}
			for(Disciplina disciplina : paraPersistir){
				Node disciplina_= getNode("id", disciplina.getId(), Disciplina.class);
				if(!disciplina_.hasRelationship(Direction.INCOMING,relacionamento)){
					etapa_.createRelationshipTo(disciplina_, relacionamento);
				}
				Disciplina d = repositorioDisciplina.carregar(disciplina_);
				if(d.getDiario()==null)d.setDiario(new Diario());
				repositorioDisciplina.persistir(d);
			}
			for(Disciplina disciplina : paraRemover){
				repositorioDisciplina.delete(disciplina);
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
		TurmaRepository repo = new TurmaRepository();
		Node etapa_ = getNode("id", etapa.getId(), Etapa.class);
		Iterator<Node> it = etapa_.traverse(
				Traverser.Order.DEPTH_FIRST,
				StopEvaluator.DEPTH_ONE,
				ReturnableEvaluator.ALL_BUT_START_NODE,
				Relacionamento.TEM,
				Direction.INCOMING).iterator();
		while(it.hasNext()){
			return repo.carregar(it.next());
		}
		return null;
	}
	
}
