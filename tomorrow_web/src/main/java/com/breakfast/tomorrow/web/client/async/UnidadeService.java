package com.breakfast.tomorrow.web.client.async;

import java.util.Collection;
import java.util.Set;

import com.breakfast.tomorrow.core.academic.vo.Unidade;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("unidadeService")
public interface UnidadeService extends RemoteService {

	Unidade persistir(Unidade unidade);
    void excluir(Set<Unidade> unidade);
	Collection<Unidade> lista();

}
