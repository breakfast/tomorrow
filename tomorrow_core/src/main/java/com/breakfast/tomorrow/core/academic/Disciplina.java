package com.breakfast.tomorrow.core.academic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

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

public class Disciplina implements NodeRepository {

	//private static Logger LOG = Logger.getLogger(Disciplina.class);
	private static NodeRepositoryManager<Disciplina> manager = new NodeRepositoryManager<Disciplina>();
	
	public Disciplina() {}
	
	public Disciplina(String nome){
		this.nomeDisciplina = nome;
	}
	
	public Disciplina(String nome, int indice){
		this.nomeDisciplina = nome;
		this.indice = indice;
	}

	@IdNode private long id;
	@IndexNode private String nomeDisciplina;
	@FieldNode private int indice;
	

	public Node getNode(){
		return manager.getNode("id", this.getId());
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNomeDisciplina() {
		return this.nomeDisciplina;
	}
	
	public int getIndice() {
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

	public static Collection<Disciplina> getDisciplinas() {
		Iterator<Node> nodeIterator = DataBase
					.get()
					.getReferenceNode()
					.traverse(Traverser.Order.DEPTH_FIRST,
							StopEvaluator.DEPTH_ONE,
							ReturnableEvaluator.ALL_BUT_START_NODE,
							EntityRelashionship.DISCIPLINAS, Direction.OUTGOING)
					.iterator();
		Collection<Disciplina> lista = new ArrayList<Disciplina>();
		while(nodeIterator.hasNext()){
			lista.add(manager.get(nodeIterator.next(), Disciplina.class));
		}
		return lista;
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		Disciplina clone =  new Disciplina();
		clone.setIndice(this.getIndice());
		clone.setNomeDisciplina(this.getNomeDisciplina());
		return clone;
	}

}
