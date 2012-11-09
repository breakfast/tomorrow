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

import com.breakfast.tomorrow.core.academic.vo.Usuario;
import com.breakfast.tomorrow.core.database.DataBase;
import com.breakfast.tomorrow.core.database.EntityRelashionship;
import com.breakfast.tomorrow.core.database.NodeRepositoryManager;

public class UsuarioRepository extends NodeRepositoryManager<Usuario> {

	
	@Override
	public void persistir(Usuario nodeEntity) {
		super.persistir(nodeEntity);
		super.createEntityRelationship(nodeEntity, EntityRelashionship.USUARIOS);
	}
	
	public Usuario carregar(Node node){
		Usuario usuario  = get(node, Usuario.class);
		return usuario;
	}
	
	public Usuario getUsuarioPorId(long id){
		Node node = getNode("id",id,Usuario.class);
		Usuario usuario = carregar(node);
		return usuario;
	}
	
	public Collection<Usuario> getUsuarios(){
		Iterator<Node> nodeIterator = DataBase.get().getReferenceNode().traverse(
				Traverser.Order.DEPTH_FIRST,
				StopEvaluator.DEPTH_ONE,
				ReturnableEvaluator.ALL_BUT_START_NODE,
				EntityRelashionship.USUARIOS,
				Direction.OUTGOING).iterator();
		List<Usuario> lista = new ArrayList<Usuario>();
		while(nodeIterator.hasNext()){
			lista.add(this.get(nodeIterator.next(), Usuario.class));
		}
		System.out.println("Lista Usuario : " + lista.size());
		return lista;
	}
	
	
	
	
}
