package com.breakfast.tomorrow.web.server.service;

import java.util.Collection;
import java.util.Set;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import com.breakfast.tomorrow.core.academic.repository.AlunoRepository;
import com.breakfast.tomorrow.core.academic.vo.Aluno;
import com.breakfast.tomorrow.web.client.async.AlunoService;
import com.breakfast.tomorrow.web.shared.Constants;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * Implementacao do Servico Aluno Service
 * @author kleberilario@gmail.com
 *
 */
public class AlunoServiceImpl extends RemoteServiceServlet implements AlunoService {

	AlunoRepository reposotorio = new AlunoRepository();
	
	@Override
	public Aluno persistir(Aluno aluno) {
		reposotorio.persistir(aluno);
		return aluno;
	}

	@Override
	public Collection<Aluno> lista() {
		return reposotorio.getAlunos();
	}

	@Override
	public void excluir(Set<Aluno> alunos) {
		for(Aluno aluno : alunos){
			reposotorio.delete(aluno);
		}
	}

	@Override
	public String gerarRelatorio(Collection<Aluno> lista) {
		String caminho = "";
		try{
			JasperReport report = JasperCompileManager.compileReport(Constants.RALATORIO_PATH + "relatorio-alunos.jrxml");
			JasperPrint print = JasperFillManager.fillReport(report, null,new JRBeanCollectionDataSource(lista));
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
