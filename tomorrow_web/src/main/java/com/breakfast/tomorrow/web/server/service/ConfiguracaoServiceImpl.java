package com.breakfast.tomorrow.web.server.service;

import java.util.Collection;
import java.util.Set;

import com.breakfast.tomorrow.core.academic.repository.ConfiguracaoRepository;
import com.breakfast.tomorrow.core.academic.vo.Configuracao;
import com.breakfast.tomorrow.web.client.async.ConfiguracaoService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ConfiguracaoServiceImpl extends RemoteServiceServlet implements ConfiguracaoService{

	ConfiguracaoRepository reposotorio = new ConfiguracaoRepository();
	
	private static final long serialVersionUID = 1L;

	@Override
	public Configuracao persistir(Configuracao configuracao) {
		reposotorio.persistir(configuracao);
		return configuracao;
	}

	@Override
	public void excluir(Set<Configuracao> configuracao) {
	    
	}
	@Override
	public Collection<Configuracao> lista() {
		return reposotorio.getConfiguracoes();
	}

}
