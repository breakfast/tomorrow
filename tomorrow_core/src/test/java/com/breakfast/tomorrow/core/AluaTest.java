package com.breakfast.tomorrow.core;


import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;

import com.breakfast.tomorrow.core.academic.repository.AulaRepository;
import com.breakfast.tomorrow.core.academic.vo.Aula;

public class AluaTest {
	
	//@Test
	public void AulaTest() {
		Aula aula = new Aula();
		aula.setDescricao("Aula de Programação Comercial");
	
		Date date = new Date();
		aula.setData(date);
		
		AulaRepository repo = new AulaRepository();
		
		repo.persistir(aula);
		
		long id = aula.getId();
		
		Assert.assertNotSame("Insert Aula ", id, 0);	
		Aula encontrada = repo.getAulaPorId(id);
		System.out.println(encontrada);
		Assert.assertNotNull("Aula nao encontrado! ",encontrada);
		repo.delete(aula);
		encontrada = repo.getAulaPorId(id);
		Assert.assertNotSame("Mensagem aqui",encontrada, 0);
	}
		


}
