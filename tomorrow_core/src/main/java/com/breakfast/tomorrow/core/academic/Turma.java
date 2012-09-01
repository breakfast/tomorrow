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

public class Turma {

	private static Logger LOG = Logger.getLogger(Turma.class);

	public Turma() {
	}

	/**
	 * Constants for indexes fields
	 */
	public final static String INDEX_ID_TURMA = "idTurma";
	public final static String INDEX_NOME_TURMA = "nomeTurma";

	/**
	 * Constants for fields
	 */
	public final static String ID_TURMA = "idTurma";
	public final static String NOME_TURMA = "nomeTurma";
	public final static String OBSERVACAO = "observacao";

	/**
	 * fields of class
	 */

	private long idTurma;
	private String nomeTurma;
	private String observacao;

	/**
	 * Prepare Node creating index for the class entity and setter properties in
	 * the node. this method used by persist elements in graph data base The
	 * node, can exist loaded from database or can it created for new node. Note
	 * : The SubClasses
	 * 
	 * @param node
	 */
	protected void prepareNode(Node node) {

		Utils.setNodeProperty(node, ID_TURMA, this.idTurma);
		Utils.setNodeProperty(node, NOME_TURMA, this.nomeTurma);
		Utils.setNodeProperty(node, OBSERVACAO, this.observacao);
	}

	/**
	 * Prepare Index for current values, if Node different of the null, remove a
	 * index for node. Use this method in subclasses if persist
	 * 
	 * @param node
	 */

	protected void prepareIndex(Node node) {
		Index<Node> idTurma = DataBase.get().index().forNodes(INDEX_ID_TURMA);
		Index<Node> nomeTurma = DataBase.get().index()
				.forNodes(INDEX_NOME_TURMA);
		if (node != null) {
			idTurma.remove(node, ID_TURMA, node.getProperty(ID_TURMA));
			nomeTurma.remove(node, NOME_TURMA, node.getProperty(NOME_TURMA));
		}
	}

	/***
	 * Add Index at creating new Node. Use this class in subclass method if
	 * persist;
	 * 
	 * @param node
	 */
	protected void addIndex(Node node) {
		Index<Node> idTurma = DataBase.get().index().forNodes(INDEX_ID_TURMA);
		Index<Node> nomeTurma = DataBase.get().index()
				.forNodes(INDEX_NOME_TURMA);
		idTurma.add(node, ID_TURMA, node.getProperty(ID_TURMA));
		nomeTurma.add(node, NOME_TURMA, node.getProperty(NOME_TURMA));
		// TODO verificar se ao atualizar o node, ou seja, o ALuno, ver se ele
		// estah criando outra relacao
		DataBase.get().getReferenceNode()
				.createRelationshipTo(node, EntityRelashionship.TURMA);
	}
	
	
	/**
	 * Graph Node
	 */
	protected Node node;
	
	/**
	 * Default Constructor for Turma
	 */
	public Turma(Turma turma){
		
	}
	
	public Turma(Node node){
		this.node = node;
		
	}
	
	public long getidTurma() {
		this.idTurma = node != null ? ((Long) node.getProperty(ID_TURMA)).longValue() : this.idTurma;
		return this.idTurma;
	}

	public void setidTurma(long idTurma) {
		this.idTurma= idTurma;
	}
	
	public String getnomeTurma(){
		this.nomeTurma = node != null ? ((String) node.getProperty(NOME_TURMA)): this.nomeTurma;
		return this.nomeTurma ;
	}
	
	public void setnomeTurma(String nomeTurma){
		this.nomeTurma = nomeTurma;
	}
	
	public String getObservacao(){
		this.observacao = node !=null ? ((String) node.getProperty(OBSERVACAO)): this.observacao;
		return this.observacao ; 
	}
	
	public void setObservacao(String observacao){
		this.observacao = observacao;
	}
	
	public static void persist(Turma turma) throws DataBaseException {
		String info = "NOT A INFO";
		boolean hasNode = turma.node != null;
		Transaction tx = DataBase.get().beginTx();
		turma.prepareIndex(turma.node);
		//Criar indice para alunos aqui.
		try {
			Node node;
			if(hasNode) {
				node = turma.node;
				info = Utils.UPDATED;
			}
			else { 
				node = DataBase.get().createNode();
				turma.setidTurma((EntityProperties.getID(Turma.class)));
				info = Utils.PESISTED;
			}
			turma.prepareNode(node);
			turma.addIndex(node);
			DataBase.get().getReferenceNode().createRelationshipTo(node, EntityRelashionship.TURMA);
			tx.success();
			LOG.info(turma.toString() + info); 
		} catch (Exception e) {
			tx.failure();
			LOG.error("Erro at Persist Turma ",e);
			throw new DataBaseException(e);
		} finally {
			tx.finish();
		}
	}
		
	public static Turma getTurmaPorId(long id){
		Node nodeFound = DataBase.get().index().forNodes(INDEX_ID_TURMA).get(ID_TURMA, id).getSingle();
		Turma tur = new Turma(nodeFound);
		return tur;
	}

	public Iterator<Turma> getTurmas(){
		   Iterator<Turma> iterator = new Iterator<Turma>(){

			
		   public final Iterator<Node> nodeIterator = DataBase.get().getReferenceNode().traverse(Traverser.Order.DEPTH_FIRST,
						  StopEvaluator.DEPTH_ONE,
						  ReturnableEvaluator.ALL_BUT_START_NODE,
						  EntityRelashionship.TURMA,
						  Direction.OUTGOING).iterator();

			
			
			
			@Override
			public boolean hasNext() {
				// TODO Auto-generated method stub
				return nodeIterator.hasNext();
			}

			@Override
			public Turma next() {
				// TODO Auto-generated method stub
				
				Node nextNode = nodeIterator.next();
				return new Turma(nextNode);
			}

			@Override
			public void remove() {
				// TODO Auto-generated method stub
				nodeIterator.remove();
			}
			
		};
		
		return iterator;
	}
	
	public static void delete(Turma turma) throws DataBaseException{
		Transaction transaction = DataBase.get().beginTx();
		try{
			String info = turma.toString();
			Utils.deleteRelantionShips(turma.node);
			turma.removeIndex(turma.node);
			turma.node.delete();
			transaction.success();
			LOG.info(info + Utils.DELETED);
		}
		catch (Exception e) {
			transaction.failure();
			LOG.error("Erro at Delete Turma ",e);
			throw new DataBaseException(e);
		}
		transaction.finish();
	}
	
	// removendo Turma pelo ID
	
	protected void removeIndex(Node node){
		
		Index<Node> idTurma = DataBase.get().index().forNodes(INDEX_ID_TURMA);
		Index<Node> nomeTurma = DataBase.get().index().forNodes(INDEX_NOME_TURMA);
		nomeTurma.remove(node, NOME_TURMA, node.getProperty(NOME_TURMA));
		idTurma.remove(node, ID_TURMA, node.getProperty(ID_TURMA));
	}

}
