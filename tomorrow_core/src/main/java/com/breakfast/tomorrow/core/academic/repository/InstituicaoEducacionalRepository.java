package com.breakfast.tomorrow.core.academic.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.ReturnableEvaluator;
import org.neo4j.graphdb.StopEvaluator;
import org.neo4j.graphdb.Traverser;

import com.breakfast.tomorrow.core.academic.vo.InstituicaoEducacional;
import com.breakfast.tomorrow.core.database.DataBase;
import com.breakfast.tomorrow.core.database.EntityRelashionship;
import com.breakfast.tomorrow.core.database.NodeRepositoryManager;

public class InstituicaoEducacionalRepository extends NodeRepositoryManager<InstituicaoEducacional>{

	public Collection<InstituicaoEducacional> getInstituicoes(){
		Iterator<Node> nodeIterator = DataBase.get().getReferenceNode().traverse(Traverser.Order.DEPTH_FIRST,
				  StopEvaluator.DEPTH_ONE,
				  ReturnableEvaluator.ALL_BUT_START_NODE,
				  EntityRelashionship.INSTITUICAOES,
				  Direction.OUTGOING).iterator();
		Collection<InstituicaoEducacional> lista = new ArrayList<InstituicaoEducacional>();
		while(nodeIterator.hasNext()){
			lista.add(get(nodeIterator.next(), InstituicaoEducacional.class));
		}
		return lista;

	}
	
	public InstituicaoEducacional getInstituicaoPorId(long id){
		return getNodeEntityById(id, InstituicaoEducacional.class);
	}
	
//	public void adicionarUnidades(Collection<UnidadeEducacional> unidades){	
//		if(unidades==null) return;
//			Transaction tx = DataBase.get().beginTx();
//			try{
//				for(UnidadeEducacional unidade : unidades){
//					Node node = getNode("id", unidade.getId(), UnidadeEducacional.class);
//					this.getNode().createRelationshipTo(unidade.getNode(), Relacionamento.TEM);
//				}
//				tx.success();
//			}
//			catch(Exception e){
//				tx.failure();
//				throw new RepositoryException(e);
//			}
//			finally{
//				tx.finish();
//				LOG.info("Unidade Educacional " + unidade + "Adicionada a Instituicao" + this);
//			}
//		}
//		else throw new RuntimeException("Unidade está nula");
//	}
//	
//	public void removerUnidade(UnidadeEducacional unidade){
//		if(unidade==null) throw new IllegalArgumentException("Unidade está nula");
//		if(unidade.getNode()==null) throw new IllegalArgumentException("Unidade não foi persistida");
//		if(this.getNode()==null) throw new IllegalArgumentException("Instituição não está persistida");
//		Transaction tx = DataBase.get().beginTx();
//		try{
//			Relationship relationship = unidade.getNode().getSingleRelationship(Relacionamento.TEM, Direction.INCOMING);
//			relationship.delete();
//			tx.success();
//		}
//		catch(Exception e){
//			tx.failure();
//			throw new RepositoryException(e);
//		}
//		finally{
//			tx.finish();
//			LOG.info("Unidade " + unidade + "removida");
//		}
//	}


}
