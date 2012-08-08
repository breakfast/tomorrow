package com.breakfast.tomorrow.web.client.async;


import java.util.List;
import java.util.Set;

import com.breakfast.tomorrow.web.client.vo.AlunoVO;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * Interface que contem o comportamento do serviço de alunos.
 * @author kleberilario@gmail.com
 *
 */
@RemoteServiceRelativePath("alunoService")
public interface AlunoService extends RemoteService {

	AlunoVO persistir(AlunoVO aluno);
	List<AlunoVO> lista();
	void excluir(Set<AlunoVO> aluno);
	public String gerarRelatorio(List<AlunoVO> lista);

}
