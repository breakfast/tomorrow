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

import com.breakfast.tomorrow.core.academic.vo.Avaliacao;
import com.breakfast.tomorrow.core.database.DataBase;
import com.breakfast.tomorrow.core.database.EntityRelashionship;
import com.breakfast.tomorrow.core.database.NodeRepositoryManager;

public class AvaliacaoRepository extends NodeRepositoryManager<Avaliacao> {

	public Collection<Avaliacao> getAvaliacoes(){
		Iterator<Node> nodeIterator = DataBase.get().getReferenceNode().traverse(Traverser.Order.DEPTH_FIRST,
				  StopEvaluator.DEPTH_ONE,
				  ReturnableEvaluator.ALL_BUT_START_NODE,
				  EntityRelashionship.AVALIACOES,
				  Direction.OUTGOING).iterator();
		List<Avaliacao> lista = new ArrayList<Avaliacao>();
		while(nodeIterator.hasNext()){
			lista.add(this.get(nodeIterator.next(), Avaliacao.class));
		}
		return lista;
	}
	
	public Avaliacao getAvaliacaoPorId(long id){
		return getNodeEntityByIndex("id", id, Avaliacao.class);
	}
	
}
