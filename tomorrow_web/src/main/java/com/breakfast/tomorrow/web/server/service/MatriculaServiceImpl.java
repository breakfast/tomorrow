package com.breakfast.tomorrow.web.server.service;

import java.util.ArrayList;
import java.util.Collection;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import com.breakfast.tomorrow.core.academic.repository.AlunoRepository;
import com.breakfast.tomorrow.core.academic.repository.CursoRepository;
import com.breakfast.tomorrow.core.academic.repository.TurmaRepository;
import com.breakfast.tomorrow.core.academic.vo.Aluno;
import com.breakfast.tomorrow.core.academic.vo.Curso;
import com.breakfast.tomorrow.core.academic.vo.Etapa;
import com.breakfast.tomorrow.core.academic.vo.Turma;
import com.breakfast.tomorrow.web.client.async.MatriculaService;
import com.breakfast.tomorrow.web.shared.Constants;
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
	
	@Override
	public String gerarMatriculaPDF(Aluno aluno) {
		Collection<Aluno> alunos = new ArrayList<Aluno>();
		alunos.add(aluno);
		String caminho = "";
		try{
			JasperReport report = JasperCompileManager.compileReport(Constants.RALATORIO_PATH + "matricula.jrxml");
			JasperPrint print = JasperFillManager.fillReport(report, null,new JRBeanCollectionDataSource(alunos));
			caminho = Constants.RALATORIO_PATH + "relatorio-aluno.pdf";
			JasperExportManager.exportReportToPdfFile(print,caminho);
		}
		catch(JRException e){
			throw new RuntimeException("ERRO REL " + e);
		}
		return caminho;
	}

	
	private static final long serialVersionUID = 1L;

	
}
