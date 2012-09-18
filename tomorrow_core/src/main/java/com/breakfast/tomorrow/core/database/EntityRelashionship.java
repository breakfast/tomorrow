package com.breakfast.tomorrow.core.database;

import org.neo4j.graphdb.RelationshipType;

/**
 * Classe representa as relacões das entidades o nó de referência do banco de dados.
 * É usada para a criação de uma relação da entidade com o nó de referencia, assim através de um Traverser, 
 * buscar todos os dados de certa Entidade sem a necessidade de um indice.  
 * @author administrador
 *
 */
public enum EntityRelashionship implements RelationshipType {
	ALUNOS,
	CURSOS,
	DISCIPLINAS,
	ETAPAS,
	TURMA,
	AULAS,
	INSTITUICAOES,
	UNIDADES,
	DIARIOS,
	AVALIACOES,
}
