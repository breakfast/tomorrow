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

public class UnidadeEducacional {
	
	private static Logger LOG = Logger.getLogger(UnidadeEducacional.class);

	/**
	 * Constants for indexes fields
	 */

	public final static String INDEX_ID_UNIDADE= "id";
	public final static String INDEX_NOME_UNIDADE = "nomeUnidade";

	/**
	 * Constants for fields
	 */

	public final static String ID_UNIDADE = "id";
	public final static String NOME_UNIDADE = "nomeUnidade";
	public final static String LOCAL = "local";

	/**
	 * fields of class
	 */

	private long id ; 
	private String nomeUnidade; 
	private String local;
	
	
	/**
	 * Prepare Node creating index for the class entity and setter properties in
	 * the node. this method used by persist elements in graph data base The
	 * node, can exist loaded from database or can it created for new node. Note
	 * : The SubClasses
	 * 
	 * @param node
	 */
	protected void prepareNode(Node node) {

		Utils.setNodeProperty(node, ID_UNIDADE, this.id);
		Utils.setNodeProperty(node, NOME_UNIDADE, this.nomeUnidade);
		Utils.setNodeProperty(node, LOCAL, this.local);

	}

	/**
	 * Prepare Index for current values, if Node different of the null, remove a
	 * index for node. Use this method in subclasses if persist
	 * 
	 * @param node
	 */

	protected void prepareIndex(Node node) {
		Index<Node> idUnidade = DataBase.get().index().forNodes(INDEX_ID_UNIDADE);
		Index<Node> nomeUnidade = DataBase.get().index().forNodes(INDEX_NOME_UNIDADE);
		if (node != null) {
			idUnidade.remove(node, ID_UNIDADE,node.getProperty(ID_UNIDADE));
			nomeUnidade.remove(node, NOME_UNIDADE,node.getProperty(NOME_UNIDADE));
		}
	}

	/***
	 * Add Index at creating new Node. Use this class in subclass method if
	 * persist;
	 * 
	 * @param node
	 */
	protected void addIndex(Node node) {
		Index<Node> idUnidade = DataBase.get().index().forNodes(INDEX_ID_UNIDADE);
		Index<Node> nomeUnidade = DataBase.get().index().forNodes(INDEX_NOME_UNIDADE);
		idUnidade.add(node, ID_UNIDADE,node.getProperty(ID_UNIDADE));
		nomeUnidade.add(node, NOME_UNIDADE,node.getProperty(NOME_UNIDADE));
		DataBase.get().getReferenceNode().createRelationshipTo(node, EntityRelashionship.UNIDADES);
	}

	protected void removeIndex(Node node) {
		Index<Node> idUnidade = DataBase.get().index().forNodes(INDEX_ID_UNIDADE);
		Index<Node> nomeUnidade = DataBase.get().index().forNodes(INDEX_NOME_UNIDADE);
		idUnidade.remove(node, ID_UNIDADE ,node.getProperty(ID_UNIDADE));
		nomeUnidade.remove(node, NOME_UNIDADE,node.getProperty(NOME_UNIDADE));
	}

	/**
	 * Graph Node
	 */
	protected Node node;

	/**
	 * Default Constructor for Unidade
	 */
	public UnidadeEducacional() {
		// TODO Auto-generated constructor stub
	
	}

	public Node getNode() {
		return this.node;
	}

	public UnidadeEducacional (Node node) {
		this.node = node;
	}

	public long getId() {
		this.id = node != null ? ((Long) node.getProperty(ID_UNIDADE)).longValue() : this.id;
		return id;
		
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNomeUnidade() {
		this.nomeUnidade = node != null ? ((String) node.getProperty(NOME_UNIDADE)) : this.nomeUnidade;
		return nomeUnidade;
	}

	public void setNomeUnidade(String nomeUnidade) {
		this.nomeUnidade = nomeUnidade;
	}

	public String getLocal() {
		this.local =  node != null ? ((String) node.getProperty(LOCAL)) : this.nomeUnidade;
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public static void persist(UnidadeEducacional unidadeEducacional) throws DataBaseException {
		String info = "NOT A INFO";
		boolean hasNode = unidadeEducacional.node != null;
		Transaction tx = DataBase.get().beginTx();
		unidadeEducacional.prepareIndex(unidadeEducacional.node);
		//Criar indice para alunos aqui.
		try {
			Node node;
			if(hasNode) {
				node = unidadeEducacional.node;
				info = Utils.UPDATED;
			}
			else { 
				node = DataBase.get().createNode();
				unidadeEducacional.setId(EntityProperties.getID(UnidadeEducacional.class));
				unidadeEducacional.node = node;
				info = Utils.PESISTED;
			}
			unidadeEducacional.prepareNode(node);
			unidadeEducacional.addIndex(node);
			DataBase.get().getReferenceNode().createRelationshipTo(node, EntityRelashionship.UNIDADES);
			tx.success();
			LOG.info(unidadeEducacional.toString() + info); 
		} catch (Exception e) {
			tx.failure();
			LOG.error("Erro at Persist Unidade Educacional",e);
			throw new DataBaseException(e);
		} finally {
			tx.finish();
		}
	}
	
	
	public static void delete(UnidadeEducacional unidadeEducacional) throws DataBaseException{
		Transaction transaction = DataBase.get().beginTx();
		try{
			String info = unidadeEducacional.toString();
			Utils.deleteRelantionShips(unidadeEducacional.node);
			unidadeEducacional.removeIndex(unidadeEducacional.node);
			unidadeEducacional.node.delete();
			transaction.success();
			LOG.info(info + Utils.DELETED);
		}
		catch (Exception e) {
			transaction.failure();
			LOG.error("Erro at Delete unidadeEducacional Educacional ",e);
			throw new DataBaseException(e);
		}
		transaction.finish();
	}
	
	public static UnidadeEducacional getUnidadePorId(long id){
		Node nodeFound = DataBase.get().index().forNodes(INDEX_ID_UNIDADE).get(ID_UNIDADE, id).getSingle();
		if(nodeFound != null){
			return new UnidadeEducacional(nodeFound);
		}
		return null;
	}
    
public static Iterator<UnidadeEducacional> getUnidades(){
		
		Iterator<UnidadeEducacional> iterator = new Iterator<UnidadeEducacional>() {
		
		
		public final Iterator<Node> nodeIterator = DataBase.get().getReferenceNode().traverse(Traverser.Order.DEPTH_FIRST,
				  StopEvaluator.DEPTH_ONE,
				  ReturnableEvaluator.ALL_BUT_START_NODE,
				  EntityRelashionship.UNIDADES,
				  Direction.OUTGOING).iterator();

		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return nodeIterator.hasNext();
		}

		@Override
		public UnidadeEducacional next() {
			// TODO Auto-generated method stub
			Node nextNode = nodeIterator.next();
			return new UnidadeEducacional(nextNode);
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
