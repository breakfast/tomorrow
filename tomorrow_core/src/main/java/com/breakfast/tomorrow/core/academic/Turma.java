package com.breakfast.tomorrow.core.academic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
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


public class Turma implements Cloneable, NodeRepository{

	private static NodeRepositoryManager<Turma> manager = new NodeRepositoryManager<Turma>();
	private static Logger LOG = Logger.getLogger(Turma.class);

	/**
	 * fields of class
	 */
	@IdNode private long id;
	@IndexNode private String nomeTurma;	
	@FieldNode private String observacao;
	@FieldNode private long inicio;
	@FieldNode private String turno;
	
	public Node getNode(){
		return manager.getNode("id", this.getId());
	}


	@Override
	protected Object clone() throws CloneNotSupportedException {
		Turma clone = new Turma();
		clone.setNomeTurma(this.nomeTurma);
		clone.setObservacao(this.observacao);
		clone.setTurno(this.turno);
		LOG.info("Turma " + clone + "clonada de " + this);
		return clone;
	}
	
	
	public List<Etapa> listaEtapas(){
		if(this.getNode()==null) return null;
		List<Etapa> lista = new ArrayList<Etapa>();
		Iterator<Node> it = this.getNode().traverse(Order.DEPTH_FIRST, 
													StopEvaluator.DEPTH_ONE, 
													ReturnableEvaluator.ALL_BUT_START_NODE, 
													Relacionamento.TEM,
													Direction.OUTGOING).iterator();
		while(it.hasNext()){
			lista.add((Etapa)manager.getObject(it.next(), Etapa.class));
		}
		return lista;
	}
	
	public void adiconarEtapa(Etapa etapa){
		if(etapa==null)throw new IllegalArgumentException("Etapa esta nula");
		if(etapa.getNode()==null)throw new IllegalArgumentException("Etapa não está persistida");
		Transaction tx = DataBase.get().beginTx();
		try{
			this.getNode().createRelationshipTo(etapa.getNode(), Relacionamento.TEM);
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
	
	public Curso getCurso(){
		return (Curso)manager.getObject(this.getNode().traverse(Order.DEPTH_FIRST, 
												 StopEvaluator.DEPTH_ONE, 
												 ReturnableEvaluator.ALL_BUT_START_NODE, 
												 Relacionamento.TEM,
												 Direction.INCOMING).iterator().next(), Curso.class);
	}
	
	public Turma getTurma(){
		return null;
	}

	public static void persist(Turma turma) throws RepositoryException {
		manager.persistir(turma);
		manager.createEntityRelationship(turma, EntityRelashionship.TURMA);	
	}
		
	public static Turma getTurmaPorId(long id){
		return manager.getNodeEntityById(id, Turma.class);
	}
	
	public static void delete(Turma turma) throws RepositoryException{
		manager.delete(turma);
	}

	public static Collection<Turma> getTurmas(){
		Iterator<Node> nodeIterator = DataBase.get().getReferenceNode().traverse(Traverser.Order.DEPTH_FIRST,
						  StopEvaluator.DEPTH_ONE,
						  ReturnableEvaluator.ALL_BUT_START_NODE,
						  EntityRelashionship.TURMA,
						  Direction.OUTGOING).iterator();
		Collection<Turma> lista = new ArrayList<Turma>();
		while(nodeIterator.hasNext()){
			lista.add(manager.get(nodeIterator.next(), Turma.class));
		}
		return lista;
	}
	
	public Turma() {
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}


	public String getNomeTurma(){
		return this.nomeTurma ;
	}
	
	public void setNomeTurma(String nomeTurma){
		this.nomeTurma = nomeTurma;
	}
	
	public String getObservacao(){
		return this.observacao; 
	}
	
	public void setObservacao(String observacao){
		this.observacao = observacao;
	}
	
	public Date getInicio() {
		Date date = new Date();
		date.setTime(this.inicio);
		return date;
	}

	public void setInicio(Date inicio) {
		if(inicio!=null) this.inicio = inicio.getTime();
	}

	public String getTurno() {
		return turno;
	}
	
	public void setTurno(String turno) {
		this.turno = turno;
	}
	
	@Override
	public String toString() {
		return "[" + this.id + "] " + this.nomeTurma;
	}
	

}
