package com.breakfast.tomorrow.web.client.async;

import java.util.Collection;

import com.breakfast.tomorrow.core.academic.vo.Aluno;
import com.breakfast.tomorrow.core.academic.vo.Curso;
import com.breakfast.tomorrow.core.academic.vo.Etapa;
import com.breakfast.tomorrow.core.academic.vo.Turma;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface MatriculaServiceAsync {

	void getCursos(AsyncCallback<Collection<Curso>> callback);

	void getEtapasPorTurma(Turma turma,
			AsyncCallback<Collection<Etapa>> callback);

	void getTurmasPorCurso(Curso curso,
			AsyncCallback<Collection<Turma>> callback);

	void matricularAluno(Aluno aluno, AsyncCallback<Void> callback);

	void gerarMatriculaPDF(Aluno aluno, AsyncCallback<String> callback);

}
