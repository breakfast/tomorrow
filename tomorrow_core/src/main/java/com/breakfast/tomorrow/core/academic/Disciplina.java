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

public class Disciplina {

	private static Logger LOG = Logger.getLogger(Disciplina.class);

	public Disciplina() {
	}

	/**
	 * Constants for indexes fields
	 */

	public final static String INDEX_ID_DISCIPLINA = "idDisciplina";
	public final static String INDEX_NOME_DISCIPLINA = "nomeDisciplina";

	/**
	 * Constants for fields
	 */
	public final static String ID_DISCIPLINA = "idDisciplina";
	public final static String NOME_DISCIPLINA = "nomeDisciplina";

	/**
	 * Constants for fields
	 */

	private long idDisciplina;
	private String nomeDisciplina;

	/**
	 * Prepare Node creating index for the class entity and setter properties in
	 * the node. this method used by persist elements in graph data base The
	 * node, can exist loaded from database or can it created for new node. Note
	 * : The SubClasses
	 * 
	 * @param node
	 */
	protected void prepareNode(Node node) {

		Utils.setNodeProperty(node, ID_DISCIPLINA, this.idDisciplina);
		Utils.setNodeProperty(node, NOME_DISCIPLINA, this.nomeDisciplina);

	}

	/**
	 * Prepare Index for current values, if Node different of the null, remove a
	 * index for node. Use this method in subclasses if persist
	 * 
	 * @param node
	 */

	protected void prepareIndex(Node node) {
		Index<Node> idDisciplina = DataBase.get().index()
				.forNodes(INDEX_ID_DISCIPLINA);
		Index<Node> nomeDisciplina = DataBase.get().index()
				.forNodes(INDEX_NOME_DISCIPLINA);
		if (node != null) {
			idDisciplina.remove(node, ID_DISCIPLINA,
					node.getProperty(ID_DISCIPLINA));
			nomeDisciplina.remove(node, NOME_DISCIPLINA,
					node.getProperty(NOME_DISCIPLINA));
		}
	}

	/***
	 * Add Index at creating new Node. Use this class in subclass method if
	 * persist;
	 * 
	 * @param node
	 */
	protected void addIndex(Node node) {
		Index<Node> idDisciplina = DataBase.get().index()
				.forNodes(INDEX_ID_DISCIPLINA);
		Index<Node> nomeDisciplina = DataBase.get().index()
				.forNodes(INDEX_NOME_DISCIPLINA);

		idDisciplina.add(node, ID_DISCIPLINA, node.getProperty(ID_DISCIPLINA));
		nomeDisciplina.add(node, NOME_DISCIPLINA,
				node.getProperty(NOME_DISCIPLINA));
		// TODO verificar se ao atualizar o node, ou seja, o DISCIPLINA, ver se
		// ele esta criando outra relacao
		DataBase.get().getReferenceNode()
				.createRelationshipTo(node, EntityRelashionship.DISCIPLINA);
	}

	protected void removeIndex(Node node) {
		Index<Node> idDisciplina = DataBase.get().index()
				.forNodes(INDEX_ID_DISCIPLINA);
		Index<Node> nomeDisciplina = DataBase.get().index()
				.forNodes(INDEX_NOME_DISCIPLINA);

		idDisciplina.remove(node, ID_DISCIPLINA,
				node.getProperty(ID_DISCIPLINA));
		nomeDisciplina.remove(node, NOME_DISCIPLINA,
				node.getProperty(NOME_DISCIPLINA));
	}

	/**
	 * Graph Node
	 */
	protected Node node;

	/**
	 * Default Constructor for Disciplina
	 */
	public Disciplina(Disciplina disciplina) {

	}

	public Disciplina(Node node) {
		this.node = node;
	}

	public long getIdDisciplina() {
		this.idDisciplina = node != null ? ((Long) node
				.getProperty(ID_DISCIPLINA)).longValue() : this.idDisciplina;
		return this.idDisciplina;
	}

	public void setIdDisciplina(long idDisciplina) {
		this.idDisciplina = idDisciplina;
	}

	public String getNomeDisciplina() {
		this.nomeDisciplina = node != null ? ((String) node
				.getProperty(nomeDisciplina)) : this.nomeDisciplina;
		return this.nomeDisciplina;
	}

	public void setNomeDisciplina(String nomeDisciplina) {
		this.nomeDisciplina = nomeDisciplina;
	}

	public static void persist(Disciplina disciplina) throws DataBaseException {
		String info = "NOT A INFO";
		boolean hasNode = disciplina.node != null;
		Transaction tx = DataBase.get().beginTx();
		disciplina.prepareIndex(disciplina.node);
		// Criar indice para alunos aqui.
		try {
			Node node;
			if (hasNode) {
				node = disciplina.node;
				info = Utils.UPDATED;
			} else {
				node = DataBase.get().createNode();
				disciplina.setIdDisciplina(EntityProperties
						.getID(Disciplina.class));
				info = Utils.PESISTED;
			}
			disciplina.prepareNode(node);
			disciplina.addIndex(node);
			DataBase.get().getReferenceNode()
					.createRelationshipTo(node, EntityRelashionship.DISCIPLINA);
			tx.success();
			LOG.info(disciplina.toString() + info);
		} catch (Exception e) {
			tx.failure();
			LOG.error("Erro at Persist disciplina", e);
			throw new DataBaseException(e);
		} finally {
			tx.finish();
		}
	}
	
	
	public static void delete(Disciplina disciplina) throws DataBaseException{
		Transaction transaction = DataBase.get().beginTx();
		try{
			String info = disciplina.toString();
			Utils.deleteRelantionShips(disciplina.node);
			disciplina.removeIndex(disciplina.node);
			disciplina.node.delete();
			transaction.success();
			LOG.info(info + Utils.DELETED);
		}
		catch (Exception e) {
			transaction.failure();
			LOG.error("Erro at Delete disciplina ",e);
			throw new DataBaseException(e);
		}
		transaction.finish();
	}

	public static Disciplina getDisciplinaPorId(long id) {
		Node nodeFound = DataBase.get().index().forNodes(INDEX_ID_DISCIPLINA)
				.get(ID_DISCIPLINA, id).getSingle();
		Disciplina dis = new Disciplina(nodeFound);
		return dis;
	}

	public static Iterator<Disciplina> getDisciplinas() {

		Iterator<Disciplina> iterator = new Iterator<Disciplina>() {

			public final Iterator<Node> nodeIterator = DataBase
					.get()
					.getReferenceNode()
					.traverse(Traverser.Order.DEPTH_FIRST,
							StopEvaluator.DEPTH_ONE,
							ReturnableEvaluator.ALL_BUT_START_NODE,
							EntityRelashionship.DISCIPLINA, Direction.OUTGOING)
					.iterator();

			@Override
			public boolean hasNext() {
				// TODO Auto-generated method stub
				return nodeIterator.hasNext();
			}

			@Override
			public Disciplina next() {
				// TODO Auto-generated method stub
				Node nextNode = nodeIterator.next();
				return new Disciplina(nextNode);
			}

			@Override
			public void remove() {
				// TODO Auto-generated method stub
				nodeIterator.remove();

			}
		};

		return iterator;

	}

}
