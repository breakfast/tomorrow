package com.breakfast.tomorrow.web.server.service;

import java.util.Collection;
import java.util.Set;

import com.breakfast.tomorrow.core.academic.repository.ProfessorRepository;
import com.breakfast.tomorrow.core.academic.vo.Professor;
import com.breakfast.tomorrow.web.client.async.ProfessorService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ProfessorServiceImpl extends RemoteServiceServlet implements ProfessorService  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	ProfessorRepository reposotorio = new ProfessorRepository();
	

	@Override
	public void excluir(Set<Professor> professores) {
		for(Professor professor : professores){
			reposotorio.delete(professor);	
		}	
	}

	@Override
	public String gerarRelatorio(Collection<Professor> lista) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Professor> lista() {	
		return reposotorio.getProfessores();
	}

	@Override
	public Professor persistir(Professor professor) {	
		reposotorio.persistir(professor);	
		return professor;
	}
	

}
