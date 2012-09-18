package com.breakfast.tomorrow.core;

import org.junit.Test;

import com.breakfast.tomorrow.core.academic.Aluno;


import junit.framework.Assert;


public class AlunoTest{
	
	@Test
	public void testeCrud(){
		
		Aluno aluno = new Aluno();
		aluno.setNome("Maicon Bueno");
		aluno.setEmail("maicondonizete@hotmail.com");
		aluno.setTelefone("(19) 91731244");
		aluno.setCidade("Americana");
		aluno.setComplemento("Casa");
		aluno.setEndereco("Av: Avenida - Affonso Arinos");
		Aluno.persist(aluno);
		long id = aluno.getIdPessoa();
		Assert.assertNotSame("Insert Aluno ", id, 0);
		Aluno encontrado = Aluno.getAlunosPorId(id);
		Assert.assertNotNull("Aluno nao encontrado! ",encontrado);
		Aluno.delete(encontrado);
		encontrado = Aluno.getAlunosPorId(id);
		Assert.assertNotSame("Mensagem aqui",encontrado, 0);	
	   	
	}
	
	
	

}
