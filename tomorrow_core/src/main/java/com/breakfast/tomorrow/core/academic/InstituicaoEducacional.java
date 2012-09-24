package com.breakfast.tomorrow.core.academic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import org.apache.log4j.Logger;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.ReturnableEvaluator;
import org.neo4j.graphdb.StopEvaluator;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.Traverser;


import com.breakfast.tomorrow.core.database.DataBase;
import com.breakfast.tomorrow.core.database.DataBaseException;	
import com.breakfast.tomorrow.core.database.EntityRelashionship;
import com.breakfast.tomorrow.core.database.IndexNode;
import com.breakfast.tomorrow.core.database.NodeEntity;
import com.breakfast.tomorrow.core.database.NodeEntityManager;

public class InstituicaoEducacional extends NodeEntity {

	private static NodeEntityManager<InstituicaoEducacional> manager = new NodeEntityManager<InstituicaoEducacional>();
	private static Logger LOG = Logger.getLogger(InstituicaoEducacional.class);

	/**
	 * fields of class
	 */
	@IndexNode private String nomeInstituicao;

	
	public Node getNode() {
		return this.node;
	}
	
	public InstituicaoEducacional(){}

	public InstituicaoEducacional(Node node) {
		super(node);
	}

	public String getNomeInstituicao() {
		this.nomeInstituicao = (String) getProperty("nomeInstituicao");
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
				throw new DataBaseException(e);
			}
			finally{
				tx.finish();
				LOG.info("Unidade Educacional " + unidade + "Adicionada a Instituicao" + this);
			}
		}
		else throw new RuntimeException("Unidade está nula");
	}
	
	public void removerUnidade(UnidadeEducacional unidade){
		if(unidade!=null){
			
		}
		else throw new RuntimeException("Unidade está nula");
	}
	
	public List<UnidadeEducacional> listaUnidade(){
		List<UnidadeEducacional> lista = new ArrayList<UnidadeEducacional>();
		Iterator<Node> it = this.getNode().traverse(Traverser.Order.DEPTH_FIRST,
				  				StopEvaluator.DEPTH_ONE,
				  				ReturnableEvaluator.ALL_BUT_START_NODE,
				  				Relacionamento.TEM,
				  				Direction.OUTGOING).iterator();
		while(it.hasNext()){
			lista.add(new UnidadeEducacional(it.next()));
		}
		return lista;
	}
	
	
	public static void persist(InstituicaoEducacional instituicaoEducacional) throws DataBaseException {
		manager.persistir(instituicaoEducacional);
	}
	
	
	public static void delete(InstituicaoEducacional instituicaoEducacional) throws DataBaseException{
		manager.delete(instituicaoEducacional);
	}
	
	public static InstituicaoEducacional getInstituicaoPorId(long id){
		return manager.getNodeEntityById(id, InstituicaoEducacional.class);
	}
	
	
	public static Iterator<InstituicaoEducacional> getInstituicoes(){
		
		Iterator<InstituicaoEducacional> iterator = new Iterator<InstituicaoEducacional>() {
		
		
		public final Iterator<Node> nodeIterator = DataBase.get().getReferenceNode().traverse(Traverser.Order.DEPTH_FIRST,
				  StopEvaluator.DEPTH_ONE,
				  ReturnableEvaluator.ALL_BUT_START_NODE,
				  EntityRelashionship.INSTITUICAOES,
				  Direction.OUTGOING).iterator();

		@Override
		public boolean hasNext() {
			return nodeIterator.hasNext();
		}

		@Override
		public InstituicaoEducacional next() {
			Node nextNode = nodeIterator.next();
			return new InstituicaoEducacional(nextNode);
		}

		@Override
		public void remove() { 
			nodeIterator.remove();
		}

		
		};
		return iterator;
	}
	
}
