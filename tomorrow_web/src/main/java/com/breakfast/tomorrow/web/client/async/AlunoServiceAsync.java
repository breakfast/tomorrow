package com.breakfast.tomorrow.web.client.async;

import java.util.List;
import java.util.Set;
import com.breakfast.tomorrow.web.client.vo.AlunoVO;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface AlunoServiceAsync {

	void lista(AsyncCallback<List<AlunoVO>> callback);
	void persistir(AlunoVO aluno, AsyncCallback<AlunoVO> callback);
	void excluir(Set<AlunoVO> aluno, AsyncCallback<Void> callback);

}
