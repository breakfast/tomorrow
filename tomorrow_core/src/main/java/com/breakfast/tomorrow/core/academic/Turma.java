package com.breakfast.tomorrow.core.academic;

import java.util.Date;
import java.util.Iterator;

import org.apache.log4j.Logger;
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


public class Turma extends NodeEntity implements Cloneable{

	private static NodeEntityManager<Turma> manager = new NodeEntityManager<Turma>();
	private static Logger LOG = Logger.getLogger(Turma.class);

	/**
	 * fields of class
	 */
	@IndexNode private String nomeTurma;	
	@FieldNode private String observacao;
	@FieldNode private Date inicio;
	@FieldNode private String turno;


	@Override
	protected Object clone() throws CloneNotSupportedException {
		Turma clone = new Turma();
		clone.setnomeTurma(this.nomeTurma);
		clone.setObservacao(this.observacao);
		clone.setTurno(this.turno);
		LOG.info("Turma " + clone + "clonada de " + this);
		return clone;
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
	
	public String getnomeTurma(){
		this.nomeTurma = (String) getProperty("nomeTurma");
		return this.nomeTurma ;
	}
	
	public void setnomeTurma(String nomeTurma){
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
		this.inicio = (Date) getProperty("inicio");
		return inicio;
	}

	public void setInicio(Date inicio) {		
		this.inicio = inicio;
	}

	public String getTurno() {
		this.turno = (String) getProperty("turno");
		return turno;
	}
	
	public void setTurno(String turno) {
		this.turno = turno;
	}
	
	

}
