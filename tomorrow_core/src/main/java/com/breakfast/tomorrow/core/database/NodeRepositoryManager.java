package com.breakfast.tomorrow.core.database;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.NotFoundException;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.Index;

public class NodeRepositoryManager<T> {
	
	T nodeEntity;
	Class<T> clazz;
	Node node;
	List<Field> indexFieldNodes;
	List<Field> fieldNodes;
	Field idFieldNode;
	boolean ignoreIndexs = false;
	
	@SuppressWarnings("unchecked")
	private void init(T nodeEntity) {
		this.nodeEntity = nodeEntity;  
		clazz = (Class<T>) nodeEntity.getClass();
		indexFieldNodes = registerFieldNodesByAnnotation(clazz.getDeclaredFields(), IndexNode.class);
		fieldNodes = registerFieldNodesByAnnotation(clazz.getDeclaredFields(), FieldNode.class);
		idFieldNode = registerFieldNodesByAnnotation(clazz.getDeclaredFields(), IdNode.class).get(0);
		indexFieldNodes.add(idFieldNode);	
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
	
	protected void prepareNode() throws IllegalArgumentException, IllegalAccessException, SecurityException, NoSuchMethodException, InvocationTargetException{
		List<Field> allFieldsNodes = new ArrayList<Field>();
		allFieldsNodes.addAll(indexFieldNodes);
		allFieldsNodes.addAll(fieldNodes);
		for(Field field : allFieldsNodes){
			field.setAccessible(true);
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
		String info = "NOT A INFO";
		init(nodeEntity);
		idFieldNode.setAccessible(true);
		String indexFieldName = getIndexFieldName(clazz, idFieldNode.getName());
		try {
			
			node = DataBase.get().index().forNodes(indexFieldName).get(idFieldNode.getName(), idFieldNode.get(nodeEntity)).getSingle();
		
			boolean hasNode = node != null;
			Transaction tx = DataBase.get().beginTx();
			if(!ignoreIndexs) prepareIndex();
			try {
				if(hasNode){
					info = Utils.UPDATED;
				}
				else { 
					node = DataBase.get().createNode();
					info = Utils.PESISTED;
				}
				idFieldNode.set(nodeEntity, node.getId());//TODO rever id generator
				prepareNode();
				if(!ignoreIndexs) registerIndex();
				tx.success();
				LOG.info(nodeEntity.toString() + info); 
			} catch (Exception e) {
				tx.failure();
				LOG.error("Erro at Persist " + clazz.getSimpleName(),e);
				throw new RepositoryException(e);
			} finally {
				tx.finish();
			}
			setObjectFromNode(clazz, nodeEntity, node);
		
		} catch (IllegalArgumentException e) {
			LOG.error("Erro at Persist " + clazz.getSimpleName(),e);
			throw new RepositoryException(e);
		} catch (IllegalAccessException e) {
			LOG.error("Erro at Persist " + clazz.getSimpleName(),e);
			throw new RepositoryException(e);
		}

	}
	
	public void delete(T nodeEntity){
		init(nodeEntity);
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
			throw new RepositoryException(e);
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
			throw new RepositoryException(e); 
		}
		
	}

	
	
	public T getNodeEntityById(Object value, Class<T> clazz){
		return getNodeEntityByIndex("id", value, clazz);
	}
	
	private void setObjectFromNode(Class<?> clazz2, Object object, Node node) throws IllegalArgumentException, IllegalAccessException{
		for(Field field : clazz2.getDeclaredFields()){
			if(field.isAnnotationPresent(FieldNode.class)||
			   field.isAnnotationPresent(IndexNode.class)||
			   field.isAnnotationPresent(IdNode.class)){
				field.setAccessible(true);
				try{
					field.set(object,node.getProperty(field.getName()));
				}
				catch(NotFoundException e){
					LOG.warn("Node Property not found", e);
				}
			}
		}
	}
	
	public T get(Node from, Class<T> clazz){
		return (T) get(from,clazz);
	}
	
	public Object getObject(Node from, Class<? extends NodeRepository> clazz){
		Object newObject;
		try{
			newObject =  clazz.newInstance();
			setObjectFromNode(clazz, newObject, from);
		}
		catch(Exception e){
			throw new RepositoryException(e);
		}
		return newObject;
	}
	
	public Node getNode(String attributeIdName, Object value){
		String indexFieldName = getIndexFieldName(clazz, attributeIdName);
		Node nodeFound = DataBase.get().index().forNodes(indexFieldName).get(attributeIdName, value).getSingle();
		return nodeFound;
	}
	
	public T getNodeEntityByIndex(String attributeIdName, Object value, Class<T> clazz){
		Node nodeFound = getNode(attributeIdName, value);
		if(nodeFound==null) return null;
		T newNodeEntity;
		try {
			newNodeEntity = clazz.newInstance();
			setObjectFromNode(clazz, newNodeEntity, nodeFound);
		} catch (InstantiationException e) {
			LOG.error(e);
			return null;
		} catch (IllegalAccessException e) {
			LOG.error(e);
			return null;
		}
		return newNodeEntity;
		/*
		public static Aluno getAlunosPorId(long id){
			Node nodeFound = DataBase.get().index().forNodes(INDEX_PESSOA_ID).get(ID_PESSOA, id).getSingle();
			Aluno student = new Aluno(nodeFound);
			return student;
		}
		*/
	}

	public void ignoreIndexs(boolean ignore){
		this.ignoreIndexs = ignore;
	}
	
	public boolean isIgnoringIndex(){
		return this.ignoreIndexs;
	}
	
	
	private static Logger LOG = Logger.getLogger(NodeEntityManager.class);
	
	
}
