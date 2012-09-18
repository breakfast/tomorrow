package com.breakfast.tomorrow.core.academic;

import java.util.Date;
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

public class Aula {

	private static Logger LOG = Logger.getLogger(Aula.class);
	/**
	 * Constants for indexes fields
	 */
	public final static String INDEX_ID_AULA = "idAula";
	public final static String INDEX_DESC_AULA = "descricao";

	/**
	 * Constants for fields
	 */

	public final static String ID_AULA = "idAula";
	public final static String DATA = "data";
	public final static String DESCRICAO = "descricao";

	/**
	 * fields of class
	 */

	private long id;
	private Date data;
	private String descricao;

	/**
	 * Prepare Node creating index for the class entity and setter properties in
	 * the node. this method used by persist elements in graph data base The
	 * node, can exist loaded from database or can it created for new node. Note
	 * : The SubClasses
	 * 
	 * @param node
	 */
	protected void prepareNode(Node node) {

		Utils.setNodeProperty(node, ID_AULA, this.id);
		Utils.setNodeProperty(node, DATA, this.data);
		Utils.setNodeProperty(node, DESCRICAO, this.descricao);

	}

	/**
	 * Prepare Index for current values, if Node different of the null, remove a
	 * index for node. Use this method in subclasses if persist
	 * 
	 * @param node
	 */

	protected void prepareIndex(Node node) {
		Index<Node> idAula = DataBase.get().index().forNodes(INDEX_ID_AULA);
		Index<Node> descAula = DataBase.get().index().forNodes(INDEX_DESC_AULA);
		if (node != null) {
			idAula.remove(node, ID_AULA, node.getProperty(ID_AULA));
			descAula.remove(node, DESCRICAO, node.getProperty(DESCRICAO));
		}
	}

	/***
	 * Add Index at creating new Node. Use this class in subclass method if
	 * persist;
	 * 
	 * @param node
	 */

	protected void addIndex(Node node) {
		Index<Node> idAula = DataBase.get().index().forNodes(INDEX_ID_AULA);
		Index<Node> descAula = DataBase.get().index().forNodes(INDEX_DESC_AULA);
		idAula.add(node, ID_AULA, node.getProperty(ID_AULA));
		descAula.add(node, DESCRICAO, node.getProperty(DESCRICAO));
		DataBase.get().getReferenceNode()
				.createRelationshipTo(node, EntityRelashionship.AULAS);

	}

	protected void removeIndex(Node node) {
		Index<Node> idAula = DataBase.get().index().forNodes(INDEX_ID_AULA);
		Index<Node> descAula = DataBase.get().index().forNodes(INDEX_DESC_AULA);
		idAula.remove(node, ID_AULA, node.getProperty(ID_AULA));
		descAula.remove(node, DESCRICAO, node.getProperty(DESCRICAO));
	}

	/**
	 * Graph Node
	 */

	protected Node node;

	/**
	 * Default Constructor for Aula
	 */
	public Aula() {

	}

	public Aula(Node node) {
		this.node = node;
	}

	// get e set

	public long getidAula() {
		this.id = node != null ? ((Long) node.getProperty(ID_AULA)).longValue()
				: this.id;
		return this.id;

	}

	public void setidAula(long id) {
		this.id = id;
	}

	public Date getData() {
		this.data = node != null ? ((Date) node.getProperty(DATA)) : this.data;
		return this.data;
	}

	public void setData(Date data) {
		this.data = data;

	}

	public String getDescricao() {
		this.descricao = node != null ? ((String) node.getProperty(DESCRICAO))
				: this.descricao;
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public static void persist(Aula aula) throws DataBaseException {
		String info = "NOT A INFO";
		boolean hasNode = aula.node != null;
		Transaction tx = DataBase.get().beginTx();
		aula.prepareIndex(aula.node);
		// Criar indice para alunos aqui.
		try {
			Node node;
			if (hasNode) {
				node = aula.node;
				info = Utils.UPDATED;
			} else {
				node = DataBase.get().createNode();
				aula.setidAula((EntityProperties.getID(Aula.class)));
				info = Utils.PESISTED;
				aula.node = node;
			}
			aula.prepareNode(node);
			aula.addIndex(node);
			DataBase.get().getReferenceNode()
					.createRelationshipTo(node, EntityRelashionship.AULAS);
			tx.success();
			LOG.info(aula.toString() + info);
		} catch (Exception e) {
			tx.failure();
			LOG.error("Erro at Persist Aula ", e);
			throw new DataBaseException(e);
		} finally {
			tx.finish();
		}
	}

	public static void delete(Aula aula) throws DataBaseException {
		Transaction transaction = DataBase.get().beginTx();
		try {
			String info = aula.toString();
			Utils.deleteRelantionShips(aula.node);
			aula.removeIndex(aula.node);
			aula.node.delete();
			transaction.success();
			LOG.info(info + Utils.DELETED);
		} catch (Exception e) {
			transaction.failure();
			LOG.error("Erro at Delete Aula ", e);
			throw new DataBaseException(e);
		}
		transaction.finish();
	}
	
	public static Aula getAulaPorId(long id) {
		Node nodeFound = DataBase.get().index().forNodes(INDEX_ID_AULA).get(ID_AULA, id).getSingle();
		if (nodeFound != null) {
			return new Aula(nodeFound);
		}
		return null;
	}

	public Iterator<Aula> getAulas() {

		Iterator<Aula> iterator = new Iterator<Aula>() {

			public final Iterator<Node> nodeIterator = DataBase.get().getReferenceNode().traverse(Traverser.Order.DEPTH_FIRST,
																									StopEvaluator.DEPTH_ONE,
																									ReturnableEvaluator.ALL_BUT_START_NODE,
																									EntityRelashionship.AULAS, Direction.OUTGOING)
																									.iterator();

			@Override
			public boolean hasNext() {
				return nodeIterator.hasNext();
			}

			@Override
			public Aula next() {
				Node nextNode = nodeIterator.next();
				return new Aula(nextNode);

			}

			@Override
			public void remove() {
				nodeIterator.remove();
			}

		};

		return iterator;

	}

}
