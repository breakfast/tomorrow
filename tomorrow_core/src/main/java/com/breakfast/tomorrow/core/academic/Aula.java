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
import org.neo4j.graphdb.Traverser;

import com.breakfast.tomorrow.core.database.DataBase;
import com.breakfast.tomorrow.core.database.IdNode;
import com.breakfast.tomorrow.core.database.NodeRepository;
import com.breakfast.tomorrow.core.database.NodeRepositoryManager;
import com.breakfast.tomorrow.core.database.RepositoryException;
import com.breakfast.tomorrow.core.database.EntityRelashionship;
import com.breakfast.tomorrow.core.database.FieldNode;
import com.breakfast.tomorrow.core.database.IndexNode;

public class Aula implements NodeRepository {
	
	private static NodeRepositoryManager<Aula> manager = new NodeRepositoryManager<Aula>();
	//private static Logger LOG = Logger.getLogger(Aula.class);
	
	/**
	 * fields of class
	 */
	@IdNode private long id;
	@FieldNode private long data;
	@IndexNode private String descricao;

	/**
	 * Default Constructor for Aula
	 */
	public Aula() {}
	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getData() {
		Date date = new Date();
		date.setTime(this.data);
		return date;
	}

	public void setData(Date data) {
		this.data = data.getTime();

	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public static void persist(Aula aula) throws RepositoryException {
		manager.persistir(aula);
		manager.createEntityRelationship(aula, EntityRelashionship.AULAS);
	}

	public static void delete(Aula aula) throws RepositoryException {
		manager.delete(aula);
	}
	
	public static Aula getAulaPorId(long id) {
		return manager.getNodeEntityById(id, Aula.class);
	}

	public Collection<Aula> getAulas() {
			Iterator<Node> nodeIterator = DataBase.get().getReferenceNode().traverse(Traverser.Order.DEPTH_FIRST,
				StopEvaluator.DEPTH_ONE,
				ReturnableEvaluator.ALL_BUT_START_NODE,
				EntityRelashionship.AULAS, Direction.OUTGOING).iterator();
			List<Aula> lista = new ArrayList<Aula>();
			while(nodeIterator.hasNext()){
				lista.add(manager.get(nodeIterator.next(), Aula.class));
			}
			
			return lista;
	}
	
	@Override
	public String toString() {
		return "[" + this.getId() + "]" + this.getDescricao();
	}

}
