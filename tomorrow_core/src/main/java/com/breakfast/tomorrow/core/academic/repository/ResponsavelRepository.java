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
import com.breakfast.tomorrow.core.academic.vo.Responsavel;
import com.breakfast.tomorrow.core.database.DataBase;
import com.breakfast.tomorrow.core.database.EntityRelashionship;
import com.breakfast.tomorrow.core.database.NodeRepositoryManager;

public class ResponsavelRepository extends NodeRepositoryManager<Responsavel> {
	
	@Override
	public void persistir(Responsavel nodeEntity) {
		super.persistir(nodeEntity);
		super.createEntityRelationship(nodeEntity, EntityRelashionship.RESPONSAVEIS);
	}
	
	public Responsavel getResponsavelPorId(long id){
		
		Node node = getNode("id",id,Responsavel.class);
		Responsavel responsavel = carregar(node);
		return responsavel;
		
	}
	
	public Responsavel carregar(Node node){
		Responsavel responsavel = get(node, Responsavel.class);
		return responsavel;
	}

	public Collection<Responsavel> getResponsaveis(){
		Iterator<Node> nodeIterator = DataBase.get().getReferenceNode().traverse(
				Traverser.Order.DEPTH_FIRST,
				StopEvaluator.DEPTH_ONE,
				ReturnableEvaluator.ALL_BUT_START_NODE,
				EntityRelashionship.RESPONSAVEIS,
				Direction.OUTGOING).iterator();
		List<Responsavel> lista = new ArrayList<Responsavel>();
		while(nodeIterator.hasNext()){
			lista.add(this.get(nodeIterator.next(), Responsavel.class));
		}
		
		return lista;
	}

}
