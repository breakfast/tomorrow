package com.breakfast.tomorrow.core.academic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.ReturnableEvaluator;
import org.neo4j.graphdb.StopEvaluator;
import org.neo4j.graphdb.Traverser;

import com.breakfast.tomorrow.core.database.DataBase;
import com.breakfast.tomorrow.core.database.RepositoryException;
import com.breakfast.tomorrow.core.database.EntityRelashionship;
import com.breakfast.tomorrow.core.database.FieldNode;
import com.breakfast.tomorrow.core.database.IdNode;
import com.breakfast.tomorrow.core.database.NodeRepositoryManager;
import com.breakfast.tomorrow.core.database.NodeRepository;

public class Avaliacao implements NodeRepository {
	
	private static NodeRepositoryManager<Avaliacao> manager = new NodeRepositoryManager<Avaliacao>();
   //private static Logger LOG = Logger.getLogger(Avaliacao.class);
	
	
	 /**
	 * fields of class
	 */ 
	@IdNode private long id;
	@FieldNode private int peso;
	@FieldNode private String descricao;

	
	/**
	 * Default Constructor for 
	 */
	public Avaliacao(){}

	public long getId(){
		return id;
	}
	
	public void setId(long id){
		this.id = id;
	}
	
	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public static void persist(Avaliacao avaliacao) throws RepositoryException {
		manager.persistir(avaliacao);
		manager.createEntityRelationship(avaliacao, EntityRelashionship.AVALIACOES);
	}
	
	
	public static void delete(Avaliacao avaliacao) throws RepositoryException{
		manager.delete(avaliacao);
	}

	public static Avaliacao getAvaliacaoPorId(long id){
		return manager.getNodeEntityById(id, Avaliacao.class);
	}
	
	public static Collection<Avaliacao> getAvaliacoes(){
		Iterator<Node> nodeIterator = DataBase.get().getReferenceNode().traverse(Traverser.Order.DEPTH_FIRST,
				  StopEvaluator.DEPTH_ONE,
				  ReturnableEvaluator.ALL_BUT_START_NODE,
				  EntityRelashionship.AVALIACOES,
				  Direction.OUTGOING).iterator();
		List<Avaliacao> lista = new ArrayList<Avaliacao>();
		while(nodeIterator.hasNext()){
			lista.add(manager.get(nodeIterator.next(), Avaliacao.class));
		}
		return lista;
	}
	
	@Override
	public String toString() {
		return "[" + this.getId() + "]" + this.getDescricao();
	}

}


