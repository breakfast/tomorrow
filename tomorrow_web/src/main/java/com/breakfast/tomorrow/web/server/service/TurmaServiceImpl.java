package com.breakfast.tomorrow.web.server.service;

import java.util.Collection;
import java.util.Set;

import com.breakfast.tomorrow.core.academic.repository.TurmaRepository;
import com.breakfast.tomorrow.core.academic.vo.Turma;
import com.breakfast.tomorrow.web.client.async.TurmaService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class TurmaServiceImpl extends RemoteServiceServlet implements TurmaService  {

	/**
	 * 
	 */
	TurmaRepository reposotorio = new TurmaRepository();
	
	private static final long serialVersionUID = 1L;

	@Override
	public Turma persistir(Turma turma) {
		reposotorio.persistir(turma);
		return turma;
	
	}

	@Override
	public Collection<Turma> lista() {
		return reposotorio.getTurmas();
		
	}

	@Override
	public void excluir(Set<Turma> turmas) {
		for(Turma turma : turmas){
			reposotorio.delete(turma);
		}
		
	}

}
