package com.breakfast.tomorrow.core.academic.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.ReturnableEvaluator;
import org.neo4j.graphdb.StopEvaluator;
import org.neo4j.graphdb.Traverser;

import com.breakfast.tomorrow.core.academic.vo.UnidadeEducacional;
import com.breakfast.tomorrow.core.database.DataBase;
import com.breakfast.tomorrow.core.database.EntityRelashionship;
import com.breakfast.tomorrow.core.database.NodeRepositoryManager;

public class UnidadeEducacionalRepository extends NodeRepositoryManager<UnidadeEducacional>{

	
	public Collection<UnidadeEducacional> getUnidades(){
		Iterator<Node> nodeIterator = DataBase.get().getReferenceNode().traverse(Traverser.Order.DEPTH_FIRST,
				  StopEvaluator.DEPTH_ONE,
				  ReturnableEvaluator.ALL_BUT_START_NODE,
				  EntityRelashionship.UNIDADES,
				  Direction.OUTGOING).iterator();
		Collection<UnidadeEducacional> lista = new ArrayList<UnidadeEducacional>();
		while(nodeIterator.hasNext()){
			lista.add(get(nodeIterator.next(), UnidadeEducacional.class));
		}
		return lista;
	}
	
	public UnidadeEducacional getUnidadePorId(long id){
		return getNodeEntityByIndex("id", id, UnidadeEducacional.class);
	}
	
}
