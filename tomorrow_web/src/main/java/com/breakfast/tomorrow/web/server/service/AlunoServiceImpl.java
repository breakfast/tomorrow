package com.breakfast.tomorrow.web.server.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;



import com.breakfast.tomorrow.core.academic.Aluno;
import com.breakfast.tomorrow.core.database.DataBaseException;
import com.breakfast.tomorrow.web.client.async.AlunoService;
import com.breakfast.tomorrow.web.client.vo.AlunoVO;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * Implementacao do Servico Aluno Service
 * @author kleberilario@gmail.com
 *
 */
public class AlunoServiceImpl extends RemoteServiceServlet implements AlunoService {

	@Override
	public AlunoVO persistir(AlunoVO aluno) {
		
		Aluno model = new Aluno();
		
		if(aluno.getIdPessoa()!=null){
			model = Aluno.getAlunosPorId(Long.valueOf(aluno.getIdPessoa()));
			System.out.println(model);
		}
		
		model.setNome(aluno.getNome());
		model.setEndereco(aluno.getEndereco());
		model.setCelular(aluno.getCelular());
		model.setTelefone(aluno.getTelefone());
		model.setDistrito(aluno.getDistrito());
		model.setCidade(aluno.getCidade());
		model.setCodigoPostal(aluno.getCodigoPostal());
		model.setComplemento(aluno.getComplemento());
		
		
		try{
			Aluno.persist(model);
			aluno.setIdPessoa(String.valueOf(model.getIdPessoa()));
		}
		catch (DataBaseException e) {
			e.printStackTrace();
		}
		
		return aluno;
	}

	@Override
	public List<AlunoVO> lista() {
		List<AlunoVO> list = new ArrayList<AlunoVO>();
		Iterator<Aluno> it = Aluno.getAlunos();
		while(it.hasNext()){
			
			Aluno aluno = it.next();
			AlunoVO model = new AlunoVO();
			
			model.setIdPessoa(String.valueOf(aluno.getIdPessoa()));
			model.setNome(aluno.getNome());
			model.setEndereco(aluno.getEndereco());
			//model.setCelular(aluno.getCelular());//TODO Verificar pq de não achar a propiedade ao carregar
			model.setTelefone(aluno.getTelefone());
			model.setDistrito(aluno.getDistrito());
			model.setCidade(aluno.getCidade());
			model.setCodigoPostal(aluno.getCodigoPostal());
			model.setComplemento(aluno.getComplemento());
			
			list.add(model);
		}
		return list;
	}


	@Override
	public void excluir(Set<AlunoVO> alunos) {
		for(AlunoVO aluno : alunos){
			Aluno model = Aluno.getAlunosPorId(Long.valueOf(aluno.getIdPessoa()));
			if(model!=null){
				Aluno.delete(model);
			}
		}
		
	}
	/*
	@Override
	public String gerarRelatorio(List<AlunoVO> lista) {
		
		String caminho = "";
		try{
			JasperReport report = JasperCompileManager
					.compileReport("/reports/relatorio-alunos.jrxml");
			
			JasperPrint print = JasperFillManager.fillReport(report, null,
					new JRBeanCollectionDataSource(lista));

			caminho = "/reports/relatorio-aluno.pdf";
			JasperExportManager.exportReportToPdfFile(print,"/RelatorioAlunos.pdf");
		}
		catch(JRException e){
			throw new RuntimeException("ERRO REL " + e);
		}
		return caminho;
		return null;
	}
	*/

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	
}
