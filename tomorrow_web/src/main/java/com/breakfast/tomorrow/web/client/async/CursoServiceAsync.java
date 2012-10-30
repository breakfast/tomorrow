package com.breakfast.tomorrow.web.client.async;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import com.breakfast.tomorrow.core.academic.vo.Curso;
import com.breakfast.tomorrow.core.academic.vo.Disciplina;
import com.breakfast.tomorrow.core.academic.vo.Etapa;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface CursoServiceAsync {

	void excluir(Set<Curso> aluno, AsyncCallback<Void> callback);
	void lista(AsyncCallback<Collection<Curso>> callback);
	void persistir(Curso aluno, AsyncCallback<Curso> callback);
	void salvarConfiguracao(Curso curso, Map<Etapa, Collection<Disciplina>> config,
			AsyncCallback<Void> callback);
	void carregarConfiguracao(Curso curso,
			AsyncCallback<Map<Etapa, Collection<Disciplina>>> callback);

}
