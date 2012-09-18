package com.breakfast.tomorrow.core.academic;

import org.apache.log4j.Logger;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.index.Index;

import com.breakfast.base.Utils;
import com.breakfast.tomorrow.core.database.DataBase;
import com.breakfast.tomorrow.core.database.EntityRelashionship;

public class Avaliacao {
	
	
   private static Logger LOG = Logger.getLogger(Avaliacao.class);
	
	/**
	 * Constants for indexes fields "git hub"
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

}
