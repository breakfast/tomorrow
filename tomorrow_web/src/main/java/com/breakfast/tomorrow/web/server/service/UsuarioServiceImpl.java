package com.breakfast.tomorrow.web.server.service;

import java.util.Collection;
import java.util.Set;

import com.breakfast.tomorrow.core.academic.repository.UsuarioRepository;
import com.breakfast.tomorrow.core.academic.vo.Usuario;
import com.breakfast.tomorrow.web.client.async.UsuarioService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class UsuarioServiceImpl  extends RemoteServiceServlet implements UsuarioService{

	private static final long serialVersionUID = 1L;

	UsuarioRepository reposotorio = new UsuarioRepository();
	
	@Override
	public Usuario persistir(Usuario usuario) {
		reposotorio.persistir(usuario);
		return usuario;
	}

	@Override
	public Collection<Usuario> lista() {
		return reposotorio.getUsuarios();
	}

	@Override
	public void excluir(Set<Usuario> usuarios) {
		for(Usuario usuario : usuarios ){
			reposotorio.delete(usuario);
		}
	}

	@Override
	public String gerarRelatorio(Collection<Usuario> lista) {
		// TODO Adicionar o caminho do relatorio
		return null;
	}

}
