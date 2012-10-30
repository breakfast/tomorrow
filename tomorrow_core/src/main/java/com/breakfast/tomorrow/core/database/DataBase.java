package com.breakfast.tomorrow.core.database;

import org.junit.Test;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.kernel.EmbeddedGraphDatabase;

/**
 * Class represents the DataBase of the Aplication
 * When add the new Database, implements here.
 * @author administrador
 *
 */
public class DataBase {
	
	
	//TODO dinamyc config database path and file.
	/**
	 * the path of the graph db file, this is loaded for
	 */
	public final static String DATABASE_FILE = "/graphdb/dev/database";
	public final static String DATABASE_FILE_TEST = "/graphdb/xbd/database";
	public final static String DATABASE_FILE_PROD = "/graphdb/prod/database";
	public static Node referenceNode = null;
	
	public static String enviormentPath = DATABASE_FILE;
	
	/**
	 * Graph database service for the application
	 */
	private static GraphDatabaseService graphDB = null;
	
	/**
	 * Inicia o banco de dados definido em 
	 */
	public static void startUp(){
		startUp(enviormentPath);
		referenceNode = graphDB.getReferenceNode();
	}
	
	/**
	 * Inicia o banco de dados 
	 * @param databaseFilePath
	 */
	public static void startUp(String databaseFilePath){
		graphDB = new EmbeddedGraphDatabase(databaseFilePath);
		registerShutDownHook(graphDB);
	}
	
	public static void shutDown(){
		graphDB.shutdown();
	}
	 
	/**
	 * Get the GraphDataBase for the Neo4J
	 * @return {@link EmbeddedGraphDatabase} .
	 */
	public static GraphDatabaseService getGraphDataBase(){
		if(graphDB == null){
			startUp();
		}
		return graphDB;
	}
	
	/**
	 * return the default database of version the application
	 */
	public static GraphDatabaseService get(){
		return getGraphDataBase();
	}
	
	/**
	 * Registra addShutDownHook
	 * 
	 * <code>
	 * Runtime.getRuntime().addShutdownHook(new Thread(){
	 *		@Override
	 *		public void run(){
	 *			graphDB.shutdown();
	 *	 	}
	 *	});
	 * </code>
	 * 
	 * @param graphDB
	 */
	public static void registerShutDownHook(final GraphDatabaseService graphDB){
		Runtime.getRuntime().addShutdownHook(new Thread(){
			@Override
			public void run(){
				graphDB.shutdown();
			}
		});
	}
	
	

}

