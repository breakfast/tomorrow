package com.breakfast.tomorrow.web.server.service;

import java.util.Collection;

import com.breakfast.tomorrow.core.academic.repository.AlunoRepository;
import com.breakfast.tomorrow.core.academic.repository.CursoRepository;
import com.breakfast.tomorrow.core.academic.repository.TurmaRepository;
import com.breakfast.tomorrow.core.academic.vo.Aluno;
import com.breakfast.tomorrow.core.academic.vo.Curso;
import com.breakfast.tomorrow.core.academic.vo.Etapa;
import com.breakfast.tomorrow.core.academic.vo.Turma;
import com.breakfast.tomorrow.web.client.async.MatriculaService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class MatriculaServiceImpl extends RemoteServiceServlet implements MatriculaService {
	
	CursoRepository cursoRepo = new CursoRepository();
	TurmaRepository turmaRepo = new TurmaRepository();
	AlunoRepository alunoRepo = new AlunoRepository();

	@Override
	public Collection<Curso> getCursos() {
		return cursoRepo.getCursos();
	}

	@Override
	public Collection<Turma> getTurmasPorCurso(Curso curso) {
		return cursoRepo.getTurmas(curso);
	}

	@Override
	public Collection<Etapa> getEtapasPorTurma(Turma curso) {
		return turmaRepo.getEtapas(curso);
	}

	@Override
	public void matricularAluno(Aluno aluno) {
		alunoRepo.setEtapaCursando(aluno);
	}

	
	private static final long serialVersionUID = 1L;
}
