package com.breakfast.tomorrow.web.client.async;

import java.util.Collection;
import java.util.Set;

import com.breakfast.tomorrow.core.academic.vo.Configuracao;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ConfiguracaoServiceAsync {

	void excluir(Set<Configuracao> configuracao, AsyncCallback<Void> callback);

	void lista(AsyncCallback<Collection<Configuracao>> callback);

	void persistir(Configuracao configuracao,
			AsyncCallback<Configuracao> callback);
	
	

}
