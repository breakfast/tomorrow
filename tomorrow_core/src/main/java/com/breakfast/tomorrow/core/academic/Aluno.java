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
import org.neo4j.graphdb.index.IndexHits;

import com.breakfast.base.Pessoa;
import com.breakfast.base.Utils;
import com.breakfast.tomorrow.core.database.DataBase;
import com.breakfast.tomorrow.core.database.DataBaseException;
import com.breakfast.tomorrow.core.database.EntityProperties;
import com.breakfast.tomorrow.core.database.EntityRelashionship;

/**
 * Classe representante da entidade Aluno.
 * @author administrador
 *
 */
public class Aluno extends Pessoa {
	
	private static Logger LOG = Logger.getLogger(Aluno.class);
	public static String OBSERVATION = "observation";
	
	private String observation;
	
	public Aluno(){}
	
	public Aluno(Node node) {
		super(node);
	}
	
	public String getObservation() {
		this.observation = node != null ? (String) node.getProperty(OBSERVATION) : this.observation;
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	@Override
	public Node getNode(){
		return this.node;
	}
	
	@Override
	protected void prepareNode(Node node){
		super.prepareNode(node);
		//node.setProperty(OBSERVATION, this.observation);
	}
	
	protected void prepareIndex(Node node){
		super.prepareIndex(node);
	}
	
	/**
	 * Persist Student on Graph DataBase
	 * @param student
	 */
	public static void persist(Aluno aluno) throws DataBaseException {
		String info = "NOT A INFO";
		boolean hasNode = aluno.node != null;
		Transaction tx = DataBase.get().beginTx();
		aluno.prepareIndex(aluno.node);
		//Criar indice para alunos aqui.
		try {
			Node node;
			if(hasNode) {
				node = aluno.node;
				info = Utils.UPDATED;
			}
			else { 
				node = DataBase.get().createNode();
				aluno.setIdPessoa(EntityProperties.getID(Aluno.class));
				info = Utils.PESISTED;
			}
			aluno.prepareNode(node);
			aluno.addIndex(node);
			DataBase.get().getReferenceNode().createRelationshipTo(node, EntityRelashionship.ALUNOS);
			tx.success();
			LOG.info(aluno.toString() + info); 
		} catch (Exception e) {
			tx.failure();
			LOG.error("Erro at Persist Student",e);
			throw new DataBaseException(e);
		} finally {
			tx.finish();
		}
	}
	
	
	/**
	 * Delete Student on Graph Database
	 * @param student
	 */
	public static boolean delete(Aluno aluno) throws DataBaseException{
		Transaction transaction = DataBase.get().beginTx();
		try{
			String info = aluno.toString();
			Utils.deleteRelantionShips(aluno.node);
			aluno.removeIndex(aluno.node);
			aluno.node.delete();
			transaction.success();
			LOG.info(info + Utils.DELETED);
		}
		catch (Exception e) {
			transaction.failure();
			LOG.error("Erro at Delete Student",e);
			throw new DataBaseException(e);
		}
		transaction.finish();
		return true;
	}
	
	/**
	 * Get a Students by Name
	 * @param name
	 * @return a list of the Students indexed by name
	 */
	public static List<Aluno> getAlunosPorNome(String nome){
		List<Aluno> alunoList = new ArrayList<Aluno>();
		IndexHits<Node> nodesFound = DataBase.get().index().forNodes(INDEX_NOME_PESSOA).get(NOME, nome);
		for(Node hitNode : nodesFound){
			Aluno aluno = new Aluno(hitNode);
			alunoList.add(aluno);
		}
		return alunoList;
	}
	
	/**
	 * Get Student by Id
	 * @param name
	 * @return a Student indexed by id property
	 */
	public static Aluno getAlunosPorId(long id){
		Node nodeFound = DataBase.get().index().forNodes(INDEX_PESSOA_ID).get(ID_PESSOA, id).getSingle();
		Aluno student = new Aluno(nodeFound);
		return student;
	}
	
	/**
	 * Captura todos alunos matriculados na escola
	 * @return um Iterator de alunos
	 */
	public static Iterator<Aluno> getAlunos(){
		
		Iterator<Aluno> iterator = new Iterator<Aluno>() {

			//Navega no grafo atraves da relação de entidade com Alunos (EntityRelashionship) capturando os nós de alunos
			public final Iterator<Node> nodeIterator = DataBase.get().getReferenceNode().traverse(Traverser.Order.DEPTH_FIRST,
																								  StopEvaluator.DEPTH_ONE,
																								  ReturnableEvaluator.ALL_BUT_START_NODE,
																								  EntityRelashionship.ALUNOS,
																								  Direction.OUTGOING).iterator();
					
				
			//implementa o metodo hasNext de iterator devolvendo o proximo nó do atributo nodeIterator
			@Override
			public boolean hasNext() {
				return nodeIterator.hasNext();
			}

			//implementa o metodo next de iterator devolvendo um Aluno 
			@Override
			public Aluno next() {
				Node nextNode = nodeIterator.next();
				return new Aluno(nextNode);
			}

			@Override
			public void remove() {
				nodeIterator.remove();
			}
		
		
		};
		
		return iterator;
	}
	
	public static List<Aluno> getListAlunos(){
		List<Aluno> list = new ArrayList<Aluno>();
		Iterator<Aluno> it = getAlunos();
		while(it.hasNext()){
			Aluno aluno = it.next();
			list.add(aluno);
		}
		return list; 
	}
	
	@Override
	public String toString(){
		return Utils.toStringObject(this, this.getClass());
	}

}
