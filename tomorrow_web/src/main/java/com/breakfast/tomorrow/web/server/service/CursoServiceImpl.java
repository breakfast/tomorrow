package com.breakfast.tomorrow.web.server.service;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import com.breakfast.tomorrow.core.academic.repository.CursoRepository;
import com.breakfast.tomorrow.core.academic.vo.Curso;
import com.breakfast.tomorrow.core.academic.vo.Disciplina;
import com.breakfast.tomorrow.core.academic.vo.Etapa;
import com.breakfast.tomorrow.web.client.async.CursoService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class CursoServiceImpl extends RemoteServiceServlet implements CursoService{

	
	CursoRepository repository = new CursoRepository();
	
	@Override
	public Curso persistir(Curso curso) {
		repository.persistir(curso);
		return curso;
	}

	@Override
	public Collection<Curso> lista() {
		return repository.getCursos();
	}

	@Override
	public void excluir(Set<Curso> cursos) {
		for(Curso curso : cursos){
			repository.delete(curso);
		}
	}

	@Override
	public Map<Etapa, Collection<Disciplina>> carregarConfiguracao(Curso curso) {
		return repository.getConfiguracao(curso);
	}

	@Override
	public void salvarConfiguracao(Curso curso,
			Map<Etapa, Collection<Disciplina>> config) {
		repository.setConfiguracao(curso, config);
		
	}
	
	private static final long serialVersionUID = 1L;

}
