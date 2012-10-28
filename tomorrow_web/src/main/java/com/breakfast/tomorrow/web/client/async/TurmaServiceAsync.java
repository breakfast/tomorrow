package com.breakfast.tomorrow.web.client.async;

import java.util.Collection;
import java.util.Set;

import com.breakfast.tomorrow.core.academic.vo.Turma;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface TurmaServiceAsync {

	void persistir(Turma turma, AsyncCallback<Turma> callback);
	void excluir(Set<Turma> turma, AsyncCallback<Void> callback);
	void lista(AsyncCallback<Collection<Turma>> callback);
	
	
	

}
