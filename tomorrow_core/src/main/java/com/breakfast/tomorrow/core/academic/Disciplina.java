package com.breakfast.tomorrow.core.academic;

import java.util.Iterator;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.ReturnableEvaluator;
import org.neo4j.graphdb.StopEvaluator;
import org.neo4j.graphdb.Traverser;

import com.breakfast.tomorrow.core.database.DataBase;
import com.breakfast.tomorrow.core.database.DataBaseException;
import com.breakfast.tomorrow.core.database.EntityRelashionship;
import com.breakfast.tomorrow.core.database.IndexNode;
import com.breakfast.tomorrow.core.database.NodeEntity;
import com.breakfast.tomorrow.core.database.NodeEntityManager;

public class Disciplina extends NodeEntity {

	//private static Logger LOG = Logger.getLogger(Disciplina.class);
	private static NodeEntityManager<Disciplina> manager = new NodeEntityManager<Disciplina>();
	
	public Disciplina() {
	}

	@IndexNode private String nomeDisciplina;

	public Disciplina(Node node) {
		super(node);
	}

	public String getNomeDisciplina() {
		this.nomeDisciplina = (String) getProperty("nomeDisciplina");
		return this.nomeDisciplina;
	}

	public void setNomeDisciplina(String nomeDisciplina) {
		this.nomeDisciplina = nomeDisciplina;
	}

	public static void persist(Disciplina disciplina) throws DataBaseException {
		manager.persistir(disciplina);
		manager.createEntityRelationship(disciplina, EntityRelashionship.DISCIPLINAS);
	}
	
	
	public static void delete(Disciplina disciplina) throws DataBaseException{
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
				// TODO Auto-generated method stub
				return nodeIterator.hasNext();
			}

			@Override
			public Disciplina next() {
				// TODO Auto-generated method stub
				Node nextNode = nodeIterator.next();
				return new Disciplina(nextNode);
			}

			@Override
			public void remove() {
				// TODO Auto-generated method stub
				nodeIterator.remove();

			}
		};

		return iterator;

	}

}
