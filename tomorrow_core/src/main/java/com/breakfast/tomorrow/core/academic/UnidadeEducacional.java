package com.breakfast.tomorrow.core.academic;

import java.util.ArrayList;
import java.util.Collection;
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
import org.neo4j.graphdb.Traverser.Order;

import com.breakfast.tomorrow.core.database.DataBase;
import com.breakfast.tomorrow.core.database.IdNode;
import com.breakfast.tomorrow.core.database.NodeRepository;
import com.breakfast.tomorrow.core.database.NodeRepositoryManager;
import com.breakfast.tomorrow.core.database.RepositoryException;
import com.breakfast.tomorrow.core.database.EntityRelashionship;
import com.breakfast.tomorrow.core.database.FieldNode;
import com.breakfast.tomorrow.core.database.IndexNode;

public class UnidadeEducacional implements NodeRepository{
	
	private static NodeRepositoryManager<UnidadeEducacional> manager = new NodeRepositoryManager<UnidadeEducacional>();
	private static Logger LOG = Logger.getLogger(UnidadeEducacional.class);
	
	@IdNode private long id;
	@IndexNode private String nomeUnidade;
	@FieldNode private String local;
	
	public UnidadeEducacional() {}

	public Node getNode(){
		return manager.getNode("id", this.getId());
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNomeUnidade() {
		return nomeUnidade;
	}

	public void setNomeUnidade(String nomeUnidade) {
		this.nomeUnidade = nomeUnidade;
	}

	public String getLocal() {
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
			return (InstituicaoEducacional)manager.getObject(it.next(), InstituicaoEducacional.class);
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
			throw new RepositoryException(e);
		}
		finally{
			tx.finish();
			LOG.info("Curso " + curso + "Adicionada a Unidade" + this);
		} 
	}
	
	public Etapa getCurso(){
		Iterator<Node> it = this.getNode().traverse(Order.DEPTH_FIRST, 
												 StopEvaluator.DEPTH_ONE, 
												 ReturnableEvaluator.ALL_BUT_START_NODE, 
												 Relacionamento.TEM,
												 Direction.INCOMING).iterator();
		while(it.hasNext()){
			return (Etapa) manager.getObject(it.next(), Etapa.class);
		}
		return null;
	}
	
	public List<Curso> listarCursos(){
		if(this.getNode()==null) return null;
		List<Curso> lista = new ArrayList<Curso>();
		Iterator<Node> it = this.getNode().traverse(Traverser.Order.DEPTH_FIRST, 
													StopEvaluator.DEPTH_ONE, 
													ReturnableEvaluator.ALL_BUT_START_NODE, 
													Relacionamento.TEM,
													Direction.OUTGOING).iterator();
		while(it.hasNext()){
			lista.add((Curso) manager.getObject(it.next(), Curso.class));
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
			throw new RepositoryException(e);
		}
		finally{
			tx.finish();
		}
	}

	public static void persist(UnidadeEducacional unidadeEducacional) throws RepositoryException {
		manager.persistir(unidadeEducacional);
		manager.createEntityRelationship(unidadeEducacional, EntityRelashionship.UNIDADES);
	}
	
	
	public static void delete(UnidadeEducacional unidadeEducacional) throws RepositoryException{
		manager.delete(unidadeEducacional);
	}
	
	public static UnidadeEducacional getUnidadePorId(long id){
		return manager.getNodeEntityById(id, UnidadeEducacional.class);
	}
    
	
	public static Collection<UnidadeEducacional> getUnidades(){
		Iterator<Node> nodeIterator = DataBase.get().getReferenceNode().traverse(Traverser.Order.DEPTH_FIRST,
				  StopEvaluator.DEPTH_ONE,
				  ReturnableEvaluator.ALL_BUT_START_NODE,
				  EntityRelashionship.UNIDADES,
				  Direction.OUTGOING).iterator();
		Collection<UnidadeEducacional> lista = new ArrayList<UnidadeEducacional>();
		while(nodeIterator.hasNext()){
			lista.add(manager.get(nodeIterator.next(), UnidadeEducacional.class));
		}
		return lista;
	}
	

}
