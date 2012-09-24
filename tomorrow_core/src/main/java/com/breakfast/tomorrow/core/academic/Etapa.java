package com.breakfast.tomorrow.core.academic;

import java.util.Date;
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

public class Etapa extends NodeEntity{
	
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

	
	public Etapa(Node node) {
		super(node);
	}
	

	public String getnomeEtapa(){
		this.nomeEtapa = (String) getProperty("nomeEtapa");
		return this.nomeEtapa ;
	}
	public void setnomeEtapa(String nomeEtapa){
		this.nomeEtapa = nomeEtapa ;
	}
	
	public Date getinicioEtapa(){
		this.inicioEtapa= (Date) getProperty("inicioEtapa");
		return this.inicioEtapa ;
	}
	
	public void setinicioEtapa(Date inicioEtapa){
		this.inicioEtapa = inicioEtapa;
				
	}
	
	public Date getfimEtapa(){
		this.fimEtapa = (Date) getProperty("fimEtapa");
		return this.fimEtapa;
	}
	
	public void setfimEtapa(Date fimEtapa){
		this.fimEtapa = fimEtapa;
				
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

	
	
	
	
	
	
	
	
	
	
	

}
