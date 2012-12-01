package com.breakfast.tomorrow.web.client.async;

import java.util.Collection;
import java.util.Set;

import com.breakfast.tomorrow.core.academic.vo.Usuario;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("usuarioService")
public interface UsuarioService extends RemoteService{
	
	Usuario persistir(Usuario usuario);
	Collection<Usuario> lista();
	void excluir(Set<Usuario> usuario);
	public String gerarRelatorio(Collection<Usuario> lista);
	public Usuario getLogin(String email, String senha);


}
