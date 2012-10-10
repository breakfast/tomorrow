package com.breakfast.tomorrow.core.academic;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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

public class Etapa extends NodeEntity {
	
	private static NodeEntityManager<Etapa> manager = new NodeEntityManager<Etapa>();
	//private static Logger LOG = Logger.getLogger(Etapa.class);

	public Etapa() {
	}

	/**
	 * fields of class
	 */
	@IndexNode private String nomeEtapa;
	@FieldNode private Date inicioEtapa;
	@FieldNode private Date fimEtapa;
	@FieldNode private int indice;

	
	public Etapa(Node node) {
		super(node);
	}
	
	public List<Disciplina> listaDisciplinas(){
		if(this.getNode()==null) return null;
		List<Disciplina> lista = new ArrayList<Disciplina>();
		Iterator<Node> it = this.getNode().traverse(Order.DEPTH_FIRST, 
													StopEvaluator.DEPTH_ONE, 
													ReturnableEvaluator.ALL_BUT_START_NODE, 
													Relacionamento.TEM,
													Direction.OUTGOING).iterator();
		while(it.hasNext()){
			lista.add(new Disciplina(it.next()));
		}
		return lista;
	}
	
	public void adiconarEtapa(Disciplina disciplina){
		if(disciplina==null)throw new IllegalArgumentException("Etapa esta nula");
		if(disciplina.getNode()==null)throw new IllegalArgumentException("Etapa não está persistida");
		Transaction tx = DataBase.get().beginTx();
		try{
			this.getNode().createRelationshipTo(disciplina.getNode(), Relacionamento.TEM);
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

	public String getNomeEtapa(){
		this.nomeEtapa = (String) getProperty("nomeEtapa");
		return this.nomeEtapa ;
	}
	public void setNomeEtapa(String nomeEtapa){
		this.nomeEtapa = nomeEtapa ;
	}
	
	public Date getInicioEtapa(){
		this.inicioEtapa= (Date) getProperty("inicioEtapa");
		return this.inicioEtapa ;
	}
	
	public void setInicioEtapa(Date inicioEtapa){
		this.inicioEtapa = inicioEtapa;
				
	}
	
	public Date getFimEtapa(){
		this.fimEtapa = (Date) getProperty("fimEtapa");
		return this.fimEtapa;
	}
	
	public void setFimEtapa(Date fimEtapa){
		this.fimEtapa = fimEtapa;
				
	}
	
	public int getIndice() {
		this.indice = (Integer) getProperty("indice");
		return this.indice;
	}


	public void setIndice(int indice) {
		this.indice = indice;
	}


	public static void persist(Etapa etapa) throws DataBaseException {
		manager.persistir(etapa);
		manager.createEntityRelationship(etapa, EntityRelashionship.ETAPAS);
	}
	
	
	public static void delete(Etapa etapa) throws DataBaseException{
		manager.delete(etapa);
	}
	
		
	public static Etapa getEtapaPorId(long id){
		return manager.getNodeEntityById(id, Etapa.class);
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
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		Etapa etapa = new Etapa();
		etapa.setIndice(this.getIndice());
		etapa.setInicioEtapa(this.getInicioEtapa());
		etapa.setFimEtapa(this.getFimEtapa());
		etapa.setNomeEtapa(this.getNomeEtapa());
		return etapa;
	}

}
