package com.breakfast.tomorrow.web.client.async;


import java.util.Collection;
import java.util.Set;

import com.breakfast.tomorrow.core.academic.vo.Aluno;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * Interface que contem o comportamento do serviço de alunos.
 * @author kleberilario@gmail.com
 *
 */
@RemoteServiceRelativePath("alunoService")
public interface AlunoService extends RemoteService {

	Aluno persistir(Aluno aluno);
	Collection<Aluno> lista();
	void excluir(Set<Aluno> aluno);
	public String gerarRelatorio(Collection<Aluno> lista);

}
