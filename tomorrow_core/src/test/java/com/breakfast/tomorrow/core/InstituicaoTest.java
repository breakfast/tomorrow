package com.breakfast.tomorrow.core;

import junit.framework.Assert;
import org.junit.Test;

import com.breakfast.tomorrow.core.academic.repository.InstituicaoRepository;
import com.breakfast.tomorrow.core.academic.vo.Instituicao;
import com.breakfast.tomorrow.core.academic.vo.Unidade;

public class InstituicaoTest {
	
	
	@Test
	public void testCrud(){
		/*
		InstituicaoRepository repo = new InstituicaoRepository();
		Instituicao ins = new Instituicao();
		ins.setNomeInstituicao("Unisal de Americana");
		repo.persistir(ins);
		
		long id = ins.getId();
		System.out.println("Instituicao : " + ins.getId());
		
		Assert.assertNotSame("instituicao Id eh zero ", id, 0);	
		Instituicao encontrada = repo.getInstituicaoPorId(id);
		Assert.assertNotNull("Instituicao Educacional nao encontrado! ",encontrada);
		
		repo.delete(ins);
		encontrada = repo.getInstituicaoPorId(id);
		Assert.assertSame("Instituição não foi excluida",encontrada, null);
	}
	
	@Test
	public void unidade(){
		System.out.println("testes Instituicao Educacional --------------");
		InstituicaoRepository repo = new InstituicaoRepository();
		Instituicao i = new Instituicao();
		i.setNomeInstituicao("Centro Universitario Salesiano");
		repo.persistir(i);
		long id = i.getId();
		Unidade uA = new Unidade();
		Unidade uB = new Unidade();
		
		uA.setNomeUnidade("Maria Auxiliadora");
		uA.setLocal("Campo Salles, pq Novo Mundo");

		uB.setNomeUnidade("Dom Bosco");
		uB.setLocal("Rua Dom Bosco, Santa Catarina");
		
		
		i.getUnidades().add(uA);
		i.getUnidades().add(uB);
		
		
		for(Unidade u : i.getUnidades()){
			System.out.println(u.getNomeUnidade());
		}
		
		Instituicao iE = repo.getInstituicaoPorId(id);
		for(Unidade u : iE.getUnidades()){
			System.out.println(u.getNomeUnidade());
		}
		System.out.println(iE.getNomeInstituicao());
	
		
		System.out.println("----------------------------------------------");
		*/
	}

}
