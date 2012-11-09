package com.breakfast.tomorrow.web.client.async;

import java.util.Collection;
import java.util.Set;

import com.breakfast.tomorrow.core.academic.vo.Usuario;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UsuarioServiceAsync {

	void persistir(Usuario usuario, AsyncCallback<Usuario> callback);
	void lista(AsyncCallback<Collection<Usuario>> callback);
	void excluir(Set<Usuario> usuario, AsyncCallback<Void> callback);
	void gerarRelatorio(Collection<Usuario> lista,AsyncCallback<String> callback);
	

}
