package com.breakfast.tomorrow.core.database;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.Index;

import com.breakfast.base.Utils;


public class NodeEntityManager<T extends NodeEntity> {
	
	T nodeEntity;
	Class<?> clazz;
	Node node;
	List<Field> indexFieldNodes;
	List<Field> fieldNodes;
	
	private void init(T nodeEntity){
		this.nodeEntity = nodeEntity;
		node = nodeEntity.node;
		clazz = nodeEntity.getClass();
		indexFieldNodes = registerFieldNodesByAnnotation(clazz.getDeclaredFields(), IndexNode.class);
		fieldNodes = registerFieldNodesByAnnotation(clazz.getDeclaredFields(), FieldNode.class);
	}

	
	private List<Field> registerFieldNodesByAnnotation(Field[] fields, Class<? extends Annotation> annotationClass){
		List<Field> listField = new ArrayList<Field>();
		for(Field field : fields){
			if(field.isAnnotationPresent(annotationClass)){
				listField.add(field);
			}
		}
		return listField;	
	}
	
	private String getIndexFieldName(Class<?> clazz, Field field){
		return getIndexFieldName(clazz, field.getName());
	}
	
	private String getIndexFieldName(Class<?> clazz, String fieldName){
		return "idx." + clazz.getSimpleName() + "." + fieldName;
	}

	
	protected void prepareIndex(){
		for(Field field : indexFieldNodes){
			String indexNodeName = getIndexFieldName(clazz, field);
			Index<Node> indexNode = DataBase.get().index().forNodes(indexNodeName);
			if(node != null){
				indexNode.remove(node, field.getName(), node.getProperty(field.getName()));
			}
		}
		/*
		 * 
		Index<Node> idcursos = DataBase.get().index().forNodes(INDEX_ID_CURSO);
		Index<Node> nomeCursos = DataBase.get().index().forNodes(INDEX_NOME_CURSO);
		if(node != null){
			idcursos.remove(node, ID_CURSO, node.getProperty(ID_CURSO));
			nomeCursos.remove(node,NOME_CURSO, node.getProperty(NOME_CURSO));
		}
		*/
	}
	
	protected void prepareNode() throws IllegalArgumentException, IllegalAccessException{
		List<Field> allFieldsNodes = new ArrayList<Field>();
		allFieldsNodes.addAll(indexFieldNodes);
		allFieldsNodes.addAll(fieldNodes);
		for(Field field : allFieldsNodes){
			setNodeProperty(node, field.getName(), field.get(nodeEntity));
		}
		
		//Utils.setNodeProperty(node,ID_CURSO,this.idCurso);
		//Utils.setNodeProperty(node,NOME_CURSO,this.nomeCurso);
		//Utils.setNodeProperty(node,DESCRICAO,this.descricao);
		//Utils.setNodeProperty(node,DURACAO,this.duracao);	
	}
	
	protected void registerIndex() throws IllegalArgumentException, IllegalAccessException{
		for(Field field : indexFieldNodes){
			String indexFieldName = getIndexFieldName(clazz, field);
			Index<Node> indexNode = DataBase.get().index().forNodes(indexFieldName);
			indexNode.add(node, field.getName(), node.getProperty(field.getName()));
		}
			
		/*
		Index<Node> idcursos = DataBase.get().index().forNodes(INDEX_ID_CURSO);
		Index<Node> nomeCursos = DataBase.get().index().forNodes(INDEX_NOME_CURSO);
		idcursos.add(node, ID_CURSO, node.getProperty(ID_CURSO));
		nomeCursos.add(node, NOME_CURSO, node.getProperty(NOME_CURSO));
		//TODO verificar se ao atualizar o node, ou seja, o ALuno, ver se ele esta criando outra relacao
		DataBase.get().getReferenceNode().createRelationshipTo(node, EntityRelashionship.CURSOS);
		*/
	}
	
	protected void removeIndex(){
		for(Field field : indexFieldNodes){
			Index<Node> indexNode = DataBase.get().index().forNodes(getIndexFieldName(clazz, field));
			indexNode.remove(node, field.getName(), node.getProperty(field.getName()));
		}
		/*
		Index<Node> idcursos = DataBase.get().index().forNodes(INDEX_ID_CURSO);
		Index<Node> nomeCursos = DataBase.get().index().forNodes(INDEX_NOME_CURSO);
		idcursos.remove(node, ID_CURSO, node.getProperty(ID_CURSO));
		nomeCursos.remove(node, NOME_CURSO, node.getProperty(NOME_CURSO));
		*/
	}
	
	/**
	 * Use for set node's property at prepare node fo persist.
	 * @param node
	 * @param propertyName
	 * @param propertyValue
	 */
	private void setNodeProperty(Node node, String propertyName, Object propertyValue){
		if(propertyValue != null){
			node.setProperty(propertyName, propertyValue);
		}
	}
	
	/**
	 * Deleted all relationships of node, use this when deleted node.
	 * Obs : this method can only be executed in Transaction
	 * @param node
	 */
	private void deleteRelantionShips(Node node){
		Iterable<Relationship> relationships = node.getRelationships();
		for(Relationship relationship : relationships){
			relationship.delete();
		}
	} 
	
	public void persistir(T nodeEntity){
		init(nodeEntity);
		String info = "NOT A INFO";
		boolean hasNode = nodeEntity.node != null;
		Transaction tx = DataBase.get().beginTx();
		prepareIndex();
		try {
			if(hasNode){
				node = nodeEntity.node;
				info = Utils.UPDATED;
			}
			else { 
				node = DataBase.get().createNode();
				//nodeEntity.setId(EntityProperties.getID(NodeEntity.class));
				info = Utils.PESISTED;
			}
			prepareNode();
			registerIndex();
			tx.success();
			LOG.info(nodeEntity.toString() + info); 
		} catch (Exception e) {
			tx.failure();
			LOG.error("Erro at Persist " + clazz.getSimpleName(),e);
			throw new DataBaseException(e);
		} finally {
			tx.finish();
		}
		
	}
	
	public void delete(T nodeEntity){
		node = nodeEntity.getNode();
		Transaction transaction = DataBase.get().beginTx();
		try{
			String info = nodeEntity.toString();
			deleteRelantionShips(node);
			removeIndex();
			node.delete();
			transaction.success();
			LOG.info(info + Utils.DELETED);
		}
		catch (Exception e) {
			transaction.failure();
			LOG.error("Error at Delete" + clazz.getSimpleName() ,e);
			throw new DataBaseException(e);
		}
		transaction.finish();
	}
	
	public void createEntityRelationship(T nodeEntity, RelationshipType relationship){
		Transaction transaction= DataBase.get().beginTx();
		try{
			DataBase.get().getReferenceNode().createRelationshipTo(node, relationship);
			transaction.success();
		}
		catch (Exception e) {
			transaction.failure();
			LOG.error("Error at create Entity Relationship " + clazz.getSimpleName() ,e);
			throw new DataBaseException(e); 
		}
		
	}
	
	public NodeEntity getNodeEntityById(String attributeIdName, Object value, T newNodeEntity){
		init(newNodeEntity);
		String indexFieldName = getIndexFieldName(clazz, attributeIdName);
		Node nodeFound = DataBase.get().index().forNodes(indexFieldName).get(attributeIdName, value).getSingle();
		if(nodeFound != null){
			nodeEntity.setNode(nodeFound);
			return nodeEntity;
		}
		else return null;
		/*
		public static Aluno getAlunosPorId(long id){
			Node nodeFound = DataBase.get().index().forNodes(INDEX_PESSOA_ID).get(ID_PESSOA, id).getSingle();
			Aluno student = new Aluno(nodeFound);
			return student;
		}
		*/
	}
	private static Logger LOG = Logger.getLogger(NodeEntityManager.class);
}
