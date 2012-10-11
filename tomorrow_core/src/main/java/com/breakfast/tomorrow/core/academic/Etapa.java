package com.breakfast.tomorrow.core.academic;

import java.util.ArrayList;
import java.util.Collection;
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
import com.breakfast.tomorrow.core.database.IdNode;
import com.breakfast.tomorrow.core.database.NodeRepository;
import com.breakfast.tomorrow.core.database.NodeRepositoryManager;
import com.breakfast.tomorrow.core.database.RepositoryException;
import com.breakfast.tomorrow.core.database.EntityRelashionship;
import com.breakfast.tomorrow.core.database.FieldNode;
import com.breakfast.tomorrow.core.database.IndexNode;

public class Etapa implements NodeRepository{
	
	private static NodeRepositoryManager<Etapa> manager = new NodeRepositoryManager<Etapa>();
	//private static Logger LOG = Logger.getLogger(Etapa.class);

	public Etapa() {
	}

	/**
	 * fields of class
	 */
	@IdNode private long id;
	@IndexNode private String nomeEtapa;
	@FieldNode private Date inicioEtapa;
	@FieldNode private Date fimEtapa;
	@FieldNode private int indice;

	public Node getNode(){
		return manager.getNode("id", this.getId());
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
			lista.add(new NodeRepositoryManager<Disciplina>().get(it.next(), Disciplina.class));
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
			throw new RepositoryException(e);
		}
		finally{
			tx.finish();
		}
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNomeEtapa(){
		return this.nomeEtapa ;
	}
	public void setNomeEtapa(String nomeEtapa){
		this.nomeEtapa = nomeEtapa ;
	}
	
	public Date getInicioEtapa(){
		return this.inicioEtapa ;
	}
	
	public void setInicioEtapa(Date inicioEtapa){
		this.inicioEtapa = inicioEtapa;
				
	}
	
	public Date getFimEtapa(){
		return this.fimEtapa;
	}
	
	public void setFimEtapa(Date fimEtapa){
		this.fimEtapa = fimEtapa;
				
	}
	
	public int getIndice() {
		return this.indice;
	}


	public void setIndice(int indice) {
		this.indice = indice;
	}


	public static void persist(Etapa etapa) throws RepositoryException {
		manager.persistir(etapa);
		manager.createEntityRelationship(etapa, EntityRelashionship.ETAPAS);
	}
	
	
	public static void delete(Etapa etapa) throws RepositoryException{
		manager.delete(etapa);
	}
	
		
	public static Etapa getEtapaPorId(long id){
		return manager.getNodeEntityById(id, Etapa.class);
	}

	
	public Collection<Etapa> getEtapas(){
		Iterator<Node> nodeIterator = DataBase.get().getReferenceNode().traverse(Traverser.Order.DEPTH_FIRST,
					  StopEvaluator.DEPTH_ONE,
					  ReturnableEvaluator.ALL_BUT_START_NODE,
					  EntityRelashionship.ETAPAS,
					  Direction.OUTGOING).iterator();
		List<Etapa> lista = new ArrayList<Etapa>();
		while(nodeIterator.hasNext()){
			lista.add(manager.get(nodeIterator.next(), Etapa.class));
		}
		return lista;
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
