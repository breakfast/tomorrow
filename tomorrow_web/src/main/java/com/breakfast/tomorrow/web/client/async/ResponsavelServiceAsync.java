package com.breakfast.tomorrow.web.client.async;

import java.util.Collection;
import java.util.Set;


import com.breakfast.tomorrow.core.academic.vo.Responsavel;
import com.google.gwt.user.client.rpc.AsyncCallback;


public interface ResponsavelServiceAsync {
	
	void persistir(Responsavel responsavel, AsyncCallback<Responsavel> callback);
	void excluir(Set<Responsavel> responsavel, AsyncCallback<Void> callback);
	void gerarRelatorio(Collection<Responsavel> lista, AsyncCallback<String> callback);
	void lista(AsyncCallback<Collection<Responsavel>> callback); 
	

}
