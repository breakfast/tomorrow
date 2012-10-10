package com.breakfast.tomorrow.core;


import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;

import com.breakfast.tomorrow.core.academic.Aula;

public class AluaTest {
	
	@Test
	public void AulaTest() {
		Aula aula = new Aula();
		aula.setDescricao("Aula de Programação Comercial");
	
		Date date = new Date();
		
		aula.setData(date);
		Aula.persist(aula);
		
		long id = aula.getId();
		
		Assert.assertNotSame("Insert Aula ", id, 0);	
		Aula encontrada = Aula.getAulaPorId(id);
		System.out.println(encontrada);
		Assert.assertNotNull("Aula nao encontrado! ",encontrada);
		Aula.delete(aula);
		encontrada = Aula.getAulaPorId(id);
		Assert.assertNotSame("Mensagem aqui",encontrada, 0);
	}
		


}
