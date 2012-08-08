package com.breakfast.tomorrow.core;

import javax.validation.ValidationException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.breakfast.tomorrow.core.academic.Aluno;
import com.breakfast.tomorrow.core.database.DataBase;



public class AlunoTeste{
	
	@Before
	public void setarBancoDeDadosTeste(){
		DataBase.enviormentPath = DataBase.DATABASE_FILE_TEST;
	}
	
	
	@Test
	public void persistir(){
		Aluno aluno = new Aluno();
		Aluno.persist(aluno);
	}
	
	@Test
	public void persistir2(){
		Aluno aluno = new Aluno();
		aluno.setNome("Teste");
		Aluno.persist(aluno);
		Assert.assertTrue(aluno.getIdPessoa() > 0);
	}
	
	@After
	public void setarBancoDeDados(){
		DataBase.enviormentPath = DataBase.DATABASE_FILE;
	}

}
