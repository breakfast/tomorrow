package com.breakfast.tomorrow.core;

import junit.framework.Assert;

import org.junit.Test;


import com.breakfast.tomorrow.core.academic.InstituicaoEducacional;

public class InstituicaoEducacionalTest {
	
	
	@Test
	public void testCrud(){
		InstituicaoEducacional ins = new InstituicaoEducacional();
		
		ins.setNomeInstituicao("Unisal de Americana");
		InstituicaoEducacional.persist(ins);
		long id = ins.getId();
		
		Assert.assertNotSame("Insert Instituicao Educacional ", id, 0);	
		
		InstituicaoEducacional encontrada = InstituicaoEducacional.getInstituicaoPorId(id);
		Assert.assertNotNull("Instituicao Educacional nao encontrado! ",encontrada);
		InstituicaoEducacional.delete(ins);
		
		encontrada = InstituicaoEducacional.getInstituicaoPorId(id);
		
		Assert.assertNotSame("Mensagem aqui",encontrada, 0);
	}

}
