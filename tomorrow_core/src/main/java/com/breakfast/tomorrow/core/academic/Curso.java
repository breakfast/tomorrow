package com.breakfast.tomorrow.core.academic;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
		adicionarTurma(turma);
		return turma;
	}
	
	public Turma abrirTurma(String nome, Turma from) throws CloneNotSupportedException{
		Turma turma = (Turma)from.clone();
		turma.setnomeTurma(nome);
		adicionarTurma(turma);
		return turma;
	}
	
	
	/**
	 * Salva a configuracao do Curso corrente no Banco de Dados
	 * @param configuracao
	 */
	public void salvarConfiguracao(Map<Etapa, List<Disciplina>> configuracao){
		if(configuracao==null) throw new IllegalArgumentException("Configuração está nula");
		if(this.getNode()==null) throw new IllegalArgumentException("Curso não está persistido");
		Transaction tx = DataBase.get().beginTx();
		try{
			for(Etapa etapa : configuracao.keySet()){
				NodeEntityManager<Etapa> managerE = new NodeEntityManager<Etapa>();
				managerE.ignoreIndexs(true);
				managerE.persistir(etapa);
				this.getNode().createRelationshipTo(etapa.getNode(),Relacionamento.CONFIGURADO);
				
				for(Disciplina disciplina : configuracao.get(etapa)){
					NodeEntityManager<Disciplina> managerD = new NodeEntityManager<Disciplina>();
					managerD.ignoreIndexs(true);
					managerD.persistir(disciplina);
					etapa.getNode().createRelationshipTo(disciplina.getNode(), Relacionamento.CONFIGURADO);
				}
				
			}
			tx.finish();
		}
		catch(Exception e){
			tx.failure();
			throw new DataBaseException(e);
		}
		finally{
			tx.finish();
		}
	}
	
	public Map<Etapa, List<Disciplina>> carregarConfiguracao(){
		Map<Etapa,List<Disciplina>> configuracao = new HashMap<Etapa, List<Disciplina>>();
		Iterator<Node> it = this.getNode().traverse(Order.DEPTH_FIRST, 
													StopEvaluator.DEPTH_ONE, 
													ReturnableEvaluator.ALL_BUT_START_NODE, 
													Relacionamento.CONFIGURADO,
													Direction.OUTGOING).iterator();
		while(it.hasNext()){
			Etapa etapa = new Etapa(it.next());
			List<Disciplina> listaDisciplina = new ArrayList<Disciplina>();
			Iterator<Node> it_ = etapa.getNode().traverse(Order.DEPTH_FIRST, 
														  StopEvaluator.DEPTH_ONE, 
														  ReturnableEvaluator.ALL_BUT_START_NODE, 
														  Relacionamento.CONFIGURADO, 
														  Direction.OUTGOING).iterator();
			while(it_.hasNext()){
				listaDisciplina.add(new Disciplina(it.next()));
			}
			configuracao.put(etapa, listaDisciplina);
		}
		return configuracao;
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
