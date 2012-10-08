package com.breakfast.tomorrow.core.academic;

import org.neo4j.graphdb.RelationshipType;

/**
 * Define os relacionamentos entre as entidades  
 * @author kleberilario@gmail.com
 *
 */
public enum Relacionamento implements RelationshipType{
	TEM,
	CONFIGURADO,
	MATRICULADO
}
