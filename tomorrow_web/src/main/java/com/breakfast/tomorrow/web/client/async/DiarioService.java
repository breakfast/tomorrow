package com.breakfast.tomorrow.web.client.async;

import java.util.Collection;

import com.breakfast.tomorrow.core.academic.vo.Diario;
import com.breakfast.tomorrow.core.academic.vo.Disciplina;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("diarioService")
public interface DiarioService extends RemoteService {
	
	Collection<Diario> getDiarios();
	Collection<Disciplina> getDisciplinas();
	Diario persistir(Diario diario);

}
