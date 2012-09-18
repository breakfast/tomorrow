package com.breakfast.tomorrow.core;



import junit.framework.Assert;
import org.junit.Test;
import com.breakfast.tomorrow.core.academic.Turma;


public class TurmaTest {
		
	@Test
	public void testCrud(){
		
		Turma turma = new Turma();	
		turma.setnomeTurma("Turma Logica");
		turma.setObservacao("estao aprendendo");
		turma.setTurno("Manhã");
		turma.setInicio(null);
		Turma.persist(turma);
		
		long id = turma.getidTurma();
	
		Assert.assertNotSame("Insert Turma ", id, 0);	
		Turma encontrada = Turma.getTurmaPorId(id);
		Assert.assertNotNull("Turma nao encontrado! ",encontrada);
		Turma.delete(turma);
		encontrada = Turma.getTurmaPorId(id);
		Assert.assertNotSame("Mensagem aqui",encontrada, 0);
		
	}
	


}
