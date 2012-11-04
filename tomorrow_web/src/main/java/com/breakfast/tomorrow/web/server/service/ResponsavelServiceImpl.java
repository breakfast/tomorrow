package com.breakfast.tomorrow.web.server.service;

import java.util.Collection;
import java.util.Set;

import com.breakfast.tomorrow.core.academic.repository.ResponsavelRepository;
import com.breakfast.tomorrow.core.academic.vo.Responsavel;
import com.breakfast.tomorrow.web.client.async.ResponsavelService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ResponsavelServiceImpl extends RemoteServiceServlet implements ResponsavelService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	ResponsavelRepository reposotorio = new ResponsavelRepository();

	@Override
	public Responsavel persistir(Responsavel responsavel) {
		reposotorio.persistir(responsavel);
		return responsavel;
	}

	@Override
	public void excluir(Set<Responsavel> responsaveis) {
		for(Responsavel responsavel : responsaveis){
			reposotorio.delete(responsavel);
		}
		
	}

	@Override
	public String gerarRelatorio(Collection<Responsavel> lista) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Responsavel> lista() {		
		return reposotorio.getResponsaveis();
	}

}
