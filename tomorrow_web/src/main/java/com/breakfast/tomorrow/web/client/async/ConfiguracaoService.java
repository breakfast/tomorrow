package com.breakfast.tomorrow.web.client.async;


import java.util.Collection;
import java.util.Set;

import com.breakfast.tomorrow.core.academic.vo.Configuracao;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("configuracaoesService")
public interface ConfiguracaoService extends RemoteService {

	void excluir(Set<Configuracao> configuracao);

	Collection<Configuracao> lista();

	Configuracao persistir(Configuracao configuracao);

	

	
}
