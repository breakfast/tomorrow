package com.breakfast.tomorrow.core;

import junit.framework.Assert;

import org.junit.Test;

import com.breakfast.tomorrow.core.academic.Curso;


public class CursoTest {
	
	@Test
	public void testCrud(){
		Curso curso = new Curso();
		curso.setnomeCurso("Curso de Porgramação para jogos");
		curso.setDescricao("Para realizacao des modulo , precisa !");
		curso.setDuracao("12 meses");
		Curso.persist(curso);
		long id = curso.getId();
		Assert.assertNotSame("Insert Curso ", id, 0);	
		Curso encontrada = Curso.getCursoPorId(id);
		Assert.assertNotNull("Curso nao encontrado! ",encontrada);
		Curso.delete(curso);
		encontrada = Curso.getCursoPorId(id);
		Assert.assertNotSame("Mensagem aqui",encontrada, 0);
	}
	
	


}
