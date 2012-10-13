package com.breakfast.tomorrow.core.academic.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.ReturnableEvaluator;
import org.neo4j.graphdb.StopEvaluator;
import org.neo4j.graphdb.Traverser;

import com.breakfast.tomorrow.core.academic.vo.Curso;
import com.breakfast.tomorrow.core.database.DataBase;
import com.breakfast.tomorrow.core.database.EntityRelashionship;
import com.breakfast.tomorrow.core.database.NodeRepositoryManager;

public class CursoRepository extends NodeRepositoryManager<Curso>{
	
	public Collection<Curso> getCursos(){
		Iterator<Node> nodeIterator = DataBase.get().getReferenceNode().traverse(Traverser.Order.DEPTH_FIRST,
				  StopEvaluator.DEPTH_ONE,
				  ReturnableEvaluator.ALL_BUT_START_NODE,
				  EntityRelashionship.CURSOS,
				  Direction.OUTGOING).iterator();
		List<Curso> lista = new ArrayList<Curso>();
		while(nodeIterator.hasNext()){
			get(nodeIterator.next(), Curso.class);
		}
		return lista;
	}
	
	public Curso getCursoPorId(long id){
		return getNodeEntityByIndex("id",id, Curso.class);
	}

}
