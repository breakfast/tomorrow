package com.breakfast.tomorrow.core.academic;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.RelationshipType;
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

public class Curso extends NodeEntity{
	
	private static NodeEntityManager<Curso> manager = new NodeEntityManager<Curso>();
	//private static Logger LOG = Logger.getLogger(Curso.class);
	
	/**
	 * fields of class
	*/ 
	@IndexNode private String nomeCurso; 
	@FieldNode private String descricao;
	@FieldNode private String duracao;
	
	
	private void adicionarTurma(Turma turma){
		if(turma==null) throw new IllegalArgumentException("Turma esta nulo");
		if(turma.getNode()==null) throw new IllegalArgumentException("Turma não foi persistida");
		if(this.getNode()==null) throw new IllegalArgumentException("Curso Não foi persistido");
		Transaction tx = DataBase.get().beginTx();
		try{
			this.getNode().createRelationshipTo(turma.getNode(), Relacionamento.TEM);
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
	
	public Turma abrirTurma(String nome, Date inicio, String turno){
		Turma turma = new Turma();
		turma.setnomeTurma(nome);
		turma.setInicio(inicio);
		turma.setTurno(turno);
		abrirTurma(turma);
		return turma;
	}
	
	public Turma abrirTurma(String nome, Turma from) throws CloneNotSupportedException{
		Turma turma = (Turma)from.clone();
		turma.setnomeTurma(nome);
		abrirTurma(turma);
		return turma;
	}
	
	public void abrirTurma(Turma turma){
		Map<Etapa, List<Disciplina>> cfg = this.carregarConfiguracao();
		Turma.persist(turma);
		salvarTurma(turma, cfg);
		adicionarTurma(turma);
	}
	
	
	/**
	 * Salva a configuracao do Curso corrente no Banco de Dados
	 * @param configuracao
	 */
	public void criarNodeEtapaDisplina(Node node, Map<Etapa, List<Disciplina>> configuracao, RelationshipType relationship, boolean ignoreIndexs ){
		if(configuracao==null) throw new IllegalArgumentException("Configuração está nula");
		if(node==null) throw new IllegalArgumentException("Curso / Turma não está persistido");
		Transaction tx = DataBase.get().beginTx();
		try{
			for(Etapa etapa : configuracao.keySet()){
				Etapa cloneE = ignoreIndexs ? etapa : (Etapa) etapa.clone();
				NodeEntityManager<Etapa> managerE = new NodeEntityManager<Etapa>();
				managerE.ignoreIndexs(ignoreIndexs);
				managerE.persistir(cloneE);
				node.createRelationshipTo(cloneE.getNode(),relationship);
				
				for(Disciplina disciplina : configuracao.get(etapa)){
					Disciplina cloneD = ignoreIndexs ? disciplina : (Disciplina) disciplina.clone();
					NodeEntityManager<Disciplina> managerD = new NodeEntityManager<Disciplina>();
					managerD.ignoreIndexs(ignoreIndexs);
					managerD.persistir(cloneD);
					cloneE.getNode().createRelationshipTo(cloneD.getNode(), relationship);
				}
				
			}
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

	
	public Map<Etapa, List<Disciplina>> carregarNodeEtapaDisciplina(Node node,RelationshipType relationship){
		Map<Etapa,List<Disciplina>> configuracao = new HashMap<Etapa, List<Disciplina>>();
		Iterator<Node> it = getNodeConfiguracao(node,relationship);
		while(it.hasNext()){
			Etapa etapa = new Etapa(it.next());
			List<Disciplina> listaDisciplina = new ArrayList<Disciplina>();
			Iterator<Node> it_ = getNodeConfiguracao(etapa.getNode(),relationship);
			while(it_.hasNext()){
				listaDisciplina.add(new Disciplina(it_.next()));
			}
			configuracao.put(etapa, listaDisciplina);
		}
		return configuracao;
	}
	
	public void removerNodeEtapaDisciplina(Node node,Map<Etapa, List<Disciplina>> configuracao, RelationshipType relationship){
		Transaction tx = DataBase.get().beginTx();
		try{
			Iterator<Node> it = getNodeConfiguracao(node,relationship);
			while(it.hasNext()){
				Node node_ = it.next();
				Iterator<Node> it_ = getNodeConfiguracao(node_,relationship);
				while(it_.hasNext()){
					Node node__ = it_.next();
					node__.delete();
				}
				node.delete();
			}
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
	
	private Iterator<Node> getNodeConfiguracao(Node node, RelationshipType relation){
		return node.traverse(Order.DEPTH_FIRST, 
							 StopEvaluator.DEPTH_ONE, 
							 ReturnableEvaluator.ALL_BUT_START_NODE, 
							 relation,
							 Direction.OUTGOING).iterator();
	}
	
	public void salvarConfiguracao(Map<Etapa, List<Disciplina>> configuracao){
		removerConfiguracao(configuracao);
		criarNodeEtapaDisplina(this.getNode(), configuracao, Relacionamento.CONFIGURADO, true);
	}
	
	public Map<Etapa, List<Disciplina>> carregarConfiguracao(){
		return carregarNodeEtapaDisciplina(this.getNode(), Relacionamento.CONFIGURADO);
	}
	
	public void removerConfiguracao(Map<Etapa, List<Disciplina>> configuracao){
		removerNodeEtapaDisciplina(this.getNode(),configuracao, Relacionamento.CONFIGURADO);
	}
	
	public void salvarTurma(Turma turma,Map<Etapa, List<Disciplina>> configuracao){
		criarNodeEtapaDisplina(turma.getNode(),configuracao, Relacionamento.TEM, false);
	}
	
	public Map<Etapa, List<Disciplina>> carregarTurma(Turma turma){
		return carregarNodeEtapaDisciplina(turma.getNode(),Relacionamento.TEM);
	}
	
	public void removerTurma(Turma turma, Map<Etapa, List<Disciplina>> configuracao){
		removerNodeEtapaDisciplina(turma.getNode(),configuracao, Relacionamento.TEM);
	}
	
		
	public static void persist(Curso curso) throws DataBaseException {
		manager.persistir(curso);
		manager.createEntityRelationship(curso, EntityRelashionship.CURSOS);
	}
	
	
	public static void delete(Curso curso) throws DataBaseException{
		manager.delete(curso);
	}
	
		
	public static Curso getCursoPorId(long id){
		return manager.getNodeEntityById(id, Curso.class);
	}

	
	public static Iterator<Curso> getCursos(){
		
		Iterator<Curso> iterator = new Iterator<Curso>() {
		
		public final Iterator<Node> nodeIterator = DataBase.get().getReferenceNode().traverse(Traverser.Order.DEPTH_FIRST,
				  StopEvaluator.DEPTH_ONE,
				  ReturnableEvaluator.ALL_BUT_START_NODE,
				  EntityRelashionship.CURSOS,
				  Direction.OUTGOING).iterator();

		@Override
		public boolean hasNext() {
			return nodeIterator.hasNext();
		}

		@Override
		public Curso next() {
			Node nextNode = nodeIterator.next();
			return new Curso(nextNode);
		}

		@Override
		public void remove() { 
			nodeIterator.remove();
			
		}
		
		};
		return iterator;
	}
	
	/**
	 * Default Constructor for Curso
	 */
	public Curso(){}

	public Curso(Node node) {
		super(node);
	}
	
	public String getnomeCurso(){
		this.nomeCurso = (String) getProperty("nomeCurso");
		return this.nomeCurso ;
	}
	
	public void setnomeCurso(String nomeCurso){
		this.nomeCurso = nomeCurso;
	}
	
	public String getDescricao(){
		this.descricao = (String) getProperty("descricao");
		return this.descricao ; 
	}
	
	public void setDescricao(String descricao){
		this.descricao = descricao;
	}
	
	public String getDuracao (){
		this.duracao = (String) getProperty("duracao");
		return this.duracao;
	}
	
	public void setDuracao(String duracao){
		this.duracao = duracao;
	}
}
