package com.breakfast.tomorrow.core.academic;

import java.util.Iterator;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.ReturnableEvaluator;
import org.neo4j.graphdb.StopEvaluator;
import org.neo4j.graphdb.Traverser;

import com.breakfast.tomorrow.core.database.DataBase;
import com.breakfast.tomorrow.core.database.RepositoryException;
import com.breakfast.tomorrow.core.database.EntityRelashionship;
import com.breakfast.tomorrow.core.database.FieldNode;
import com.breakfast.tomorrow.core.database.IndexNode;
import com.breakfast.tomorrow.core.database.NodeEntity;
import com.breakfast.tomorrow.core.database.NodeEntityManager;

public class Disciplina extends NodeEntity {

	//private static Logger LOG = Logger.getLogger(Disciplina.class);
	private static NodeEntityManager<Disciplina> manager = new NodeEntityManager<Disciplina>();
	
	public Disciplina() {}
	
	public Disciplina(String nome){
		this.nomeDisciplina = nome;
	}
	
	public Disciplina(String nome, int indice){
		this.nomeDisciplina = nome;
		this.indice = indice;
	}

	@IndexNode private String nomeDisciplina;
	@FieldNode private int indice;

	public Disciplina(Node node) {
		super(node);
	}

	public String getNomeDisciplina() {
		this.nomeDisciplina = (String) getProperty("nomeDisciplina");
		return this.nomeDisciplina;
	}
	
	public int getIndice() {
		this.indice = (Integer) getProperty("indice");
		return this.indice;
	}


	public void setIndice(int indice) {
		this.indice = indice;
	}

	public void setNomeDisciplina(String nomeDisciplina) {
		this.nomeDisciplina = nomeDisciplina;
	}

	public static void persist(Disciplina disciplina) throws RepositoryException {
		manager.persistir(disciplina);
		manager.createEntityRelationship(disciplina, EntityRelashionship.DISCIPLINAS);
	}
	
	
	public static void delete(Disciplina disciplina) throws RepositoryException{
		manager.delete(disciplina);
	}

	public static Disciplina getDisciplinaPorId(long id) {
		return manager.getNodeEntityById(id, Disciplina.class);
		
	}

	public static Iterator<Disciplina> getDisciplinas() {

		Iterator<Disciplina> iterator = new Iterator<Disciplina>() {

			public final Iterator<Node> nodeIterator = DataBase
					.get()
					.getReferenceNode()
					.traverse(Traverser.Order.DEPTH_FIRST,
							StopEvaluator.DEPTH_ONE,
							ReturnableEvaluator.ALL_BUT_START_NODE,
							EntityRelashionship.DISCIPLINAS, Direction.OUTGOING)
					.iterator();

			@Override
			public boolean hasNext() {
				return nodeIterator.hasNext();
			}

			@Override
			public Disciplina next() {
				Node nextNode = nodeIterator.next();
				return new Disciplina(nextNode);
			}

			@Override
			public void remove() {
				nodeIterator.remove();

			}
		};

		return iterator;

	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		Disciplina clone =  new Disciplina();
		clone.setIndice(this.getIndice());
		clone.setNomeDisciplina(this.getNomeDisciplina());
		return clone;
	}

}
