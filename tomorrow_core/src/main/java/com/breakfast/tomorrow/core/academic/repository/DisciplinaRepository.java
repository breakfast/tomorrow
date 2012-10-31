package com.breakfast.tomorrow.core.academic.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.ReturnableEvaluator;
import org.neo4j.graphdb.StopEvaluator;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.Traverser;
import org.neo4j.graphdb.Traverser.Order;

import com.breakfast.tomorrow.core.academic.vo.Diario;
import com.breakfast.tomorrow.core.academic.vo.Disciplina;
import com.breakfast.tomorrow.core.academic.vo.Professor;
import com.breakfast.tomorrow.core.database.DataBase;
import com.breakfast.tomorrow.core.database.EntityRelashionship;
import com.breakfast.tomorrow.core.database.NodeRepositoryManager;

public class DisciplinaRepository extends NodeRepositoryManager<Disciplina> {

	public Collection<Disciplina> getDisciplinas() {
		Iterator<Node> nodeIterator = DataBase
				.get()
				.getReferenceNode()
				.traverse(Traverser.Order.DEPTH_FIRST, StopEvaluator.DEPTH_ONE,
						ReturnableEvaluator.ALL_BUT_START_NODE,
						EntityRelashionship.DISCIPLINAS, Direction.OUTGOING)
				.iterator();
		Collection<Disciplina> lista = new ArrayList<Disciplina>();
		while (nodeIterator.hasNext()) {
			lista.add(get(nodeIterator.next(), Disciplina.class));
		}
		return lista;
	}

	public Disciplina getDisciplinaPorId(long id) {
		Node node = getNode("id", id, Disciplina.class);
		return carregar(node);
	}

	public Disciplina carregar(Node node) {
		Disciplina disciplina = get(node, Disciplina.class);
		return disciplina;
	}

	public void setProfessor(Disciplina disciplina) {
		if (disciplina == null)
			return;
		if (disciplina.getProfessor() == null)
			return;
		ProfessorRepository repo = new ProfessorRepository();
		Transaction tx = DataBase.get().beginTx();
		Node disciplina_ = getNode("id", disciplina.getId(), Disciplina.class);
		try {
			Iterator<Relationship> it = disciplina_.getRelationships(
					Relacionamento.TEM, Direction.OUTGOING).iterator();
			repo.persistir(disciplina.getProfessor());
			Node professor_ = getNode("id", disciplina.getProfessor(),
					Professor.class);
			disciplina_.createRelationshipTo(professor_, Relacionamento.TEM);
			while (it.hasNext()) {
				Relationship r = it.next();
				r.delete();
			}
			tx.success();
		} catch (Exception e) {
			tx.failure();
		} finally {
			tx.finish();
		}
	}

	public Professor getProfessor(Disciplina disciplina) {
		ProfessorRepository repo = new ProfessorRepository();
		Node disciplina_ = getNode("id", disciplina.getId(), Disciplina.class);
		Iterator<Node> it = disciplina_.traverse(Order.DEPTH_FIRST,
				StopEvaluator.DEPTH_ONE,
				ReturnableEvaluator.ALL_BUT_START_NODE, Relacionamento.TEM,
				Direction.OUTGOING).iterator();
		while (it.hasNext()) {
			return repo.carregar(it.next());
		}
		return null;
	}

	public void setDiario(Disciplina disciplina) {
		if (disciplina == null)
			return;
		if (disciplina.getDiario() == null)
			return;
		DiarioRepository repo = new DiarioRepository();
		Transaction tx = DataBase.get().beginTx();
		Node disciplina_ = getNode("id", disciplina.getId(), Disciplina.class);
		try {
			Iterator<Relationship> it = disciplina_.getRelationships(
					Relacionamento.TEM, Direction.OUTGOING).iterator();
			repo.persistir(disciplina.getDiario());
			Node diario_ = getNode("id", disciplina.getProfessor(),
					Diario.class);
			disciplina_.createRelationshipTo(diario_, Relacionamento.TEM);
			while (it.hasNext()) {
				Relationship r = it.next();
				r.delete();
			}
			tx.success();
		} catch (Exception e) {
			tx.failure();
		} finally {
			tx.finish();
		}
	}

	public Diario getDiario(Disciplina disciplina) {
		DiarioRepository repo = new DiarioRepository();
		Node disciplina_ = getNode("id", disciplina.getId(), Disciplina.class);
		Iterator<Node> it = disciplina_.traverse(Order.DEPTH_FIRST,
				StopEvaluator.DEPTH_ONE,
				ReturnableEvaluator.ALL_BUT_START_NODE, Relacionamento.TEM,
				Direction.OUTGOING).iterator();
		while (it.hasNext()) {
			return repo.carregar(it.next());
		}
		return null;
	}

}
