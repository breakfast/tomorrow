package com.breakfast.tomorrow.web.server.service;

import java.util.Collection;

import com.breakfast.tomorrow.core.academic.repository.DiarioRepository;
import com.breakfast.tomorrow.core.academic.repository.DisciplinaRepository;
import com.breakfast.tomorrow.core.academic.vo.Diario;
import com.breakfast.tomorrow.core.academic.vo.Disciplina;
import com.breakfast.tomorrow.web.client.async.DiarioService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class DiarioServiceImpl extends RemoteServiceServlet implements DiarioService  {

	DiarioRepository repo = new DiarioRepository();
	DisciplinaRepository disciplinaRepository = new DisciplinaRepository();
	
	@Override
	public Collection<Diario> getDiarios() {
		return repo.getDiarioes();
	}

	@Override
	public Diario persistir(Diario diario) {
		return null;
	}

	@Override
	public Collection<Disciplina> getDisciplinas() {
		return disciplinaRepository.getDisciplinas();
	}
	
	private static final long serialVersionUID = 1L;

}
