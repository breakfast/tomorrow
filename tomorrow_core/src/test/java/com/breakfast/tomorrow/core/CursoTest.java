package com.breakfast.tomorrow.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

import com.breakfast.tomorrow.core.academic.Curso;
import com.breakfast.tomorrow.core.academic.Disciplina;
import com.breakfast.tomorrow.core.academic.Etapa;
import com.breakfast.tomorrow.core.academic.Turma;


public class CursoTest {
	
	@Test
	public void testCrud(){
		Curso curso = new Curso();
		curso.setNomeCurso("Curso de Porgramação para jogos");
		curso.setDescricao("Para realizacao des modulo , precisa !");
		curso.setDuracao("12 meses");
		Curso.persist(curso);
		long id = curso.getId();
		Assert.assertNotSame("Insert Curso ", id, 0);	
		Curso encontrada = Curso.getCursoPorId(id);
		Assert.assertNotNull("Curso nao encontrado! ",encontrada);
		Curso.delete(curso);
		encontrada = Curso.getCursoPorId(id);
		Assert.assertNotSame("Mensagem aqui",encontrada, 0);
	}
	
	@Test
	public void testeConfiguracaoCurso(){
		
		Curso curso = new Curso();
		curso.setDescricao("Curso de Musica");
		curso.setDuracao("2 meses");
		curso.setNomeCurso("MUS76");
		
		Etapa etapa1 = new Etapa();
		etapa1.setNomeEtapa("Etapa 1");
		etapa1.setIndice(1);
		
		Etapa etapa2 = new Etapa();
		etapa2.setNomeEtapa("Etapa 2");
		etapa2.setIndice(2);
		
		Disciplina disciplina1 = new Disciplina("Disciplina 1",1);
		Disciplina disciplina2 = new Disciplina("Disciplina 2",2);
		
		Disciplina disciplina3 = new Disciplina("Disciplina 3",3);
		Disciplina disciplina4 = new Disciplina("Disciplina 4",4);
		Disciplina disciplina5 = new Disciplina("Disciplina 5");
		
		/*TODO verificar quando indice estiver nulo, ou seja, naum for passado pelo contrutor, 
		 * pensar em tratar o 0 : 
		 * usando Wrapper no caso de vez int, Integer.
		 * ou não permitir que indice não seja inicializado
		 * 
		 */
		
		Map<Etapa, List<Disciplina>> map = new HashMap<Etapa, List<Disciplina>>();
		
		List<Disciplina> lista1  = new ArrayList<Disciplina>();
		lista1.add(disciplina1);
		lista1.add(disciplina2);
		
		List<Disciplina> lista2  = new ArrayList<Disciplina>();
		lista2.add(disciplina3);
		lista2.add(disciplina4);
		lista2.add(disciplina5);
		
		map.put(etapa1, lista1);
		map.put(etapa2, lista2);
		
		Curso.persist(curso);
		
		curso.salvarConfiguracao(map);
		
		Map<Etapa, List<Disciplina>> mapLoaded =  curso.carregarConfiguracao();
		
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n\n");
		
		for(Etapa e : mapLoaded.keySet()){
			System.out.println(">>> Etapa : " + e.getNomeEtapa() + " indice -> " + e.getIndice());
			for(Disciplina d : mapLoaded.get(e)){
				System.out.println(">>>>>> Curso : " + d.getNomeDisciplina() + " indice -> " + d.getIndice());
			}
		
		}
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n\n");
		
		Turma turma1 = curso.abrirTurma("Turma 1", new Date(), "Noturno");
		for(Etapa e : turma1.listaEtapas()){
			System.out.println(">>> Etapa : " + e.getNomeEtapa() + " id -> " + e.getId());
			for(Disciplina d : e.listaDisciplinas()){
				System.out.println(">>>>>> Curso : " + d.getNomeDisciplina() + " id -> " + d.getId());
			}
		}
		
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n\n");
		
		Turma turma2 = curso.abrirTurma("Turma 2", new Date(), "Diurno");
		for(Etapa e : turma2.listaEtapas()){
			System.out.println(">>> Etapa : " + e.getNomeEtapa() + " id -> " + e.getId());
			for(Disciplina d : e.listaDisciplinas()){
				System.out.println(">>>>>> Curso : " + d.getNomeDisciplina() + " id -> " + d.getId());
			}
		}
		
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n\n");
		
		Iterator<Curso> it = Curso.getCursos();
		while(it.hasNext()){
			Curso e = it.next();
			System.out.println(e.getNomeCurso() + "[" + e.getId() + "]");
		}
	
	}
	
}
