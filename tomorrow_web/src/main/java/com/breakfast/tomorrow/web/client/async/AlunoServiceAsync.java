package com.breakfast.tomorrow.web.client.async;

import java.util.Collection;
import java.util.Set;

import com.breakfast.tomorrow.core.academic.vo.Aluno;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface AlunoServiceAsync {

	void persistir(Aluno aluno, AsyncCallback<Aluno> callback);
	void excluir(Set<Aluno> aluno, AsyncCallback<Void> callback);
	void gerarRelatorio(Collection<Aluno> lista, AsyncCallback<String> callback);
	void lista(AsyncCallback<Collection<Aluno>> callback);

	

}
