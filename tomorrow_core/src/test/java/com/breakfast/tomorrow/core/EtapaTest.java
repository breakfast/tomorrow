package com.breakfast.tomorrow.core;


import org.junit.Test;

import com.breakfast.tomorrow.core.academic.Etapa;


import junit.framework.Assert;



public class EtapaTest {
   
	
	@Test
	public void testCrud(){	
		Etapa etapa = new Etapa();
		etapa.setNomeEtapa("Etapa de Desenvolvimento Web");
		etapa.setInicioEtapa(null);
		etapa.setFimEtapa(null);	
		Etapa.persist(etapa);		
		long id = etapa.getId();
		Assert.assertNotSame("Insert Etapa ", id, 0);	
		Etapa encontrada = Etapa.getEtapaPorId(id);
		Assert.assertNotNull("Etapa nao encontrado! ",encontrada);
		Etapa.delete(etapa);
		encontrada = Etapa.getEtapaPorId(id);
		Assert.assertNotSame("Mensagem aqui",encontrada, 0);	
	}
	
	@Test
	public void etapa(){
		
	}
	
}
