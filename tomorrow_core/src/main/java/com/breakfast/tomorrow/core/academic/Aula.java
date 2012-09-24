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

public class Aula extends NodeEntity {
	
	private static NodeEntityManager<Aula> manager = new NodeEntityManager<Aula>();
	//private static Logger LOG = Logger.getLogger(Aula.class);
	
	/**
	 * fields of class
	 */
	@FieldNode private long data;
	@IndexNode private String descricao;

	/**
	 * Default Constructor for Aula
	 */
	public Aula() {}

	public Aula(Node node) {
		super(node);
	}

	public Date getData() {
		this.data = (Long) getProperty("data");
		Date date = new Date();
		date.setTime(this.data);
		return date;
	}

	public void setData(Date data) {
		this.data = data.getTime();

	}

	public String getDescricao() {
		this.descricao = (String) getProperty("descricao");
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public static void persist(Aula aula) throws DataBaseException {
		manager.persistir(aula);
		manager.createEntityRelationship(aula, EntityRelashionship.AULAS);
	}

	public static void delete(Aula aula) throws DataBaseException {
		manager.delete(aula);
	}
	
	public static Aula getAulaPorId(long id) {
		return manager.getNodeEntityById(id, Aula.class);
	}

	public Iterator<Aula> getAulas() {

		Iterator<Aula> iterator = new Iterator<Aula>() {

			public final Iterator<Node> nodeIterator = DataBase.get().getReferenceNode().traverse(Traverser.Order.DEPTH_FIRST,
																									StopEvaluator.DEPTH_ONE,
																									ReturnableEvaluator.ALL_BUT_START_NODE,
																									EntityRelashionship.AULAS, Direction.OUTGOING)
																									.iterator();
			@Override
			public boolean hasNext() {
				return nodeIterator.hasNext();
			}

			@Override
			public Aula next() {
				Node nextNode = nodeIterator.next();
				return new Aula(nextNode);

			}

			@Override
			public void remove() {
				nodeIterator.remove();
			}
			
		};
		return iterator;
	}

}
