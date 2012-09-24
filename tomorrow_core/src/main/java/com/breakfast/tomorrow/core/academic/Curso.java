package com.breakfast.tomorrow.core.academic;

import java.util.Iterator;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.ReturnableEvaluator;
import org.neo4j.graphdb.StopEvaluator;
import org.neo4j.graphdb.Traverser;
import com.breakfast.tomorrow.core.database.DataBase;
import com.breakfast.tomorrow.core.database.DataBaseException;
import com.breakfast.tomorrow.core.database.EntityRelashionship;
import com.breakfast.tomorrow.core.database.FieldNode;
import com.breakfast.tomorrow.core.database.IndexNode;
import com.breakfast.tomorrow.core.database.NodeEntity;
import com.breakfast.tomorrow.core.database.NodeEntityManager;

public class Curso extends NodeEntity{
	
	private static NodeEntityManager<Curso> manager = new NodeEntityManager<Curso>();
	//private static Logger LOG = Logger.getLogger(Curso.class);
	
	/**
	 * fields of class
	*/ 
	@IndexNode private String nomeCurso; 
	@FieldNode private String descricao;
	@FieldNode private String duracao; 
		
	/**
	 * Default Constructor for Curso
	 */
	public Curso(){}

	public Curso(Node node) {
		super(node);
	}
	
	public String getnomeCurso(){
		this.nomeCurso = (String) getProperty("nomeCurso");
		return this.nomeCurso ;
	}
	
	public void setnomeCurso(String nomeCurso){
		this.nomeCurso = nomeCurso;
	}
	
	public String getDescricao(){
		this.descricao = (String) getProperty("descricao");
		return this.descricao ; 
	}
	
	public void setDescricao(String descricao){
		this.descricao = descricao;
	}
	
	public String getDuracao (){
		this.duracao = (String) getProperty("duracao");
		return this.duracao;
	}
	
	public void setDuracao(String duracao){
		this.duracao = duracao ;
	}
	
		
	public static void persist(Curso curso) throws DataBaseException {
		manager.persistir(curso);
		manager.createEntityRelationship(curso, EntityRelashionship.CURSOS);
	}
	
	
	public static void delete(Curso curso) throws DataBaseException{
		manager.delete(curso);
	}
	
		
	public static Curso getCursoPorId(long id){
		return manager.getNodeEntityById(id, Curso.class);
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
			return nodeIterator.hasNext();
		}

		@Override
		public Curso next() {
			Node nextNode = nodeIterator.next();
			return new Curso(nextNode);
		}

		@Override
		public void remove() { 
			nodeIterator.remove();
			
		}

		
		};
		return iterator;
	}
}
