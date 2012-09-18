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

public class Etapa {
	
	private static Logger LOG = Logger.getLogger(Etapa.class);

	public Etapa() {
	}

	/**
	 * Constants for indexes fields
	 */
	public final static String INDEX_ID_ETAPA = "idEtapa";
	public final static String INDEX_NOME_ETAPA = "nomeEtapa";

	/**
	 * Constants for fields
	 */
	public final static String ID_ETAPA = "idEtapa";
	public final static String NOME_ETAPA = "nomeEtapa";
	public final static String INICIO_ETAPA = "inicioEtapa";
	public final static String FIM_ETAPA = "fimEtapa";

	/**
	 * fields of class
	 */

	private long id;
	private String nomeEtapa;
	private Date inicioEtapa;
	private Date fimEtapa;

	/**
	 * Prepare Node creating index for the class entity and setter properties in
	 * the node. this method used by persist elements in graph data base The
	 * node, can exist loaded from database or can it created for new node. Note
	 * : The SubClasses
	 * 
	 * @param node
	 */
	protected void prepareNode(Node node) {

		Utils.setNodeProperty(node, ID_ETAPA, this.id);
		Utils.setNodeProperty(node, NOME_ETAPA, this.nomeEtapa);
		Utils.setNodeProperty(node, INICIO_ETAPA, this.inicioEtapa);
		Utils.setNodeProperty(node, FIM_ETAPA, this.fimEtapa);

	}

	/**
	 * Prepare Index for current values, if Node different of the null, remove a
	 * index for node. Use this method in subclasses if persist
	 * 
	 * @param node
	 */

	protected void prepareIndex(Node node) {
		Index<Node> idEtapa = DataBase.get().index().forNodes(INDEX_ID_ETAPA);
		Index<Node> nomeEtapa = DataBase.get().index()
				.forNodes(INDEX_NOME_ETAPA);
		if (node != null) {
			idEtapa.remove(node, ID_ETAPA, node.getProperty(ID_ETAPA));
			nomeEtapa.remove(node, NOME_ETAPA, node.getProperty(NOME_ETAPA));
		}
	}

	/***
	 * Add Index at creating new Node. Use this class in subclass method if
	 * persist;
	 * 
	 * @param node
	 */

	protected void addIndex(Node node) {
		Index<Node> idEtapa = DataBase.get().index().forNodes(INDEX_ID_ETAPA);
		Index<Node> nomeEtapa = DataBase.get().index().forNodes(INDEX_NOME_ETAPA);
		idEtapa.add(node, ID_ETAPA, node.getProperty(ID_ETAPA));
		nomeEtapa.add(node, NOME_ETAPA, node.getProperty(NOME_ETAPA));
		DataBase.get().getReferenceNode()
				.createRelationshipTo(node, EntityRelashionship.ETAPAS);


	}
	
	
	protected void removeIndex(Node node){
		Index<Node> idEtapa = DataBase.get().index().forNodes(INDEX_ID_ETAPA);
		Index<Node> nomeEtapa = DataBase.get().index().forNodes(INDEX_NOME_ETAPA);
		idEtapa.remove(node, ID_ETAPA, node.getProperty(ID_ETAPA));
		nomeEtapa.remove(node, NOME_ETAPA, node.getProperty(NOME_ETAPA));
	}

	/**
	 * Graph Node
	 */
	
	protected Node node;
	
	/**
	 * Default Constructor for Etapa
	 */
	public Etapa(Etapa etapa){
		
	}
	
	public Etapa(Node node) {
		this.node = node;
	}
	
	 // get e set 
	
	
	public long getidEtapa(){
		this.id= node != null ? ((Long) node.getProperty(ID_ETAPA)).longValue() : this.id;
		return this.id;
		
	}
	
	public void setidEtapa(long id){
		this.id = id;
	}
	
	public String getnomeEtapa(){
		this.nomeEtapa = node != null ? ((String) node.getProperty(NOME_ETAPA)): this.nomeEtapa;
		return this.nomeEtapa ;
	}
	public void setnomeEtapa(String nomeEtapa){
		this.nomeEtapa = nomeEtapa ;
	}
	
	public Date getinicioEtapa(){
		this.inicioEtapa= node != null ? ((Date) node.getProperty(INICIO_ETAPA)): this.inicioEtapa;
		return this.inicioEtapa ;
	}
	
	public void setinicioEtapa(Date inicioEtapa){
		this.inicioEtapa = inicioEtapa;
				
	}
	
	public Date getfimEtapa(){
		this.fimEtapa = node!=null ? ((Date) node.getProperty(FIM_ETAPA)): this.fimEtapa;
		return this.fimEtapa;
	}
	
	public void setfimEtapa(Date fimEtapa){
		this.fimEtapa = fimEtapa;
				
	}
	
	
	public static void persist(Etapa etapa) throws DataBaseException {
		String info = "NOT A INFO";
		boolean hasNode = etapa.node != null;
		Transaction tx = DataBase.get().beginTx();
		etapa.prepareIndex(etapa.node);
		//Criar indice para alunos aqui.
		try {
			Node node;
			if(hasNode) {
				node = etapa.node;
				info = Utils.UPDATED;
			}
			else { 
				node = DataBase.get().createNode();
				etapa.setidEtapa((EntityProperties.getID(Etapa.class)));
				info = Utils.PESISTED;
				etapa.node = node;
			}
			etapa.prepareNode(node);
			etapa.addIndex(node);
			DataBase.get().getReferenceNode().createRelationshipTo(node, EntityRelashionship.ETAPAS);
			tx.success();
			LOG.info(etapa.toString() + info); 
		} catch (Exception e) {
			tx.failure();
			LOG.error("Erro at Persist Etapa ",e);
			throw new DataBaseException(e);
		} finally {
			tx.finish();
		}
	}
	
	
	public static void delete(Etapa etapa) throws DataBaseException{
		Transaction transaction = DataBase.get().beginTx();
		try{
			String info = etapa.toString();
			Utils.deleteRelantionShips(etapa.node);
			etapa.removeIndex(etapa.node);
			etapa.node.delete();
			transaction.success();
			LOG.info(info + Utils.DELETED);
		}
		catch (Exception e) {
			transaction.failure();
			LOG.error("Erro at Delete Etapa ",e);
			throw new DataBaseException(e);
		}
		transaction.finish();
	}
	
		
	public static Etapa getEtapaPorId(long id){
		Node nodeFound = DataBase.get().index().forNodes(INDEX_ID_ETAPA).get(ID_ETAPA, id).getSingle();
		if(nodeFound != null){
			return new Etapa(nodeFound);
		}
		return null;
	}

	
	public Iterator<Etapa> getEtapas(){
		
		Iterator<Etapa> iterator = new Iterator<Etapa>() {

			
			public final Iterator<Node> nodeIterator = DataBase.get().getReferenceNode().traverse(Traverser.Order.DEPTH_FIRST,
					  StopEvaluator.DEPTH_ONE,
					  ReturnableEvaluator.ALL_BUT_START_NODE,
					  EntityRelashionship.ETAPAS,
					  Direction.OUTGOING).iterator();
		
			
			@Override
			public boolean hasNext() {
				return nodeIterator.hasNext();
			}

			@Override
			public Etapa next() {
				Node nextNode = nodeIterator.next();
				return new Etapa(nextNode);
				
			}

			@Override
			public void remove() {			
				nodeIterator.remove();
			}
		
		
		};
		
		return iterator ;
		
	}

	
	
	
	
	
	
	
	
	
	
	

}
