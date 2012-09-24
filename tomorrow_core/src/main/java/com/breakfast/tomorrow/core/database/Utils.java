package com.breakfast.tomorrow.core.database;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;

/**
 * Class Utils 
 * @author kleber Ilario
 * 
 */
public abstract class Utils {
	
	public static final String PESISTED = "\n***** HAS PERSISTED *****";
	public static final String DELETED =  "\n***** WAS DELETED *****";
	public static final String UPDATED = "\n***** HAS BEEN UPDATED *****";
	
	/**
	 * Use for set node's property at prepare node fo persist.
	 * @param node
	 * @param propertyName
	 * @param propertyValue
	 */
	public static void setNodeProperty(Node node, String propertyName, Object propertyValue){
		if(propertyValue != null){
			node.setProperty(propertyName, propertyValue);
		}
		else{
			node.setProperty(propertyName, 0);
		}
		
	}
	
	/**
	 * Deleted all relationships of node, use this when deleted node.
	 * Obs : this method can only be executed in Transaction
	 * @param node
	 */
	public static void deleteRelantionShips(Node node){
		Iterable<Relationship> relationships = node.getRelationships();
		for(Relationship relationship : relationships){
			relationship.delete();
		}
	} 
	
	/**
	 * Returns a String containing attributes and values of class by reflection
	 * @param o - Instance
	 * @param clazz
	 * @return
	 */
	public static String toStringObject(Object o, Class<?> clazz){
		
		final String line = "********************************************************************";
		
		StringBuffer text = new StringBuffer();
		
		text.append("\n" + line);
		text.append("\n" + clazz.getName());
		text.append("\n" + line);
		
		if(o != null){
			for(Field staticField : clazz.getFields()){
				if (Modifier.isStatic(staticField.getModifiers())){
					try{
						Object value = staticField.get(null);
						String methodGetName = "get" + captalize(value.toString());
						Method methodGet = clazz.getMethod(methodGetName);
						Object methodGetReturn = methodGet.invoke(o);
						text.append("\n" + staticField.getName() + " : " + methodGetReturn.toString());
					}
					catch (Exception e) {
						text.append("\n" + staticField.getName() + " : " + e.getMessage());
					}
				}
			}
		}
		else{
			text.append("OBJECT IS NULL");
		}
		
		text.append("\n" + line);
		return text.toString();
	}
	
	/**
	 * Capitalize text
	 * @param text
	 * @return
	 */
	public static String captalize(String text){
		return text.substring(0,1).toUpperCase() + text.substring(1, text.length());
	}
	
		

}
