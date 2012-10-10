package com.breakfast.tomorrow.core;

import junit.framework.Assert;
import org.junit.Test;

import com.breakfast.tomorrow.core.academic.InstituicaoEducacional;
import com.breakfast.tomorrow.core.academic.UnidadeEducacional;

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
	
	@Test
	public void unidade(){
		System.out.println("testes Instituicao Educacional --------------");
		
		InstituicaoEducacional i = new InstituicaoEducacional();
		i.setNomeInstituicao("Centro Universitario Salesiano");
		InstituicaoEducacional.persist(i);
		UnidadeEducacional uA = new UnidadeEducacional();
		UnidadeEducacional uB = new UnidadeEducacional();
		uA.setNomeUnidade("Maria Auxiliadora");
		uA.setLocal("Campo Salles, pq Novo Mundo");
		UnidadeEducacional.persist(uA);
		uB.setNomeUnidade("Dom Bosco");
		uB.setLocal("Rua Dom Bosco, Santa Catarina");
		UnidadeEducacional.persist(uB);
		
		i.adicionarUnidade(uA);
		i.adicionarUnidade(uB);
		
		for(UnidadeEducacional u :i.listaUnidades()){
			System.out.println(u.getNomeUnidade());
		}
		
		InstituicaoEducacional iE = uA.getInstituicaoEducacional();
		System.out.println(iE.getNomeInstituicao());
		
		iE.removerUnidade(uA);
		
		for(UnidadeEducacional u :i.listaUnidades()){
			System.out.println(u.getNomeUnidade());
		}
		
		System.out.println("----------------------------------------------");
	}

}
