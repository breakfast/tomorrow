package com.breakfast.tomorrow.web.client.async;

import java.util.Collection;
import java.util.Set;
import com.breakfast.tomorrow.core.academic.vo.Unidade;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UnidadeServiceAsync {
	
	void persistir(Unidade unidade , AsyncCallback<Unidade> callback);
	void excluir(Set<Unidade> unidade ,AsyncCallback<Void> callback);
	void lista(AsyncCallback<Collection<Unidade>> callback); 

}
