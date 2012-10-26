package com.breakfast.tomorrow.web.client.async;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import com.breakfast.tomorrow.core.academic.vo.Curso;
import com.breakfast.tomorrow.core.academic.vo.Disciplina;
import com.breakfast.tomorrow.core.academic.vo.Etapa;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("cursoService")
public interface CursoService extends RemoteService {
	
	Curso persistir(Curso curso);
	Collection<Curso> lista();
	void excluir(Set<Curso> curso);
	Map<Etapa, Collection<Disciplina>> carregarConfiguracao(Curso curso);
	void salvarConfiguracao(Curso curso, Map<Etapa, Collection<Disciplina>> config);

}
