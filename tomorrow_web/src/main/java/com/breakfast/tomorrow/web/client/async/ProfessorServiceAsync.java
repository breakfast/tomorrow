package com.breakfast.tomorrow.web.client.async;


import java.util.Collection;
import java.util.Set;


import com.breakfast.tomorrow.core.academic.vo.Professor;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ProfessorServiceAsync {
	
	void persistir(Professor professor, AsyncCallback<Professor> callback);
	void excluir(Set<Professor> professor, AsyncCallback<Void> callback);
	void gerarRelatorio(Collection<Professor> lista, AsyncCallback<String> callback);
	void lista(AsyncCallback<Collection<Professor>> callback); 

}
