package com.breakfast.tomorrow.core.academic.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.ReturnableEvaluator;
import org.neo4j.graphdb.StopEvaluator;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.Traverser;

import com.breakfast.tomorrow.core.academic.vo.Instituicao;
import com.breakfast.tomorrow.core.academic.vo.Unidade;
import com.breakfast.tomorrow.core.database.DataBase;
import com.breakfast.tomorrow.core.database.EntityRelashionship;
import com.breakfast.tomorrow.core.database.NodeRepositoryManager;
import com.breakfast.tomorrow.core.database.RepositoryException;

public class InstituicaoRepository extends NodeRepositoryManager<Instituicao>{

	public Instituicao getInstituicaoPorId(long id){
		Node node = getNode("id",id,Instituicao.class); 
		return carregar(node);
	}
	
	public Instituicao carregar(Node node){
		Instituicao instituicao = get(node, Instituicao.class);
		if(instituicao!=null){
			instituicao.setUnidades(getUnidadesEducacional(instituicao));
		}
		return instituicao;
	}
	
	@Override
	public void persistir(Instituicao nodeEntity) {
		super.persistir(nodeEntity);
		super.createEntityRelationship(nodeEntity, EntityRelashionship.INSTITUICAOES);
	}
	
	public Collection<Instituicao> getInstituicoes(){
		Iterator<Node> nodeIterator = DataBase.get().getReferenceNode().traverse(
				Traverser.Order.DEPTH_FIRST,
				StopEvaluator.DEPTH_ONE,
				ReturnableEvaluator.ALL_BUT_START_NODE,
				EntityRelashionship.INSTITUICAOES,
				Direction.OUTGOING).iterator();
		Collection<Instituicao> lista = new ArrayList<Instituicao>();
		while(nodeIterator.hasNext()){
			lista.add(carregar(nodeIterator.next()));
		}
		return lista;
	}
	
	public void setUnidadesEducacional(Instituicao instituicao, 
									   Collection<Unidade> unidades) throws RepositoryException{
		UnidadeRepository repositorioUnidade = new UnidadeRepository();
		Collection<Unidade> persistidas = getUnidadesEducacional(instituicao);
		Collection<Unidade> paraPersistir = new HashSet<Unidade>(unidades);
		Collection<Unidade> paraRemover = new HashSet<Unidade>(persistidas);
		paraPersistir.removeAll(persistidas);
		paraRemover.removeAll(unidades);
		Transaction tx = DataBase.get().beginTx();
		Node instituicao_ = getNode("id", instituicao.getId(), Instituicao.class);
		try{
			for(Unidade unidade : paraPersistir){
				repositorioUnidade.persistir(unidade);
				Node unidade_ = getNode("id", unidade.getId(), Unidade.class);
				instituicao_.createRelationshipTo(unidade_, Relacionamento.TEM);
			}
			for(Unidade unidade : paraRemover){
				Node unidade_ = getNode("id", unidade.getId(), Unidade.class);
				unidade_.delete();
			}
			tx.success();
		}
		catch(Exception e){
			tx.failure();
			throw new RepositoryException(e);
		}
		finally{
			tx.finish();
		}
	}
	
	public Collection<Unidade> getUnidadesEducacional(Instituicao instituicao){
		UnidadeRepository repo = new UnidadeRepository();
		Node instituicao_ = getNode("id", instituicao.getId(), Instituicao.class);
		Iterator<Node> it = instituicao_.traverse(
				Traverser.Order.DEPTH_FIRST,
				StopEvaluator.DEPTH_ONE,
				ReturnableEvaluator.ALL_BUT_START_NODE,
				Relacionamento.TEM,
				Direction.OUTGOING).iterator();
		Collection<Unidade> colecao = new ArrayList<Unidade>();
		while(it.hasNext()){
			colecao.add(repo.carregar(it.next()));
		}
		return colecao;
	}

}
