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

public class InstituicaoEducacional {

	private static Logger LOG = Logger.getLogger(InstituicaoEducacional.class);

	/**
	 * Constants for indexes fields
	 */

	public final static String INDEX_ID_INSTITUICAO = "id";
	public final static String INDEX_NOME_INSTITUICAO = "nomeInstituicao";

	/**
	 * Constants for fields
	 */

	public final static String ID_INSTITUICAO = "id";
	public final static String NOME_INSTITUICAO = "nomeInstituicao";

	/**
	 * fields of class
	 */

	private long id;
	private String nomeInstituicao;

	/**
	 * Prepare Node creating index for the class entity and setter properties in
	 * the node. this method used by persist elements in graph data base The
	 * node, can exist loaded from database or can it created for new node. Note
	 * : The SubClasses
	 * 
	 * @param node
	 */
	protected void prepareNode(Node node) {

		Utils.setNodeProperty(node, ID_INSTITUICAO, this.id);
		Utils.setNodeProperty(node, NOME_INSTITUICAO, this.nomeInstituicao);

	}

	/**
	 * Prepare Index for current values, if Node different of the null, remove a
	 * index for node. Use this method in subclasses if persist
	 * 
	 * @param node
	 */

	protected void prepareIndex(Node node) {
		Index<Node> idInstituicao = DataBase.get().index().forNodes(INDEX_ID_INSTITUICAO);
		Index<Node> nomeInstituicao = DataBase.get().index().forNodes(INDEX_NOME_INSTITUICAO);
		if (node != null) {
			idInstituicao.remove(node, ID_INSTITUICAO,node.getProperty(ID_INSTITUICAO));
			nomeInstituicao.remove(node, NOME_INSTITUICAO,node.getProperty(NOME_INSTITUICAO));
		}
	}

	/***
	 * Add Index at creating new Node. Use this class in subclass method if
	 * persist;
	 * 
	 * @param node
	 */
	protected void addIndex(Node node) {
		Index<Node> idInstituicao = DataBase.get().index()
				.forNodes(INDEX_ID_INSTITUICAO);
		Index<Node> nomeInstituicao = DataBase.get().index()
				.forNodes(INDEX_NOME_INSTITUICAO);
		idInstituicao.add(node, ID_INSTITUICAO,
				node.getProperty(ID_INSTITUICAO));
		nomeInstituicao.add(node, NOME_INSTITUICAO,
				node.getProperty(NOME_INSTITUICAO));
		DataBase.get().getReferenceNode()
				.createRelationshipTo(node, EntityRelashionship.INSTITUICAOES);
	}

	protected void removeIndex(Node node) {
		Index<Node> idInstituicao = DataBase.get().index()
				.forNodes(INDEX_ID_INSTITUICAO);
		Index<Node> nomeInstituicao = DataBase.get().index()
				.forNodes(INDEX_NOME_INSTITUICAO);
		idInstituicao.remove(node, ID_INSTITUICAO,
				node.getProperty(ID_INSTITUICAO));
		nomeInstituicao.remove(node, NOME_INSTITUICAO,
				node.getProperty(NOME_INSTITUICAO));
	}

	/**
	 * Graph Node
	 */
	protected Node node;

	/**
	 * Default Constructor for Instituicao
	 */
	public InstituicaoEducacional() {
	}

	public Node getNode() {
		return this.node;
	}

	public InstituicaoEducacional(Node node) {
		this.node = node;
	}

	// set e get 
	
	public long getId() {
		this.id = node != null ? ((Long) node.getProperty(ID_INSTITUICAO)).longValue() : this.id;
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNomeInstituicao() {
		this.nomeInstituicao = node != null ? (String) node.getProperty(NOME_INSTITUICAO) : this.nomeInstituicao;
		return nomeInstituicao;
	}

	public void setNomeInstituicao(String nomeInstituicao) {
		this.nomeInstituicao = nomeInstituicao;
	}

	
	public static void persist(InstituicaoEducacional instituicaoEducacional) throws DataBaseException {
		String info = "NOT A INFO";
		boolean hasNode = instituicaoEducacional.node != null;
		Transaction tx = DataBase.get().beginTx();
		instituicaoEducacional.prepareIndex(instituicaoEducacional.node);
		//Criar indice para alunos aqui.
		try {
			Node node;
			if(hasNode) {
				node = instituicaoEducacional.node;
				info = Utils.UPDATED;
			}
			else { 
				node = DataBase.get().createNode();
				instituicaoEducacional.setId(EntityProperties.getID(InstituicaoEducacional.class));
				instituicaoEducacional.node = node;
				info = Utils.PESISTED;
			}
			instituicaoEducacional.prepareNode(node);
			instituicaoEducacional.addIndex(node);
			DataBase.get().getReferenceNode().createRelationshipTo(node, EntityRelashionship.INSTITUICAOES);
			tx.success();
			LOG.info(instituicaoEducacional.toString() + info); 
		} catch (Exception e) {
			tx.failure();
			LOG.error("Erro at Persist Instituicao Educacional ",e);
			throw new DataBaseException(e);
		} finally {
			tx.finish();
		}
	}
	
	
	public static void delete(InstituicaoEducacional instituicaoEducacional) throws DataBaseException{
		Transaction transaction = DataBase.get().beginTx();
		try{
			String info = instituicaoEducacional.toString();
			Utils.deleteRelantionShips(instituicaoEducacional.node);
			instituicaoEducacional.removeIndex(instituicaoEducacional.node);
			instituicaoEducacional.node.delete();
			transaction.success();
			LOG.info(info + Utils.DELETED);
		}
		catch (Exception e) {
			transaction.failure();
			LOG.error("Erro at Delete Instituicao Educacional ",e);
			throw new DataBaseException(e);
		}
		transaction.finish();
	}
	
	public static InstituicaoEducacional getInstituicaoPorId(long id){
		Node nodeFound = DataBase.get().index().forNodes(INDEX_ID_INSTITUICAO).get(ID_INSTITUICAO, id).getSingle();
		if(nodeFound != null){
			return new InstituicaoEducacional(nodeFound);
		}
		return null;
	}
	
	
public static Iterator<InstituicaoEducacional> getInstituicoes(){
		
		Iterator<InstituicaoEducacional> iterator = new Iterator<InstituicaoEducacional>() {
		
		
		public final Iterator<Node> nodeIterator = DataBase.get().getReferenceNode().traverse(Traverser.Order.DEPTH_FIRST,
				  StopEvaluator.DEPTH_ONE,
				  ReturnableEvaluator.ALL_BUT_START_NODE,
				  EntityRelashionship.INSTITUICAOES,
				  Direction.OUTGOING).iterator();

		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return nodeIterator.hasNext();
		}

		@Override
		public InstituicaoEducacional next() {
			// TODO Auto-generated method stub
			Node nextNode = nodeIterator.next();
			return new InstituicaoEducacional(nextNode);
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
