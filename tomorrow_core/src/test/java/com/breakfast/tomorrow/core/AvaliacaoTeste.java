package com.breakfast.tomorrow.core;


import org.junit.Test;

import com.breakfast.tomorrow.core.academic.Avaliacao;


import junit.framework.Assert;


public class AvaliacaoTeste{

	@Test
	public void testeCrud(){
		
		Avaliacao av = new Avaliacao();
		av.setDescricao("Avaliação de Matematica");
		av.setPeso(10);
		Avaliacao.persist(av);
		long id = av.getId();
		
		System.out.println(id);
		Assert.assertNotSame("Id Avaliação is 0, talvez não está gerando id ", id, 0l);
		Avaliacao av_ = Avaliacao.getAvaliacaoPorId(id);
		Assert.assertNotNull("Avaliação "+ id + " nao encontrado! ",av_);
		//Avaliacao.delete(av_);
		//av_ = Avaliacao.getAvaliacaoPorId(id);
		//Assert.assertNotSame("Mensagem aqui",av_, 0);
		
		
		System.out.println(av_.getId());
		System.out.println(av_.getDescricao());
		System.out.println(av_.getPeso());
	   	
	}
	
	
	

}
