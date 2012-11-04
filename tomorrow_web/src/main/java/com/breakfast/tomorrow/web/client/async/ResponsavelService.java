package com.breakfast.tomorrow.web.client.async;

import java.util.Collection;
import java.util.Set;
import com.breakfast.tomorrow.core.academic.vo.Responsavel;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;


@RemoteServiceRelativePath("responsavelService")
public interface ResponsavelService extends RemoteService {

	Responsavel persistir(Responsavel responsavel);
	void excluir(Set<Responsavel> responsavel);
	String gerarRelatorio(Collection<Responsavel> lista);
	Collection<Responsavel> lista();

	
	

}
