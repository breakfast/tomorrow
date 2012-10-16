package com.breakfast.tomorrow.core.academic.repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.ReturnableEvaluator;
import org.neo4j.graphdb.StopEvaluator;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.Traverser.Order;

import com.breakfast.tomorrow.core.academic.vo.Disciplina;
import com.breakfast.tomorrow.core.academic.vo.Etapa;
import com.breakfast.tomorrow.core.database.DataBase;
import com.breakfast.tomorrow.core.database.NodeRepositoryManager;
import com.breakfast.tomorrow.core.database.RepositoryException;

public class EtapaRepository extends NodeRepositoryManager<Etapa> {
	
	
	public List<Disciplina> listaDisciplinas(Etapa etapa){
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
	
}
