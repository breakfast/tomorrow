package com.breakfast.tomorrow.web.client.async;



import java.util.Collection;
import java.util.Set;


import com.breakfast.tomorrow.core.academic.vo.Turma;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;


@RemoteServiceRelativePath("turmaService")
public interface TurmaService extends RemoteService {
	
	Turma persistir(Turma turma);
	Collection<Turma> lista();
	void excluir(Set<Turma> turma);
	//public String gerarRelatorio(Collection<Aluno> lista);
	
}
