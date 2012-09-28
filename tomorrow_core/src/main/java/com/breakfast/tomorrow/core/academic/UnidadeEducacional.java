package com.breakfast.tomorrow.core.academic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.ReturnableEvaluator;
import org.neo4j.graphdb.StopEvaluator;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.Traverser;

import com.breakfast.tomorrow.core.database.DataBase;
import com.breakfast.tomorrow.core.database.DataBaseException;
import com.breakfast.tomorrow.core.database.EntityRelashionship;
import com.breakfast.tomorrow.core.database.FieldNode;
import com.breakfast.tomorrow.core.database.IndexNode;
import com.breakfast.tomorrow.core.database.NodeEntity;
import com.breakfast.tomorrow.core.database.NodeEntityManager;

public class UnidadeEducacional extends NodeEntity{
	
	private static NodeEntityManager<UnidadeEducacional> manager = new NodeEntityManager<UnidadeEducacional>();
	private static Logger LOG = Logger.getLogger(UnidadeEducacional.class);
	
	@IndexNode private String nomeUnidade; 
	@FieldNode private String local;
	
	public UnidadeEducacional() {}

	public UnidadeEducacional (Node node) {
		super(node);
	}

	public String getNomeUnidade() {
		this.nomeUnidade = (String) getProperty("nomeUnidade");
		return nomeUnidade;
	}

	public void setNomeUnidade(String nomeUnidade) {
		this.nomeUnidade = nomeUnidade;
	}

	public String getLocal() {
		this.local =  (String) getProperty("local");
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}
	
	public InstituicaoEducacional getInstituicaoEducacional(){
		Iterator<Node> it = this.getNode().traverse(Traverser.Order.DEPTH_FIRST,
  													StopEvaluator.DEPTH_ONE,
  													ReturnableEvaluator.ALL_BUT_START_NODE,
  													Relacionamento.TEM,
  													Direction.INCOMING).iterator();
		while(it.hasNext()){
			return new InstituicaoEducacional(it.next());
		}
		return null;
	}
	
	public void adicionarCurso(Curso curso){
		if(curso==null) throw new IllegalArgumentException("Unidade está nula");
		if(curso.getNode()==null) throw new IllegalArgumentException("Curso nao foi persistido");
		if(this.getNode()==null) throw new IllegalArgumentException("Unidade não foi persistida");
		Transaction tx = DataBase.get().beginTx();
		try{
			this.getNode().createRelationshipTo(curso.getNode(), Relacionamento.TEM);
			tx.success();
		}
		catch(Exception e){
			tx.failure();
			throw new DataBaseException(e);
		}
		finally{
			tx.finish();
			LOG.info("Curso " + curso + "Adicionada a Unidade" + this);
		} 
	}
	
	public List<Curso> listaCursos(){
		List<Curso> lista = new ArrayList<Curso>();
		Iterator<Node> it = this.getNode().traverse(Traverser.Order.DEPTH_FIRST, 
													StopEvaluator.DEPTH_ONE, 
													ReturnableEvaluator.ALL_BUT_START_NODE, 
													Relacionamento.TEM,
													Direction.OUTGOING).iterator();
		while(it.hasNext()){
			lista.add(new Curso(it.next()));
		}
		return lista;
	}
	
	public void removerCurso(Curso curso){

		Transaction tx = DataBase.get().beginTx();
		try{
			Relationship relationship = curso.getNode().getSingleRelationship(Relacionamento.TEM, Direction.INCOMING);
			relationship.delete();
			tx.success();
		}
		catch(Exception e){
			tx.failure();
			throw new DataBaseException(e);
		}
		finally{
			tx.finish();
		}
	}

	public static void persist(UnidadeEducacional unidadeEducacional) throws DataBaseException {
		manager.persistir(unidadeEducacional);
	}
	
	
	public static void delete(UnidadeEducacional unidadeEducacional) throws DataBaseException{
		manager.delete(unidadeEducacional);
	}
	
	public static UnidadeEducacional getUnidadePorId(long id){
		return manager.getNodeEntityById(id, UnidadeEducacional.class);
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
