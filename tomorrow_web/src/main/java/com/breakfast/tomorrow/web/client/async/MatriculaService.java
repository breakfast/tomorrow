package com.breakfast.tomorrow.web.client.async;

import java.util.Collection;

import com.breakfast.tomorrow.core.academic.vo.Aluno;
import com.breakfast.tomorrow.core.academic.vo.Curso;
import com.breakfast.tomorrow.core.academic.vo.Etapa;
import com.breakfast.tomorrow.core.academic.vo.Turma;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("matriculaService")
public interface MatriculaService extends RemoteService{
	
	public Collection<Curso> getCursos();
	public Collection<Turma> getTurmasPorCurso(Curso curso);
	public Collection<Etapa> getEtapasPorTurma(Turma turma);
	public void matricularAluno(Aluno aluno);
	
}
