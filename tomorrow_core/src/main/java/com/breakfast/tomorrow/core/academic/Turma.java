package com.breakfast.tomorrow.core.academic;

import java.util.ArrayList;
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
import com.breakfast.tomorrow.core.database.DataBaseException;
import com.breakfast.tomorrow.core.database.EntityRelashionship;
import com.breakfast.tomorrow.core.database.FieldNode;
import com.breakfast.tomorrow.core.database.IndexNode;
import com.breakfast.tomorrow.core.database.NodeEntity;
import com.breakfast.tomorrow.core.database.NodeEntityManager;


public class Turma extends NodeEntity implements Cloneable{

	private static NodeEntityManager<Turma> manager = new NodeEntityManager<Turma>();
	private static Logger LOG = Logger.getLogger(Turma.class);

	/**
	 * fields of class
	 */
	@IndexNode private String nomeTurma;	
	@FieldNode private String observacao;
	@FieldNode private long inicio;
	@FieldNode private String turno;


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
			lista.add(new Etapa(it.next()));
		}
		if (lista.size() == 0) return null;
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
			throw new DataBaseException(e);
		}
		finally{
			tx.finish();
		}
	}
	
	public Curso getCurso(){
		return new Curso(this.getNode().traverse(Order.DEPTH_FIRST, 
												 StopEvaluator.DEPTH_ONE, 
												 ReturnableEvaluator.ALL_BUT_START_NODE, 
												 Relacionamento.TEM,
												 Direction.INCOMING).iterator().next());
	}
	
	public Turma getTurma(){
		return null;
	}

	public static void persist(Turma turma) throws DataBaseException {
		manager.persistir(turma);
		manager.createEntityRelationship(turma, EntityRelashionship.TURMA);	
	}
		
	public static Turma getTurmaPorId(long id){
		return manager.getNodeEntityById(id, Turma.class);
	}
	
	public static void delete(Turma turma) throws DataBaseException{
		manager.delete(turma);
	}

	public static Iterator<Turma> getTurmas(){
		   Iterator<Turma> iterator = new Iterator<Turma>(){

			
		   public final Iterator<Node> nodeIterator = DataBase.get().getReferenceNode().traverse(Traverser.Order.DEPTH_FIRST,
						  StopEvaluator.DEPTH_ONE,
						  ReturnableEvaluator.ALL_BUT_START_NODE,
						  EntityRelashionship.TURMA,
						  Direction.OUTGOING).iterator();

			@Override
			public boolean hasNext() {
				return nodeIterator.hasNext();
			}

			@Override
			public Turma next() {
				Node nextNode = nodeIterator.next();
				return new Turma(nextNode);
			}

			@Override
			public void remove() {
				nodeIterator.remove();
			}
			
		};
		
		return iterator;
	}
	
	public Turma() {
	}
	
	public Turma(Node node){
		super(node);
	}
	
	public String getNomeTurma(){
		this.nomeTurma = (String) getProperty("nomeTurma");
		return this.nomeTurma ;
	}
	
	public void setNomeTurma(String nomeTurma){
		this.nomeTurma = nomeTurma;
	}
	
	public String getObservacao(){
		this.observacao = (String) getProperty("observacao");
		return this.observacao; 
	}
	
	public void setObservacao(String observacao){
		this.observacao = observacao;
	}
	
	public Date getInicio() {
		this.inicio = (Long) getProperty("inicio");
		Date date = new Date();
		date.setTime(this.inicio);
		return date;
	}

	public void setInicio(Date inicio) {		
		this.inicio = inicio.getTime();
	}

	public String getTurno() {
		this.turno = (String) getProperty("turno");
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
