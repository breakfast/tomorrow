package com.breakfast.tomorrow.web.client.async;



import java.util.Collection;
import java.util.Set;

import com.breakfast.tomorrow.core.academic.vo.Professor;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;


@RemoteServiceRelativePath("professorService")
public interface ProfessorService extends RemoteService{

	void excluir(Set<Professor> professor);
	String gerarRelatorio(Collection<Professor> lista);
	Collection<Professor> lista();
	Professor persistir(Professor professor);
	
	
	

}
