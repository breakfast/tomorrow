package com.breakfast.tomorrow.core;


import org.junit.Test;

import com.breakfast.tomorrow.core.academic.repository.AvaliacaoRepository;
import com.breakfast.tomorrow.core.academic.vo.Avaliacao;


import junit.framework.Assert;


public class AvaliacaoTeste{

	//@Test
	public void testeCrud(){
		
		Avaliacao av = new Avaliacao();
		av.setDescricao("Avaliação de Matematica");
		av.setPeso(10);
		AvaliacaoRepository repo = new AvaliacaoRepository();
		repo.persistir(av);
		long id = av.getId();
		
		System.out.println(id);
		Assert.assertNotSame("Id Avaliação is 0, talvez não está gerando id ", id, 0l);
		Avaliacao av_ = repo.getAvaliacaoPorId(id);
		System.out.println(av_.getId());
		System.out.println(av_.getDescricao());
		System.out.println(av_.getPeso());
		Assert.assertNotNull("Avaliação "+ id + " nao encontrado! ",av_);
		repo.delete(av_);
		av_ = repo.getAvaliacaoPorId(id);
		Assert.assertNotSame("Mensagem aqui",av_, 0);
		
		
		
	   	
	}
	
	
	

}
