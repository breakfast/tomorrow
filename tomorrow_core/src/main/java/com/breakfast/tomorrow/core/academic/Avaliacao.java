package com.breakfast.tomorrow.core.academic;

import java.util.Iterator;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.ReturnableEvaluator;
import org.neo4j.graphdb.StopEvaluator;
import org.neo4j.graphdb.Traverser;

import com.breakfast.tomorrow.core.database.DataBase;
import com.breakfast.tomorrow.core.database.DataBaseException;
import com.breakfast.tomorrow.core.database.EntityRelashionship;
import com.breakfast.tomorrow.core.database.NodeEntity;
import com.breakfast.tomorrow.core.database.NodeEntityManager;

public class Avaliacao extends NodeEntity {
	
	private static NodeEntityManager<Avaliacao> manager = new NodeEntityManager<Avaliacao>();
   //private static Logger LOG = Logger.getLogger(Avaliacao.class);
	
	
	 /**
	 * fields of class
	 */  	
	 private int peso;
	 private String descricao;

	
	/**
	 * Default Constructor for 
	 */
	public Avaliacao(){}

	public Avaliacao(Node node) {
		super(node);
	}

	public int getPeso() {
		this.peso = (Integer)  getProperty("peso");
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}

	public String getDescricao() {
		this.descricao = (String) getProperty("descricao");
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public static void persist(Avaliacao avaliacao) throws DataBaseException {
		manager.persistir(avaliacao);
		manager.createEntityRelationship(avaliacao, EntityRelashionship.AVALIACOES);
	}
	
	
	public static void delete(Avaliacao avaliacao) throws DataBaseException{
		manager.delete(avaliacao);
	}

	public static Avaliacao getAvaliacaoPorId(long id){
		return manager.getNodeEntityById(id, Avaliacao.class);
	}
	
	public static Iterator<Avaliacao> getAvaliacoes(){
		
		Iterator<Avaliacao> iterator = new Iterator<Avaliacao>() {
		
		
		public final Iterator<Node> nodeIterator = DataBase.get().getReferenceNode().traverse(Traverser.Order.DEPTH_FIRST,
				  StopEvaluator.DEPTH_ONE,
				  ReturnableEvaluator.ALL_BUT_START_NODE,
				  EntityRelashionship.AVALIACOES,
				  Direction.OUTGOING).iterator();

		@Override
		public boolean hasNext() {
			return nodeIterator.hasNext();
		}

		@Override
		public Avaliacao next() {
			Node nextNode = nodeIterator.next();
			return new Avaliacao(nextNode);
		}

		@Override
		public void remove() {
			nodeIterator.remove();
		}

			
		};
		return iterator;
	}

}
