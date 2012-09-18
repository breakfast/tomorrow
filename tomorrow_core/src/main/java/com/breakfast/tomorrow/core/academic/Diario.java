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

public class Diario {
	
	
    private static Logger LOG = Logger.getLogger(Diario.class);
	
	/**
	 * Constants for indexes fields 
	*/
	
	public final static String INDEX_ID_DIARIO  =  "id";
	
	/**
	 * Constants for fields
	 */
	public final static String ID_DIARIO = "id";
	
	
	private long id;
	
	/**
	 * Prepare Node creating index for the class entity and setter properties in the node.
	 * this method used by persist elements in graph data base
	 * The node, can exist loaded from database or can it created for new node. 
	 * Note : The SubClasses  
	 * @param node
	 */
	protected void prepareNode(Node node){		
		Utils.setNodeProperty(node,ID_DIARIO,this.id);	
	}
	
	/**
	 * Prepare Index for current values, if Node different of the null, remove a index for node.
	 * Use this method in subclasses if persist
	 * @param node
	 */
	
	protected void prepareIndex(Node node){
		  Index<Node> idDiario = DataBase.get().index().forNodes(INDEX_ID_DIARIO);
	
		if(node != null){
		   idDiario.remove(node, ID_DIARIO, node.getProperty(ID_DIARIO));
			
		}
	}
	
	/***
	 * Add Index at creating new Node.
	 * Use this class in subclass method if persist;
	 * @param node
	 */
	protected void addIndex(Node node){
		Index<Node> idDiario = DataBase.get().index().forNodes(INDEX_ID_DIARIO);
	    idDiario.add(node, ID_DIARIO, node.getProperty(ID_DIARIO));		
		DataBase.get().getReferenceNode().createRelationshipTo(node, EntityRelashionship.DIARIOS);
	}
	
	
	protected void removeIndex(Node node){
		Index<Node> idDiario = DataBase.get().index().forNodes(INDEX_ID_DIARIO);
		idDiario.remove(node, ID_DIARIO, node.getProperty(ID_DIARIO));

	}
	
	/**
	 * Graph Node
	 */
	protected Node node;
	
	/**
	 * Default Constructor for Curso
	 */
	public Diario(){}

	
	public Node getNode(){
		return this.node;
	}

	public Diario (Node node){
		
	}

	public long getId() {
		this.id = node != null ? ((Long) node.getProperty(ID_DIARIO)).longValue() : this.id;
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public static void persist(Diario diario) throws DataBaseException {
		String info = "NOT A INFO";
		boolean hasNode = diario.node != null;
		Transaction tx = DataBase.get().beginTx();
		diario.prepareIndex(diario.node);
		//Criar indice para alunos aqui.
		try {
			Node node;
			if(hasNode) {
				node = diario.node;
				info = Utils.UPDATED;
			}
			else { 
				node = DataBase.get().createNode();
				diario.setId(EntityProperties.getID(Diario.class));
				diario.node = node;
				info = Utils.PESISTED;
			}
			diario.prepareNode(node);
			diario.addIndex(node);
			DataBase.get().getReferenceNode().createRelationshipTo(node, EntityRelashionship.DIARIOS);
			tx.success();
			LOG.info(diario.toString() + info); 
		} catch (Exception e) {
			tx.failure();
			LOG.error("Erro at Persist Diario",e);
			throw new DataBaseException(e);
		} finally {
			tx.finish();
		}
	}
	
	
	public static void delete(Diario diario) throws DataBaseException{
		Transaction transaction = DataBase.get().beginTx();
		try{
			String info = diario.toString();
			Utils.deleteRelantionShips(diario.node);
			diario.removeIndex(diario.node);
			diario.node.delete();
			transaction.success();
			LOG.info(info + Utils.DELETED);
		}
		catch (Exception e) {
			transaction.failure();
			LOG.error("Erro at Delete Diario ",e);
			throw new DataBaseException(e);
		}
		transaction.finish();
	}
	

	public static Diario getDiarioPorId(long id){
		Node nodeFound = DataBase.get().index().forNodes(INDEX_ID_DIARIO).get(ID_DIARIO, id).getSingle();
		if(nodeFound != null){
			return new Diario(nodeFound);
		}
		return null;
	}
	
    public static Iterator<Diario> getDiarios(){
		
		Iterator<Diario> iterator = new Iterator<Diario>() {
		
		
		public final Iterator<Node> nodeIterator = DataBase.get().getReferenceNode().traverse(Traverser.Order.DEPTH_FIRST,
				  StopEvaluator.DEPTH_ONE,
				  ReturnableEvaluator.ALL_BUT_START_NODE,
				  EntityRelashionship.DIARIOS,
				  Direction.OUTGOING).iterator();

		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return nodeIterator.hasNext();
		}

		@Override
		public Diario next() {
			// TODO Auto-generated method stub
			Node nextNode = nodeIterator.next();
			return new Diario(nextNode);
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
