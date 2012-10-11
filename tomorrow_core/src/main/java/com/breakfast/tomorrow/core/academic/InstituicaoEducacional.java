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


import com.breakfast.tomorrow.core.database.DataBase;
import com.breakfast.tomorrow.core.database.IdNode;
import com.breakfast.tomorrow.core.database.NodeRepository;
import com.breakfast.tomorrow.core.database.NodeRepositoryManager;
import com.breakfast.tomorrow.core.database.RepositoryException;	
import com.breakfast.tomorrow.core.database.EntityRelashionship;
import com.breakfast.tomorrow.core.database.IndexNode;

public class InstituicaoEducacional implements NodeRepository {

	private static NodeRepositoryManager<InstituicaoEducacional> manager = new NodeRepositoryManager<InstituicaoEducacional>();
	private static Logger LOG = Logger.getLogger(InstituicaoEducacional.class);

	/**
	 * fields of class
	 */
	@IdNode private long id;
	@IndexNode private String nomeInstituicao;

	
	public Node getNode() {
		return manager.getNode("id", this.getId());
	}
	
	public InstituicaoEducacional(){}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNomeInstituicao() {
		return nomeInstituicao;
	}

	public void setNomeInstituicao(String nomeInstituicao){
		this.nomeInstituicao = nomeInstituicao;
	}

	public void adicionarUnidade(UnidadeEducacional unidade){	
		if(unidade!=null){
			Transaction tx = DataBase.get().beginTx();
			try{
				this.getNode().createRelationshipTo(unidade.getNode(), Relacionamento.TEM);
				tx.success();
			}
			catch(Exception e){
				tx.failure();
				throw new RepositoryException(e);
			}
			finally{
				tx.finish();
				LOG.info("Unidade Educacional " + unidade + "Adicionada a Instituicao" + this);
			}
		}
		else throw new RuntimeException("Unidade está nula");
	}
	
	public void removerUnidade(UnidadeEducacional unidade){
		if(unidade==null) throw new IllegalArgumentException("Unidade está nula");
		if(unidade.getNode()==null) throw new IllegalArgumentException("Unidade não foi persistida");
		if(this.getNode()==null) throw new IllegalArgumentException("Instituição não está persistida");
		Transaction tx = DataBase.get().beginTx();
		try{
			Relationship relationship = unidade.getNode().getSingleRelationship(Relacionamento.TEM, Direction.INCOMING);
			relationship.delete();
			tx.success();
		}
		catch(Exception e){
			tx.failure();
			throw new RepositoryException(e);
		}
		finally{
			tx.finish();
			LOG.info("Unidade " + unidade + "removida");
		}
	}
	
	public List<UnidadeEducacional> listaUnidades(){
		List<UnidadeEducacional> lista = new ArrayList<UnidadeEducacional>();
		Iterator<Node> it = this.getNode().traverse(Traverser.Order.DEPTH_FIRST,
				  				StopEvaluator.DEPTH_ONE,
				  				ReturnableEvaluator.ALL_BUT_START_NODE,
				  				Relacionamento.TEM,
				  				Direction.OUTGOING).iterator();
		while(it.hasNext()){
			lista.add((UnidadeEducacional)manager.getObject(it.next(), UnidadeEducacional.class));
		}
		return lista;
	}
	
	
	public static void persist(InstituicaoEducacional instituicaoEducacional) throws RepositoryException {
		manager.persistir(instituicaoEducacional);
	}
	
	
	public static void delete(InstituicaoEducacional instituicaoEducacional) throws RepositoryException{
		manager.delete(instituicaoEducacional);
	}
	
	public static InstituicaoEducacional getInstituicaoPorId(long id){
		return manager.getNodeEntityById(id, InstituicaoEducacional.class);
	}
	
	
	public static Collection<InstituicaoEducacional> getInstituicoes(){
		Iterator<Node> nodeIterator = DataBase.get().getReferenceNode().traverse(Traverser.Order.DEPTH_FIRST,
				  StopEvaluator.DEPTH_ONE,
				  ReturnableEvaluator.ALL_BUT_START_NODE,
				  EntityRelashionship.INSTITUICAOES,
				  Direction.OUTGOING).iterator();
		Collection<InstituicaoEducacional> lista = new ArrayList<InstituicaoEducacional>();
		while(nodeIterator.hasNext()){
			lista.add(manager.get(nodeIterator.next(), InstituicaoEducacional.class));
		}
		return lista;

	}
	
}
