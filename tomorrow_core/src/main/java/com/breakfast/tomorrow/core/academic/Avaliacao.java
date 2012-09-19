package com.breakfast.tomorrow.core.academic;

import java.util.Iterator;

import org.apache.log4j.Logger;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.ReturnableEvaluator;
import org.neo4j.graphdb.StopEvaluator;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.Traverser;
import org.neo4j.graphdb.index.Index;

import com.breakfast.base.Utils;
import com.breakfast.tomorrow.core.database.DataBase;
import com.breakfast.tomorrow.core.database.DataBaseException;
import com.breakfast.tomorrow.core.database.EntityProperties;
import com.breakfast.tomorrow.core.database.EntityRelashionship;

public class Avaliacao {
	
	
   private static Logger LOG = Logger.getLogger(Avaliacao.class);
	
	/**
	 * Constants for indexes fields 
	*/
	
	public final static String INDEX_ID_AVALIACAO  =  "id" ;
	public final static String INDEX_DESC_AVALIACAO = "descricao" ;
	
	/**
	 * Constants for fields
	*/
	
	public final static String ID_AVALIACAO = "id";
	public final static String PESO = "peso";
	public final static String DESCRICAO = "discricao";
	
	 /**
	 * fields of class
	 */
	  	
	 private long id;
	 private int peso;
	 private String descricao;
	 
	 /**
		 * Prepare Node creating index for the class entity and setter properties in the node.
		 * this method used by persist elements in graph data base
		 * The node, can exist loaded from database or can it created for new node. 
		 * Note : The SubClasses  
		 * @param node
		 */
		protected void prepareNode(Node node){
			
			Utils.setNodeProperty(node,ID_AVALIACAO,this.id);
			Utils.setNodeProperty(node,PESO,this.peso);
			Utils.setNodeProperty(node,DESCRICAO,this.descricao);
				
		}
		
		/**
		 * Prepare Index for current values, if Node different of the null, remove a index for node.
		 * Use this method in subclasses if persist
		 * @param node
		 */
		
		protected void prepareIndex(Node node){
			Index<Node> idAvaliacao = DataBase.get().index().forNodes(INDEX_ID_AVALIACAO);
			Index<Node> descAvaliacao = DataBase.get().index().forNodes(INDEX_DESC_AVALIACAO);	
			if(node != null){
				idAvaliacao.remove(node, ID_AVALIACAO, node.getProperty(ID_AVALIACAO));
				descAvaliacao.remove(node, DESCRICAO, node.getProperty(DESCRICAO));
				
		    }
		}
		
		/***
		 * Add Index at creating new Node.
		 * Use this class in subclass method if persist;
		 * @param node
		 */
		protected void addIndex(Node node){
			Index<Node> idAvaliacao = DataBase.get().index().forNodes(INDEX_ID_AVALIACAO);
			Index<Node> descAvaliacao = DataBase.get().index().forNodes(INDEX_DESC_AVALIACAO);	
			idAvaliacao.add(node, ID_AVALIACAO, node.getProperty(ID_AVALIACAO));
			descAvaliacao.add(node, DESCRICAO, node.getProperty(DESCRICAO));			
			DataBase.get().getReferenceNode().createRelationshipTo(node, EntityRelashionship.AVALIACOES);
		}
		
		protected void removeIndex(Node node){
			Index<Node> idAvaliacao = DataBase.get().index().forNodes(INDEX_ID_AVALIACAO);
			Index<Node> descAvaliacao = DataBase.get().index().forNodes(INDEX_DESC_AVALIACAO);
			idAvaliacao.remove(node, ID_AVALIACAO, node.getProperty(ID_AVALIACAO));
			descAvaliacao.remove(node, DESCRICAO, node.getProperty(DESCRICAO));
		}
		
		/**
		 * Graph Node
		 */
		protected Node node;
		
		/**
		 * Default Constructor for 
		 */
		public Avaliacao(){}

		public Node getNode(){
			return this.node;
		}

		public Avaliacao(Node node) {
			this.node = node;
		}

		public long getId() {
			this.id = node != null ? ((Long) node.getProperty(ID_AVALIACAO)).longValue() : this.id;
			return this.id;

		}

		public void setId(long id) {
			this.id = id;
		}

		public int getPeso() {
			this.peso = node != null ? (Integer)  (node.getProperty(PESO)) :this.peso;
			return peso;
		}

		public void setPeso(int peso) {
			this.peso = peso;
		}

		public String getDescricao() {
			this.descricao = node != null ? (String) node.getProperty(DESCRICAO) : this.descricao;
			return descricao;
		}

		public void setDescricao(String descricao) {
			this.descricao = descricao;
		}
		
		public static void persist(Avaliacao avaliacao) throws DataBaseException {
			String info = "NOT A INFO";
			boolean hasNode = avaliacao.node != null;
			Transaction tx = DataBase.get().beginTx();
			avaliacao.prepareIndex(avaliacao.node);
			//Criar indice para avalicaoes aqui.
			try {
				Node node;
				if(hasNode) {
					node = avaliacao.node;
					info = Utils.UPDATED;
				}
				else { 
					node = DataBase.get().createNode();
					avaliacao.setId(EntityProperties.getID(Avaliacao.class));
					avaliacao.node = node;
					info = Utils.PESISTED;
				}
				avaliacao.prepareNode(node);
				avaliacao.addIndex(node);
				DataBase.get().getReferenceNode().createRelationshipTo(node, EntityRelashionship.AVALIACOES);
				tx.success();
				LOG.info(avaliacao.toString() + info); 
			} catch (Exception e) {
				tx.failure();
				LOG.error("Erro at Persist Avaliacoes ",e);
				throw new DataBaseException(e);
			} finally {
				tx.finish();
			}
		}
		
		
		public static void delete(Avaliacao avaliacao) throws DataBaseException{
			Transaction transaction = DataBase.get().beginTx();
			try{
				String info = avaliacao.toString();
				Utils.deleteRelantionShips(avaliacao.node);
				avaliacao.removeIndex(avaliacao.node);
				avaliacao.node.delete();
				transaction.success();
				LOG.info(info + Utils.DELETED);
			}
			catch (Exception e) {
				transaction.failure();
				LOG.error("Erro at Delete avaliacao ",e);
				throw new DataBaseException(e);
			}
			transaction.finish();
		}
	
		public static Avaliacao getAvaliacaoPorId(long id){
			Node nodeFound = DataBase.get().index().forNodes(INDEX_ID_AVALIACAO).get(ID_AVALIACAO, id).getSingle();
			if(nodeFound != null){
				return new Avaliacao(nodeFound);
			}
			return null;
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
				// TODO Auto-generated method stub
				return nodeIterator.hasNext();
			}

			@Override
			public Avaliacao next() {
				// TODO Auto-generated method stub
				Node nextNode = nodeIterator.next();
				return new Avaliacao(nextNode);
			}

			@Override
			public void remove() { 
				
				nodeIterator.remove();
				// TODO Auto-generated method stub
				
			}

			
			};
			return iterator;
		}

}
