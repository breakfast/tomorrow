package com.breakfast.tomorrow.web.server.service;

import java.util.Collection;
import java.util.Set;

import com.breakfast.tomorrow.core.academic.repository.UnidadeRepository;
import com.breakfast.tomorrow.core.academic.vo.Unidade;
import com.breakfast.tomorrow.web.client.async.UnidadeService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class UnidadeServiceImpl extends RemoteServiceServlet implements UnidadeService {


	UnidadeRepository repositorio = new UnidadeRepository();
	
	private static final long serialVersionUID = 1L;

	@Override
	public Unidade persistir(Unidade unidade) {
		repositorio.persistir(unidade);
		return unidade;
	}

	@Override
	public void excluir(Set<Unidade> unidades) {
		for(Unidade unidade : unidades){
			repositorio.delete(unidade);
		}	
	}

	@Override
	public Collection<Unidade> lista() {
		return repositorio.getUnidades();
	}
	

}
