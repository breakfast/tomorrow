package com.breakfast.tomorrow.core;

import junit.framework.Assert;

import org.junit.Test;


import com.breakfast.tomorrow.core.academic.UnidadeEducacional;

public class UnidadeEducacionalTest {
	
	@Test
	public void testCrud(){
		UnidadeEducacional uni = new UnidadeEducacional();
		
		uni.setNomeUnidade("Sao Bernado");
		uni.setLocal("Sao Paulo");
		UnidadeEducacional.persist(uni);
		
		long id = uni.getId();
		
		Assert.assertNotSame("Insert Unidade Educacional  ", id, 0);	
		
		UnidadeEducacional encontrada = UnidadeEducacional.getUnidadePorId(id);
		
		Assert.assertNotNull("Instituicao Unidade nao encontrado! ",encontrada);
		UnidadeEducacional.delete(uni);
		
		encontrada = UnidadeEducacional.getUnidadePorId(id);
		
		Assert.assertNotSame("Mensagem aqui",encontrada, 0);
	}

}
