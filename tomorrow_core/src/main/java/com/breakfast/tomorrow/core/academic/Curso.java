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

public class Curso {
	
	private static Logger LOG = Logger.getLogger(Curso.class);
	
	/**
	 * Constants for indexes fields "git hub"
	*/
	
	public final static String INDEX_ID_CURSO  =  "idCurso";
	public final static String INDEX_NOME_CURSO = "nomeCurso" ;
	
		
	/**
	 * Constants for fields
	 */
	public final static String ID_CURSO = "idCurso";
	public final static String NOME_CURSO = "cursoNome";
	public final static String DESCRICAO = "discricao";
	public final static String DURACAO = "duracao";
	
	
	/**
	 * fields of class
	*/
	
	private long id  ; 
	private String nomeCurso ; 
	private String descricao ;
	private String duracao ; 
	
	/**
	 * Prepare Node creating index for the class entity and setter properties in the node.
	 * this method used by persist elements in graph data base
	 * The node, can exist loaded from database or can it created for new node. 
	 * Note : The SubClasses  
	 * @param node
	 */
	protected void prepareNode(Node node){
		
		Utils.setNodeProperty(node,ID_CURSO,this.id);
		Utils.setNodeProperty(node,NOME_CURSO,this.nomeCurso);
		Utils.setNodeProperty(node,DESCRICAO,this.descricao);
		Utils.setNodeProperty(node,DURACAO,this.duracao);	
	}
	
	/**
	 * Prepare Index for current values, if Node different of the null, remove a index for node.
	 * Use this method in subclasses if persist
	 * @param node
	 */
	
	protected void prepareIndex(Node node){
		Index<Node> idcursos = DataBase.get().index().forNodes(INDEX_ID_CURSO);
		Index<Node> nomeCursos = DataBase.get().index().forNodes(INDEX_NOME_CURSO);
		if(node != null){
			idcursos.remove(node, ID_CURSO, node.getProperty(ID_CURSO));
			nomeCursos.remove(node,NOME_CURSO, node.getProperty(NOME_CURSO));
		}
	}
	
	/***
	 * Add Index at creating new Node.
	 * Use this class in subclass method if persist;
	 * @param node
	 */
	protected void addIndex(Node node){
		Index<Node> idcursos = DataBase.get().index().forNodes(INDEX_ID_CURSO);
		Index<Node> nomeCursos = DataBase.get().index().forNodes(INDEX_NOME_CURSO);
		idcursos.add(node, ID_CURSO, node.getProperty(ID_CURSO));
		nomeCursos.add(node, NOME_CURSO, node.getProperty(NOME_CURSO));
		
		DataBase.get().getReferenceNode().createRelationshipTo(node, EntityRelashionship.CURSOS);
	}
	
	protected void removeIndex(Node node){
		Index<Node> idcursos = DataBase.get().index().forNodes(INDEX_ID_CURSO);
		Index<Node> nomeCursos = DataBase.get().index().forNodes(INDEX_NOME_CURSO);
		idcursos.remove(node, ID_CURSO, node.getProperty(ID_CURSO));
		nomeCursos.remove(node, NOME_CURSO, node.getProperty(NOME_CURSO));
	}
	
	/**
	 * Graph Node
	 */
	protected Node node;
	
	/**
	 * Default Constructor for Curso
	 */
	public Curso(){}

	public Node getNode(){
		return this.node;
	}

	public Curso(Node node) {
		this.node = node;
	}
	
	public long getidCurso() {
		this.id = node != null ? ((Long) node.getProperty(ID_CURSO)).longValue() : this.id;
		return this.id;
	}

	public void setidCurso(long id) {
		this.id= id;
	}
	
	public String getnomeCurso(){
		this.nomeCurso = node != null ? ((String) node.getProperty(NOME_CURSO)): this.nomeCurso;
		return this.nomeCurso ;
	}
	
	public void setnomeCurso(String nomeCurso){
		this.nomeCurso = nomeCurso;
	}
	
	public String getDescricao(){
		this.descricao = node !=null ? ((String) node.getProperty(DESCRICAO)): this.descricao;
		return this.descricao ; 
	}
	
	public void setDescricao(String descricao){
		this.descricao = descricao;
	}
	
	public String getDuracao (){
		
		this.duracao = node!= null ? ((String) node.getProperty(DURACAO)): this.duracao;
		return this.duracao;
	}
	
	public void setDuracao(String duracao){
		this.duracao = duracao ;
	}
	
		
	public static void persist(Curso curso) throws DataBaseException {
		String info = "NOT A INFO";
		boolean hasNode = curso.node != null;
		Transaction tx = DataBase.get().beginTx();
		curso.prepareIndex(curso.node);
		//Criar indice para alunos aqui.
		try {
			Node node;
			if(hasNode) {
				node = curso.node;
				info = Utils.UPDATED;
			}
			else { 
				node = DataBase.get().createNode();
				curso.setidCurso(EntityProperties.getID(Curso.class));
				curso.node = node;
				info = Utils.PESISTED;
			}
			curso.prepareNode(node);
			curso.addIndex(node);
			DataBase.get().getReferenceNode().createRelationshipTo(node, EntityRelashionship.CURSOS);
			tx.success();
			LOG.info(curso.toString() + info); 
		} catch (Exception e) {
			tx.failure();
			LOG.error("Erro at Persist Curso",e);
			throw new DataBaseException(e);
		} finally {
			tx.finish();
		}
	}
	
	
	public static void delete(Curso curso) throws DataBaseException{
		Transaction transaction = DataBase.get().beginTx();
		try{
			String info = curso.toString();
			Utils.deleteRelantionShips(curso.node);
			curso.removeIndex(curso.node);
			curso.node.delete();
			transaction.success();
			LOG.info(info + Utils.DELETED);
		}
		catch (Exception e) {
			transaction.failure();
			LOG.error("Erro at Delete Curso ",e);
			throw new DataBaseException(e);
		}
		transaction.finish();
	}
	
		
	public static Curso getCursoPorId(long id){
		Node nodeFound = DataBase.get().index().forNodes(INDEX_ID_CURSO).get(ID_CURSO, id).getSingle();
		if(nodeFound != null){
			return new Curso(nodeFound);
		}
		return null;
	}
	
	public static Iterator<Curso> getCursos(){
		
		Iterator<Curso> iterator = new Iterator<Curso>() {
		
		
		public final Iterator<Node> nodeIterator = DataBase.get().getReferenceNode().traverse(Traverser.Order.DEPTH_FIRST,
				  StopEvaluator.DEPTH_ONE,
				  ReturnableEvaluator.ALL_BUT_START_NODE,
				  EntityRelashionship.CURSOS,
				  Direction.OUTGOING).iterator();

		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return nodeIterator.hasNext();
		}

		@Override
		public Curso next() {
			// TODO Auto-generated method stub
			Node nextNode = nodeIterator.next();
			return new Curso(nextNode);
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
