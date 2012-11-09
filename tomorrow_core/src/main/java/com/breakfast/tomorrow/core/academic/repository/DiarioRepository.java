package com.breakfast.tomorrow.core.academic.repository;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.ReturnableEvaluator;
import org.neo4j.graphdb.StopEvaluator;
import org.neo4j.graphdb.Traverser;
import com.breakfast.tomorrow.core.academic.vo.Diario;
import com.breakfast.tomorrow.core.database.DataBase;
import com.breakfast.tomorrow.core.database.EntityRelashionship;
import com.breakfast.tomorrow.core.database.NodeRepositoryManager;

public class DiarioRepository extends NodeRepositoryManager<Diario>{
	
	public Diario getDiarioPorId(long id){
		Node node = getNode("id", id, Diario.class);
		return carregar(node);
	}
	
	public Diario carregar(Node node){
		Diario diario = get(node,Diario.class);
		return diario;
	}
	
	@Override
	public void persistir(Diario nodeEntity) {
		super.persistir(nodeEntity);
		super.createEntityRelationship(nodeEntity, EntityRelashionship.DIARIOS);
	}
	
	public Collection<Diario> getDiarioes() {
		Iterator<Node> nodeIterator = DataBase.get()
					.getReferenceNode()
					.traverse(Traverser.Order.DEPTH_FIRST,
							StopEvaluator.DEPTH_ONE,
							ReturnableEvaluator.ALL_BUT_START_NODE,
							EntityRelashionship.PROFESSORES, Direction.OUTGOING)
					.iterator();
		Collection<Diario> lista = new ArrayList<Diario>();
		while(nodeIterator.hasNext()){
			Diario diario = carregar(nodeIterator.next());
			lista.add(diario);
		}
		return lista;
	}
	

}
