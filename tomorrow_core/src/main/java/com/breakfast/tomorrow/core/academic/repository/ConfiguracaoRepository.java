package com.breakfast.tomorrow.core.academic.repository;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.ReturnableEvaluator;
import org.neo4j.graphdb.StopEvaluator;
import org.neo4j.graphdb.Traverser;

import com.breakfast.tomorrow.core.academic.vo.Configuracao;
import com.breakfast.tomorrow.core.database.DataBase;
import com.breakfast.tomorrow.core.database.EntityRelashionship;
import com.breakfast.tomorrow.core.database.NodeRepositoryManager;

public class ConfiguracaoRepository extends NodeRepositoryManager<Configuracao>{
	
	@Override
	public void persistir(Configuracao nodeEntity) {
		super.persistir(nodeEntity);
		super.createEntityRelationship(nodeEntity, EntityRelashionship.CONFIGURACOES);
	}
	
	public Configuracao getConfiguracaoPorId(long id) {
		Node node = getNode("id",id,Configuracao.class);
		return carregar(node);
	}
	
	public Configuracao carregar(Node node){
		Configuracao configuracao = get(node,Configuracao.class);
		return configuracao;
	}
	
	public Collection<Configuracao> getConfiguracoes(){
		Iterator<Node> nodeIterator = DataBase.get().getReferenceNode().traverse(Traverser.Order.DEPTH_FIRST,
				  StopEvaluator.DEPTH_ONE,
				  ReturnableEvaluator.ALL_BUT_START_NODE,
				  EntityRelashionship.CONFIGURACOES,
				  Direction.OUTGOING).iterator();
		Collection<Configuracao> lista = new ArrayList<Configuracao>();
		while(nodeIterator.hasNext()){
			lista.add(carregar(nodeIterator.next()));
		}
		System.out.println("Configuracao : " + lista.size());
		return lista;
	}

}
